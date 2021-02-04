package bgu.spl.net.Operation;

import bgu.spl.net.impl.BGRSServer.Database;
import java.util.List;

public class KDAMCHECK {
    private final int courseNum;
    private String toRetuen;
    private Database database;
    private String userName;

    public KDAMCHECK(String message, String username) {
        database = Database.getInstance();
        courseNum = Integer.parseInt(message);
        userName = username;
        toRetuen = act();
    }

    private String act() {

        if (!database.isStudentExsist(userName)) return "E06";
        if (!database.exsistingCours(courseNum)) return "E06";
        List<Integer> kdam = database.getKDAMCourse(courseNum);
        toRetuen = "06[";
        for (Integer i : kdam) {
            if (toRetuen.length() > 3)
                toRetuen += ",";
            toRetuen = toRetuen + i;
        }
        toRetuen += "]";
        return toRetuen;
    }

    public String toReturn() {
        return toRetuen;
    }
}
