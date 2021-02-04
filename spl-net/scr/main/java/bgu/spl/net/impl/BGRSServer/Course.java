package bgu.spl.net;

public class Course {

    private int Number;
    private String Name;
    private Integer[] Kdams;
    private int Max;

    public Course(int num,String name,int max){
        Number=num;
        Name=name;
        Kdams=new Integer[max];
        Max=max;
    }
    public Course(int num,String name,Integer[] atus,int max){
        Number=num;
        Name=name;
        Kdams=atus;
        Max=max;
    }
    public int Number(){return Number;}
    public String Name(){return Name;}
    public void Number(int num){Number=num;}
    public void Name(String name){Name=name;}
    public int Max(){ return Max;}
    public void Max(int max){Max=max;}
    public Integer[] Kdams(){return Kdams;}
    public void Kdams(Integer[] arr){Kdams=arr;}
}
