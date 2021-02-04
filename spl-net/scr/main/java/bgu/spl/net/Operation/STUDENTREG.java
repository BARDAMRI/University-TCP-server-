package bgu.spl.net.Operation;

import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;

public class STUDENTREG {
    private String userName;
    private String password;
    private final Message message;
    private String toReturn;
    private Database database;

    public STUDENTREG(Message message) {
        database = Database.getInstance();
        this.message = message;
        userName = message.VAR1();
        password = message.VAR2();
        toReturn = act();
    }

    private String act() {

        if (database.isStudentExsist(userName)) return "E02";
        if (database.isAdminExsist(userName)) return "E02";
        else {
            database.registerStudent(userName, password);
            return "02";
        }
    }

    public String toRetuen() {
        return toReturn;
    }
}
