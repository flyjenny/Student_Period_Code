package City;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException{
		create city;
		building[] bu;
		
		BufferedReader in = new BufferedReader(new FileReader("input1.dat"));
		PrintWriter out = new PrintWriter(new FileWriter("result1.dat"));
		int total = Integer.parseInt(in.readLine());
		bu = new building[total];
		for(int i=0; i<total; i++){
			int l,h,r;
			String str;
			String[] list;
			str = in.readLine();
			list = str.split(" ");
			l = Integer.parseInt(list[0]);
			h = Integer.parseInt(list[1]);
			r = Integer.parseInt(list[2]);
			bu[i] = new building(l,h,r);
			System.out.println(l+"  "+h+"  "+r);
			//out.println(l+"  "+h+"  "+r);
		}
		city = new create(bu,total);
		int[] result=new int[total*10];
		result = city.run();
		int i=0;
		while(result[i]!=0 || result[i+1]!=0){
			out.println(result[i]);
			i++;
		}
		
		out.close();
		System.out.println("success!");
	}

}
