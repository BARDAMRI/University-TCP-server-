
#include "../include/Communicator.h"
#include <iostream>
using namespace std;
Communicator::Communicator(ConnectionHandler& conn):connectionHandler(conn),commandOp(-1),opCode(-1),_toTerminate(false){}
bool Communicator::toTerminate(){return _toTerminate;}
int Communicator::IntFromString(string t)
{
    stringstream s(t);
    int in=0;
    s>>in;
    return in;

}
short Communicator::bytesToShort(char* bytes) {
    short result = (short) ((bytes[0] & 0xff) << 8);
    result += (short) (bytes[1] & 0xff);
    return result;
}
//The main function of the class that read from the socket of the server connection
bool Communicator::run(string& basicString) {
    bool done;
    char opCode[2];
    done = connectionHandler.getBytes(opCode, 2); //receiving answer opcode from socket
    short opAns = bytesToShort(opCode); //transporting to short value
    if (opAns == 12) { //case of ack message

        char AskopCode[2];
        done = connectionHandler.getBytes(AskopCode, 2); //receiving question opcode from socket
        short opAsk = bytesToShort(AskopCode); //transporting to short value
        basicString += "ACK " + to_string(opAsk);

        if (opAsk == 4) {
            connectionHandler.Terminate(); //sign for the client to stop
        }

        //in case of additional data as lists and general data we look for another line from the server
        if (opAsk == 6 | opAsk == 7 | opAsk == 8 | opAsk == 9 | opAsk == 11) {
            string additionalData;
            done = connectionHandler.getLine(additionalData);
            basicString += "\n" + additionalData;
        }
    } else if (opAns == 13) { //case of error opcode
        char AskopCode[2];
        done = connectionHandler.getBytes(AskopCode, 2); //receiving question opcode from socket
        short opAsk = bytesToShort(AskopCode); //transporting to short value
        basicString += "ERROR " + to_string(opAsk);


    }
    return done;
}
