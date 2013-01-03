package RecordManager;

import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

import Record.*;
import DiskManager.*;

/*The class that carry out the basic operation for a database */
/**
 * @author wyj 记录管理器，按一定格式数据库对磁盘读写操作
 */
public class RecordManager {
	/**
	 * 完成对磁盘底层读写操作的对象
	 */
	DiskManager mydiskMgr;
	// 用户名
	String userName;
	// 用户登录密码
	String userPasswd;
	/**
	 * 数据库大小（字节数）
	 */
	int sizeOfDatabase;
	/**
	 * 每块大小（字节数）
	 */
	int blkSize;
	/**
	 * 每块的页面数
	 */
	int blkCount;// ����
	/**
	 * 下一个空闲块的块号
	 */
	int nextFreeBlk;
	/**
	 * 每块的实际字节数
	 */
	int realBlkSize;
	/**
	 * 插入时下一个表区位置
	 */
	int nextFreeTableStruct;
	/**
	 * 下一个VARCHAR位置
	 */
	int nextFreeVchar;
	/**
	 * 位图大小
	 */
	int bitmapSize;
	Transform trans;
	/**
	 * 数据字典索引
	 */
	Map tableStructIndex = new HashMap<String, String>();
	/**
	 * 每个Table属性字节地址索引
	 */
	Map tableAddrIndex = new HashMap<String, Integer>();
	MYFile fileHandle;

	public RecordManager() {
		trans = new Transform();
	}
	/* modify mark */
	public TableInfo getTableInfo(String tableName){
		TableInfo ti= null;
		try{
			ti = getTableFt(tableName)[0].tInfo;
		}catch (Exception e) {
			System.out.println("getTableInfo err");
		}
		return ti;
	}
	
	/* modify mark */
	public boolean tableExists (String tableName){
		return (tableStructIndex.get(tableName) != null);
	}
	
