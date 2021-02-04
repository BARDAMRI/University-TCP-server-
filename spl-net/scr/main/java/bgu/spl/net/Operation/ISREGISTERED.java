package bgu.spl.net.Operation;

import bgu.spl.net.impl.BGRSServer.Database;

public class ISREGISTERED {
    private final String userName;
    private final int courseNum;
    private final String toReturn;
    private Database database;

    public ISREGISTERED(String userName, String courseNum) {
        database = Database.getInstance();
        this.userName = userName;
        this.courseNum = Integer.parseInt(courseNum);
        toReturn = act();
    }

    private String act() {

        if (!database.isStudentExsist(userName)) return "E09";
        if (!database.exsistingCours(courseNum)) return "E09";
        if (database.isStudentRegisterToCourse(userName, courseNum)) return "09REGISTERED";
        return "09NOT REGISTERED";
    }

    public String toReturn() {
        return toReturn;
    }
}
