//
// Created by barda on 09/01/2021.
//

#ifndef COMMUNICATOR__
#define COMMUNICATOR__
#include "../include/connectionHandler.h"
using namespace std;

class Communicator{
public:
    Communicator(ConnectionHandler& conn);
    bool toTerminate();
    int IntFromString(string str);
    short bytesToShort(char* bytes);
    bool run(string & basicString);

private:
    ConnectionHandler &connectionHandler;
    string message;
    bool _toTerminate;
    int commandOp;
    int opCode;
};

#endif //COMMUNICATOR__
