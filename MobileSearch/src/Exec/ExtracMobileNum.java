package Exec;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import HandleExcel.HandleSheet;

public class ExtracMobileNum {
	public static void main(String[] args) throws IOException {
		String content = "";
		HandleSheet sheet1 = new HandleSheet("r:/MobileNumbers.xls");
		HandleSheet sheet2 = new HandleSheet("r:/最终完整名单031.xls");
		sheet1.setSheet(0);
		sheet2.setSheet(0);
		ArrayList<String> mobiles1 = sheet1.getMobile(1);
		
		ArrayList<String> mobiles2 = sheet2.getMobile(1);
		boolean mark=false;
		for (int i = 0; i < mobiles1.size(); i++) {
			String mobileNum1 = mobiles1.get(i).trim();
			for (int j = 0; j < mobiles2.size(); j++) {
				String mobileNum2 = mobiles2.get(j).trim();
				if (mobileNum1.equals(mobileNum2)) {
					mark=true;
					break;
				}
				mark=false;
			}
			if(!mark){
				content += mobileNum1 + "\r";
			}
		}
		writeFile(content);

	}

	public static void writeFile(String content) throws IOException {
		FileWriter fw = new FileWriter("r:/numbers.txt", true);
		fw.write(content);
		fw.flush();
		fw.close();
	}
}
