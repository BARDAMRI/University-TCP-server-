package bgu.spl.net.Operation;

import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;

public class ADMINREG {
    private String userName;
    private String password;
    private final Message message;
    private String toReturn;
    Database database;

    public ADMINREG(Message message) {
        database = Database.getInstance();
        this.message = message;
        userName = message.VAR1();
        password = message.VAR2();
        toReturn = act();
    }


    private String act() {
        if (database.isAdminExsist(userName)) return "E01";
        if (database.isStudentExsist(userName)) return "E01";
        else {
            database.registerAdmin(userName, password);
            return "01";
        }
    }

    public String toRetuen() {
        return toReturn;
    }
}
