package bgu.spl.net.Operation;

import bgu.spl.net.impl.BGRSServer.Database;

public class COURSESTAT {
    private String userName;
    private int courseNum;
    private final String toReturn;
    private Database database;

    public COURSESTAT(String courseNum, String userName) {
        database = Database.getInstance();
        this.courseNum = Integer.parseInt(courseNum);
        this.userName = userName;
        toReturn = act();
    }

    private String act() {
        if (!database.isAdminExsist(userName)) return "E07";
        else if (!database.exsistingCours(courseNum)) return "E07";
        else {
            String stus = "[";
            for (String name : database.getStuentsInCourse(courseNum)) {
                if (stus.length() > 1)
                    stus += ",";
                stus += name;

            }
            stus += "]";
            return "07"
                    + "Course: " + "(" + courseNum + ") " + database.getCourseName(courseNum) + "\n"
                    + "Seats Available: " + database.getAvailableSeats(courseNum) + "/" + database.getMaxSeats(courseNum) + "\n"
                    + "Students Registered: " + stus;
        }
    }

    public String toReturn() {
        return toReturn;
    }
}
