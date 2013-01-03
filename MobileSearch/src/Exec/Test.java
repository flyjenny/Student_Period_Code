package Exec;

import java.io.File;
import java.util.ArrayList;

import HandleExcel.HandleSheet;

public class Test {
	public static void main(String[] args) {
		// String mobile = "15121025925";
		String regex = "1[0-9]{10}";
		// System.out.println(mobile.matches(regex));
		FileRecursion fr = new FileRecursion("R:/Excels");
		for (File file : fr.vecFile) {
			String path = file.getAbsolutePath();
			System.out.println(path);
			HandleSheet sheet = new HandleSheet(path);
			for (int j = 0; j < 15; j++) {
				sheet.setSheet(j);
				ArrayList<String> mobiles = sheet.getMobile(8);
				for (int i = 0; i < mobiles.size(); i++) {
					String mobileNum = mobiles.get(i);
					if (null!=mobileNum&&!mobileNum.equals("") && !mobileNum.equals("ÊÖ»ú")
							&& !mobileNum.matches(regex))
						System.out.println("\t\t"+(i+1)+"ÐÐ:\t"+mobileNum);
				}
			}
			System.out.println();
		}

	}
}
