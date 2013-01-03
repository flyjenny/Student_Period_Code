package zhcp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws IOException {
//		System.out.println("Start");
//		FindClass fc=new FindClass(args[0],args[1],Integer.valueOf(args[2]));
//		System.out.println("Finished");
		Scanner c=new Scanner(System.in);
		int a=c.nextInt();
		System.out.println(a);
		File readf=new File("r:/abc.txt");
		BufferedReader br=new BufferedReader(new FileReader(readf));
		String tmp=br.readLine();
		while(tmp!=null){
			System.out.println(tmp);
			tmp=br.readLine();
		}
		
	}
}
