package bgu.spl.net.Operation;

import bgu.spl.net.impl.BGRSServer.Database;

public class COURSEREG {
    private final String username;
    private final int courseNum;
    private final String toReturn;
    private Database database;

    public COURSEREG(String courseNum, String username) {
        database = Database.getInstance();
        this.username = username;
        this.courseNum = Integer.parseInt(courseNum);
        toReturn = act();
    }

    public String act() {
        if (!database.isStudentExsist(username)) return "E05";
        else if (!database.exsistingCours(courseNum)) return "E05";
        else if (!database.availableSeats(courseNum)) return "E05";
        else if (!database.allKdamCourse(username, courseNum)) return "E05";
        else if (database.courseTaken(username, courseNum)) return "E05";
        else {
            database.registerCourse(courseNum, username);
            return "05";
        }
    }

    public String toReturn() {
        return toReturn;
    }

}
