package map;

import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.io.PrintWriter; 
import java.io.StringReader; 
import java.util.ArrayList; 
import java.util.Collection; 
import java.util.Collections; 
import java.util.HashMap; 
import java.util.Iterator; 
import java.util.StringTokenizer; 

/** 
* @author �Ž��� 
* @since 1.0 
* 
*  ѧ���ɼ�����ϵͳ 
* ͨ��ѧ�Ų���,�޸�,ɾ������ 
* 
*/ 
public class LittleProgram 
{ 
static boolean isDelete = true; 
static boolean isFind = true; 
public static void main(String [] args)//������,��������￪ʼ���� 
throws IOException,NumberNotFoundException 
{ 
int choice=-1; 
do{ 
    LittleProgram lp = new LittleProgram(); 
    System.out.println(); 
    System.out.println("\t####################################"); 
    System.out.println(); 
    System.out.println("\t\t Javaѧ���ɼ�����ϵͳ1.1"); 
    System.out.println("\t\t����ѧ�Ų���,�޸�,ɾ������"); 
    System.out.println(); 
    System.out.println("\t####################################\n"); 
    System.out.print("1.��������:\n"+ 
    		"2.��������:\n"+ 
    		"3.ɾ������:\n"+ 
    		"4.�����������:\n"+ 
    		"5.������ȫ����ӡ����Ļ\n"+ 
    		"6.�ѳɼ���ѧ������\n"+ 
    		"7.�޸�����\n"+ 
    		"8.ͳ���Ѽ�¼�ɼ�ѧ����\n"+ 
    		"9.��������\n"+ 
    		"0.�˳�����.\n" + 
    		"����:"); 
   BufferedReader in =new BufferedReader( new InputStreamReader(System.in));//���ն˽�������ѡ��; 
   String inputLine = in.readLine();  
   choice= Integer.valueOf(inputLine).intValue();
switch(choice) 
{ 
case 1: {//1.�������� 
     String str = lp.inputData(); 
     lp.addData(str); 
     System.out.println("�������ݳɹ�."); 
     timeOut(1); 
	}break; 
case 2: {//2.�������� 
	long find = 0; 
	System.out.print("��������Ҫ���ҵ�ѧ��ѧ��:"); 
	BufferedReader inn = new BufferedReader( new InputStreamReader(System.in)); 
	String inputLi = inn.readLine(); 
	find = Integer.valueOf(inputLi).longValue(); 
	lp.findData(find); 
    timeOut(2); 
	}break; 
case 3: {//3.ɾ������ 
	long deleteNumber = 0; 
	System.out.print("����������ɾ����ͬѧ��ѧ��:"); 
	BufferedReader bf = new BufferedReader ( new InputStreamReader(System.in)); 
	String inputL = bf.readLine(); 
	deleteNumber = Integer.valueOf(inputL).longValue(); 
	lp.deleteData(deleteNumber); 
	if(isDelete) System.out.println("ɾ�����ݳɹ�!"); 
	timeOut(1); 
	}break; 
case 4: { 
	lp.clearData();//4.����������� 
	timeOut(1); 
	}break; 
case 5: { 
	print();//5.������ȫ����ӡ����Ļ 
	timeOut(2); 
	}break; 
case 6: { 
	lp.numSort();//6.�ѳɼ���ѧ������ 
	System.out.println("����ѧ�Ŵ�С��������ɹ�!\n"+ "�����:\n"); 
	print(); 
	timeOut(2); 
	}break; 
case 7: { 
	lp.rewrite();//7.�޸����� 
	timeOut(2); 
	}break; 
case 8: { 
	int count = lp.count(); 
	System.out.println("����"+count+"��ѧ���Ѿ���¼."); 
	timeOut(2); 
	}break; 
case 9: { 
	System.out.print("\t\t�Ž���\n"+ "\t\t�Ϻ���ͨ��ѧCS\n"+ 
	    	"\t\tѧ�ţ�5080309522\n"); 
	timeOut(4); 
	}break; 
}
}while (choice != 0); 
System.out.println("Bye! ^-^"); 
System.exit(0); 
} 

public String inputData()//���ն˽������ݵķ���,�����ַ��� 
throws IOException,NumberFormatException 
{ 
		System.out.print("���������� :ѧ�� ���� �Ա� �ɼ�\n" + "ÿ���������ÿո����:"); 
		String all = ""; 
	try{ 
		BufferedReader in = new BufferedReader ( new InputStreamReader(System.in));
		String inputLine = in.readLine(); 
		StringTokenizer str = new StringTokenizer(inputLine," ");//���յ������ÿո����,�����������ȡÿ���ַ��� 
		long num = Integer.valueOf(str.nextToken()).longValue();//ѧ�� 
		String name = (String)str.nextToken(); //���� 
		String sex = (String)str.nextToken(); //�Ա� 
		double mark = Integer.valueOf(str.nextToken()).doubleValue();//���� 
		all = String.valueOf(num) +" , "+ name +" , "+ sex +" , "+ 
		String.valueOf(mark);//�����е�������" , "����Ȼ�����������Ž��ַ���all 
	}catch (IOException e){} 
	catch (NumberFormatException e){} 
	return all;//�����ַ���all 
} 

public void addData(String str)//�������ݵķ��� 
throws IOException 
{ 
		String s1 ="",s2="" ,s3= ""; 
		File file = new File("data.txt"); 
		if (file.exists())//����ļ�data.txt���� 
		{ 
		try{ 
				BufferedReader in = new BufferedReader( new FileReader("data.txt")); 
			while ((s1=in.readLine())!=null) 
			s2+=s1+"\n"; s2+=str+"\n"; ////���ļ��е�ÿ������ȫ���Ž�һ���ַ���s2�ٰ�s2���β�str�����Ž�s2 
			BufferedReader in2 = //���ַ� 
				new BufferedReader( new StringReader(s2)); 
			PrintWriter out = new PrintWriter(new BufferedWriter( new FileWriter("data.txt"))); //��s2Ҳ ����ԭ �ļ�
			//+ �β�str(�������һ������) ����д��data.txt ����ԭ�������� 
		while ((s3=in2.readLine())!= null) 
		{ 
			out.println(s3); 
		} 
		out.close(); 
		//System.out.println("write data true."); 
		}catch (IOException e){} 
		}else{ 
			System.err.println("File \"data\" Missing!"); 
		} 
} 
public void clearData()//���data.txt���������ݵķ��� 
throws IOException 
{ 
File file = new File("data.txt"); 
if(file.exists())//����ļ��� 
{ 
try{ 
PrintWriter out = 
new PrintWriter( 
new BufferedWriter( 
new FileWriter(file))); 
out.print("");//���ļ�data.txt��д��һ�����ַ�,���������ԭ�������� 
out.close(); //�ر��ļ� 
System.out.println("clear data true!"); 
}catch(IOException e){} 
}else{ 
System.err.println("File \"data\" Missing!"); 
} 
} 
public void deleteData(long deleteNumber)//ɾ��ĳ������ 
throws IOException,


FileNotFoundException 
{ 
isDelete = true; 
try{ 
DataMap mp = new DataMap();//����һ���Լ���д������ 
long j=0; 
String s1="",s2="",s3=""; 
BufferedReader in = 
new BufferedReader( 
new FileReader("data.txt")); 
while ((s1=in.readLine())!=null) 
{ 
j=numberTokenizer(s1); 
mp.put(j,s1); 
} 
try{ 
if(mp.containsKey( String.valueOf(deleteNumber).toString())) 
{ 
mp.remove(deleteNumber); 
}else 
throw new NumberNotFoundException(); 
Collection c = mp.values(); 
Iterator iter = c.iterator(); 
while(iter.hasNext()) 
{ 
s1 = (String)iter.next(); 
s3 +=s1+"\n"; 
} 
BufferedReader in2 = 
new BufferedReader( 
new StringReader(s3)); 
PrintWriter out = 
new PrintWriter( 
new BufferedWriter( 
new FileWriter("data.txt"))); 
//System.out.println("delete No"+deleteNumber); 
while( (s1 = in2.readLine())!=null) 
{ 
out.println(s1); 
} 
out.close(); 
}catch (NumberNotFoundException e) 
{ 
isDelete = false; 
System.out.println(deleteNumber+" no found :("); 
} 
}catch(IOException e){} 
} 
public long numberTokenizer(String s) 
throws IOException 
{ 
StringTokenizer st = 
new StringTokenizer(s," "); 
return Integer.valueOf((st.nextToken())).longValue(); 
} 
public void
findData(long find)//�������� 
throws IOException,NumberNotFoundException 
{ 
isFind = true; 
String s = "",

findString =""; 
long i; 
DataMap dm = new DataMap(); 
BufferedReader in = 
new BufferedReader( 
new FileReader("data.txt")); 
while ((s=in.readLine())!=null) 
{ 
i=numberTokenizer(s); 
dm.put(i,s); 
} 
//in.close(); 
try{ 
if(dm.containsKey( String.valueOf(find).toString())) 
{ 
findString = dm.get(find); 
System.out.println("ѧ��"+find+"ѧ����������:"); 
System.out.println(findString); 
}else 
throw new NumberNotFoundException(); 
}catch (NumberNotFoundException e){ 
System.out.println(find+" no found :("); 
isFind = false; 
} 
} 
public static void print()//��ȡ�ı��ļ������ݴ�ӡ���ն˵ķ��� 
throws IOException 
{ 
try{ 
BufferedReader in = 
new BufferedReader( 
new FileReader("data.txt")); 
String read = ""; 
while ((read = in.readLine())!=null) 
System.out.println(read); 
}catch(IOException e){} 
} 
public static void timeOut(double sec)//ͣ�ٶ���ʱ���һ��������ȫ���Բ�Ҫ������� 
{ 
double seconds = sec; 
long t = System.currentTimeMillis()+(int)(seconds*1000); 
while ((System.currentTimeMillis())<t) 
; 
} 
public void numSort()//��ѧ������ 
throws IOException 
{ 
long i = 0; 
String s = ""; 
try{ 
DataArrayList dal = new DataArrayList(); 
BufferedReader in = 
new BufferedReader( 
new FileReader("data.txt")); 
while ((s=in.readLine())!=null) 
{ 
i=numberTokenizer(s); 
dal.add(i); 
} 
Collections.sort(dal); 
DataMap dm = new DataMap(); 
BufferedReader in2 = 
new BufferedReader( 
new FileReader("data.txt")); 
while ((s=in2.readLine())!=null) 
{ 
i=numberTokenizer(s); 
dm.put(i,s); 
} 
PrintWriter out = 
new PrintWriter ( 
new BufferedWriter( 
new FileWriter("data.txt"))); 
Iterator it = dal.iterator(); 
long temp = 0; 
String tempStr = ""; 
while (it.hasNext()) 
{ 
temp = Integer.valueOf((String)it.next()).longValue(); 
tempStr = dm.get(temp); 
out.println(tempStr); 
} 
out.close(); 
}catch(IOException e){} 
} 
public void rewrite() 
throws IOException,NumberNotFoundException 
{ 
try{ 
System.out.print("��������Ҫ�޸ĵ�ѧ��ѧ��:"); 
BufferedReader in = 
new BufferedReader ( 
new InputStreamReader(System.in)); 
String inputLine = in.readLine(); 
long num = Integer.valueOf(inputLine).longValue(); 
findData(num); 
if(isFind) 
{ 
deleteData(num); 
System.out.print("�����������ѧ��������:"); 
String str = inputData(); 
addData(str); 
System.out.println("rewrite true!"); 
} 
}catch(IOException e){} 
catch(NumberNotFoundException e){} 
} 
public int count() 
throws IOException 
{ 

DataArrayList dal = new DataArrayList(); 
try{ 
String s = ""; 
long i =0; 
BufferedReader in = 
new BufferedReader( 
new FileReader("data.txt")); 
while ((s=in.readLine())!=null) 
{ 
i=numberTokenizer(s);


 dal.add(i); 
} 
}catch(IOException e){} 
return dal.size(); 
} 
} 
/* 
* 
* @author �Ž��� 
* TODO ���Ǹ�����д��һ������,�̳й�����HashMap 
* ��ŵĹ��ܾ��൱һ������ 
* 
*/ 
class DataMap extends HashMap//һ���洢���ݵ�Map 
{ 
public void put(long i,String str)//��ѧ�ź����ݷŽ����Map 
{ //�Ժ�һ��ѧ��(key)��Ӧ����һ���˵�����(value) 
put(String.valueOf(i).toString(),str); 
} 
public void remove(long i)//����ѧ��,Ȼ��ɾ��ѧ��(key)������Ӧ������(value) 
{ 
remove(String.valueOf(i).toString().toString()); 
} 
public String get(long i)//����һ��ѧ��,Ȼ�󷵻����key��Ӧ��value 
{ 
String s = String.valueOf(i).toString(); 
if (!containsKey(s)) 
{ 
System.err.println("Not found Key: "+s); 
} 
return (String)get(s); 
} 
} 
/* 
* 
* @author �Ž��� 
* 
* TODO �����̳�ArrayList 
* ��������������,����ѧ������ʱҪ�õ��� 
* 
*/ 
class DataArrayList extends ArrayList 
{ 
public void add(long num) 
{ 
String numToString = String.valueOf(num).toString(); 
add(numToString); 
} 
} 
/* 
* 
* @author �Ž���
* 
* TODO ���ӵ�һ��Exception,��Ҫ�����ļ���û��Ҫ�� 
* ��ѧ�ž��׳� 
* 
*/ 
class NumberNotFoundException extends Exception 
{ 
public NumberNotFoundException() 
{} 
} 
