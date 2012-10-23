package MCS;
import java.io.*;
import java.util.ArrayList;


public class Main {
	public static void main(String[] args)throws IOException{
		double[] num;
		String str="";
		ArrayList<String> Golbal = new ArrayList<String>();
		ArrayList<String> Suffix = new ArrayList<String>();
		ArrayList<String> candidate = new ArrayList<String>();
		double Golbal_Max = 0;
		double Suffix_Max =0;
		BufferedReader in = new BufferedReader(new FileReader("input2.dat"));
		PrintWriter out = new PrintWriter(new File("result2.dat"));
		str = in.readLine();
		int size = Integer.parseInt(str);
		//System.out.println(size);
		num = new double[size];
		String[] list;
		str = in.readLine();
		list = str.split(" ");
		for(int i=0;i<size;i++){
			num[i] = Double.parseDouble(list[i]);
		    //System.out.println(num[i]);
			if((num[i]+Suffix_Max)>Golbal_Max){
				Suffix_Max = Suffix_Max + num[i];
				Golbal_Max = Suffix_Max;
				Suffix.add(list[i]);
				Golbal.clear();
				//Golbal = Suffix;
				for(int j=0;j<Suffix.size();j++){
					Golbal.add(Suffix.get(j));
				}
			}
			else if((num[i]+Suffix_Max)>0){
				if(Suffix_Max==Golbal_Max){
					if(candidate.isEmpty()){
						candidate.add(String.valueOf(Suffix_Max));//候选式中第一个元素为候选式的最大连续子序列的大小
						for(int j=0;j<Suffix.size();j++){
							candidate.add(Suffix.get(j));
						}
					}else if(Suffix_Max==Double.parseDouble(candidate.get(0))){
						for(int j=0;j<Suffix.size();j++){
							candidate.add(Suffix.get(j));
						}
					}else if(Suffix_Max>Double.parseDouble(candidate.get(0))){
						candidate.clear();
						candidate.add(String.valueOf(Suffix_Max));
						for(int j=0;j<Suffix.size();j++){
							candidate.add(Suffix.get(j));
						}
					}
				}
				Suffix_Max = Suffix_Max + num[i];
				Suffix.add(list[i]);
			}else{//若此时Suffix_Max==Golbal_Max，就放入候选队列
			/*	if(Suffix_Max==Golbal_Max){
					if(candidate.isEmpty()){
						candidate.add(String.valueOf(Suffix_Max));//候选式中第一个元素为候选式的最大连续子序列的大小
						for(int j=0;j<Suffix.size();j++){
							candidate.add(Suffix.get(j));
						}
					}else if(Suffix_Max==Double.parseDouble(candidate.get(0))){
						for(int j=0;j<Suffix.size();j++){
							candidate.add(Suffix.get(j));
						}
					}else if(Suffix_Max>Double.parseDouble(candidate.get(0))){
						candidate.clear();
						candidate.add(String.valueOf(Suffix_Max));
						for(int j=0;j<Suffix.size();j++){
							candidate.add(Suffix.get(j));
						}
					}
				}*/
				Suffix_Max = 0;
				Suffix.clear();
			}
		}
		if(Suffix_Max > Golbal_Max){
			System.out.println(Suffix_Max);
			System.out.println(Suffix.toString());
			out.println(Suffix_Max);
			out.println(Suffix.toString());
		}else{
			if(Suffix_Max < Golbal_Max){
				System.out.println(Golbal_Max);
				out.println(Golbal_Max);
				//System.out.println(Golbal.toString());
				//out.println(Golbal.toString());
			}else {
				System.out.println(Golbal_Max);
				out.println(Golbal_Max);
				//System.out.println(Golbal.toString());
				//out.println(Golbal.toString());
				//System.out.println(Suffix.toString());
				//out.println(Suffix.toString());
				for(int i=0;i<Suffix.size();i++){
					System.out.print(Suffix.get(i)+"  ");
					out.print(Suffix.get(i)+"  ");
				}	
				System.out.println();
				out.println();
				for(int i=0;i<Golbal.size();i++){
					System.out.print(Golbal.get(i)+" ");
				}
			}
			if(Golbal_Max==Double.parseDouble(candidate.get(0))){
				double k=0;
				for(int j=1;j<candidate.size();j++){
					k+=Double.parseDouble(candidate.get(j));
					if(k!=Golbal_Max){
						System.out.print(candidate.get(j)+"  ");
						out.print(candidate.get(j)+"  ");
					}else{
						System.out.println(candidate.get(j)+"  ");
						out.println(candidate.get(j)+"  ");
						k=0;
					}
				}
				
			}
		}
		//System.out.println(candidate.toString());
			
		out.close();
	}

}
