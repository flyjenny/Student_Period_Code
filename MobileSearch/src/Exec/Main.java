package Exec;

import java.net.ConnectException;
import java.util.ArrayList;

import org.htmlcleaner.XPatherException;

import ClawerHtml.Clawer;
import HandleExcel.HandleSheet;

public class Main {
	public static void main(String[] args) throws XPatherException,
			InterruptedException {
		String preUrl = "http://wap.ip138.com/sim_search.asp?mobile=";
		

		HandleSheet sheet = new HandleSheet("d:/名单按部门分类_0615_冯筱.xls");

		String url = null;
		Clawer clawer = new Clawer();
		for (int j = 0; j < 15; j++) {//每个sheet
			sheet.setSheet(j);
			ArrayList<String> location = new ArrayList<String>();
			ArrayList<String> mobile = sheet.getMobile(8);
			int size = mobile.size();

			for (int i = 0; i < size; i++) {
				url = preUrl + mobile.get(i);
				System.out.print(sheet.getSheet().getSheetName() + "\t"
						+ (i + 1) + "/" + size + "\t" + url);
				if (mobile.get(i).equals("")) {
					System.out.println();
					location.add("");

					continue;
				}
				// Thread.sleep(1000);

				try {
					String loc = clawer.getLocation(clawer.getSourceUrl(clawer
							.getHTML(url)));
					System.out.println("\t" + loc);
					location.add(loc);
				} catch (ConnectException e) {

					location.add("");
					System.out.println(i + ":\t" + mobile.get(i)
							+ " Connection timed out ");
				}

			}
			sheet.modify(12, location);
		}

		sheet = new HandleSheet("d:/待定表格.xls");

		url = null;
		clawer = new Clawer();
		for (int j = 0; j < 1; j++) {
			sheet.setSheet(j);
			ArrayList<String> location = new ArrayList<String>();
			ArrayList<String> mobile = sheet.getMobile(8);
			int size = mobile.size();

			for (int i = 0; i < size; i++) {
				url = preUrl + mobile.get(i);
				System.out.print(sheet.getSheet().getSheetName() + "\t"
						+ (i + 1) + "/" + size + "\t" + url);
				if (mobile.get(i).equals("")) {
					System.out.println();
					location.add("");

					continue;
				}
				// Thread.sleep(1000);

				try {
					String loc = clawer.getLocation(clawer.getSourceUrl(clawer
							.getHTML(url)));
					System.out.println("\t" + loc);
					location.add(loc);
				} catch (ConnectException e) {

					location.add("");
					System.out.println(i + ":\t" + mobile.get(i)
							+ " Connection timed out ");
				}

			}
			sheet.modify(12, location);
		}
	}
}
