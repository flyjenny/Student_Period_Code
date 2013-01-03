/*
 * Created on 2010-7-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package _employee;

import java.util.*;
/**
 * @author BennyChan
 * Copyright ® 2010 Shanghai
 * 
 * 
 */
public class Employee {
	Employee(int id,String name,char gender,int age){
		$id=id;  		
		$name=name; 	
		$gender=gender;
		$age=age;	  
		$flag=false; 
	}
	private int    $id;		 //ID of employee.
	private String $name;	//Name of employee.
	private	char   $gender; //Gender of employee.
	private	int    $age;	//AGE of employee.
	private boolean $flag;  //Flag for whether matches output demand.
	public int Getid(){
		return $id;
	}
	public String Getname(){
		return $name;
	}
	public char Getgender(){
		return $gender;
	}
	public int Getage(){
		return $age;
	}
	
	//Judge What are the infomation of employees for output.
	public int OutputInfo(String input1){
		int count=0;
		if (input1.indexOf("$ID")!=-1) count+=1;  //if comand include $ID,plus 1.
		if (input1.indexOf("$NAME")!=-1) count+=2;//if comand include $ID,plus 2.
		if (input1.indexOf("$GENDER")!=-1)count+=4;//if comand include $ID,plus 4.
		if (input1.indexOf("$AGE")!=-1) count+=8;//if comand include $ID,plus 8.
		return count;
	}
	
	//Compare whether the employee matches conditon.
	public boolean Comparable(String s,Employee employees){
		//Scanner scanner=new Scanner(s);
		//String[] Array=new String[10];
		int j=0;
		int Number;
		String temp;
		String ConditionName;	//The name separated from your command.
		char ConditionGender;	//The Gender separated from your command.
		boolean radar1=false;   //The  temporary variable for storage.
		s=s.trim();				//Cut off the spaces in String.
		char index=s.charAt(1); //Get the second char in input.
		
		switch(index){
			case 'I':    //ID info
				while((s.charAt(j)>='0'&&s.charAt(j)<='9')==false)
					j++;
				temp=s.substring(3);
				if ((j-3)==2){  //Maybe <= or >=
					if (temp.charAt(0)=='>'){
						temp=temp.substring(2);				//Cut off >=.keep the left.
						Scanner scanner1=new Scanner(temp);
						Number=scanner1.nextInt();
						radar1=employees.Getid()>=Number;						
					}
					else{
						temp=temp.substring(2);				//Cut off <=.keep the left.
						Scanner scanner1=new Scanner(temp);
						Number=scanner1.nextInt();			//Get the next Int.
						radar1=employees.Getid()<=Number;
					}
				}
				else if(temp.charAt(0)=='>'){
					temp=temp.substring(1);					//Cut off >.keep the left.
					Scanner scanner1=new Scanner(temp);
					Number=scanner1.nextInt();				//Get the next Int.
					radar1=employees.Getid()>Number;
				}
				else if (temp.charAt(0)=='<'){
					temp=temp.substring(1);					//Cut off <.keep the left.
					Scanner scanner1=new Scanner(temp);
					Number=scanner1.nextInt();				//Get the next Int.
					radar1=employees.Getid()<Number;
				}
				else if (temp.charAt(0)=='='){
					temp=temp.substring(1);					//Cut off =.keep the left.
					Scanner scanner1=new Scanner(temp);
					Number=scanner1.nextInt();				//Get the next Int.
					radar1=employees.Getid()==Number;
				}
						
				break;
			case 'A':   //AGE info
				while((s.charAt(j)>='0'&&s.charAt(j)<='9')==false)
					j++;
				temp=s.substring(4);
				if ((j-4)==2){
					if (temp.charAt(0)=='>'){
						temp=temp.substring(2);
						Scanner scanner1=new Scanner(temp);
						Number=scanner1.nextInt();
						radar1=employees.Getage()>=Number;						
					}
					else{
						temp=temp.substring(2);
						Scanner scanner1=new Scanner(temp);
						Number=scanner1.nextInt();
						radar1=employees.Getage()<=Number;
					}
				}
				else if(temp.charAt(0)=='>'){
					temp=temp.substring(1);
					Scanner scanner1=new Scanner(temp);
					Number=scanner1.nextInt();
					radar1=employees.Getage()>Number;
				}
				else if (temp.charAt(0)=='<'){
					temp=temp.substring(1);
					Scanner scanner1=new Scanner(temp);
					Number=scanner1.nextInt();
					radar1=employees.Getage()<Number;
				}
				else if (temp.charAt(0)=='='){
					temp=temp.substring(1);
					Scanner scanner1=new Scanner(temp);
					Number=scanner1.nextInt();
					radar1=employees.Getage()==Number;
				}
				break;		
			case 'N':   //NAME info
				temp=s.substring(6);
				Scanner scanner1=new Scanner(temp);
				ConditionName=scanner1.next();
				radar1=employees.Getname()==ConditionName;
			case 'G':	//GENDER info
				temp=s.substring(8);	
				Scanner scanner11=new Scanner(temp);
				ConditionGender=scanner11.nextLine().charAt(0);
				radar1=(employees.Getgender()==ConditionGender);
				break;
			default: break;
			
		}
		return radar1;
	}
	
