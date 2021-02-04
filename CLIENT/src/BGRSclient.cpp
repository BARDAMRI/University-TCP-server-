#include "../include/connectionHandler.h"
#include <iostream>
#include <thread>
#include "../include/InputHandler.h"
#include "../include/Communicator.h"


/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
using namespace std;
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);
    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    //Making the keyboard reader and the socket communicator
    InputHandler inputHandler(connectionHandler);
    Communicator communicator(connectionHandler);
    //the reader will run from new thread and the communicator from the main. 2 thread in general as asked
    thread reader(&InputHandler::run,&inputHandler);
    while(1){

        std::string print;
        if(!communicator.run(print)){
            std::cout << "Connection Error ..."<<std::endl;
            break;
        }
        //prints the results from the socket
        std::cout << print <<std::endl;
        //if logout we terminate the main
        if(print=="ACK 4"){ break;}
    }
    connectionHandler.close();
    reader.join();
    return 0;
}
