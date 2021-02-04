package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Server;

public class ReactorMain {

    public static void main(String[] args){

        int port=Integer.parseInt(args[0]);
        int numOfThreads=Integer.parseInt(args[1]);
        Server.reactor(numOfThreads,port, MessagingProtocol::new, MessageEncoderDecoder::new).serve();

    }
}
