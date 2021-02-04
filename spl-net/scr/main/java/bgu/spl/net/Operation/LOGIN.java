package bgu.spl.net.Operation;

import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.Message;

public class LOGIN {
    private String userName;
    private String password;
    private final Message message;
    private String toReturn;
    private Database database;

    public LOGIN(Message message) {
        database = Database.getInstance();
        this.message = message;
        userName = message.VAR1();
        password = message.VAR2();
        toReturn = act();
    }

    private String act() {

        boolean admin;
        if (!database.isAdminExsist(userName) & !database.isStudentExsist(userName)) return "E03";
        if (database.isAdminExsist(userName))
            admin = true;
        else admin = false;
        if (admin) {
            if (!database.admin_Username_Password(userName, password)) return "E03";
        } else if (!database.students_Username_Password(userName, password)) return "E03";
        return "03";
    }

    public String toRetuen() {
        return toReturn;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
