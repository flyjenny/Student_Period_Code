package _Find;

import java.io.*;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.read.biff.HyperlinkRecord;

import java.util.*;

public class FindCS {
	public static void main(String args[]) throws BiffException, IOException{
		
		try {
			InputStream input1 = new FileInputStream("teacher.xls");
			jxl.Workbook rwb1 = Workbook.getWorkbook(input1);
			InputStream input2 = new FileInputStream("lab.xls");
			jxl.Workbook rwb2 = Workbook.getWorkbook(input2);
			InputStream input3 = new FileInputStream("g2.xls");
			jxl.Workbook rwb3 = Workbook.getWorkbook(input3);
			
			Sheet rs1 = rwb1.getSheet(0);
			int rows1=rs1.getRows();
			Cell a=null;
			String strc1 = null;
			
			Sheet rs2 = rwb2.getSheet(0);
			int rows2=rs2.getRows();
			Cell b=null;
			String strc2 = null;
			int i;
			/*for(i=1;i<rows1;i++){
				a = rs1.getCell(1, i);
				strc1 = a.getContents();
				System.out.println(strc1);
			}*/
			Hyperlink[] links=rs2.getHyperlinks();

			for(Hyperlink link:links){
				
//				HyperlinkRecord l = (HyperlinkRecord)link;
				System.out.println(link.getURL());
			}
/*			for(i=1;i<rows2;i++){
				b=rs2.getCell(0, i);
			
				strc1=b.getContents();
				System.out.println(strc1);
			}
*/			
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
