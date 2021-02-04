package bgu.spl.net.Operation;

import bgu.spl.net.impl.BGRSServer.Database;

public class UNREGISTER {
    private final int courseNum;
    private final String student;
    private String toReturn;
    private Database database;

    public UNREGISTER(String courseNum, String student) {
        database = Database.getInstance();
        this.courseNum = Integer.parseInt(courseNum);
        this.student = student;
        toReturn = act();
    }

    private String act() {

        if (!database.isStudentExsist(student)) return "E10";
        if (!database.exsistingCours(courseNum)) return "E10";
        if (!database.isStudentRegisterToCourse(student, courseNum)) return "E10";
        database.unregisterStudentFromCourse(student, courseNum);
        return "10";
    }

    public String toReturn() {
        return toReturn;
    }
}
