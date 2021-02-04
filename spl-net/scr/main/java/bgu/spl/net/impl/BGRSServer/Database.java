package bgu.spl.net.impl.BGRSServer;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
    private static Database instance=null;
    private ConcurrentHashMap<String, String> Admins;
    private ConcurrentHashMap<String , String> Students;
    private ConcurrentHashMap<Integer ,String> Courses;
    private ConcurrentHashMap<Integer, List<String> > StudentsInCourses;
    private ConcurrentHashMap<String , List<Integer> > CoursesOfStudent;
    private ConcurrentHashMap<Integer , Integer> availableSeatsInCourse;
    private ConcurrentHashMap<Integer , Integer> maxSeatsInCourse;
    private ConcurrentHashMap<Integer, List<Integer>> KdamCourses;
    private ConcurrentHashMap<String , List<Integer>> studentKdamCourse;

    //to prevent user from creating new Database
    private Database() {

        Admins=new ConcurrentHashMap<>();
        Students=new ConcurrentHashMap<>();
        Courses=new ConcurrentHashMap<>();
        StudentsInCourses=new ConcurrentHashMap<>();
        CoursesOfStudent=new ConcurrentHashMap<>();
        availableSeatsInCourse=new ConcurrentHashMap<>();
        maxSeatsInCourse=new ConcurrentHashMap<>();
        KdamCourses=new ConcurrentHashMap<>();
        studentKdamCourse=new ConcurrentHashMap<>();
        initialize("Courses.txt");
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        if(instance==null)
            instance=new Database();
        return instance;

    }

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    public boolean initialize(String coursesFilePath) {
        try {
            BufferedReader reader =new BufferedReader(new FileReader(coursesFilePath));
            String line=reader.readLine();
            while(line!=null&& !line.isEmpty())
            {
                String[] cour=line.split("\\|");
                Integer Num= Integer.parseInt(cour[0]);
                String Name= cour[1];
                String[] Kdams=cour[2].substring(1, cour[2].length()-1).split(",");
                Integer max=Integer.parseInt(cour[3]);
                Courses.put(Num,Name);
                maxSeatsInCourse.put(Num,max);
                StudentsInCourses.put(Num,new LinkedList<>());
                KdamCourses.put(Num,new LinkedList<>());
                if(Kdams.length>1) {
                    for (String kda : Kdams)
                        KdamCourses.get(Num).add(Integer.parseInt(kda));
                }
                availableSeatsInCourse.put(Num,max);
                line= reader.readLine();

            }
            return true;
        }
        catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean admin_Username_Password(String userName , String password) {
        return Admins.get(userName).equals(password);
    }

    public boolean students_Username_Password(String userName , String password) {
        return Students.get(userName).equals(password);
    }

    public boolean isAdminExsist(String userName){
        return Admins.get(userName) != null;
    }

    public boolean isStudentExsist(String userName){
        return Students.get(userName) != null;
    }

    public void registerAdmin(String userName , String password){ Admins.put(userName , password); }

    public void registerStudent(String userName , String password){

        studentKdamCourse.put(userName , new LinkedList<>());
        CoursesOfStudent.put(userName , new LinkedList<>());
        Students.put(userName , password);

    }

    public boolean exsistingCours(int numCourse){ return Courses.get(numCourse) != null; }

    public boolean availableSeats(int numCourse) { return availableSeatsInCourse.get(numCourse) > 0; }
    public boolean courseTaken(String student, Integer course) { return studentKdamCourse.get(student).contains(course);}

    public boolean allKdamCourse(String username , int courseNum) {

        for (Integer course : KdamCourses.get(courseNum)) {

            if (!studentKdamCourse.get(username).contains(course)) return false;
        }
        return true;
    }
    public void registerCourse(int courseNum , String student){
        StudentsInCourses.get(courseNum).add(student);
        CoursesOfStudent.get(student).add(courseNum);
        int newVal=availableSeatsInCourse.get(courseNum) - 1;
        availableSeatsInCourse.remove(courseNum );
        availableSeatsInCourse.put(courseNum,newVal);
        studentKdamCourse.get(student).add(courseNum);
    }

    public List<Integer> getKDAMCourse(int courseNum) { return KdamCourses.get(courseNum); }

    public String getCourseName(int courseNum) { return Courses.get(courseNum); }

    public int getAvailableSeats(int courseNum){ return availableSeatsInCourse.get(courseNum); }

    public int getMaxSeats(int courseNum) { return maxSeatsInCourse.get(courseNum); }

    public List<String> getStuentsInCourse(int courseNum){
        java.util.Collections.sort(StudentsInCourses.get(courseNum));
        return StudentsInCourses.get(courseNum);
    }

    public List<Integer> getCoursesOfStudent(String student){
        return CoursesOfStudent.get(student);
    }

    public boolean isStudentRegisterToCourse(String student , int courseNum) { return StudentsInCourses.get(courseNum).contains(student); }

    public void unregisterStudentFromCourse(String student , int courseNum){
        if(StudentsInCourses.get(courseNum).contains(student)){
            int index= StudentsInCourses.get(courseNum).indexOf(student);
            StudentsInCourses.get(courseNum).remove(index);
        }
        if(CoursesOfStudent.get(student).contains(courseNum)) {
            int index=CoursesOfStudent.get(student).indexOf(courseNum);
            CoursesOfStudent.get(student).remove(index);
        }
        int num=availableSeatsInCourse.get(courseNum) + 1;
        availableSeatsInCourse.remove(courseNum);
        availableSeatsInCourse.put(courseNum,num);
        if(studentKdamCourse.get(student).contains(courseNum)) {
            int index= studentKdamCourse.get(student).indexOf(courseNum);
            studentKdamCourse.get(student).remove(index);
        }
    }

}