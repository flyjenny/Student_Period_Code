
package _scoreManage;
import java.util.*;

//储存学生信息 包括学号、姓名、性别、专业、个人作业成绩、小组作业成绩、出勤成绩以及总分。
class StudentInfo{
	StudentInfo(String name,String gender,String specialty,
				int individualScore,int teamScore,int attendanceScore){
		$name=name;
		$gender=gender;
		$specialty=specialty;
		$individualScore=individualScore;
		$teamScore=teamScore;
		$attendanceScore=attendanceScore;
		$totalScore=individualScore+teamScore+attendanceScore;
	}
	String $name;
	String $gender;
	String $specialty;
	int    $individualScore;
	int    $teamScore;
	int    $attendanceScore;
	int	   $totalScore;
}

//学生成绩管理，包括具体各项功能实现。
public class ScoreManage {
	ScoreManage(){number=new int[5];highest=0;lowest=100;}
	
	Map<Integer,StudentInfo> StudentMap=new HashMap<Integer,StudentInfo>();
	int[] number;
	int highest;
	int lowest;
	int average;
	int sum;
	
//添加学生数据。
	public void addStudent(int ID,String name,String gender,String specialty,
						 int individualScore,int teamScore,int attendanceScore){
		StudentInfo stu=new StudentInfo(name,gender,specialty,individualScore,teamScore,attendanceScore);
		StudentMap.put(ID, stu);
		int total=StudentMap.get(ID).$totalScore;
		sum=sum+total;
		int size=StudentMap.size();
		average=sum/size;
		if (total>highest)		
			highest=total;
		if (total<lowest)
			lowest=total;
		if(total>=90&&total<=100)
			number[0]++;
		if(total>=80&&total<=89)
			number[1]++;
		if(total>=70&&total<=79)
			number[2]++;
		if(total>=60&&total<=69)
			number[3]++;
		if(total>=0&&total<=59)
			number[4]++;
	}
	
	//删除学生数据。
	public void deleteStudent(int ID){
		StudentMap.remove(ID);
		System.out.println("Delete Successful!");
	}
	//根据学号查找相应学生的数据。
	public void searchStudent(int ID){//格式化输入学生信息。
		System.out.format("%-5s %-15s %-8s %-16s %13s %13s %8s %5s\n",
						  "ID","NAME","GENDER","SPECIALTY","INDIVIDUAL","TEAM","ATTENDANCE","TOTAL");
		System.out.format("%-5d %-15s %-8s %-16s %13d %13d %8d %5d\n",
							ID,
							StudentMap.get(ID).$name,
							StudentMap.get(ID).$gender,
							StudentMap.get(ID).$specialty,
							StudentMap.get(ID).$individualScore,
							StudentMap.get(ID).$teamScore,
							StudentMap.get(ID).$attendanceScore,
							StudentMap.get(ID).$individualScore+
							StudentMap.get(ID).$teamScore+
							StudentMap.get(ID).$attendanceScore);
	
	}
	
	//统计整个信息，包括平均分、最高分、最低分以及分数段人数。
	public void searchInfo(){
		System.out.println("The average Score is: "+average);			//平均成绩
		System.out.println("The highest Score is: "+highest);			//最高分
		System.out.println("The lowest Score is:  "+lowest);			//最低分
		System.out.print("The number between 90-100: "+number[0]+"\n"+	//90到100分数段人数
						 "The number between 80-89 : "+number[1]+"\n"+	//80到89  分数段人数
						 "The number between 70-79 : "+number[2]+"\n"+	//70到79  分数段人数
						 "The number between 60-69 : "+number[3]+"\n"+	//60到69  分数段人数
						 "The number between  0-59 : "+number[4]+"\n");	//0  到59  分数段人数
	}
	
	
	public static void main(String[] agrs){//主程序从这里开始。
		String[] $name={"Tom","Henry","Harry","Monster","Vincent","Emily","Andy","James","Sheldon","Penny"};
		Random rand=new Random(47);
		ScoreManage student=new ScoreManage();
		for (int i=0;i<10;i++){ //随机创建10个学生信息。
		student.addStudent(i, $name[i],rand.nextInt(2)==0?"MALE":"FEMAL", 
						   "Computer science",rand.nextInt(46),rand.nextInt(51),rand.nextInt(6));
		}
		Scanner stdin=new Scanner(System.in);
		int choice=0;
		do{
			System.out.println();
		    System.out.print("1.添加一个学生成绩记录;\n"+ 
		    				 "2.删除一个学生成绩记录;\n"+ 
		    				 "3.查询学生成绩信息;\n"+ 
		    				 "4.查询平均分，最高分和最低分以及个分数段人数；\n"+ 
		    				 "0.退出程序.\n" + 
		    				 "输入:"); 
		    choice=stdin.nextInt();
		    switch(choice){
		    case 1:
		    	Scanner input=new Scanner(System.in);
		        System.out.println("输入学生信息（学号 姓名 性别 专业  个人作业成绩 小组作业成绩 出勤分）"+"\n"+
		        				   "以空格隔开,其中个人作业成绩范围0~45,小组作业成绩范围0~50,出勤分0~5 ：\n");
		        int ID=input.nextInt();
		        String name=input.next();
		        String gender=input.next();
		        String specialty=input.next();
		        int individualScore=input.nextInt();
		        int teamScore=input.nextInt();
		        int attendanceScore=input.nextInt();
		        
		        student.addStudent(ID, name, gender, specialty, individualScore, teamScore, attendanceScore);
		        break;
		    case 2:
		    	Scanner input1=new Scanner(System.in);
		    	System.out.print("输入学生学号: ");
		    	int id=input1.nextInt();
		    	student.deleteStudent(id);
		    	break;
		    case 3:
		    	Scanner input2=new Scanner(System.in);
		    	System.out.print("输入学生学号: ");
		    	int $ID=input2.nextInt();
		    	student.searchStudent($ID);
		    	break;
		    case 4:
		    	student.searchInfo();
		    	break;
		    case 0:
		    	System.out.println("GOOD BYE!");
		    	break;
		    default:break;
		   }
		    
		}while(choice!=0);
	}
}
