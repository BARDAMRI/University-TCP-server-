package bgu.spl.net.Operation;

import bgu.spl.net.impl.BGRSServer.Database;
import java.util.List;

public class MYCOURSES {
    private String student;
    private String toReturn;
    private Database database;

    public MYCOURSES(String student) {
        database = Database.getInstance();
        this.student = student;
        toReturn = act();
    }

    private String act() {

        if (!database.isStudentExsist(student)) return "E11";
        List<Integer> courses = database.getCoursesOfStudent(student);
        toReturn = "11[";
        for (Integer course : courses) {
            if (toReturn.length() > 3)
                toReturn += ",";
            toReturn += course;
        }
        toReturn += "]";
        return toReturn;
    }

    public String toReturn() {
        return toReturn;
    }

}
