package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Server;

public class TPCMain {

    public static void main(String[] args){

        int port=Integer.parseInt(args[0]);
        Server.threadPerClient(port, MessagingProtocol::new, MessageEncoderDecoder::new).serve();
    }

}