	// modify mark
	public int CreateTable(TableInfo tableInfo) {
		TableFeature ft = new TableFeature(tableInfo);
		return CreateTable(ft);
	}
	public static void main(String[] args) {
		System.out.println("Program start..........................");// Decimal????

		RecordManager rm = null;
		rm = new RecordManager(1 * 1024 * 1024, 1, "test21");
//		 rm = new RecordManager("test17");
//		 rm = new RecordManager();
		String tableName = "student0";
		AttrDataType[] type = { new AttrDataType(Const.VARCHAR, 10, 2, 3),
				new AttrDataType(Const.INT, 5, 6, 7),
				new AttrDataType(Const.FLOAT, 10, 2, 3),
				new AttrDataType(Const.DOUBLE, 10, 2, 3),
				new AttrDataType(Const.DECIMAL, 10, 6, 3),
				new AttrDataType(Const.BOOL, 10, 2, 3),
				new AttrDataType(Const.CHAR, 10, 2, 3) };
		String[] attrName = { "name", "ID", "Name2", "Name3", "Name4", "Name5",
				"Name6" };
		String[] defaultValue = { "wxd", "234", "$null", "1123.4", "655.345",
				"true", "$null" };
		String[] attrValue = { "$null", "5080", "12.57", "1123.5", "655.344",
				"false", "$null" };
		int[] constraint = { 0, 1, 2, 1, 2, 3, 1 };
		TableFeature t = new TableFeature(tableName, type, attrName,
				defaultValue, constraint);
		 rm.CreateTable(t);
		Record r = new Record(attrName, attrValue);
		
		AttrDataType[] type1 = { new AttrDataType(Const.CHAR, 2, 2, 3),
				new AttrDataType(Const.INT, 5, 6, 7),
				new AttrDataType(Const.CHAR, 8, 2, 3) };
		String[] attrName1 = { "name", "MODEL", "Name2"};
		String[] defaultValue1 = { "$null", "$null", "$null"};
		String[] attrValue1 = { "A","1001","PC"};
		int[] constraint1 = { 0, 1, 2 };
		TableFeature t1 = new TableFeature("pRODUCT", type1, attrName1,
				defaultValue1, constraint1);
		 rm.CreateTable(t1);
		 Record r1 = new Record(attrName1, attrValue1);
		 rm.InsertRecord("pRODUCT", r1);
//		 String []re=rm.RecordToString(r, "student0");
//		 Record r1=rm.StringToRecord(re[0],t);
//		 System.out.println(re.length+"&&&&&"+re[0]);
//		 System.out.println(r1.attrName[0]);
//		 System.out.println(r1.attrName[1]);
//		 System.out.println(r1.attrValue[0]);
//		 System.out.println(r1.attrValue[1]);
//		 System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//		 for(int i=0;i<7;i++)
//			 System.out.println(r1.attrValue[i]);
		// rm.InsertRecord("student0", r);
		// Record[] mr=rm.getRecord("student0");
		// System.out.println("mr.length: "+mr.length);
		// System.out.println(rm.RecordToString(mr[0],"student0")[0].length());
		// for(int i=0;i<mr.length;i++){
		// System.out.println(rm.RecordToString(mr[i],"student0")[0]);
		// }

//		for (int i = 0; i < 1; i++) {
//			System.out
//					.println("…………………………………………………………………………………………………………………………");
//			tableName = "student" + i;
//			TableFeature tf = new TableFeature(tableName, type, attrName,
//					defaultValue, constraint);
//			rm.CreateTable(tf);
//			for (int j = 0; j < 100; j++)
//				rm.InsertRecord(tableName, r);
//			 try {
//			 rm.DeleteTable(tableName);
//			 } catch (UnsupportedEncodingException e) {
//			 // TODO Auto-generated catch block
//			 e.printStackTrace();
//			 }
//			// int k= rm.getNextFreeBlk(-1);
//			// rm.freePage(k);
//		}
		Record[] mr = rm.getRecord("pRODUCT");
		System.out.println("…………………………………………………………………………………………………………………………");
		System.out.println("mr.length: " + mr.length + " "
				+ mr[0].attrName.length);
		System.out.println(mr[0].attrName[0]);
		System.out.println(mr[0].attrName[1]);
		System.out.println(mr[0].attrValue[0]);
		System.out.println(mr[0].attrValue[1]);
		System.out.println(mr[0].attrValue[2]);
//		System.out.println(mr[0].attrValue[3]);
//		System.out.println(mr[0].attrValue[4]);
//		System.out.println(mr[0].attrValue[5]);
//		System.out.println(mr[0].attrValue[6]);
//
//		// System.out.println("(((((((((((((("+rm.RecordToString(mr[0],"student0")[0].length());
//		// rm.DeleteTable("student2002");*/
//		System.out.println("…………………………………………………………………………………………………………………………");
//		byte[] delete = new byte[50];
//		for (int j = 0; j < 50; j++)
//			delete[j] = 1;
//		// for(int j=0;j<50;j++)
//		rm.DeleteRecord(tableName, delete);
//		mr = rm.getRecord("student0");
//		if(mr!=null)
//		System.out.println("mr.length: " + mr.length);

//		 String m;
//		 System.out.println(m=rm.TableFtToString(t));
//		 System.out.println(m.length());
//		 TableFeature mytf=rm.StringToTableFt(m);
//		 System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<");
//		 System.out.println(mytf.tInfo.tableName);
//		 for(int i=0;i<7;i++)
//			 System.out.println(mytf.tInfo.attrNames[i]);
//		 System.out.println(mytf.tInfo.attrDefaultValues[0]);
//		 System.out.println(mytf.tInfo.attrDefaultValues[1]);
//		 System.out.println(mytf.tInfo.attrDefaultValues[2]);
//		 System.out.println(mytf.tInfo.attrDefaultValues[3]);
//		 System.out.println(mytf.tInfo.attrDefaultValues[4]);
//		 System.out.println(mytf.tInfo.attrDefaultValues[5]);
//		 System.out.println(mytf.tInfo.attrDefaultValues[6]);
//		 System.out.println(mytf.tInfo.attrDataTypes[0].type);
//		 System.out.println(mytf.tInfo.attrConstraints[0]);
//		 for(int i=0;i<7;i++)
//			 System.out.println(mytf.tInfo.attrConstraints[i]);
//		 System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<");
//		 String fi=rm.TableFtToString(mytf);
//		 System.out.println(fi);
//		 System.out.println(fi.length());
//		 if(fi.equals(m)) System.out.println("true");
//		 else System.out.println("false");
//		 System.out.println("…………………………………………………………………………………………………………………………………………");

		// System.out.println("****************************************************************");
		// for(int i=0;i<100;i++){
		// String tbl = (String)rm.tableStructIndex.get("student"+i);
		// if(tbl!=null){
		// System.out.print(rm.StringToTableFt(tbl).size+" ");
		// System.out.print(rm.StringToTableFt(tbl).curPos+" ");
		// System.out.print(rm.StringToTableFt(tbl).firstPage+" ");
		// System.out.print(rm.StringToTableFt(tbl).tableCount+" ");
		// System.out.println(rm.StringToTableFt(tbl).nextTable);
		// }
		// }

		try {
			rm.updateDisk();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * byte[] buffer = new byte[4096]; rm.mydiskMgr.ReadPage((2), buffer);
		 * System.out.println("\r\n\r\n\r\n\r\n"); for (int i = 0; i < 4096;
		 * i++) { System.out.println(buffer[i]); }
		 */
	}

	/**
	 * 将超块和位图的缓存数据修改后，将该部分信息写回磁盘
	 * 
	 * @return 0为写回成功；1为写回失败
	 * @throws UnsupportedEncodingException
	 */
	public int updateDisk() throws UnsupportedEncodingException {
		System.out.println("tableStructIndex size: " + tableStructIndex.size());
		System.out.println(sizeOfDatabase + " " + blkSize + " " + blkCount
				+ " " + bitmapSize + " " + nextFreeTableStruct + " "
				+ nextFreeVchar + " " + nextFreeBlk);
		int[] input = new int[7];
		input[0] = sizeOfDatabase;
		input[1] = blkSize;
		input[2] = blkCount;
		input[3] = bitmapSize;
		input[4] = nextFreeTableStruct;
		input[5] = nextFreeVchar;
		input[6] = nextFreeBlk;
		trans.IntToByte(input, fileHandle.$buffer);
		String cache = new String(fileHandle.$buffer, "8859_1");
		for (int i = 0; i < 1 + bitmapSize; i++) {
			if (mydiskMgr.WritePage(i, cache.substring(i * realBlkSize, (i + 1)
					* realBlkSize)) == false) {
				System.out
						.println("RecordManager.updateDisk(): Write page error");
				System.exit(-1);
			}
		}
		return 0;
	}

	/**
	 * Database be firstly created . Initialize the global arguments
	 * 
	 * @param sizeOfDB
	 *            数据库的大小(byte)
	 * @param blkCount1
	 *            该数据库中每个块包含几个page，默认值为1
	 * @param fileName
	 *            数据库的名字
	 */
	public RecordManager(int sizeOfDB, int blkCount1, String fileName) {
		trans = new Transform();
		sizeOfDatabase = sizeOfDB;
		blkSize = 4096;
		blkCount = blkCount1;
		realBlkSize = blkCount * blkSize;
		mydiskMgr = new DiskManager("c:/database");
		if (!mydiskMgr.CreateFile(fileName, sizeOfDB / 1024))
			System.exit(-1);
		fileHandle = mydiskMgr.OpenFile(fileName);

		bitmapSize = sizeOfDatabase % (blkSize * blkSize * 8 * blkCount) == 0 ? sizeOfDatabase
				/ (blkSize * blkSize * 8 * blkCount)
				: sizeOfDatabase / (blkSize * blkSize * 8 * blkCount) + 1;
		nextFreeTableStruct = (bitmapSize + 1) * realBlkSize + 2;
		nextFreeVchar = (bitmapSize + 2) * realBlkSize + 2;
		nextFreeBlk = bitmapSize + 3;
		int[] input = new int[7];
		input[0] = sizeOfDatabase;
		input[1] = blkSize;
		input[2] = blkCount;
		input[3] = bitmapSize;
		input[4] = nextFreeTableStruct;
		input[5] = nextFreeVchar;
		input[6] = nextFreeBlk;
		fileHandle.$buffer[4096] = 0x1f;
		trans.IntToByte(input, fileHandle.$buffer);
		byte[] temp = new byte[8];
		for (int i = 0; i < 4; i++) {
			temp[i] = -1;
			temp[4 + i] = -1;
		}
		try {
			String next = new String(temp, "8859_1");
			mydiskMgr.WriteData((bitmapSize + 1) * 4096 + 4088, next);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* IW */
		System.out.println(sizeOfDatabase + " " + blkSize + " " + blkCount
				+ " " + bitmapSize + " " + nextFreeTableStruct + " "
				+ nextFreeVchar + " " + nextFreeBlk);

	}

	/**
	 * Database has already existed, and is opened now
	 * 
	 * @param fileName
	 *            数据库名字
	 */
	public RecordManager(String fileName) {
		trans = new Transform();
		realBlkSize = blkCount * blkSize;
		mydiskMgr = new DiskManager("c:/database");
		if (mydiskMgr.Get_Hm().containsKey(fileName) == false) {
			System.out.println("Database doesn't exist");
			System.exit(-1);
			return;
		}
		fileHandle = mydiskMgr.OpenFile(fileName);
		int[] input = new int[7];
		byte[] b = new byte[4];
		for (int i = 0; i < 7; i++) {
			b[0] = fileHandle.$buffer[0 + 4 * i];
			b[1] = fileHandle.$buffer[1 + 4 * i];
			b[2] = fileHandle.$buffer[2 + 4 * i];
			b[3] = fileHandle.$buffer[3 + 4 * i];
			input[i] = trans.ByteToInt(b);
		}
		sizeOfDatabase = input[0];
		blkSize = input[1];
		blkCount = input[2];
		bitmapSize = input[3];
		nextFreeTableStruct = input[4];
		nextFreeVchar = input[5];
		nextFreeBlk = input[6];
		realBlkSize = blkCount * blkSize;
		int[] blk = new int[1];
		blk[0] = (bitmapSize + 1);
		/*
		 * File fi=new File("E:/result.txt"); FileWriter fw=null; try { fw=new
		 * FileWriter(fi,true); } catch (IOException e) { }// TODO
		 * Auto-generatedcatch block e.printStackTrace(); }
		 */

		while (blk[0] != -1) {
			// System.out.println(blk[0]);
			Map t = getTableOfBlk(blk);
			for (Iterator it = t.keySet().iterator(); it.hasNext();) {
				int addr = (Integer) it.next();
				String ct = (String) t.get(addr);
	//			System.out.println(ct);
				String key = ct.substring(0, Const.MaxNameLength).trim();
				// System.out.println("*********"+key);
				tableStructIndex.put(key, ct);
				tableAddrIndex.put(key, addr);
			}

		}
		System.out.println("tableStructIndex size: " + tableStructIndex.size());
		System.out.println(sizeOfDatabase + " " + blkSize + " " + blkCount
				+ " " + bitmapSize + " " + nextFreeTableStruct + " "
				+ nextFreeVchar + " " + nextFreeBlk);
	}

	/**
	 * 初始化时使用，获取某个数据块里的全部数据字典信息
	 * 
	 * @param blk
	 *            blk[0]为目的块号
	 * @return HashMap((Integer)addr,(String) TableFt) addr - 字节地址 ； TableFt -
	 *         表示数据字典中某个Table信息的字符串
	 */
	private Map getTableOfBlk(int[] blk) {
		String totalBuf;
		Map table = new HashMap<Integer, String>();
		byte[] buf = new byte[4096];
		mydiskMgr.ReadPage(blk[0], buf);
		try {
			totalBuf = new String(buf, "8859_1");
			int tableNum;
			byte[] temp = new byte[4];
			temp[0] = buf[0];
			temp[1] = buf[1];
			temp[2] = 0;
			temp[3] = 0;
			tableNum = trans.ByteToInt(temp);
			int count = 0;
			int i = 0;
			int start, end;
			byte[] temp1 = new byte[4];
			while (count < tableNum) {
				temp[0] = buf[4 * i + 2];
				temp[1] = buf[4 * i + 3];
				temp[2] = 0;
				temp[3] = 0;
				temp1[0] = buf[4 * i + 4];
				temp1[1] = buf[4 * i + 5];
				temp1[2] = 0;
				temp1[3] = 0;
				start = trans.ByteToInt(temp);
				end = trans.ByteToInt(temp1);
				if (!(temp1[0] == -1 && temp1[1] == -1)) {
					count++;
					table.put(blk[0] * realBlkSize + 4 * i + 2, totalBuf
							.substring(start, end));
				}
				i++;
			}
			for (int j = 0; j < 4; j++)
				temp[j] = buf[4088 + j];
			blk[0] = trans.ByteToInt(temp);
		} catch (Exception e) {
			System.out.println(" Map getTableOfBlk err");
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 创建一个新的Table
	 * 
	 * @param characteristic
	 *            描述Table信息的对象
	 * @return 0表示成功，-1表示失败
	 */
	public int CreateTable(TableFeature characteristic) {
		if (tableStructIndex.containsKey(characteristic.tInfo.tableName)) {
			System.out.println("Table: (" + characteristic.tInfo.tableName
					+ ") already existed");
			return -1;
		}
		int result = 0;
		try {
			result = getNextFreeTableStruct(characteristic);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result == -1)
			System.out.println("getNextFreeTableStruct(characteristic) err");
		return result;

	}

	/**
	 * 删除Table时调用，删除Table所有块
	 * 
	 * @param firstBlk
	 *            要删除Table对应的第一个数据块的块号
	 * @return 0表示成功，-1表示失败
	 */
	private int deleteAllBlkOfTable(int firstBlk) {
		// System.out.println("deleteAllBlkOfTable called");
		int blk = firstBlk;
		while (blk != -1)
			blk = DeleteBlkOfTable(blk);
		return 0;
	}

	/**
	 * 删除Table时调用,删除某个块
	 * 
	 * @param blk
	 *            删除Table时要删除的块
	 * @return 该块的下一块
	 */
	private int DeleteBlkOfTable(int blk) {
		String totalBuf;
		byte[] buf = new byte[4096];
		mydiskMgr.ReadPage(blk, buf);
		byte[] temp = new byte[4];
		for (int j = 0; j < 4; j++)
			temp[j] = buf[4088 + j];
		int nextblk = trans.ByteToInt(temp);
		// System.out.println("DeleteBlkOfTable: "+blk);
		freePage(blk);
		return nextblk;
	}

	/**
	 * 删除Table及其表项
	 * 
	 * @param tableName
	 *            要删除的表的名字
	 * @return 0表示删除成功；-1表示删除失败
	 * @throws UnsupportedEncodingException
	 */
	public int DeleteTable(String tableName)
			throws UnsupportedEncodingException {
		int tableAddr = (Integer) tableAddrIndex.get(tableName) != null ? (Integer) tableAddrIndex
				.get(tableName)
				: -1;
		if (tableAddr == -1) {
			System.out
					.println("DiskManager.DeleteTable(String tableName): no such table "
							+ tableName);
			return -1;
		}
		String tableft = (String) tableStructIndex.get(tableName);
		TableFeature tf = StringToTableFt(tableft);
		boolean exist = false;
		for (int i = 0; i < tf.size; i++) {
			if (tf.tInfo.attrDataTypes[i].type == Const.VARCHAR) {
				exist = true;
			}
		}
		if (exist == true) {

		}

		byte[] buf = new byte[realBlkSize];
		if (mydiskMgr.ReadPage(tableAddr / realBlkSize, buf) == false)
			return -1;
		byte[] numOfS = new byte[4];
		numOfS[0] = buf[0];
		numOfS[1] = buf[1];
		numOfS[2] = 0;
		numOfS[3] = 0;
		int numOfS1 = trans.ByteToInt(numOfS);
		int blkToDel = StringToTableFt((String) tableStructIndex.get(tableName)).firstPage;
		if (blkToDel != -1)
			deleteAllBlkOfTable(blkToDel);
		if (numOfS1 == 1
				&& nextFreeTableStruct / realBlkSize != tableAddr / realBlkSize
				&& tableAddr / realBlkSize != bitmapSize + 1) {
			// System.out.println("freepage: " + tableAddr / realBlkSize);
			byte[] nextS = new byte[4];
			for (int i = 0; i < 4; i++) {
				nextS[i] = buf[realBlkSize - 8 + i];
			}
			byte[] lastS = new byte[4];
			for (int i = 0; i < 4; i++) {
				lastS[i] = buf[realBlkSize - 4 + i];
			}
			String s = new String(nextS, "8859_1");
			String s1 = new String(lastS, "8859_1");
			int lastS1 = trans.ByteToInt(lastS);
			int nextS1 = trans.ByteToInt(nextS);
			// System.out.println("WriteData: intolast-" + nextS1 + " "
			// + " intonext-" + lastS1);
			mydiskMgr.WriteData(lastS1 * realBlkSize + 4096 - 8, s);
			mydiskMgr.WriteData(nextS1 * realBlkSize + 4096 - 4, s1);
			tableAddrIndex.remove(tableName);
			tableStructIndex.remove(tableName);
			freePage(tableAddr / realBlkSize);
			return 0;
		} else {
			short[] mark = new short[1];
			mark[0] = -1;
			byte[] mark1 = new byte[2];
			trans.ShortToByte(mark, mark1);
			buf[tableAddr % realBlkSize + 2] = mark1[0];
			buf[tableAddr % realBlkSize + 3] = mark1[1];
			numOfS1--;
			trans.IntToByte(numOfS1, numOfS);
			buf[0] = numOfS[0];
			buf[1] = numOfS[1];
			String s = new String(buf, "8859_1");
			if (mydiskMgr.WritePage(tableAddr / realBlkSize, s) == false)
				return -1;
			tableAddrIndex.remove(tableName);
			tableStructIndex.remove(tableName);
			return 0;
		}
	}

	/* Insert a tuple into a table */
	/**
	 * 朝一个Table里插入一个新的表项
	 * 
	 * @param tableName
	 *            Table的名字
	 * @param record
	 *            要插入的表项
	 * @return 0表示插入成功，-1表示插入失败
	 */
	public int InsertRecord(String tableName, Record record) {
		String tableStr = (String) tableStructIndex.get(tableName);
		if (tableStr == null) {
			System.out.println("tableStructIndex get " + tableName + " error");
			return -1;
		}
		TableFeature tf = StringToTableFt(tableStr);
		// System.out.println(tf.size);
		// System.out.println(tf.curPos);
		// System.out.println(tf.firstPage);
		// System.out.println(tf.nextTable);
		String[] rString = RecordToString(record, tableName);
		// System.out.println("Insert "+rString[0]);
		if (tf.nextTable == -1) {
			if (rString[0].length() > 4082) {
				System.out.println("Current record length exceeds a block");
				return -1;
			}
			if (tf.firstPage == -1) {
				// System.out.println("tf.firstPage==-1");
				tf.firstPage = getNextFreeBlk(-1);
				tf.curPos = tf.firstPage * 4096 + 2;
			}
			try {
				insertRecordIntoATable(tf, rString[0]);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updateTableFt((Integer) tableAddrIndex.get(tableName),
					TableFtToString(tf));
			tableStructIndex.put(tableName, TableFtToString(tf));
		} else {
			TableFeature[] tfList = getTableFt(tableName);
			if (tfList.length < 1 || tfList[0] == null) {
				System.out.println("InsertRecord: Trying to getTableFt err");
				return -1;
			}
			for (int i = 0; i < tfList.length; i++) {
				if (tfList[i].firstPage == -1) {
					tfList[i].firstPage = getNextFreeBlk(-1);
					tfList[i].curPos = tfList[i].firstPage * 4096 + 2;
				}
				try {
					insertRecordIntoATable(tfList[i], rString[i]);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i == 0) {
					updateTableFt((Integer) tableAddrIndex.get(tableName),
							TableFtToString(tfList[i]));
					tableStructIndex.put(tableName, TableFtToString(tfList[i]));
				} else
					updateTableFt(tfList[i - 1].nextTable,
							TableFtToString(tfList[i]));

			}
		}
		return 0;
	}

	/**
	 * 更新数据字典里某表对应的信息
	 * 
	 * @param pos
	 *            Table的字节位置
	 * @param input
	 *            更新后的字符串
	 * @return 0表示成功，-1表示失败
	 */
	private int updateTableFt(int pos, String input) {
		// System.out.println("updateTableFt: "+input);
		byte[] buf = new byte[4096];
		if (mydiskMgr.ReadPage(pos / 4096, buf) == false) {
			System.out.println("updateTableFt: ReadPage err " + pos);
			return -1;
		}
		try {
			String bufStr = new String(buf, "8859_1");
			int relative = pos % 4096;
			byte[] tmp = new byte[4];
			tmp[2] = 0;
			tmp[3] = 0;
			tmp[0] = buf[relative];
			tmp[1] = buf[relative + 1];
			int start = trans.ByteToInt(tmp);
			tmp[0] = buf[relative + 2];
			tmp[1] = buf[relative + 3];
			int end = trans.ByteToInt(tmp);
			int oriLength = end - start;
			int newLength = input.length();
			if (newLength > oriLength) {
				System.out.println("updateTableFt: newLength>oriLength");
				return -1;
			}
			// String oriStr=bufStr.substring(start, end);
			// bufStr=bufStr.replaceFirst(oriStr, input);
			// System.out.println(StringToTableFt(bufStr.substring(start,
			// end)).curPos);
			if (mydiskMgr.WriteData((pos / 4096) * 4096 + start, input) == false) {
				System.out.println("updateTableFt: WritePage err " + pos);
				return -1;
			}
		} catch (Exception e) {
			System.out.println("unsupported encode");
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 增加列属性使得一个Table可能对应多个表区，在某个表区tf中插入该表区对应表项
	 * 
	 * @param tf
	 *            Table对应表区属性
	 * @param record
	 *            要插入的表项
	 * @return 0表示插入成功，1表示插入失败
	 * @throws UnsupportedEncodingException
	 */
	private int insertRecordIntoATable(TableFeature tf, String record)
			throws UnsupportedEncodingException {

		String s = record;
		int numOfRecord = 0;
		byte[] buf = new byte[realBlkSize];
		mydiskMgr.ReadPage(tf.curPos / realBlkSize, buf);
		int length = s.length();
		byte[] ptr = new byte[4];
		if (tf.curPos % realBlkSize >= 6) {
			ptr[0] = buf[tf.curPos % realBlkSize - 4];
			ptr[1] = buf[tf.curPos % realBlkSize - 3];
		} else {

			short[] sh = new short[1];
			sh[0] = 4096 - 8;
			trans.ShortToByte(sh, ptr);
		}
		ptr[2] = 0;
		ptr[3] = 0;
		int start;
		if (tf.curPos % realBlkSize != 2) {

			start = trans.ByteToInt(ptr);
			byte[] numOfT = new byte[4];
			numOfT[0] = buf[0];
			numOfT[1] = buf[1];
			numOfT[2] = 0;
			numOfT[3] = 0;
			numOfRecord = trans.ByteToInt(numOfT);
			int relativePos = tf.curPos % realBlkSize;
			byte[] end = new byte[2];
			byte[] st = new byte[4];
			st[2] = 0;
			st[3] = 0;
			;
			int i = 0;
			// System.out.println(relativePos);
			for (end[0] = buf[relativePos - 1], end[1] = buf[relativePos - 2]; end[0] == -1
					&& end[1] == -1; i += 4, end[0] = buf[relativePos - 1 - i], end[1] = buf[relativePos
					- 2 - i]) {
				if (relativePos - 1 - i == 5) {
					tf.curPos = tf.curPos - 4;
					start = 4088;
				} else {
					tf.curPos -= 4;
					st[0] = buf[relativePos - 8 - i];
					st[1] = buf[relativePos - 7 - i];
					start = trans.ByteToInt(st);
				}
			}
		} else {
			int next = -1;
			byte[] tempo = new byte[4];
			trans.IntToByte(next, tempo);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = tempo[i];
			}
			numOfRecord = 0;
			start = realBlkSize - 8;
			if (length + 4 > realBlkSize - 2) {
				System.out.println("TableFeature exceeds a block");
				return -1;
			}
		}
		byte[] cur = s.getBytes("8859_1");
		if (tf.curPos % realBlkSize + 4 + length > start) {
			int temp = tf.curPos;
//			System.out.println("insertRecordIntoATable: " + tf.tInfo.tableName);
			tf.curPos = getNextFreeBlk(tf.curPos / realBlkSize) * realBlkSize
					+ 2;
			byte[] temp1 = new byte[4];
			trans.IntToByte(tf.curPos / realBlkSize, temp1);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = temp1[i];
			}
			mydiskMgr.WritePage(temp / realBlkSize, new String(buf, "8859_1"));
			mydiskMgr.ReadPage(tf.curPos / realBlkSize, buf);
			numOfRecord = 0;
			start = realBlkSize - 8;
		}
		short[] curPtr = new short[2];
		curPtr[0] = (short) (start - length);
		curPtr[1] = (short) (start);
		byte[] curB = new byte[4];
		trans.ShortToByte(curPtr, curB);
		int[] numOfT = new int[1];
		numOfT[0] = numOfRecord;
		numOfT[0]++;
		byte[] numOfT1 = new byte[4];
		trans.IntToByte(numOfT, numOfT1);
		buf[0] = numOfT1[0];
		buf[1] = numOfT1[1];
		for (int i = 0; i < 4; i++) {
			buf[tf.curPos % realBlkSize + i] = curB[i];
		}
		for (int i = 0; i < cur.length; i++) {
			buf[((int) curPtr[0] + i)] = cur[i];
		}
		int oriAddr = tf.curPos / realBlkSize;
		if (tf.curPos % realBlkSize + 4 + length == start) {
//			System.out.println("insertRecordIntoATable: " + tf.tInfo.tableName);
			tf.curPos = getNextFreeBlk(tf.curPos / realBlkSize) * realBlkSize
					+ 2;
			byte[] temp2 = new byte[4];
			trans.IntToByte(tf.curPos / realBlkSize, temp2);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = temp2[i];
			}
		} else
			tf.curPos += 4;

		String byteBuffer = new String(buf, "8859_1");
		mydiskMgr.WritePage(oriAddr, byteBuffer);
		return 0;
	}

	/**
	 * 获取某个Table对应的所有表项
	 * 
	 * @param tableName
	 *            Table名字
	 * @return 表项对应的数组
	 */
	public Record[] getRecord(String tableName) {
		TableFeature[] tft = getTableFt(tableName);
		Record[][] myRecord = null;
		if (tft == null) {
			System.out.println("getRecord: " + tableName + " doesn't exist");
			return null;
		}
		if (tft[0] != null) {
			myRecord = new Record[tft.length][tft[0].tableCount];
		} else {
			System.out.println("getRecord: " + tableName + " doesn't exist");
			return null;
		}
		for (int i = 0; i < tft.length; i++) {
			// for(int j=0; j<tft[0].tableCount; ){
			myRecord[i] = getRecordOfATable(tft[i].firstPage, tft[i]);
			// System.out.println("+++++++++++++++++++++++++"+tft[0].tableCount);
			// System.out.println("*************************"+myRecord[i].length);

			// }
		}
//		System.out.println("myRecord[0].length "+myRecord[0].length);
		return combineRecord(myRecord);
	}

	/**
	 * 如果一个Table对应多个表区，将不同表区对应的表项合并起来
	 * 
	 * @param rList
	 *            不同表区表项数组
	 * @return Table最后的表项;NULL表示空
	 */
	private Record[] combineRecord(Record[][] rList) {
		Record[] result = new Record[rList[0].length];
		int length = 0;
		if( result.length==0) return null;
		for (int j = 0; j < rList.length; j++)
			length += rList[j][0].attrName.length;
		for (int i = 0; i < result.length; i++) {
			String[] name = new String[length];
			String[] value = new String[length];
			int index = 0;
			for (int j = 0; j < rList.length; j++) {
				for (int n = 0; n < rList[j][i].attrName.length; n++) {
					name[index] = rList[j][i].attrName[n];
					value[index] = rList[j][i].attrValue[n];
					index++;
				}
			}
			result[i] = new Record(name, value);
		}
		return result;
	}

	/**
	 * 获得Table某个表区的所有表项
	 * 
	 * @param firstBlk
	 *            该表区的起始块号
	 * @param tf
	 *            表区的属性
	 * @return 表区的所有表项
	 */
	private Record[] getRecordOfATable(int firstBlk, TableFeature tf) {
		ArrayList<Record> rList = new ArrayList<Record>();
		int[] blk = new int[1];
		blk[0] = firstBlk;
		while (blk[0] != -1) {
			getRecordOfBlk(blk, rList, tf);
			// System.out.println("getRecordOfATable: "+rList.size());
		}

		Record[] rArray = new Record[rList.size()];
		for (int i = 0; i < rList.size(); i++)
			rArray[i] = rList.get(i);
		return rArray;
	}

	/**
	 * 获得Table某表区在blk[0]中的所有表项
	 * 
	 * @param blk
	 *            blk[0]为块号
	 * @param rList
	 *            存储表项
	 * @param ft
	 *            表区属性
	 * @return 0表示成功，-1表示失败
	 */
	private int getRecordOfBlk(int[] blk, ArrayList<Record> rList,
			TableFeature ft) {
		try {
			String totalBuf;
			byte[] buf = new byte[4096];
			mydiskMgr.ReadPage(blk[0], buf);
			totalBuf = new String(buf, "8859_1");
			int tableNum;
			byte[] temp = new byte[4];
			temp[0] = buf[0];
			temp[1] = buf[1];
			temp[2] = 0;
			temp[3] = 0;
			tableNum = trans.ByteToInt(temp);
			int count = 0;
			int i = 0;
			int start, end;
			byte[] temp1 = new byte[4];
			while (count < tableNum) {
				temp[0] = buf[4 * i + 2];
				temp[1] = buf[4 * i + 3];
				temp[2] = 0;
				temp[3] = 0;
				temp1[0] = buf[4 * i + 4];
				temp1[1] = buf[4 * i + 5];
				temp1[2] = 0;
				temp1[3] = 0;
				start = trans.ByteToInt(temp);
				end = trans.ByteToInt(temp1);
				if (!(temp1[0] == -1 && temp1[1] == -1)) {
					count++;
//					System.out.println("--------------------------"+totalBuf.substring(start, end));
					rList.add(StringToRecord(totalBuf.substring(start, end),
									ft));
				}
				i++;
			}
			for (int j = 0; j < 4; j++)
				temp[j] = buf[4088 + j];
			blk[0] = trans.ByteToInt(temp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" getRecordOfBlk err");
			return -1;
		}
		return 0;
	}

	/* Delete a tuple from a table */
	public int DeleteRecord(String tableName, byte[] delete) {
		TableFeature[] tfs = getTableFt(tableName);
		if (tfs == null) {
			System.out.println("DeleteRecord: getTableFt err");
			return -1;
		}
		int size = tfs.length;
		for (int i = 0; i < size; i++) {
			DeleteTableColumn(tfs[i], delete);
		}
		return 0;
	}

	private int DeleteTableColumn(TableFeature tf, byte[] delete) {
		int[] blk = new int[2];
		blk[0] = tf.firstPage;
		blk[1] = 0;
		while (blk[0] != -1) {
			if (deleteRecordInABlk(blk, delete) == -1)
				return -1;
		}
		return 0;
	}

	private int deleteRecordInABlk(int[] blk, byte[] delete) {
		String totalBuf;
		byte[] buf = new byte[4096];
		mydiskMgr.ReadPage(blk[0], buf);
		boolean goNextBlk=true;
		try {
			int curblk = blk[0];
			int tableNum;
			int destTableNum;
			byte[] temp = new byte[4];
			temp[0] = buf[0];
			temp[1] = buf[1];
			temp[2] = 0;
			temp[3] = 0;
			tableNum = trans.ByteToInt(temp);
			destTableNum = tableNum;
			int count = 0;
			int i = 0;
//			System.out.println("tableNum " + tableNum);
			while (count < tableNum) {
//				System.out.println(blk[1] + count + " " + blk[1] + blk[0]);
				if (!(buf[4 * i + 4] == -1 && buf[4 * i + 5] == -1)) {
					if (blk[1] + count < delete.length) {
						if (delete[blk[1] + count] == 1) {
							buf[4 * i + 4] = -1;
							buf[4 * i + 5] = -1;
							destTableNum--;
						}
					}
					else{
						goNextBlk=false;
						break;
					}
					count++;
				}
				i++;
			}
			for (int j = 0; j < 4; j++)
				temp[j] = buf[4088 + j];
			blk[0] = trans.ByteToInt(temp);
			if(!goNextBlk) blk[0]=-1;
			blk[1] += tableNum;
			trans.IntToByte(destTableNum, temp);
			buf[0] = temp[0];			
			buf[1] = temp[1];
			totalBuf = new String(buf, "8859_1");
			mydiskMgr.WritePage(curblk, totalBuf);
		} catch (Exception e) {
			System.out.println(" deleteRecordInABlk err");
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	/*
	 * Search all tuples from tableName that satisfy condition , Update them as
	 * record describes
	 */
	public int UpdateRecord(String tableName, Record [] record, byte[] changed, boolean [] isChanged) {
		TableFeature[] tfs = getTableFt(tableName);
		if (tfs == null) {
			System.out.println("DeleteRecord: getTableFt err");
			return -1;
		}
		int size = tfs.length;
		String [][] input = new String [tfs.length][record.length];
		for(int i=0 ; i<record.length ;i++){
			String [] tmp=RecordToString(record[i],tableName);
			for(int j=0;j<tmp.length;j++){
				input[j][i]=tmp[j];
			}
		}
		for (int i = 0; i < size; i++) {
			UpdateTableColumn(tfs[i], input[i] ,changed, isChanged);
		}
		return 0;
	}
	
	private int UpdateTableColumn(TableFeature tf, String[] input, byte[] changed, boolean[] VChanged) {
		int[] blk = new int[2];
		blk[0] = tf.firstPage;
		blk[1] = 0;
		while (blk[0] != -1) {
			if (UpdateRecordInABlk(tf ,blk, input,changed,VChanged) == -1)
				return -1;
		}
		return 0;
	}

	private int UpdateRecordInABlk(TableFeature tf, int[] blk, String[] input, byte[] changed, boolean[] VChanged) {
		String totalBuf;
		byte[] buf = new byte[4096];
		mydiskMgr.ReadPage(blk[0], buf);
		boolean goNextBlk=true;
		try {
			int curblk = blk[0];
			int tableNum;
			byte[] temp = new byte[4];
			temp[0] = buf[0];
			temp[1] = buf[1];
			temp[2] = 0;
			temp[3] = 0;
			tableNum = trans.ByteToInt(temp);
			int count = 0;
			int i = 0;
//			System.out.println("tableNum " + tableNum);
			while (count < tableNum) {
//				System.out.println(blk[1] + count + " " + blk[1] + blk[0]);
				if (!(buf[4 * i + 4] == -1 && buf[4 * i + 5] == -1)) {
					if (blk[1] + count < changed.length) {
						if (changed[blk[1] + count] == 1) {
							temp[2]=0;
							temp[3]=0;
							temp[0]=buf[4*i+2];
							temp[1]=buf[4*i+3];
							int start=trans.ByteToInt(temp);
							temp[0]=buf[4*i+4];
							temp[1]=buf[4*i+5];
							int end=trans.ByteToInt(temp);
							byte[] bytes=input[blk[1] + count].getBytes("8859_1");
							for(int k=start;k<end;k++)
								buf[k]=bytes[k-start];							
						}
					}
					else{
						goNextBlk=false;
						break;
					}
					count++;
				}
				i++;
			}
			for (int j = 0; j < 4; j++)
				temp[j] = buf[4088 + j];
			blk[0] = trans.ByteToInt(temp);
			if(!goNextBlk) blk[0]=-1;
			blk[1] += tableNum;
			totalBuf = new String(buf, "8859_1");
			mydiskMgr.WritePage(curblk, totalBuf);
		} catch (Exception e) {
			System.out.println("UpdateRecordInABlk err");
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	/* Add new columns to Table tableName */
	public int UpdateAddColumn(String tableName, TableFeature tf) {
		TableFeature[] tfs=getTableFt(tableName);
		int addr=CreateTable(tf);
		tfs[tfs.length-1].nextTable=addr;
//		insertRecordIntoATable(tf, String record)
		if(tfs.length==1){
			
		}
		return 0;
	}

	/* File a table to take less space and in better order */
	public int TrimTable(String tableName) {
		
		 
		return 0;
	}

	/* File the whole database */
	/**
	 * 对整个数据库紧缩，减少空间占用，时间代价较大
	 * 
	 * @return 0成功，-1失败
	 */
	public int ToBetterDatabase() {
		return 0;
	}

	/**
	 * 释放某块，位图对应位清零
	 * 
	 * @param pageNum
	 *            块号
	 * @return 0成功，-1失败
	 */
	private int freePage(int pageNum) {
//		System.out.println("Free page: " + pageNum);
		byte[] buf = new byte[4096];
		int byteNum = pageNum / 8 + realBlkSize;
		int pos = pageNum % 8;
		fileHandle.$buffer[byteNum] ^= ((byte) 0x1 << pos);
		for (int i = 0; i < 4096; i++)
			buf[i] = 0;
		String s;
		try {
			s = new String(buf, "8859_1");
			mydiskMgr.WritePage(pageNum, s);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return -1;
		}
		if (pageNum < nextFreeBlk && pageNum > 0)
			nextFreeBlk = pageNum;
		return 0;
	}

	/**
	 * 将表区属性填入数据字典，修改数据字典插入位置
	 * 
	 * @param t
	 *            表区属性
	 * @return 成功则返回插入位置；失败返回-1
	 * @throws UnsupportedEncodingException
	 */
	private int getNextFreeTableStruct(TableFeature t)
			throws UnsupportedEncodingException {

		String s = TableFtToString(t);
		int numOfTable = 0;
		byte[] buf = new byte[realBlkSize];
		mydiskMgr.ReadPage(nextFreeTableStruct / realBlkSize, buf);
		int length = s.length();
		byte[] ptr = new byte[4];
		if (nextFreeTableStruct % realBlkSize >= 6) {
			ptr[0] = buf[nextFreeTableStruct % realBlkSize - 4];
			ptr[1] = buf[nextFreeTableStruct % realBlkSize - 3];
		} else {

			short[] sh = new short[1];
			sh[0] = 4096 - 8;
			trans.ShortToByte(sh, ptr);
		}
		ptr[2] = 0;
		ptr[3] = 0;
		int start;
		if (nextFreeTableStruct % realBlkSize != 2) {

			start = trans.ByteToInt(ptr);
			byte[] numOfT = new byte[4];
			numOfT[0] = buf[0];
			numOfT[1] = buf[1];
			numOfT[2] = 0;
			numOfT[3] = 0;
			numOfTable = trans.ByteToInt(numOfT);
			int relativePos = nextFreeTableStruct % realBlkSize;
			byte[] end = new byte[2];
			byte[] st = new byte[4];
			st[2] = 0;
			st[3] = 0;
			;
			int i = 0;
			for (end[0] = buf[relativePos - 1], end[1] = buf[relativePos - 2]; end[0] == -1
					&& end[1] == -1; i += 4, end[0] = buf[relativePos - 1 - i], end[1] = buf[relativePos
					- 2 - i]) {
				if (relativePos - 1 - i == 5) {
					nextFreeTableStruct = nextFreeTableStruct - 4;
					start = 4088;
				} else {
					nextFreeTableStruct -= 4;
					st[0] = buf[relativePos - 8 - i];
					st[1] = buf[relativePos - 7 - i];
					start = trans.ByteToInt(st);
				}
			}
		} else {
			int next = -1;
			byte[] tempo = new byte[4];
			trans.IntToByte(next, tempo);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = tempo[i];
			}
			numOfTable = 0;
			start = realBlkSize - 8;
			if (length + 4 > realBlkSize - 4) {
				System.out.println("TableFeature exceeds a block");
				return -1;
			}
		}
		byte[] cur = s.getBytes("8859_1");
		if (nextFreeTableStruct % realBlkSize + 4 + length > start) {
			int temp = nextFreeTableStruct;
			nextFreeTableStruct = getNextFreeBlk(nextFreeTableStruct
					/ realBlkSize)
					* realBlkSize + 2;
			byte[] temp1 = new byte[4];
			trans.IntToByte(nextFreeTableStruct / realBlkSize, temp1);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = temp1[i];
			}
			mydiskMgr.WritePage(temp / realBlkSize, new String(buf, "8859_1"));
			mydiskMgr.ReadPage(nextFreeTableStruct / realBlkSize, buf);
			numOfTable = 0;
			start = realBlkSize - 8;
		}
		short[] curPtr = new short[2];
		curPtr[0] = (short) (start - length);
		curPtr[1] = (short) (start);
		byte[] curB = new byte[4];
		trans.ShortToByte(curPtr, curB);
		int[] numOfT = new int[1];
		numOfT[0] = numOfTable;
		numOfT[0]++;
		byte[] numOfT1 = new byte[4];
		trans.IntToByte(numOfT, numOfT1);
		buf[0] = numOfT1[0];
		buf[1] = numOfT1[1];
		for (int i = 0; i < 4; i++) {
			buf[nextFreeTableStruct % realBlkSize + i] = curB[i];
		}
		for (int i = 0; i < cur.length; i++) {
			buf[((int) curPtr[0] + i)] = cur[i];
		}
		int oriTable = nextFreeTableStruct;
		int oriAddr = nextFreeTableStruct / realBlkSize;
		tableAddrIndex.put(t.tInfo.tableName, nextFreeTableStruct);
		tableStructIndex.put(t.tInfo.tableName, s);
		if (nextFreeTableStruct % realBlkSize + 4 + length == start) {
			nextFreeTableStruct = getNextFreeBlk(nextFreeTableStruct
					/ realBlkSize)
					* realBlkSize + 2;
			byte[] temp2 = new byte[4];
			trans.IntToByte(nextFreeTableStruct / realBlkSize, temp2);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = temp2[i];
			}
		} else
			nextFreeTableStruct += 4;

		String byteBuffer = new String(buf, "8859_1");
		mydiskMgr.WritePage(oriAddr, byteBuffer);

		return oriTable;
	}

	/**
	 * 将Vchar存储起来并修改存储下一个Vchar的位置
	 * 
	 * @param s
	 *            要存储的Vchar
	 * @return 存储Vchar的位置
	 * @throws UnsupportedEncodingException
	 */
	int getNextFreeVchar(String s) throws UnsupportedEncodingException {
		int numOfVchar = 0;
		byte[] buf = new byte[realBlkSize];
		mydiskMgr.ReadPage(nextFreeVchar / realBlkSize, buf);
		int length = s.length();
		byte[] ptr = new byte[4];
		if (nextFreeVchar % realBlkSize >= 6) {
			ptr[0] = buf[nextFreeVchar % realBlkSize - 4];
			ptr[1] = buf[nextFreeVchar % realBlkSize - 3];
		} else {

			short[] sh = new short[1];
			sh[0] = 4096 - 8;
			trans.ShortToByte(sh, ptr);
		}
		ptr[2] = 0;
		ptr[3] = 0;
		int start;
		if (nextFreeVchar % realBlkSize != 2) {

			start = trans.ByteToInt(ptr);
			byte[] numOfT = new byte[4];
			numOfT[0] = buf[0];
			numOfT[1] = buf[1];
			numOfT[2] = 0;
			numOfT[3] = 0;
			numOfVchar = trans.ByteToInt(numOfT);
			int relativePos = nextFreeVchar % realBlkSize;
			byte[] end = new byte[2];
			byte[] st = new byte[4];
			st[2] = 0;
			st[3] = 0;
			;
			int i = 0;
			for (end[0] = buf[relativePos - 1], end[1] = buf[relativePos - 2]; end[0] == -1
					&& end[1] == -1; i += 4, end[0] = buf[relativePos - 1 - i], end[1] = buf[relativePos
					- 2 - i]) {
				if (relativePos - 1 - i == 5) {
					nextFreeVchar = nextFreeVchar - 4;
					start = 4088;
				} else {
					nextFreeVchar -= 4;
					st[0] = buf[relativePos - 8 - i];
					st[1] = buf[relativePos - 7 - i];
					start = trans.ByteToInt(st);
				}
			}
		} else {
			int next = -1;
			byte[] tempo = new byte[4];
			trans.IntToByte(next, tempo);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = tempo[i];
			}
			numOfVchar = 0;
			start = realBlkSize - 8;
			if (length + 4 > realBlkSize - 4) {
				System.out.println("Vchar exceeds a block");
				return -1;
			}
		}
		byte[] cur = s.getBytes("8859_1");
		if (nextFreeVchar % realBlkSize + 4 + length > start) {
			int temp = nextFreeVchar;
//			System.out.print("getNextFreeVchar :");
			nextFreeVchar = getNextFreeBlk(nextFreeVchar / realBlkSize)
					* realBlkSize + 2;
			byte[] temp1 = new byte[4];
			trans.IntToByte(nextFreeVchar / realBlkSize, temp1);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = temp1[i];
			}
			mydiskMgr.WritePage(temp / realBlkSize, new String(buf, "8859_1"));
			mydiskMgr.ReadPage(nextFreeVchar / realBlkSize, buf);
			numOfVchar = 0;
			start = realBlkSize - 8;
		}
		short[] curPtr = new short[2];
		curPtr[0] = (short) (start - length);
		curPtr[1] = (short) (start);
		byte[] curB = new byte[4];
		trans.ShortToByte(curPtr, curB);
		int[] numOfT = new int[1];
		numOfT[0] = numOfVchar;
		numOfT[0]++;
		byte[] numOfT1 = new byte[4];
		trans.IntToByte(numOfT, numOfT1);
		buf[0] = numOfT1[0];
		buf[1] = numOfT1[1];
		for (int i = 0; i < 4; i++) {
			buf[nextFreeVchar % realBlkSize + i] = curB[i];
		}
		for (int i = 0; i < cur.length; i++) {
			buf[((int) curPtr[0] + i)] = cur[i];
		}
		int oriVchar = nextFreeVchar;
		int oriAddr = nextFreeVchar / realBlkSize;
		if (nextFreeVchar % realBlkSize + 4 + length == start) {
//			System.out.print("getNextFreeVchar :");
			nextFreeVchar = getNextFreeBlk(nextFreeVchar / realBlkSize)
					* realBlkSize + 2;
			byte[] temp2 = new byte[4];
			trans.IntToByte(nextFreeVchar / realBlkSize, temp2);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = temp2[i];
			}
		} else
			nextFreeVchar += 4;

		String byteBuffer = new String(buf, "8859_1");
		mydiskMgr.WritePage(oriAddr, byteBuffer);

		return oriVchar;
	}

	/**
	 * 获取下一个空闲块
	 * 
	 * @param last
	 *            上一个块的块号
	 * @return 空闲块的块号
	 */
	private int getNextFreeBlk(int last) {
//		System.out.println("getNextFreeBlk: " + nextFreeBlk);
		int first_bitmap;
		int i, j;
		byte cur, cur2, n, mask;
		int next_blk = nextFreeBlk;
		// System.out.println("getNextFreeBlk: "+next_blk);
		int actualBlkSize = blkSize * blkCount;
		mask = 0x1;
		if (next_blk == 0 || next_blk < 0) {
			System.out.println("All block has been allocated\n");
			System.exit(-1);
		}
		nextFreeBlk = next_blk + 1;
		if (nextFreeBlk >= sizeOfDatabase / (actualBlkSize)) {
			nextFreeBlk = 0;
			byte[] lastBlk = new byte[4];
			byte[] nextBlk = new byte[4];
			int next = -1;
			trans.IntToByte(last, lastBlk);
			trans.IntToByte(next, nextBlk);
			try {
				mydiskMgr.WriteData(next_blk * realBlkSize + 4088, new String(
						nextBlk, "8859_1")
						+ new String(lastBlk, "8859_1"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return next_blk;
		}

		first_bitmap = nextFreeBlk / (actualBlkSize * 8) + 1;
		cur = fileHandle.$buffer[4096 + (nextFreeBlk / 8) % actualBlkSize];
		for (n = (byte) (nextFreeBlk % 8); n < 8; n++) {
			cur2 = (byte) ((cur >> n) & mask);
			if (cur2 == 0) {
				nextFreeBlk = (nextFreeBlk / 8) * 8 + n;
				fileHandle.$buffer[4096 + (nextFreeBlk / 8) % actualBlkSize] = (byte) (fileHandle.$buffer[4096
						+ (nextFreeBlk / 8) % actualBlkSize] | (mask << n));
				byte[] lastBlk = new byte[4];
				byte[] nextBlk = new byte[4];
				int next = -1;
				trans.IntToByte(last, lastBlk);
				trans.IntToByte(next, nextBlk);
				try {
					mydiskMgr.WriteData(next_blk * realBlkSize + 4088,
							new String(nextBlk, "8859_1")
									+ new String(lastBlk, "8859_1"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return next_blk;
			}
		}
		for (i = (nextFreeBlk / 8) % actualBlkSize + 1, cur = fileHandle.$buffer[4096 + i]; i < actualBlkSize; i++) {
			for (n = 0; n < 8; n++) {
				cur2 = (byte) ((cur >> n) & mask);
				if (cur2 == 0) {
					nextFreeBlk = (nextFreeBlk / (8 * actualBlkSize)) * 8
							* actualBlkSize + i * 8 + n;
					fileHandle.$buffer[4096 + i] = (byte) (fileHandle.$buffer[4096 + i] | (mask << n));
					byte[] lastBlk = new byte[4];
					byte[] nextBlk = new byte[4];
					int next = -1;
					trans.IntToByte(last, lastBlk);
					trans.IntToByte(next, nextBlk);
					try {
						mydiskMgr.WriteData(next_blk * realBlkSize + 4088,
								new String(nextBlk, "8859_1")
										+ new String(lastBlk, "8859_1"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return next_blk;
				}
			}
			cur = fileHandle.$buffer[4096 + i];
		}
		byte[] lastBlk = new byte[4];
		byte[] nextBlk = new byte[4];
		int next = -1;
		trans.IntToByte(last, lastBlk);
		trans.IntToByte(next, nextBlk);
		try {
			mydiskMgr.WriteData(next_blk * realBlkSize + 4088, new String(
					nextBlk, "8859_1")
					+ new String(lastBlk, "8859_1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return next_blk;

	}

	/**
	 * 将从磁盘中读出的字符串转换为表区属性
	 * 
	 * @param s
	 *            读出的字符串
	 * @return 表区属性
	 */
	public TableFeature StringToTableFt(String s) {
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		String[] spl = new String[6];
		spl[0] = s.substring(0, Const.MaxNameLength);
		for (int i = 1; i < 6; i++)
			spl[i] = s.substring(Const.MaxNameLength + 4 * (i - 1),
					Const.MaxNameLength + 4 * i);
		TableFeature result = null;
		try {
			byte[] temp1 = spl[5].getBytes("8859_1");
			if (temp1.length != 4)
				System.out.println("To TableFeature error");
			int size = trans.ByteToInt(temp1);
			result = new TableFeature(size);
		} catch (Exception e) {
			System.out.println("getBytes error");
		}
		result.tInfo.tableName = spl[0];
//		 System.out.println(result.tInfo.tableName );
		byte[] tmp = new byte[4];
		int j = 1;
		try {
			for (; j < 6; j++) {
				byte[] temp = spl[j].getBytes("8859_1");
				if (temp.length != 4)
					System.out.println("To TableFeature error");
				int ans = trans.ByteToInt(temp);

				if (j == 1) {
					result.firstPage = ans;
//					 System.out.println(ans);
				} else if (j == 2) {
					// System.out.println("StringToTableFt curPos: "+ans);
					result.curPos = ans;
//					 System.out.println(ans);
				} else if (j == 3) {
					result.nextTable = ans;
//					 System.out.println(ans);

				} else if (j == 4) {
					result.tableCount = ans;
//					 System.out.println(ans);
				} else if (j == 5) {
					result.size = ans;
//					 System.out.println(ans);
				}
			}

			j = 5;
			int start = Const.MaxNameLength + 20;
			int end = 0;
			for (int i = 0; i < result.size; i++) {
				end = start + 16;
//				 System.out.println(start+" "+end);
				String tempS = s.substring(start, end);
				result.tInfo.attrDataTypes[i] = new AttrDataType(tempS);
				start = end;
				end += Const.MaxNameLength;
//				 System.out.println(start+" "+end);
				result.tInfo.attrNames[i] = s.substring(start, end).trim();
				start = end;
				int[] arg = new int[1];
//				 System.out.println(start+" "+end);
				result.tInfo.attrDefaultValues[i] = StringToValue(s
						.substring(end), result.tInfo.attrDataTypes[i], arg);
//				 System.out.println("arg: "+arg[0]);
				start += arg[0];
				end = start + 4;
//				 System.out.println(start+" "+end);
				tmp = s.substring(start, end).getBytes("8859_1");
				result.tInfo.attrConstraints[i] = trans.ByteToInt(tmp);
				start = end;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("StringToTableFt: getBytes error");
		}
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		return result;
	}

	/**
	 * 将字符串转化为对应类型的格式
	 * 
	 * @param input
	 *            输入字符串
	 * @param adt
	 *            目标类型
	 * @param result
	 *            result[0]为字符串前进长度
	 * @return 转化后的字符串
	 */
	public String StringToValue(String input, AttrDataType adt, int[] result) {
		String value = new String();
		String s = new String();
		try {
			byte[] tmp = new byte[4];
			switch (adt.type) {
			case Const.INT: {
				value = input.substring(1, 5);
				tmp = value.getBytes("8859_1");
				int r = trans.ByteToInt(tmp);
				s = String.valueOf(r);
				result[0] = 4+1;
				break;
			}
			case Const.FLOAT: {
				value = input.substring(1, 5);
				tmp = value.getBytes("8859_1");
				Float r = trans.ByteToFloat(tmp);
				s = String.valueOf(r);
				result[0] = 4+1;
				break;

			}
			case Const.DOUBLE: {
				value = input.substring(1, 9);
				tmp = value.getBytes("8859_1");
				Double r = trans.ByteToDouble(tmp);
				DecimalFormat df = new DecimalFormat("#,##");
				df.setGroupingUsed(false);
				df.setMinimumFractionDigits(1);
				df.setMaximumFractionDigits(5); // 保留小数点后3位（四舍五入）
				s = df.format(r);
				result[0] = 8+1;
				break;
			}
			case Const.BOOL: {
				value = input.substring(1, 2);
				tmp = value.getBytes("8859_1");
				if (tmp[0] == 0)
					s = "false";
				else
					s = "true";
				result[0] = 1+1;
				break;

			}
			case Const.DECIMAL: {
				value = input.substring(1, 9);
				tmp = value.getBytes("8859_1");
				long r1 = trans.ByteToLong(tmp);
				value = input.substring(9, 17);
				tmp = value.getBytes("8859_1");
				long r2 = trans.ByteToLong(tmp);
				System.out.println("&&&&&&&&&&&&&");
				System.out.println(adt.p+" "+adt.s);
				System.out.println(r1+" "+r2);
				s = StringStandard(String.valueOf(r1), adt.p-adt.s, false, (byte) '0')
						+ "."
						+ StringStandard(String.valueOf(r2), adt.s, true,
								(byte) '0');
				result[0] = 16+1;
				break;
			}
			case Const.CHAR: {
				int size = adt.size;
				s = value = input.substring(1, size+1).trim();
				result[0] = size+1;
				// System.out.println("*******"+value);
				break;
			}
			case Const.VARCHAR: {
				s = value = input.substring(1, adt.size+1).trim();
				result[0] = adt.size+1;
				break;
			}
			default:
				break;
			}
			// byte [] isNull=input.getBytes("8859_1");
			if(input.charAt(0)=='&') return "$null";
		} catch (Exception e) {
			System.out.println("TableFtToString error");
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 将某字符串格式化到对于能够长度
	 * 
	 * @param ori
	 *            原字符串
	 * @param length
	 *            要求长度
	 * @param end
	 *            是否在尾部添加
	 * @param ap
	 *            要添加的字符
	 * @return 格式化后的字符串
	 */
	public String StringStandard(String ori, int length, boolean end, byte ap) {
		int oriLength = ori.length();
		System.out.println("$$$$$$$$$$$$$$$");
		System.out.println(ori+" "+oriLength);
		System.out.println(length);
		if (oriLength > length) {
			System.out.println("StringStandard: err");
			return null;
		} else if (oriLength == length)
			return ori;
		else {
			try {
				int appendLength = length - oriLength;
				byte[] append = new byte[appendLength];
				for (int i = 0; i < appendLength; i++) {
					append[i] = ap;
				}
				String space = new String(append, "8859_1");
				String result = null;
				if (end)
					result = ori.concat(space);
				else
					result = space.concat(ori);
				return result;
			} catch (Exception e) {
				System.out.println("StringStandard: encode err");
			}
		}
		return null;
	}

	/**
	 * 将某种类型数据转化到格式化字符串存入内存
	 * 
	 * @param value
	 *            原数据字符串
	 * @param type
	 *            数据类型
	 * @return 转化后的字符串
	 */
	public String ValueToString(String value, int type) {
		String s = new String();
		try {
			boolean isNull = false;
			if (value.trim().equals("$null"))
				isNull = true;
			byte[] tmp = new byte[4];
			switch (type) {
			case Const.INT: {
				if (isNull) {
					s = s.concat(StringStandard("&", 5, true, (byte) ' '));
					break;
				} else {
					trans.IntToByte(Integer.valueOf(value), tmp);
					s = s.concat("0"+new String(tmp, "8859_1"));
					break;
				}
			}
			case Const.FLOAT: {
				if (isNull) {
					s = s.concat(StringStandard("&", 5, true, (byte) ' '));
					break;
				} else {
					tmp = trans.FloatToByte(Float.valueOf(value));
					s = s.concat("0"+new String(tmp, "8859_1"));
					break;
				}
			}
			case Const.DOUBLE: {
				if (isNull) {
					s = s.concat(StringStandard("&", 9, true, (byte) ' '));
					break;
				} else {
					byte[] tmp1 = trans.DoubleToByte(Double.valueOf(value));
					s = s.concat("0"+new String(tmp1, "8859_1"));
					break;
				}
			}
			case Const.BOOL: {
				if (isNull) {
					s = s.concat("& ");
					break;
				} else {
					byte[] b = new byte[1];
					if (value.equalsIgnoreCase("true"))
						b[0] = 1;
					else if (value.equalsIgnoreCase("false"))
						b[0] = 0;
					else {
						System.out.println("ValueToString err");
						return null;
					}
					s = s.concat("0"+new String(b, "8859_1"));
					break;
				}

			}
			case Const.DECIMAL: {
				if (isNull) {
					s = s.concat(StringStandard("&", 17, true, (byte) ' '));
					break;
				} else {
					String[] h = new String[2];
					int index = value.indexOf(".");
					h[0] = value.substring(0, index);
					h[1] = value.substring(index + 1, value.length());
					// System.out.println("Const.DECIMAL: "+value+" "+h.length);

					if (index >= value.length()) {
						System.out.println("ValueToString err,return null");
						return null;
					}
					System.out.println("_____________");
					System.out.println(h[0]);
					System.out.println(h[1]);
					System.out.println(Long.valueOf(h[0]));
					System.out.println(Long.valueOf(h[1]));
					System.out.println("_____________");
					
					byte[] tmpD = trans.LongToByte(Long.valueOf(h[0]));
					s = s.concat("0"+new String(tmpD, "8859_1"));
					tmpD = trans.LongToByte(Long.valueOf(h[1]));
					s = s.concat(new String(tmpD, "8859_1"));
					break;
				}
			}
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println("TableFtToString error");
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 将表区属性转换为字符串
	 * 
	 * @param t
	 *            表区属性
	 * @return 目标字符串
	 */
	public String TableFtToString(TableFeature t) {
		String s = new String();
		s = s.concat(StringStandard(t.tInfo.tableName, Const.MaxNameLength,
				true, (byte) ' '));
		// System.out.println(s.length());
		try {
			byte[] tmp = new byte[4];
			trans.IntToByte(t.firstPage, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			// System.out.println(s.length());
			trans.IntToByte(t.curPos, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			// System.out.println(s.length());
			trans.IntToByte(t.nextTable, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			// System.out.println(s.length());
			trans.IntToByte(t.tableCount, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			// System.out.println(s.length());
			trans.IntToByte(t.size, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			// System.out.println(s.length());

			for (int i = 0; i < t.size; i++) {
				s = s.concat(t.tInfo.attrDataTypes[i].toString());
//				 System.out.println(s.length());
				s = s.concat(StringStandard(t.tInfo.attrNames[i],
						Const.MaxNameLength, true, (byte) ' '));
//				 System.out.println(s.length());
				if (t.tInfo.attrDataTypes[i].type == Const.CHAR || t.tInfo.attrDataTypes[i].type == Const.VARCHAR) {
//					System.out.println(t.tInfo.attrDefaultValues[i]+"@@@@@@@@@@"+t.tInfo.attrDataTypes[i].size);
					if(t.tInfo.attrDefaultValues[i].equals("$null"))
						s = s.concat("&"+StringStandard("",
							t.tInfo.attrDataTypes[i].size, true, (byte) ' '));
					else
						s = s.concat("0"+StringStandard(t.tInfo.attrDefaultValues[i],
								t.tInfo.attrDataTypes[i].size, true, (byte) ' '));
				} else
					s = s.concat(ValueToString(t.tInfo.attrDefaultValues[i],
							t.tInfo.attrDataTypes[i].type));
//				 System.out.println(s.length());
				trans.IntToByte(t.tInfo.attrConstraints[i], tmp);
				s = s.concat(new String(tmp, "8859_1"));

//				 System.out.println(s.length());
			}
		} catch (Exception e) {
			System.out.println("TableFtToString error");
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * @author wyj Table数据字典表区数据结构，一个Table可能有多个表区组成
	 */
	public static class TableFeature {
		TableInfo tInfo;
		int firstPage = -1;
		int curPos = -1;// The addr to insert a new record
		int nextTable = -1;// Whether the table has other column
		int tableCount = 1;
		int size;

		public TableFeature(String tableName1, AttrDataType[] mytype,
				String[] name, String[] dValue, int[] myConstraint) {
			tInfo = new TableInfo();
			tInfo.tableName = tableName1;
			size = mytype.length;
			tInfo.attrDataTypes = mytype;
			tInfo.attrNames = name;
			tInfo.attrDefaultValues = dValue;
			tInfo.attrConstraints = myConstraint;
			firstPage = -1;
			curPos = -1;
			nextTable = -1;
			tableCount = 1;
		}
		// modify mark
		public TableFeature(TableInfo ti) {
			tInfo=ti;
			firstPage = -1;
			curPos = -1;
			nextTable = -1;
			tableCount = 1;
			size = ti.attrNames.length;
		}
		public TableFeature(int size) {
			tInfo = new TableInfo();
			tInfo.attrDataTypes = new AttrDataType[size];
			tInfo.attrNames = new String[size];
			tInfo.attrDefaultValues = new String[size];
			tInfo.attrConstraints = new int[size];
		}
	}

	/**
	 * 获取某个Table的所有表区属性
	 * 
	 * @param tableName
	 *            table名字
	 * @return 表区属性数组
	 */
	public TableFeature[] getTableFt(String tableName) {
		String tableStr = (String) tableStructIndex.get(tableName);
		if (tableStr == null) {
			System.out.println("getTableFt: tableName:" + tableName
					+ "doesn't existt");
			return null;
		}
		TableFeature tf = StringToTableFt(tableStr);
		String[] table = new String[tf.tableCount];
		TableFeature[] tableFt = new TableFeature[tf.tableCount];
		tableFt[0] = tf;
		table[0] = tableStr;
		try {
			for (int i = 0; tableFt[i].nextTable != -1; i++) {
				int addr = tableFt[i].nextTable;
				int relative = addr % 4096;
				byte[] buf = new byte[4096];
				mydiskMgr.ReadPage(addr / 4096, buf);
				String totalStr = new String(buf, "8859_1");
				byte[] tmp = new byte[4];
				tmp[2] = 0;
				tmp[3] = 0;
				tmp[0] = buf[relative];
				tmp[1] = buf[relative + 1];
				int start = trans.ByteToInt(tmp);
				tmp[0] = buf[relative + 2];
				tmp[1] = buf[relative + 3];
				int end = trans.ByteToInt(tmp);
				table[i + 1] = totalStr.substring(start, end);
				tableFt[i + 1] = StringToTableFt(table[i + 1]);
			}
		} catch (Exception e) {
			System.out.println("getTableFt err");
		}
		return tableFt;
	}

	/**
	 * 将Record划分为多个表区并转化为字符串
	 * 
	 * @param r
	 *            表项对象
	 * @param tableName
	 *            表的名字
	 * @return 对应多个表区的字符串数组
	 */
	public String[] RecordToString(Record r, String tableName) {
		String tableStr = (String) tableStructIndex.get(tableName);
		if (tableStr == null) {
			System.out
					.println("RecordToString:  tableStructIndex.get(tableName) err");
			return null;
		}
		TableFeature tf = StringToTableFt(tableStr);
		String[] table = new String[tf.tableCount];
		TableFeature[] tableFt = new TableFeature[tf.tableCount];
		String[] result = new String[tf.tableCount];
		tableFt[0] = tf;
		table[0] = tableStr;
		try {
			for (int i = 0; tableFt[i].nextTable != -1; i++) {
				int addr = tableFt[i].nextTable;
				int relative = addr % 4096;
				byte[] buf = new byte[4096];
				mydiskMgr.ReadPage(addr / 4096, buf);
				String totalStr = new String(buf, "8859_1");
				byte[] tmp = new byte[4];
				tmp[2] = 0;
				tmp[3] = 0;
				tmp[0] = buf[relative];
				tmp[1] = buf[relative + 1];
				int start = trans.ByteToInt(tmp);
				tmp[0] = buf[relative + 2];
				tmp[1] = buf[relative + 3];
				int end = trans.ByteToInt(tmp);
				table[i + 1] = totalStr.substring(start, end);
				tableFt[i + 1] = StringToTableFt(table[i + 1]);
			}
			for (int i = 0; i < tf.tableCount; i++) {
				result[i] = "";
				for (int j = 0; j < tableFt[i].size; j++) {
					boolean exist = false;
					for (int k = 0; k < r.attrName.length; k++) {
						if (tableFt[i].tInfo.attrNames[j].equals(r.attrName[k])) {
							if (tableFt[i].tInfo.attrDataTypes[j].type != Const.VARCHAR) {
								if (tableFt[i].tInfo.attrDataTypes[j].type == Const.CHAR)
									if(r.attrValue[k].equals("$null"))
									result[i] += "&"+StringStandard(
											r.attrValue[k],
											tableFt[i].tInfo.attrDataTypes[j].size,
											true, (byte) ' ');
									else
										result[i] += "0"+StringStandard(
												r.attrValue[k],
												tableFt[i].tInfo.attrDataTypes[j].size,
												true, (byte) ' ');
								else
									result[i] += ValueToString(
											r.attrValue[k],
											tableFt[i].tInfo.attrDataTypes[j].type);
							} else {
								byte[] mytmp = new byte[4];
								if(r.attrValue[k].equals("$null")){
									trans
									.IntToByte(
											-1,
											mytmp);
									result[i] +=new String(mytmp, "8859_1");
								}
								else{
								trans
										.IntToByte(
												getNextFreeVchar(r.attrValue[k]),
												mytmp);
								result[i] += new String(mytmp, "8859_1");
								}
							}

							exist = true;
						}
					}
					if (!exist) {
						if (tableFt[i].tInfo.attrDataTypes[j].type != Const.VARCHAR) {
							if (tableFt[i].tInfo.attrDataTypes[j].type == Const.CHAR){
								if(!tableFt[i].tInfo.attrDefaultValues[j].equals("$null"))
									result[i] +=StringStandard("0"+ tableFt[i].tInfo.attrDefaultValues[j],tableFt[i].tInfo.attrDataTypes[j].size+1,true,(byte)' ');
								else 
									result[i] +=StringStandard("&",tableFt[i].tInfo.attrDataTypes[j].size+1,true,(byte)' ');
							}
							else
								result[i] += ValueToString(
										tableFt[i].tInfo.attrDefaultValues[j],
										tableFt[i].tInfo.attrDataTypes[j].type);
						} else {
							byte[] mytmp = new byte[4];
							trans.IntToByte(-1, mytmp);
							result[i] += new String(mytmp, "8859_1");
						}
					}

				}
			}
		} catch (Exception e) {
			System.out.println("RecordToString err");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将从磁盘中读出的字符串转化为表项
	 * 
	 * @param str
	 *            读出的String
	 * @param tf
	 *            表区属性
	 * @return 目标表项
	 */
	Record StringToRecord(String str, TableFeature tf) {
		String[] aName = new String[tf.size];
		String[] aValue = new String[tf.size];
		aName = tf.tInfo.attrNames;
		int[] arg = new int[1];
		int start = 0;
		for (int i = 0; i < tf.size; i++) {
			if (tf.tInfo.attrDataTypes[i].type != Const.VARCHAR) {
//				System.out.println("@@@@@@@@@@@@"+str.substring(start));
				if(!(str.substring(start).charAt(0)=='&'))
					aValue[i] = StringToValue(str.substring(start),
						tf.tInfo.attrDataTypes[i], arg);
				else 
					aValue[i] = "$null";
				start += arg[0];
			} else {
				
				try {
					
					String tmpS = str.substring(start, start + 4);
					byte[] tmp;
					tmp = tmpS.getBytes("8859_1");
					int pos = trans.ByteToInt(tmp);
					if(pos==-1){
						aValue[i] = "$null";
					}else{
					int blkNum = pos / 4096;
					int relative = pos % 4096;
					String totalBuf;
					byte[] buf = new byte[4096];
					mydiskMgr.ReadPage(blkNum, buf);
					totalBuf = new String(buf, "8859_1");
					byte[] temp = new byte[4];
					int start1, end1;
					byte[] temp1 = new byte[4];

					temp[0] = buf[relative];
					temp[1] = buf[relative + 1];
					temp[2] = 0;
					temp[3] = 0;
					temp1[0] = buf[relative + 2];
					temp1[1] = buf[relative + 3];
					temp1[2] = 0;
					temp1[3] = 0;
					start1 = trans.ByteToInt(temp);
					end1 = trans.ByteToInt(temp1);
					if(temp[0]==-1 && temp[2]==-1 &&temp[3]==-1 &&temp[1]==-1 )
						aValue[i]="$null";
					else
						aValue[i] = totalBuf.substring(start1, end1);
					}
					start += 4;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		Record r = new Record(aName, aValue);
		return r;
	}

	/* Condition for searching a record */
	public static class Condition {
		int[] relation; // < > == and on on
		String[] attrName;
		String[] cmpValue;
		Vector<String>[] ccmpValue; // For in

		public Condition(int[] r, String[] a, String[] c, Vector<String>[] cc) {
			relation = r;
			attrName = a;
			cmpValue = c;
			ccmpValue = cc;
		}
	}
}
