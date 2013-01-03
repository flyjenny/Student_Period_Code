package HandleExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class HandleSheet {
	public static void main(String[] args) {
		HandleSheet sheet = new HandleSheet("r:/名单按部门分类王元元0404ok.xls");

	}

	private String $readPath = null;
	private HSSFWorkbook $wbs = null;
	private HSSFSheet $sheet = null;
	private ArrayList<String> $mobileNumber = null;
	private int $rowCount = 0;

	public HandleSheet(String readPath) {
		$readPath = readPath;
		try {
			ReadWorkBook();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HSSFSheet getSheet() {
		return $sheet;
	}

	public void setSheet(int number) {
		$sheet = $wbs.getSheetAt(number);
		System.out.println("\t"+$sheet.getSheetName());
	}

	public ArrayList<String> getMobileNumber() {

		return $mobileNumber;
	}

	public void modify(int column, ArrayList<String> location) {
		// $sheet.getRow(0).createCell(column - 1).setCellValue("所在城市");

		for (int i = 1; i < location.size(); i++) {
			HSSFRow row = $sheet.getRow(i);
			HSSFCell cell = row.createCell(column - 1);
			String loc = location.get(i);
			cell.setCellValue(loc);
		}
		Save();
	}

	public void Print() {
		System.out.println($mobileNumber.toString());
	}

	public HSSFSheet ReadWorkBook() throws IOException {
		FileInputStream is = new FileInputStream($readPath);
		HSSFWorkbook wbs = new HSSFWorkbook(is);
		HSSFSheet childSheet = wbs.getSheetAt(0);
		$wbs = wbs;
		$sheet = childSheet;
		return childSheet;
	}

	public void Save() {
		try {
			OutputStream out = new FileOutputStream(new File($readPath));
			$wbs.write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public boolean isBlankRow(HSSFRow row) {
		int columnCount = row.getLastCellNum();
		String combine = "";
		for (int i = 0; i < columnCount; i++) {
			HSSFCell currentCell = row.getCell(i);
			if (null != currentCell) {
				switch (currentCell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC: // 数字
					combine += (currentCell.getNumericCellValue() + "").trim();
					break;
				case HSSFCell.CELL_TYPE_STRING: // 字符串
					combine += currentCell.getStringCellValue().trim();
					break;
				default:
					combine += "";
					break;
				}
			}
		}
		if(combine.equals("")){
			return true;
		}else{
			return false;
		}
	}

	public ArrayList<String> getMobile(int column) {
		ArrayList<String> mobileNumber = new ArrayList<String>();
		int columnCount = $sheet.getRow(0).getLastCellNum();
		if (column < 1 || column > columnCount) {
			return null;
		} else {
			int rowCount = $sheet.getLastRowNum();
			$rowCount = rowCount++;
			DecimalFormat df = new DecimalFormat("#");// 转换成整型
			for (int i = 0; i < rowCount; i++) {
				HSSFRow row = $sheet.getRow(i);
				if (null != row) {
					if(isBlankRow(row)){
						$mobileNumber = mobileNumber;
						return mobileNumber;
					}
/*					HSSFCell judgeCell1 = row.getCell(0);
					HSSFCell judgeCell2 = row.getCell(1);
					if (judgeCell1 != null && judgeCell2 != null) {
						String judge = null;
						switch (judgeCell1.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC: // 数字
							judge = (judgeCell1.getNumericCellValue() + "")
									.trim();
							break;
						case HSSFCell.CELL_TYPE_STRING: // 字符串
							judge = judgeCell1.getStringCellValue().trim();
							break;
						default:
							judge = "";
							break;
						}
						switch (judgeCell2.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC: // 数字
							judge += (judgeCell2.getNumericCellValue() + "")
									.trim();
							break;
						case HSSFCell.CELL_TYPE_STRING: // 字符串
							judge += judgeCell2.getStringCellValue().trim();
							break;
						default:
							judge += "";
							break;
						}
						if (null == judge || judge.equals("")) {
							$mobileNumber = mobileNumber;
							return mobileNumber;
						}
					}
*/					HSSFCell cell = row.getCell(column - 1);
					if (null == cell) {
						mobileNumber.add("");
					} else {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC: // 数字
							mobileNumber.add(df.format(
									cell.getNumericCellValue()).trim());
							break;
						case HSSFCell.CELL_TYPE_STRING: // 字符串
							mobileNumber.add(cell.getStringCellValue().trim());
							break;
						default:
							mobileNumber.add("");
							break;
						}
					}
				} else {
					break;
				}

			}
			$mobileNumber = mobileNumber;
			return mobileNumber;
		}
	}
}
