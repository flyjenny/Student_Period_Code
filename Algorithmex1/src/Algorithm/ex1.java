package Algorithm;
import java.io.*;
import java.util.*;

public class ex1 {
	ex1(String filename){
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
			else if(temp1.get(temp1.size()-2)==temp2.get(0)){
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
			else{
				int i=0;
				int j=0;
				while(temp2.get(0)>temp1.get(i)){
					cityoutline.add(temp1.get(i));
					cityoutline.add(temp1.get(i+1));
					i+=2;
				}
				int mark=i;
				while((j<temp2.size())&&(i<temp1.size())){
					
						if(temp2.get(j+2)>temp1.get(i)){
							if(i==mark){
								if(temp2.get(j+1)>temp1.get(i-1)){
									if(cityoutline.get(cityoutline.size()-2)!=temp2.get(j)){
										cityoutline.add(temp2.get(j));
										cityoutline.add(temp2.get(j+1));
									}
								}
								else if(temp2.get(j+1)<temp1.get(i-1)){
									cityoutline.add(temp1.get(i));
									if(temp2.get(j+1)>temp1.get(i+1)){
										cityoutline.add(temp2.get(j+1));
									}
									else
									{
										cityoutline.add(temp1.get(i+1));
									}
								
								}
								else{
								}
								i+=2;
							}
							else{
				/*				if(temp2.get(j+1)>=temp1.get(i-1)&&temp2.get(j+1)>=temp1.get(i+1)){
					
								}
								else if(temp2.get(j+1)>temp1.get(i-1)&&temp2.get(j+1)<=temp1.get(i+1)){
									if(temp2.get(j+1)<=temp1.get(i+1)){
										
									}
									else{
									cityoutline.add(temp1.get(i));
									cityoutline.add(temp1.get(i+1));
									}
								}
								else if(temp2.get(j+1)<temp1.get(i-1)&&temp2.get(j+1)<=temp1.get(i+1)){
									cityoutline.add(temp1.get(i));
									cityoutline.add(temp1.get(i+1));
								}
								else if(temp2.get(j+1)<temp1.get(i-1)&&temp2.get(j+1)>=temp1.get(i+1)){
									cityoutline.add(temp1.get(i));
									cityoutline.add(temp2.get(j+1));
								}
								
					*/			
								if(temp2.get(j+1)>=temp1.get(i+1)){
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
							j+=2;
							i+=2;
						}
						else{
							if(temp2.get(j+1)>temp1.get(i-1)){
								if(cityoutline.get(cityoutline.size()-2)!=temp2.get(j)){
									cityoutline.add(temp2.get(j));
									cityoutline.add(temp2.get(j+1));
									cityoutline.add(temp2.get(j+2));
									cityoutline.add(temp1.get(i-1));
								}
							}
							j+=2;
						}
					
				}
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
		*/		if(j>=temp2.size()){
					for(int k=j+2;k<temp1.size();k++)
						cityoutline.add(temp1.get(k));
				}
				if(i>=temp1.size()){
					for(int k=j+2;k<temp2.size();k++)
						cityoutline.add(temp2.get(k));
				}
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
		ex1 test=new ex1("test.txt");
		int number=test.input();
		
		ArrayList<Double> a=new ArrayList<Double>();
		a=ex1.Execute(0,number-1);
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<a.size();i++){
			System.out.println(doubleTrans(a.get(i)));
			sb.append(doubleTrans(a.get(i)));
			sb.append("\n");
		}
/*		int left=1;
		int height=1;
		int right=3;
		int b;
		for (int i=0;i<1000001;i++){
				right=left+i;
				sb.append(left+" "+(int) (Math.random()*100)+" "+right+"\n");
				left++;
		}
*/		PrintWriter pw = new PrintWriter("D:\\result1.dat");
		pw.write(sb.toString());
		pw.close();
		long endTime =System.currentTimeMillis();
		System.out.println("RunningTime:"+ (endTime - beginTime) + "ms");
		
		
	}
}
