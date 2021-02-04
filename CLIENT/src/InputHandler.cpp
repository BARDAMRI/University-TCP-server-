//
// Created by spl211 on 11/01/2021.
//
#include <iostream>
#include "boost/lexical_cast.hpp"
#include <boost/algorithm/string.hpp>
#include "InputHandler.h"
using namespace std;

InputHandler::InputHandler(ConnectionHandler &connectionHandler) : connectionHandler(connectionHandler) {}
void InputHandler::shortToBytes(short num, char* bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}

void InputHandler::run() {

    //The loop will run permanently until logout which will cause a break call;.
    while (1) {
        //read a line from the keyboard.
        const short bufsize = 1024;
        char buf[bufsize];
        cin.getline(buf, bufsize);
        string line(buf);
        vector<string> params;
        //split the line to values which will be assigned lately to string cells.
        boost::split(params, line, boost::is_any_of(" "));
        string UserName;
        string Pass;
        string CourseNumber;
        short opCode;
        char OpChar[2];
        string command = params[0]; //represent the value of the command we sent.

        //here I check the command and send it to the suitable functions
        if (command == "ADMINREG") {
            opCode = 1;
            UserName = params[1];
            Pass = params[2];
            userPass(opCode, UserName, Pass);
        } else if (command == "STUDENTREG") {
            opCode = 2;
            UserName = params[1];
            Pass = params[2];
            userPass(opCode, UserName, Pass);
        } else if (command == "LOGIN") {
            opCode = 3;
            UserName = params[1];
            Pass = params[2];
            userPass(opCode, UserName, Pass);
        } else if (command == "LOGOUT") {
            opCode = 4;
            shortToBytes(opCode, OpChar);
            connectionHandler.sendBytes(OpChar, 2);
            while (!connectionHandler.toTerminate()) {}

        } else if (command == "COURSEREG") {
            opCode = 5;
            CourseNumber = params[1];
            coursePass(opCode, CourseNumber);
        } else if (command == "KDAMCHECK") {
            opCode = 6;
            CourseNumber = params[1];
            coursePass(opCode, CourseNumber);
        } else if (command == "COURSESTAT") {
            opCode = 7;
            CourseNumber = params[1];
            coursePass(opCode, CourseNumber);
        } else if (command == "STUDENTSTAT") {
            opCode = 8;
            UserName = params[1];
            studentStat(opCode, UserName);
        } else if (command == "ISREGISTERED") {
            opCode = 9;
            CourseNumber = params[1];
            coursePass(opCode, CourseNumber);
        } else if (command == "UNREGISTER") {
            opCode = 10;
            CourseNumber = params[1];
            coursePass(opCode, CourseNumber);
        } else if (command == "MYCOURSES") {
            opCode = 11;
            shortToBytes(opCode, OpChar);
            connectionHandler.sendBytes(OpChar, 2);
        }
        //check in additional field if logout command finished successfully.
        if (connectionHandler.toTerminate())
            break;
    }
}

//The function for every command with two variables that sent as lines
void InputHandler::userPass(short op, string &var1, string &var2) {
    char arr[2];
    shortToBytes(op, arr);
    connectionHandler.sendBytes(arr, 2);
    connectionHandler.sendLine(var1);
    connectionHandler.sendLine(var2);
}

//The function for every command of 2 short numbers as op code and course number
void InputHandler::coursePass(short op, string &var1) {
    char course[2];
    auto shortNum = boost::lexical_cast<short>(var1);
    char arr[2];
    shortToBytes(op, arr);
    shortToBytes(shortNum, course);
    connectionHandler.sendBytes(arr, 2);
    connectionHandler.sendBytes(course, 2);
}

//The function for Student status with one variable that sent as a line
void InputHandler::studentStat(short op,string& var1) {
    char arr[2];
    shortToBytes(op, arr);
    connectionHandler.sendBytes(arr, 2);
    connectionHandler.sendLine(var1);
}
