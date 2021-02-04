package bgu.spl.net.Operation;

import bgu.spl.net.impl.BGRSServer.Database;
public class STUDENTSTAT {

    private String student;
    private String toReturn;
    private Database database;
    private String Name;

    public STUDENTSTAT(String student, String name) {
        database = Database.getInstance();
        this.student = student;
        this.Name = name;
        toReturn = act();
    }

    private String act() {

        if (!database.isAdminExsist(Name)) return "E08";
        if (!database.isStudentExsist(student)) return "E08";
        else {
            String stus = "[";
            for (Integer name : database.getCoursesOfStudent(student)) {
                if (stus.length() > 1)
                    stus += ",";
                stus += name;

            }
            stus += "]";
            return "08Student: " + student + "\n" + "Courses:" + stus;
        }
    }

    public String toReturn() {
        return toReturn;
    }
}
