//
// Created by spl211 on 11/01/2021.
//

#ifndef INPUTHANDLER_H
#define INPUTHANDLER_H
#include "../include/connectionHandler.h"

using namespace std;
class InputHandler {
public:
    InputHandler(ConnectionHandler &conn);

    void shortToBytes(short num, char *bytesArr);

    void userPass(short op, string &var1, string &var2);

    void studentStat(short op, string &var1);

    void coursePass(short op, string &var1);

    void run();

private:
    ConnectionHandler &connectionHandler;
};


#endif //INPUTHANDLER_H
