package ex1;
import java.io.*;
import java.util.*;

public class Cityoutline {
	Cityoutline(String filename){
		$filename=filename;
	}
	private String $filename;
	public static double[][] $content;

	public int input() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader($filename));
		StringBuffer sb = new StringBuffer();
		String lineContent = null ;
		lineContent=br.readLine();
		int n=Integer.parseInt(lineContent);
		$content=new double[n][3];
		int i=0;
		while( (lineContent = br.readLine()) != null){
	
		   StringTokenizer st = new StringTokenizer( lineContent," " );
		   for( int t=0; st.hasMoreElements() ; t++){
			   double temp=Double.parseDouble((String) st.nextElement());
			   $content[i][t]=temp;
		   	}
		   
		   i++;
		}
		
		br.close();
		return n;
	}
	public static ArrayList<Double> Execute(int low,int high){
		if((high-low)==1){
			return merge(low,high);
		}
		else if(high==low){
			return buildtoline(low);
		}
		else{
			int mid=(low+high)/2;
			ArrayList<Double> temp1=Execute(low,mid);
			ArrayList<Double> temp2=Execute((mid+1),high);
			ArrayList<Double> cityoutline=new ArrayList<Double>();
			
			if(temp1.get(temp1.size()-2)<temp2.get(0)){
				for(int i=0;i<temp1.size();i++){
					cityoutline.add(temp1.get(i));
				}
				for(int i=0;i<temp2.size();i++){
					cityoutline.add(temp2.get(i));
				}
			}
			else if(temp1.get(temp1.size()-2).equals(temp2.get(0))){
				if (temp1.get(temp1.size()-3)==temp2.get(1)){
					for(int i=0;i<(temp1.size()-2);i++){
						cityoutline.add(temp1.get(i));
					}
					for(int i=2;i<temp2.size();i++){
						cityoutline.add(temp2.get(i));
					}
				}
				else{
					for(int i=0;i<(temp1.size()-1);i++){
						cityoutline.add(temp1.get(i));
					}
					for(int i=1;i<temp2.size();i++){
						cityoutline.add(temp2.get(i));
					}
				}
			}
			else{			//有重合情况
				int i=0;
				int j=0;
				while(temp2.get(0)>temp1.get(i)){		//把前面部分加入
					cityoutline.add(temp1.get(i));
					cityoutline.add(temp1.get(i+1));
					i+=2;
				}
				
				while((j<=temp2.size()-2)&&(i<=temp1.size()-2)){
					
					while((j<=temp2.size()-2)&&(i<=temp1.size()-2)&&(temp2.get(j)<temp1.get(i))){
						if(temp2.get(j+1)>=temp1.get(i-1)){
							if(!cityoutline.get(cityoutline.size()-1).equals(temp2.get(j+1))){
								cityoutline.add(temp2.get(j));
								cityoutline.add(temp2.get(j+1));
							}
						}
						else{
							if(!cityoutline.get(cityoutline.size()-1).equals(temp1.get(i-1))){
								cityoutline.add(temp2.get(j));
								cityoutline.add(temp1.get(i-1));
							}
						}
						j+=2;
					}
					while((j<=temp2.size()-2)&&(i<=temp1.size()-2)&&(temp1.get(i)<=temp2.get(j))){
						if(temp1.get(i+1)>=temp2.get(j-1)){
							if(!cityoutline.get(cityoutline.size()-1).equals(temp1.get(i+1))){
							cityoutline.add(temp1.get(i));
							cityoutline.add(temp1.get(i+1));
							}
						}
						else{
							if(!cityoutline.get(cityoutline.size()-1).equals(temp2.get(j-1))){
								cityoutline.add(temp1.get(i));
								cityoutline.add(temp2.get(j-1));
							}
						}
						i+=2;
					}
				}    //end out while
				if(!(j<=temp2.size()-2)){
					for(int k=i;k<temp1.size();k+=2){
						if(!cityoutline.get(cityoutline.size()-1).equals(temp1.get(k+1))){
							cityoutline.add(temp1.get(k));
							cityoutline.add(temp1.get(k+1));
						}
					}
				}
				if(!(i<=temp1.size()-2)){
					for(int k=j;k<temp2.size();k+=2){
						if(!cityoutline.get(cityoutline.size()-1).equals(temp2.get(k+1))){
							cityoutline.add(temp2.get(k));
							cityoutline.add(temp2.get(k+1));
						}
					}
					
				}
				
				
				
				
/*				int mark=i;
				while((j<temp2.size())&&(i<temp1.size())){
					if(temp2.get(j)!=temp1.get(i)){
						if(temp2.get(j+2)>temp1.get(i)){
							if(i==mark){
								if(temp2.get(j+1)>temp1.get(i-1)){
									if(cityoutline.get(cityoutline.size()-1)!=temp2.get(j+1)){
										cityoutline.add(temp2.get(j));
										cityoutline.add(temp2.get(j+1));
									}
									i+=2;
								}
								else{
									cityoutline.add(temp1.get(i));
									if(temp2.get(j+1)>temp1.get(i+1)){
										cityoutline.add(temp2.get(j+1));
									}
									else
									{
										cityoutline.add(temp1.get(i+1));
									}
									i+=2;
								}
							}
							else{
								if(temp2.get(j+1)>temp1.get(i+1)){
									if(temp2.get(j+1)!=cityoutline.get(cityoutline.size()-1)){
										cityoutline.add(temp1.get(i));
										cityoutline.add(temp2.get(j+1));
									}									
								}
								else{
									if(temp1.get(i+1)!=cityoutline.get(cityoutline.size()-1)){
										cityoutline.add(temp1.get(i));
										cityoutline.add(temp1.get(i+1));
									}
								}
								i+=2;
						}
						}
						
						
						else if(temp2.get(j+2)==temp1.get(i)){
							if(temp2.get(j+1)!=cityoutline.get(cityoutline.size()-1)){
								
							}
							j+=2;
						}
						else{
							if(temp2.get(j+1)>temp1.get(i-1)){
								if(cityoutline.get(cityoutline.size()-1)!=temp2.get(j+1)){
									cityoutline.add(temp2.get(j));
									cityoutline.add(temp2.get(j+1));
									cityoutline.add(temp2.get(j+2));
									cityoutline.add(temp1.get(i-1));
								}
							}
							j+=2;
						}
				}
					else{
						if(temp2.get(j+1)>temp1.get(i+1)){
							if(cityoutline.get(cityoutline.size()-1)!=temp2.get(j+1)){
								cityoutline.add(temp2.get(j));
								cityoutline.add(temp2.get(j+1));
							}
							i+=2;
						}
						else{
							if(cityoutline.get(cityoutline.size()-1)!=temp1.get(i+1)){
								cityoutline.add(temp1.get(i));
								cityoutline.add(temp1.get(j+1));
							}
							i+=2;
						}
					}
					
				}		//end while
*/				
				
/*				if((j>=temp2.size()-2)&&(temp1.get(temp1.size()-1)==0)){
					if(temp2.get(j))
				}
*/		/*		if (j>=temp2.size()-2){
					if(temp1.get(i+1)!=0){
					if (temp2.get(j-1)>temp1.get(i+1)){
						if(temp2.get(j)!=cityoutline.get(cityoutline.size()-2)){
							cityoutline.add(temp2.get(j));
						}
						else{
							cityoutline.add(temp1.get(i));
						}
						for(int k=i+1;k<temp1.size();k++)
							cityoutline.add(temp1.get(k));
					}
					else{
						for(int k=i+2;k<temp1.size();k++)
							cityoutline.add(temp1.get(k));
						
					}
					}
				}
				if(j>=temp2.size()){
					for(int k=j+2;k<temp1.size();k++)
						cityoutline.add(temp1.get(k));
				}
				if(i>=temp1.size()){
					for(int k=j+2;k<temp2.size();k++)
						cityoutline.add(temp2.get(k));
				}
		*/
			}
			return cityoutline;
		}
	}
	public static ArrayList<Double> buildtoline(int build){
		ArrayList<Double> cityoutline=new ArrayList<Double>();
		cityoutline.add($content[build][0]);
		cityoutline.add($content[build][1]);
		cityoutline.add($content[build][2]);
		cityoutline.add((double) 0);
		return cityoutline;
	}
	public static ArrayList<Double> merge(int low,int high){
		ArrayList<Double> cityoutline =new ArrayList<Double>();
		if($content[low][2]<$content[high][0]){
			cityoutline.add($content[low][0]);
			cityoutline.add($content[low][1]);
			cityoutline.add($content[low][2]);
			cityoutline.add((double) 0);
			cityoutline.add($content[high][0]);
			cityoutline.add($content[high][1]);
			cityoutline.add($content[high][2]);
			cityoutline.add((double) 0);
		}
		else if($content[low][2]==$content[high][0]){
			if($content[low][1]==$content[high][1]){
				cityoutline.add($content[low][0]);
				cityoutline.add($content[low][1]);
				cityoutline.add($content[high][2]);
				cityoutline.add((double) 0);
			}
			else{
			cityoutline.add($content[low][0]);
			cityoutline.add($content[low][1]);
			cityoutline.add($content[low][2]);
			cityoutline.add($content[high][1]);
			cityoutline.add($content[high][2]);
			cityoutline.add((double) 0);
			}
		}
		else{
			if($content[low][2]>$content[high][2]){
				if ($content[low][1]>=$content[high][1]){
					cityoutline.add($content[low][0]);
					cityoutline.add($content[low][1]);
					cityoutline.add($content[low][2]);
					cityoutline.add((double) 0);
				}
				else{
					cityoutline.add($content[low][0]);
					cityoutline.add($content[low][1]);
					cityoutline.add($content[high][0]);
					cityoutline.add($content[high][1]);
					cityoutline.add($content[high][2]);
					cityoutline.add($content[low][1]);
					cityoutline.add($content[low][2]);
					cityoutline.add((double) 0);
				}
			}
			else if($content[low][2]==$content[high][2]){
				if ($content[low][1]>=$content[high][1]){
					cityoutline.add($content[low][0]);
					cityoutline.add($content[low][1]);
					cityoutline.add($content[low][2]);
					cityoutline.add((double) 0);
				}
				else{
					cityoutline.add($content[low][0]);
					cityoutline.add($content[low][1]);
					cityoutline.add($content[high][0]);
					cityoutline.add($content[high][1]);
					cityoutline.add($content[high][2]);
					cityoutline.add((double) 0);
				}
			}
			else{
				if ($content[low][1]>$content[high][1]){
					cityoutline.add($content[low][0]);
					cityoutline.add($content[low][1]);
					cityoutline.add($content[low][2]);
					cityoutline.add($content[high][1]);
					cityoutline.add($content[high][2]);
					cityoutline.add((double) 0);
				}
				else if($content[low][1]==$content[high][1]){
					cityoutline.add($content[low][0]);
					cityoutline.add($content[low][1]);
					cityoutline.add($content[high][2]);
					cityoutline.add((double) 0);
				}
				else{
					cityoutline.add($content[low][0]);
					cityoutline.add($content[low][1]);
					cityoutline.add($content[high][0]);
					cityoutline.add($content[high][1]);
					cityoutline.add($content[high][2]);
					cityoutline.add((double) 0);
				}
			}
		}
		return cityoutline;
	}
	public static String doubleTrans(double d){
		if(Math.round(d)-d==0){
		   return String.valueOf((long)d);
		}
		  return String.valueOf(d);
		}
	public static void main(String[] args) throws IOException{
		long beginTime =System.currentTimeMillis();
		Cityoutline test1=new Cityoutline("input1_1.dat");
		Cityoutline test2=new Cityoutline("input1_2.dat");
		Cityoutline test3=new Cityoutline("input1_3.dat");
		PrintWriter pw1 = new PrintWriter("result1_1.dat");
		PrintWriter pw2 = new PrintWriter("result1_2.dat");
		PrintWriter pw3 = new PrintWriter("result1_3.dat");
		int i=0;
/*		
		int number1=test1.input();
		ArrayList<Double> a=new ArrayList<Double>();
		a=Cityoutline.Execute(0,number1-1);
		
		for(i=0;i<a.size()-2;i++){
			pw1.println(doubleTrans(a.get(i)));
		}
		pw1.print(doubleTrans(a.get(i)));
		pw1.close();
*/		
		int number2=test2.input();
		ArrayList<Double> b=new ArrayList<Double>();
		b=Cityoutline.Execute(0,number2-1);
		i=0;
		for(i=0;i<b.size()-2;i++){
			pw2.println(doubleTrans(b.get(i)));
		}
		pw2.print(doubleTrans(b.get(i)));
		pw2.close();
/*				
		int number3=test3.input();
		ArrayList<Double> c=new ArrayList<Double>();
		c=Cityoutline.Execute(0,number3-1);
		i=0;
		for(i=0;i<c.size()-2;i++){
			pw3.println(doubleTrans(c.get(i)));
		}
		pw3.print(doubleTrans(c.get(i)));
		pw3.close();
		
*/		
		long endTime =System.currentTimeMillis();
	//	System.out.println("RunningTime:"+ (endTime - beginTime) + "ms");
		
		
	}
}
