package ex3;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		SCC scc1=new SCC("data3_1.txt","result3_1.dat");
		scc1.read();
		scc1.work();
		scc1.write();
		
		SCC scc2=new SCC("data3_2.txt","result3_2.dat");
		scc2.read();
		scc2.work();
		scc2.write();
		
		SCC scc3=new SCC("data3_3.txt","result3_3.dat");
		scc3.read();
		scc3.work();
		scc3.write();
		
	}
}
