
package _scoreManage;
import java.util.*;

//����ѧ����Ϣ ����ѧ�š��������Ա�רҵ��������ҵ�ɼ���С����ҵ�ɼ������ڳɼ��Լ��ܷ֡�
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

//ѧ���ɼ�����������������ʵ�֡�
public class ScoreManage {
	ScoreManage(){number=new int[5];highest=0;lowest=100;}
	
	Map<Integer,StudentInfo> StudentMap=new HashMap<Integer,StudentInfo>();
	int[] number;
	int highest;
	int lowest;
	int average;
	int sum;
	
//���ѧ�����ݡ�
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
	
	//ɾ��ѧ�����ݡ�
	public void deleteStudent(int ID){
		StudentMap.remove(ID);
		System.out.println("Delete Successful!");
	}
	//����ѧ�Ų�����Ӧѧ�������ݡ�
	public void searchStudent(int ID){//��ʽ������ѧ����Ϣ��
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
	
	//ͳ��������Ϣ������ƽ���֡���߷֡���ͷ��Լ�������������
	public void searchInfo(){
		System.out.println("The average Score is: "+average);			//ƽ���ɼ�
		System.out.println("The highest Score is: "+highest);			//��߷�
		System.out.println("The lowest Score is:  "+lowest);			//��ͷ�
		System.out.print("The number between 90-100: "+number[0]+"\n"+	//90��100����������
						 "The number between 80-89 : "+number[1]+"\n"+	//80��89  ����������
						 "The number between 70-79 : "+number[2]+"\n"+	//70��79  ����������
						 "The number between 60-69 : "+number[3]+"\n"+	//60��69  ����������
						 "The number between  0-59 : "+number[4]+"\n");	//0  ��59  ����������
	}
	
	
	public static void main(String[] agrs){//����������￪ʼ��
		String[] $name={"Tom","Henry","Harry","Monster","Vincent","Emily","Andy","James","Sheldon","Penny"};
		Random rand=new Random(47);
		ScoreManage student=new ScoreManage();
		for (int i=0;i<10;i++){ //�������10��ѧ����Ϣ��
		student.addStudent(i, $name[i],rand.nextInt(2)==0?"MALE":"FEMAL", 
						   "Computer science",rand.nextInt(46),rand.nextInt(51),rand.nextInt(6));
		}
		Scanner stdin=new Scanner(System.in);
		int choice=0;
		do{
			System.out.println();
		    System.out.print("1.���һ��ѧ���ɼ���¼;\n"+ 
		    				 "2.ɾ��һ��ѧ���ɼ���¼;\n"+ 
		    				 "3.��ѯѧ���ɼ���Ϣ;\n"+ 
		    				 "4.��ѯƽ���֣���߷ֺ���ͷ��Լ���������������\n"+ 
		    				 "0.�˳�����.\n" + 
		    				 "����:"); 
		    choice=stdin.nextInt();
		    switch(choice){
		    case 1:
		    	Scanner input=new Scanner(System.in);
		        System.out.println("����ѧ����Ϣ��ѧ�� ���� �Ա� רҵ  ������ҵ�ɼ� С����ҵ�ɼ� ���ڷ֣�"+"\n"+
		        				   "�Կո����,���и�����ҵ�ɼ���Χ0~45,С����ҵ�ɼ���Χ0~50,���ڷ�0~5 ��\n");
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
		    	System.out.print("����ѧ��ѧ��: ");
		    	int id=input1.nextInt();
		    	student.deleteStudent(id);
		    	break;
		    case 3:
		    	Scanner input2=new Scanner(System.in);
		    	System.out.print("����ѧ��ѧ��: ");
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
