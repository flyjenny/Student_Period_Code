package ex5;

import java.io.IOException;


public class Main {
		public static void main(String[] args) throws IOException{
			Intersection inter = new Intersection();
			inter.read();
			inter.work();
			System.out.println(inter.Intersection_num);
	}
}
