package Exec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * @author BennyChan
 * 
 */
public class PoiHandleXlsx {
	public static void main(String[] args) {
		FileRecursion fr = new FileRecursion("R:/Score");
		for (File file : fr.vecFile) {
			String readPath = file.getAbsolutePath();
			String writePath = "R:/result/"
					+ readPath.replace("R:\\Score\\", "")
							.replace("xlsx", "xls");
			System.out.println(writePath);
			PoiHandleXlsx phx = new PoiHandleXlsx(readPath, writePath);
			phx.ReadWorkBook();
			phx.CreateWorkBook();
		}
		/*
		 * String writePath = "abcd.xls"; PoiHandleXlsx phx = new
		 * PoiHandleXlsx(readPath, writePath); phx.ReadWorkBook();
		 * phx.CreateWorkBook(); phx.Filter();
		 */

	}

	private String[][] $workBook = null;
	private String[][] $modified = null;
	private String $readPath = null;
	private String $writePath = null;
	private int $rowCount = 0;
	private int $columnCount = 0;
	private int $finalRowCount = 0;

	private int $afterDeleteRowsCount = 0;

	public PoiHandleXlsx(String readPath, String writePath) {
		$readPath = readPath;
		$writePath = writePath;
	}

	/**
	 * 打印内存中已被处理的表格
	 */
	public void PrintWorkBook() {
		ReadWorkBook();
		if (null != $workBook) {
			for (int i = 0; i < $rowCount; i++) {
				for (int j = 0; j < $columnCount; j++)
					System.out.print($workBook[i][j] + "\t");
				System.out.println();
			}
		} else {
			System.out.println("WorkBook is Null");
		}
	}

