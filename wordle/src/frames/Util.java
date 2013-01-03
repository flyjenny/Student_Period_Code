package frames;

import java.util.ArrayList;
import java.util.Random;

public class Util {
	public static int MAXWORDNUM = 200;
	public static int MAXX = 800;
	public static int MAXY = 600;
	public static int MINX = 50;
	public static int MINY = 50;
	private static int[][] graphList = new int[MAXWORDNUM][4];
	private static String[] wordList = new String[MAXWORDNUM];
	private static int length = 0;
	private static Random seed = new Random();
	public static ArrayList<Integer> findBlank(int width, int height, String word)
	{
		int x1, x2, y1, y2;
		while (true)
		{
			x1 = Math.abs(seed.nextInt() % (MAXX - width)) + MINX;
			y1 = Math.abs(seed.nextInt() % (MAXY - height)) + MINY + height;
			x2 = x1 + width;
			y2 = y1 - height;
			boolean flag = true;
			
			System.out.println("Trying (" + x1 + "," + y1 + "," + x2 + "," + y2 + ")");
			for (int i = 0; i < length; i++)
			{
				if (overlap(x1, x2, y1, y2, i))
				{
					flag = false;
					System.out.println("Overlap with No " + i +" (" + x1 + "," + y1 + "," + x2 + "," + y2 + ")");
					break;
				}
			}
			if (flag)
			{
				graphList[length][0] = x1;
				graphList[length][1] = x2;
				graphList[length][2] = y1;
				graphList[length][3] = y2;
				wordList[length] = word;
				length++;
				ArrayList<Integer> ret = new ArrayList<Integer>();
				ret.add(x1);ret.add(y1);
				System.out.println("Blank found! (" + x1 + "," + y1 + "," + x2 + "," + y2 + ")");
				return ret;
			}
		}
	}
	public static int hitWord(int x, int y)
	{
		for (int i = 0; i < length; i++)
		{
			if (x >= graphList[i][0] && x <= graphList[i][1] && y >= graphList[i][3] && y <= graphList[i][2])
				return i;
		}
		return -1;
	}
	private static boolean overlap(int x1, int x2, int y1, int y2, int k)
	{
		int $x1 = graphList[k][0];
		int $x2 = graphList[k][1];
		int $y1 = graphList[k][2];
		int $y2 = graphList[k][3];
		if ((x1 < $x2) && (y1 > $y2) && ($x1 < x2) && ($y1 > y2))
			return true;
		return false;
	}
	public static String getWord(int id)
	{
		return wordList[id];
	}
	public static int getX1(int id)
	{
		return graphList[id][0];
	}
	public static int getX2(int id)
	{
		return graphList[id][1];
	}
	public static int getY1(int id)
	{
		return graphList[id][2];
	}
	public static int getY2(int id)
	{
		return graphList[id][3];
	}
}