		//Print the info of employees which matches users command in a standar format.
		public void Print(Employee employees,int count){
			switch(count){
				case 0:
					System.out.println("No element you choose for print!");
					break;
				case 1:
					System.out.format("%5d\n",employees.Getid());
					break;
				case 2:
					System.out.format("%-15s\n",employees.Getname());
					break;
				case 3:
					System.out.format("%5d %-15\n",employees.Getid(),employees.Getname());
					break;
				case 4:
					System.out.format("%3c\n",employees.Getgender());
					break;
				case 5:
					System.out.format("%5d %3c\n",employees.Getid(),employees.Getgender());
					break;
				case 6:
					System.out.format("%-15s %3c\n",employees.Getname(),employees.Getgender());
					break;
				case 7:
					System.out.format("%5d %-15 %3c\n", employees.Getid(),employees.Getname(),+employees.Getgender());
					break;
				case 8:
					System.out.format("%3d\n",employees.Getage());
					break;
				case 9:
					System.out.format("%5d %3d\n",employees.Getid(),employees.Getage());
					break;
				case 10:
					System.out.format("%-15s %3d\n",employees.Getname(),employees.Getage());
					break;
				case 11:
					System.out.format("%5d %-15s %3d\n",employees.Getid(),employees.Getname(),employees.Getage());
					break;
				case 12:
					System.out.format("%3c %3d\n",employees.Getgender(),employees.Getage());
					break;
				case 13:
					System.out.format("%5d %3c %3d\n",employees.Getid(),employees.Getgender(),employees.Getage());
					break;
				case 14:
					System.out.format("%-15s %3c %3d\n",employees.Getname(),employees.Getgender(),employees.Getage());
					break;
				case 15:
					System.out.format("%5d %-15s %3c %3d\n",employees.Getid(),employees.Getname(),employees.Getgender(),employees.Getage());
					break;
				default : break;			
			}
		}
		
		
	public static void main(String[] args){
		String[] Numlist={"Vincent","Emily","Judy",
							"Xena","Xochitl","Wendy",
							"Laney","Andy","Jonny",
							"Allen","Jeff","James",};	//NAME list 
		Employee[] employees=new Employee[21];
		Random rand=new Random();
		for (int i=1;i<=20;i++)	//Construct 20 employees for random.
			employees[i]=new Employee(i,Numlist[rand.nextInt(12)],(rand.nextInt(2)==0?'M':'F'),(18+rand.nextInt(43)));
		System.out.print("Please enter your command: ");
		Scanner command= new Scanner(System.in);
		command.next();
		String input=command.nextLine();
		String input1=input.trim();
		int Output1=employees[1].OutputInfo(input1);
		int location=input.indexOf("WHERE");	//Find out teh location of WHERE
		if (location==-1){
			System.out.println("The employees match your Conditions are:");
			for(int h=1;h<=20;h++)
				employees[h].Print(employees[h], Output1);
		}
		else{
		
		String ForPrint=input.substring(1, location-1);
	
		String ForJudge=input.substring(location+6);
		int Output=employees[1].OutputInfo(ForPrint);
		
		
		
		for (int j=1;j<=20;j++){
			boolean r1=employees[j].Comparable(ForJudge,employees[j]);
			int count=ForJudge.indexOf(" ");
			String p=ForJudge.substring(count+1);
			Scanner t=new Scanner(p);
			String Judge=t.next();
			while(Judge.equals("AND")){			//AND Operation.
				String q=t.nextLine();
				q=q.trim();
				boolean r2=employees[j].Comparable(q, employees[j]);
				r1=r1&&r2;
				int count1=q.indexOf(" ");
				q=q.substring(count1+1);
				t=new Scanner(q);
				Judge=t.next();
			}
			while(Judge.equals("OR")){			//OR Operation.
				String q=t.nextLine();
				q=q.trim();
				boolean r4=employees[j].Comparable(q, employees[j]);
				r1=r1||r4;
				int count1=q.indexOf(" ");
				q=q.substring(count1+1);
				t=new Scanner(q);
				Judge=t.next();
			}
		
			employees[j].$flag=r1;
		}
		System.out.println("The employees match your Conditions are:");
		System.out.println();
		
		//IF the employees match condition.Print info.
		for(int h=1;h<=20;h++){
			if (employees[h].$flag)
			employees[h].Print(employees[h], Output);
		
		}
	}
	}
}

