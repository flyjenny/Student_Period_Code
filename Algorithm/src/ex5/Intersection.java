package ex5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Intersection {
	line[] lines;
	int num;
	public static int Intersection_num = 0;

	public void read() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("input5_3.dat"));
		String str = in.readLine();
		num = Integer.parseInt(str);
		lines = new line[num];
		
		str=in.readLine();
		String[] tem=str.split(" ");
			
		
		for (int i = 0; i < num*4; i+=4) {
			int x1, y1, x2, y2;
		
			x1=Integer.parseInt(tem[i]);
			x2=Integer.parseInt(tem[i+1]);
			y1=Integer.parseInt(tem[i+2]);
			y2=Integer.parseInt(tem[i+3]);
			lines[i/4] = new line(x1, y1, x2, y2);
			// System.out.println(x1+"  "+y1+"  "+x2+"  "+y2);
		}
	}

	public void work() {
		int xl, xr;
		for (int i = 0; i < num; i++) {
			for (int j = i + 1; j < num; j++) {
				if (lines[i].x2 >= lines[j].x1)
					lines[i].candidate.add(String.valueOf(j));
			}
			for (int k = 0; k < lines[i].candidate.size(); k++) {
				xl = max(lines[i].x1,
						lines[Integer.parseInt(lines[i].candidate.get(k))].x1);
				xr = min(lines[i].x2,
						lines[Integer.parseInt(lines[i].candidate.get(k))].x2);
				// System.out.println(xl+" "+xr);
				int k1 = Integer.parseInt(lines[i].candidate.get(k));
				boolean t1 = lines[i].getCurrent_y(xl) > lines[k1]
						.getCurrent_y(xl);
				boolean t2 = lines[i].getCurrent_y(xr) < lines[k1]
						.getCurrent_y(xr);
				boolean t3 = lines[i].getCurrent_y(xl) < lines[k1]
						.getCurrent_y(xl);
				boolean t4 = lines[i].getCurrent_y(xr) > lines[k1]
						.getCurrent_y(xr);
				boolean t5 = lines[i].getCurrent_y(xl) == lines[k1]
						.getCurrent_y(xl);
				boolean t6 = lines[i].getCurrent_y(xr) == lines[k1]
						.getCurrent_y(xr);
				// System.out.println("1: "+lines[i].getCurrent_y(xr));
				// System.out.println("2: "+lines[k1].getCurrent_y(xr));
				if ((t1 && t2) || (t3 && t4) || t5 || t6) {// 一般直线存在交点的情况
					Intersection_num++;
				}
				if (lines[i].x1 == lines[i].x2) {// 当前线段为垂线
					boolean t7 = lines[i].y1 <= lines[k1].getCurrent_y(xl);
					boolean t8 = lines[i].y2 >= lines[k1].getCurrent_y(xr);
					if (t7 && t8)
						Intersection_num++;
				}
				if (lines[k1].x1 == lines[k1].x2) {// 候选线段中存在垂线
					boolean t9 = lines[i].getCurrent_y(lines[k1].x1) >= lines[k1].y1;
					boolean t10 = lines[i].getCurrent_y(lines[k1].x1) <= lines[k1].y2;
					if (t9 && t10)
						if ((lines[i].x1 != lines[k1].x1)
								&& (lines[i].x2 != lines[k1].x1))// 不能重复计算末尾终止于垂线的情况，上面已经包含了
							Intersection_num++;
				}

			}
		}
	}

	private int max(double x1, double x2) {
		// TODO Auto-generated method stub
		if (x1 <= x2)
			return (int) x2;
		else
			return (int) x1;
	}

	private int min(double x1, double x2) {
		// TODO Auto-generated method stub
		if (x1 >= x2)
			return (int) x2;
		else
			return (int) x1;
	}

}
