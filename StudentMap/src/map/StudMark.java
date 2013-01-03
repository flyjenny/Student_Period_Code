package map;
import java.util.*;
public class StudMark {
   public static void main(String[] args){
	Map map=new HashMap();
	Student stu=new Student('n','g','m',12,34,32);
	int studentID=1;
	map.put(studentID,stu);
	System.out.print(map.get(1));
   }
}
class Student{
	char name;
	char gender;
	char major;
	int indimark;//individual mark
	int teammark;//team mark
	int attenmark;//attendance mark
	Student(char n,char g,char m,int i,int t,int a){
		name=n;gender=g;major=m;indimark=i;teammark=t;attenmark=t;
	}
}