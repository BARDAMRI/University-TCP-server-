package bgu.spl.net.impl.BGRSServer;
import bgu.spl.net.Operation.*;

public class MessagingProtocol<T> implements bgu.spl.net.api.MessagingProtocol{
    private boolean response = false;
    private boolean terminate = false;
    private String message;
    private String username;
    private String password;
    private boolean loginUser;
    private Database database;

    public MessagingProtocol() {
        loginUser = false;
        database = Database.getInstance();
    }

    @Override
    public Message process(Object msg) {
        Message mess;
        //at start we check the correctness of the input.
        if (msg instanceof Message)
            mess = (Message) msg;
        else
            mess = new Message();
        //for each opCode we created a class that will handle the mission to make the code understandable
        //at start we check the pre conditions of every command and then sent to the database for continue checking and response
        if (mess.Op() == 1) {
            ADMINREG adminreg = new ADMINREG(mess);
            message = adminreg.toRetuen();
        } else if (mess.Op() == 2) {
            STUDENTREG studentreg = new STUDENTREG(mess);
            message = studentreg.toRetuen();
        } else if (mess.Op() == 3) {
            if (!loginUser) {
                LOGIN login = new LOGIN(mess);
                message = login.toRetuen();
                if (message != "E03") {
                    username = login.getUserName();
                    password = login.getPassword();
                    loginUser = true;
                }
            } else message = "E03";
        } else if (mess.Op() == 4) {
            LOGOUT logout = new LOGOUT();
            if (!loginUser)
                message = "E04";
            else {
                message = "04";
                terminate = true;
                loginUser = false;
            }
        } else if (mess.Op() == 5) {
            if (database.isStudentExsist(username) && loginUser) {
                COURSEREG coursereg = new COURSEREG(mess.VAR1(), username);
                message = coursereg.toReturn();
            } else
                message = "E05";
        } else if (mess.Op() == 6) {
            if (loginUser) {
                KDAMCHECK kdamcheck = new KDAMCHECK(mess.VAR1(), username);
                message = kdamcheck.toReturn();
            } else
                message = "E06";
        } else if (mess.Op() == 7) {
            if (loginUser) {
                COURSESTAT coursestat = new COURSESTAT(mess.VAR1(), username);
                message = coursestat.toReturn();
            } else
                message = "E07";
        } else if (mess.Op() == 8) {
            if (loginUser) {
                STUDENTSTAT studentstat = new STUDENTSTAT(mess.VAR1(), username);
                message = studentstat.toReturn();
            } else
                message = "E08";
        } else if (mess.Op() == 9) {
            if (loginUser) {
                ISREGISTERED isregistered = new ISREGISTERED(username, mess.VAR1());
                message = isregistered.toReturn();
            } else
                message = "E09";
        } else if (mess.Op() == 10) {
            if (loginUser) {
                UNREGISTER unregister = new UNREGISTER(mess.VAR1(), username);
                message = unregister.toReturn();
            } else
                message = "E10";
        } else if (mess.Op() == 11) {
            if (loginUser) {
                MYCOURSES mycourses = new MYCOURSES(username);
                message = mycourses.toReturn();
            } else
                message = "E11";
        }

        //for each command we send for response check
        return handleMessage(message);
    }

    private Message handleMessage(String message) {

        //we took E letter to recognize errors
        if (message.charAt(0) == 'E') {
            return new Message((short) 13, message.substring(1), "");
        } else {
            return new Message((short) 12, message.substring(0, 2), message.substring(2));
        }
    }

    @Override
    public boolean shouldTerminate() {
        return terminate;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
