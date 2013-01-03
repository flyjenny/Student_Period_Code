package zhcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class FindClass {
	Vector<File> $file = null;

	FindClass(String root, String target, int mark) {
		FileRecursion fr = new FileRecursion(root);
		$file = new Vector<File>();
		$file = fr.vecFile;
		try {
			Find(target, mark);
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Find(String target, int mark) throws FileNotFoundException,
			BiffException, IOException, RowsExceededException, WriteException {
		InputStream input = null;
		int rowcount = 0;
		Sheet sh = null;
		Cell cell = null;
		String content = null;
		int i = 0;
		jxl.Workbook wb = null;

		WritableWorkbook book = Workbook.createWorkbook(new File("R:/" + target
				+ ".xls"));
		WritableSheet sheet = book.createSheet("Sheet1", 0);
		Label label = null;

		label = new Label(0, 0, "班级");
		sheet.addCell(label);
		label = new Label(1, 0, "姓名");
		sheet.addCell(label);
		label = new Label(2, 0, "学号");
		sheet.addCell(label);
		label = new Label(3, 0, "成绩");
		sheet.addCell(label);
		label = new Label(4, 0, "项目编号");
		sheet.addCell(label);
		label = new Label(5, 0, "备注");
		sheet.addCell(label);
		label = new Label(6, 0, "打分部门");
		sheet.addCell(label);

		int writerow = 1;
		for (File file : $file) {
			
			 input = new FileInputStream(file); wb =
			  Workbook.getWorkbook(input); sh = wb.getSheet(0); rowcount =
			  sh.getRows(); for (i = 0; i < rowcount; i++) { cell =
			  sh.getCell(mark, i); content =
			  cell.getContents().toLowerCase().trim();
			  
			  if (content.equals(target)) { Cell cells[] = sh.getRow(i); for
			  (int j = 0; j < cells.length; j++) { label = new Label(j,
			  writerow, cells[j].getContents()); sheet.addCell(label); }
			  writerow++; } }
			 

		}
		book.write();
		book.close();
	}

	public static void main(String[] args) {
		System.out.println("START!");
		FindClass fc=new FindClass("R:/root","1131",4);
		System.out.println("Finish" +
				"!");
	}
}