	/**
	 * 判断是XLS还是XLSX
	 * 
	 * @return ture为xls，false为xlsx
	 */
	public boolean JudgeFileType() {
		String[] cut = $readPath.split("l");
		if (cut[1].equals("s")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 把表格内容读入$workBook
	 */
	public void ReadWorkBook() {
		if (JudgeFileType()) {
			try {
				FileInputStream is = new FileInputStream($readPath);
				HSSFWorkbook wbs = new HSSFWorkbook(is);

				HSSFSheet childSheet = wbs.getSheetAt(0);
				$rowCount = childSheet.getLastRowNum();
				$columnCount = childSheet.getRow(0).getLastCellNum();
				System.out.print("有行数" + $rowCount + "\t");
				System.out.println("有列数" + $columnCount);

				$workBook = new String[$rowCount][$columnCount];
				for (int j = 0; j < childSheet.getLastRowNum(); j++) {
					HSSFRow row = childSheet.getRow(j);

					if (null != row) {
						for (int k = 0; k < row.getLastCellNum(); k++) {
							HSSFCell cell = row.getCell(k);
							if (null != cell) {
								switch (cell.getCellType()) {
								case HSSFCell.CELL_TYPE_NUMERIC: // 数字
									$workBook[j][k] = cell
											.getNumericCellValue() + "";
									/*
									 * System.out.print(cell.getNumericCellValue(
									 * ) + " 数字 ");
									 */
									break;
								case HSSFCell.CELL_TYPE_STRING: // 字符串
									$workBook[j][k] = cell.getStringCellValue();
									break;
								case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
									System.out.println(cell
											.getBooleanCellValue()
											+ " Boolean ");
									break;
								case HSSFCell.CELL_TYPE_FORMULA: // 公式
									System.out.print(cell.getCellFormula()
											+ " 公式  ");
									break;
								case HSSFCell.CELL_TYPE_BLANK: // 空值
									$workBook[j][k] = "";
									break;
								case HSSFCell.CELL_TYPE_ERROR: // 故障
									System.out.println(" 故障 ");
									break;
								default:
									System.out.print("未知类型   ");
									break;
								}
							} else {
								$workBook[j][k] = "";
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				FileInputStream is = new FileInputStream($readPath);
				XSSFWorkbook wbs = new XSSFWorkbook(is);
				XSSFSheet childSheet = wbs.getSheetAt(0);
				$rowCount = childSheet.getLastRowNum();
				$columnCount = childSheet.getRow(0).getLastCellNum();
				System.out.print("有行数" + $rowCount + "\t");
				System.out.println("有列数" + $columnCount);
				$workBook = new String[$rowCount][$columnCount];
				for (int j = 0; j < childSheet.getLastRowNum(); j++) {
					XSSFRow row = childSheet.getRow(j);
					if (null != row) {
						for (int k = 0; k < row.getLastCellNum(); k++) {
							XSSFCell cell = row.getCell(k);
							if (null != cell) {
								switch (cell.getCellType()) {
								case XSSFCell.CELL_TYPE_NUMERIC: // 数字
									$workBook[j][k] = cell
											.getNumericCellValue() + "";
									/*
									 * System.out.print(cell.getNumericCellValue(
									 * ) + " 数字 ");
									 */
									break;
								case XSSFCell.CELL_TYPE_STRING: // 字符串
									$workBook[j][k] = cell.getStringCellValue();
									break;
								case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
									System.out.print(cell.getBooleanCellValue()
											+ "(Boolean)\t");
									break;
								case XSSFCell.CELL_TYPE_FORMULA: // 公式
									System.out.print(cell.getCellFormula()
											+ "(公式)\t");
									break;
								case XSSFCell.CELL_TYPE_BLANK: // 空值
									$workBook[j][k] = "";
									break;
								case XSSFCell.CELL_TYPE_ERROR: // 故障
									System.out.print("(故障)\t");
									break;
								default:
									System.out.print("未知类型\t");
									break;
								}
							} else {
								$workBook[j][k] = "";
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		$modified = $workBook;
	}

	/**
	 * 从内存中生成表格HSSFWorkbook
	 */
	public void CreateWorkBook() {
		HSSFWorkbook workbook = new HSSFWorkbook();
//		HandleColumn();
		HSSFSheet sheet = workbook.createSheet("Sheet1");
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontHeight((short) 11);
		font.setFontName("宋体");
		String[][] result = HandleRow();// Filter();
		for (int i = 0; i < $finalRowCount; i++) {
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < $columnCount; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(result[i][j]);
				cell.setCellStyle(style);
			}
		}
		save(workbook, $writePath);
	}

	/**
	 * 处理列，添加.\Male\China以及清空某些特定列
	 */
	public void HandleColumn() {
		boolean isBlank = true;
		for (int i = 0; i < $rowCount; i++) {
			isBlank = true;

			for (int j = 0; j < $columnCount; j++) {
				if (null != $modified[i][j]
						&& !$modified[i][j].trim().isEmpty()) {
					isBlank = false;
					break;
				}
			}
			for (int j = 0; j < $columnCount; j++) {
				if (i != 0 && !isBlank) {
					if (j == 12 || j == 13 || (j > 15 && j < 20)) {
						$modified[i][j] = ""; // 7.
												// 清空“所在省”、“所在城市”、“学位”、“在公司年限”、“负责方案”和“第一语言”列
					} else if (j == 3) {
						$modified[i][j] = "."; // 6. “姓”列： 全部填写“.”（英文句号）
					} else if (j == 11) {
						$modified[i][j] = "China"; // 5. “所在国家”列：全部填写“China”
					} else if (j == 10) {
						$modified[i][j] = "Male"; // 4. “性别”列：全部填写“Male”
					}
				}
			}
		}

	}

	/**
	 * 处理行，删除某列包含特定字眼的行
	 * 
	 * @return 处理后的$workBook
	 */
	public String[][] HandleRow() {
		ArrayList<String[]> rows = new ArrayList<String[]>();
		boolean isBlank = true;
		for (int i = 0; i < $rowCount; i++) {
			String[] row = new String[$columnCount];
			isBlank = true;
			for (int j = 0; j < $columnCount; j++) {
				if (null != $modified[i][j]
						&& !$modified[i][j].trim().isEmpty()) {
					isBlank = false;
					break;
				}
			}
			if (!isBlank) {
				if ($modified[i][15].trim().contains("总监")
						|| $modified[i][15].trim().contains("副总裁")
						|| $modified[i][15].trim().contains("总裁")
						|| $modified[i][15].trim().contains("副总经理")
						|| $modified[i][15].trim().contains("总经理")
						|| $modified[i][15].trim().contains("总裁")
						|| $modified[i][15].trim().contains("董事长")
						|| $modified[i][15].trim().contains("实习生")
						|| $modified[i][15].trim().contains("秘书")
						|| $modified[i][15].trim().contains("助理")
						|| $modified[i][15].trim().contains("保卫科")
						|| $modified[i][15].trim().contains("党委")
						|| $modified[i][15].trim().contains("工会")
						|| $modified[i][15].trim().contains("党工办")
						|| $modified[i][15].trim().contains("会计")
						|| $modified[i][15].trim().contains("出纳")
						|| $modified[i][15].trim().contains("文员")
						|| $modified[i][15].trim().contains("人事")
						|| $modified[i][15].trim().toLowerCase()
								.contains("ceo")
						|| $modified[i][15].trim().toLowerCase()
								.contains("director")
						|| $modified[i][15].trim().toLowerCase()
								.contains("president")
						|| $modified[i][15].trim().toLowerCase()
								.contains("general manager")
						|| $modified[i][15].trim().toLowerCase()
								.contains("vice manager")
						|| $modified[i][15].trim().toLowerCase()
								.contains("chairman")
						|| $modified[i][15].trim().toLowerCase()
								.contains("intern")
						|| $modified[i][15].trim().toLowerCase()
								.contains("secretary")
						|| $modified[i][15].trim().toLowerCase()
								.contains("assistant")
						|| $modified[i][14].trim().contains("财务")
						|| $modified[i][14].trim().contains("人事")
						|| $modified[i][14].trim().contains("人力资源")
						|| $modified[i][14].trim().contains("行政")
						|| $modified[i][14].trim().contains("法务")
						|| $modified[i][14].trim().contains("法律")
						|| $modified[i][14].trim().contains("保卫科")
						|| $modified[i][14].trim().contains("党委")
						|| $modified[i][14].trim().contains("工会")
						|| $modified[i][14].trim().contains("党工办")
						|| $modified[i][14].trim().contains("会计")
						|| $modified[i][14].trim().contains("出纳")
						|| $modified[i][14].trim().contains("文员")
						|| $modified[i][14].trim().contains("人事")
						|| $modified[i][14].trim().toLowerCase()
								.contains("ceo")
						|| $modified[i][14].trim().toLowerCase()
								.contains("finance")
						|| $modified[i][14].trim().toLowerCase()
								.contains("human resource")
						|| $modified[i][14].trim().toLowerCase().contains("hr")
						|| $modified[i][14].trim().toLowerCase()
								.contains("administration")
						|| $modified[i][14].trim().toLowerCase()
								.contains("law")) {

				} else {
					for (int j = 0; j < $columnCount; j++) {
						row[j] = $modified[i][j];
					}
					rows.add(row);
				}
			}
		}
		$afterDeleteRowsCount = rows.size();
		$finalRowCount=$afterDeleteRowsCount;
		String[][] afterDeleteRows = new String[$afterDeleteRowsCount][$columnCount];
		for (int i = 0; i < $afterDeleteRowsCount; i++) {
			for (int j = 0; j < $columnCount; j++) {
				afterDeleteRows[i][j] = rows.get(i)[j];
			}
		}
		Arrays.sort(afterDeleteRows, new Cmp());// 排序，其实没什么必要。。。囧
		return afterDeleteRows;
	}

	/**
	 * 根据条件删除行
	 * 
	 * @return 处理后的$workBook
	 */
	public String[][] Filter() {
		ArrayList<ArrayList<Integer>> rows = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> row = new ArrayList<Integer>();
		String[][] tmp = HandleRow();

		/*
		 * for (int i = 0; i < $afterDeleteRowsCount; i++) { for(int
		 * j=0;j<$columnCount;j++){ System.out.print(tmp[i][j]+"\t"); }
		 * System.out.println(); }
		 */int counter = 0;
		for (int i = 0; i < $afterDeleteRowsCount; i++) {
			boolean isEqual = false;
			if (null != tmp[i][14] && !tmp[i][14].trim().isEmpty()
					&& null != tmp[i][15] && !tmp[i][15].trim().isEmpty()) {
				if (rows.size() > 0) {
					for (int j = 0; j < rows.size(); j++) {
						if ((tmp[i][14].trim() + tmp[i][15].trim())
								.equals(tmp[rows.get(j).get(0)][14].trim()
										+ tmp[rows.get(j).get(0)][15].trim())) {
							rows.get(j).add(i);
							isEqual = true;
							break;
						}
					}
					if (isEqual) {
						continue;
					} else {
						row = new ArrayList<Integer>();
						row.add(i);
						rows.add(row);
					}
				} else {
					row.add(i);
					rows.add(row);
				}
			} else {
				row = new ArrayList<Integer>();
				row.add(i);
				rows.add(row);
			}
		}

		int finalRowCount = 0;
		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).size() < 6) {
				finalRowCount++;

				while (rows.get(i).size() != 1) {
					rows.get(i).remove(1);
				}
			} else {
				int selectRowCount = rows.get(i).size() / 3;
				finalRowCount += selectRowCount;

				while (rows.get(i).size() != selectRowCount) {
					rows.get(i).remove(selectRowCount);
				}
			}
		}
		$finalRowCount = finalRowCount;
		String[][] result = new String[finalRowCount][$columnCount];
		int index = 0;
		for (int i = 0; i < rows.size(); i++) {
			for (int j = 0; j < rows.get(i).size(); j++) {
				result[index++] = tmp[rows.get(i).get(j)];
			}
		}
		/*
		 * System.out.println(); for(int i=0;i<finalRowCount;i++){
		 * System.out.println
		 * (result[i][2]+"\t"+result[i][14]+"\t"+result[i][15]); }
		 */return result;
	}

	/**
	 * 将excel写入到某个输出流中。
	 * 
	 * @param hwb
	 * @param filePath
	 */
	public void save(HSSFWorkbook hwb, String filePath) {
		try {
			OutputStream out = new FileOutputStream(new File(filePath));
			hwb.write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}

/**
 * @author BennyChan 继承接口实现对比器
 */
class Cmp implements Comparator {
	public int compare(Object a, Object b) {
		String[] str1 = (String[]) a;
		String[] str2 = (String[]) b;
		if (str1[14].startsWith("部门(录入栏 )")) {
			return -1;
		}
		if (str2[14].startsWith("部门(录入栏 )")) {
			return 1;
		}
		if (str1[14].compareTo(str2[14]) != 0) {
			return str1[14].trim().compareTo(str2[14].trim());
		} else {
			return str1[15].trim().compareTo(str2[15].trim());
		}
	}
}