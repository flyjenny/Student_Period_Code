package DiskManager;

import _acessfile.*;

import java.util.*;
import java.io.*;

/*The class that carry out the basic operation for a database */
public class RecordManager {

	DiskManager mydiskMgr;
	int sizeOfDatabase;
	int blkSize;
	int blkCount;// ¿éÊý
	int nextFreeBlk;
	int realBlkSize;
	int nextFreeTableStruct;
	int nextFreeVchar;
	int bitmapSize;
	Map tableStructIndex = new HashMap<String, String>();
	Map tableAddrIndex = new HashMap<String, Integer>();
	MYFile fileHandle;
	
	public RecordManager(){
		
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println("start");

		RecordManager rm = null;
//		rm = new RecordManager(10*1024*1024,1,"tests");
		rm = new RecordManager("tests");
//		rm = new RecordManager();
		String tableName = "student2002";
		int[] type = { 12, 112 };
		String[] attrName = { "name", "ID" };
		String[] defaultValue = { "wxd", "asd" };
		String [] attrValue={"wyjqwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww","5080309517cwqdcddddddddddddddddddddddddddddddd"};
		CONSTRAINT[] constraint = { CONSTRAINT.Key, CONSTRAINT.PK };
		TableFeature t = new TableFeature(tableName, type, attrName,
				defaultValue, constraint);
//		rm.CreateTable(t);
		Record r=new Record(attrName,attrValue);


/*	rm.InsertRecord("student2002", r);
		Record[] mr=rm.getRecord("student2002");
		System.out.println("mr.length: "+mr.length);
		System.out.println(rm.RecordToString(mr[0],"student2002").length);
		for(int i=0;i<mr.length;i++){
			System.out.println(rm.RecordToString(mr[i],"student2002")[0]);
		}*/

		
		
		  for (int i = 0; i < 1; i++) {
			tableName = "student"+i ;
			TableFeature tf = new TableFeature(tableName, type, attrName,
					defaultValue, constraint);
			rm.CreateTable(tf);
			for(int j=0;j<10000;j++)
				rm.InsertRecord(tableName, r);
//			rm.DeleteTable(tableName);
		}
//		  rm.DeleteTable("student2002");


	/*	byte[] buf=new byte[4096];
//		rm.mydiskMgr.WriteData(4096*100, tbl);
		rm.mydiskMgr.ReadPage(100, buf);
		String tbl1=new String(buf,"8859_1").substring(0, tbl.length());
		String[] spl1 =  rm.TableFtToString(rm.StringToTableFt(tbl)).split("#");
		for (int i = 0; i < spl1.length; i++) {
			if (spl1[i].isEmpty())
				System.out.println("Null");
			else
				System.out.println(spl1[i]);
		}*/
		  System.out.println("****************************************************************");
		for(int i=0;i<100;i++){
		String tbl = (String)rm.tableStructIndex.get("student"+i);
		if(tbl!=null){
		System.out.print(rm.StringToTableFt(tbl).size+" ");
		System.out.print(rm.StringToTableFt(tbl).curPos+" ");
		System.out.print(rm.StringToTableFt(tbl).firstPage+" ");
		System.out.print(rm.StringToTableFt(tbl).tableCount+" ");
		System.out.println(rm.StringToTableFt(tbl).nextTable);
		}
		}

		try {
			rm.updateDisk();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		  byte[] buffer = new byte[4096];
		rm.mydiskMgr.ReadPage(5, buffer);
		System.out.println("\r\n\r\n\r\n\r\n");
		for (int i = 0; i < 4096; i++) {
	//		System.out.println(buffer[i]);
		}
		
		 
		 
/*		byte[] tmp=new byte[4];
		for(int i=0;i<4;i++)
		{
			tmp[i]=-1;
		}
		int x=-1;
		rm.IntToByte(x, tmp);
		System.out.println(tmp[0]);
*/
	}

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
		IntToByte(input, fileHandle.$buffer);
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

	/* Database has been firstly created . Initialize the global attribute */
	public RecordManager(int sizeOfDB, int blkCount1, String fileName) {
		sizeOfDatabase = sizeOfDB;
		blkSize = 4096;
		blkCount = blkCount1;
		realBlkSize = blkCount * blkSize;
		mydiskMgr = new DiskManager("E:/database");
		if (mydiskMgr.Get_Hm().containsKey(fileName)) {
			System.out.println("Database " + fileName
					+ " has already existed, can't be recreated");
			return;
		}
		mydiskMgr.CreateFile(fileName, sizeOfDB / 1024);
		fileHandle = mydiskMgr.OpenFile(fileName);
		bitmapSize = sizeOfDatabase % (blkSize * blkSize * 8 * blkCount) == 0 ? sizeOfDatabase
				/ (blkSize * blkSize * 8 * blkCount)
				: sizeOfDatabase / (blkSize * blkSize * 8 * blkCount) + 1;
		nextFreeTableStruct = (bitmapSize + 1) * realBlkSize + 2;
		nextFreeVchar = (bitmapSize + 2) * realBlkSize;
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
		IntToByte(input, fileHandle.$buffer);
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

	/* Database has already existed, and is opened */
	public RecordManager(String fileName)  {
		realBlkSize = blkCount * blkSize;
		mydiskMgr = new DiskManager("E:/database");
		if (mydiskMgr.Get_Hm().containsKey(fileName) == false) {
			System.out.println("Database doesn't exist");
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
			input[i] = ByteToInt(b);
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
		  File fi=new File("E:/result.txt"); FileWriter fw=null; try { fw=new
		 FileWriter(fi,true); } catch (IOException e) { }// TODO Auto-generatedcatch block e.printStackTrace(); }*/
		 
		while (blk[0] != -1) {
			Map t = getTableOfBlk(blk);
			for (Iterator it = t.keySet().iterator(); it.hasNext();) {
				int addr = (Integer) it.next();
				String ct = (String) t.get(addr);
				System.out.println(ct);
//				StringToTableFt(ct);
				String key = ct.substring(0, ct.indexOf("#"));
				tableStructIndex.put(key, ct);
				tableAddrIndex.put(key, addr);
			}
			 
		}
		System.out.println("tableStructIndex size: " + tableStructIndex.size());
		System.out.println(sizeOfDatabase + " " + blkSize + " " + blkCount
				+ " " + bitmapSize + " " + nextFreeTableStruct + " "
				+ nextFreeVchar + " " + nextFreeBlk);
	}

	private Map getTableOfBlk(int[] blk)  {
		String totalBuf;
		Map table = new HashMap<Integer, String>();
		byte[] buf = new byte[4096];
		mydiskMgr.ReadPage(blk[0], buf);
		try{
		totalBuf = new String(buf, "8859_1");
		int tableNum;
		byte[] temp = new byte[4];
		temp[0] = buf[0];
		temp[1] = buf[1];
		temp[2] = 0;
		temp[3] = 0;
		tableNum = ByteToInt(temp);
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
			start = ByteToInt(temp);
			end = ByteToInt(temp1);
			if (!(temp1[0] == -1 && temp1[1] == -1)) {
				count++;
				table.put(blk[0] * realBlkSize + 4 * i + 2, totalBuf
						.substring(start, end));
			}
			i++;
		}
		for (int j = 0; j < 4; j++)
			temp[j] = buf[4088 + j];
		blk[0] = ByteToInt(temp);
	}catch(Exception e){
		System.out.println(" Map getTableOfBlk err");
	}
		return table;
	}

	/* Create a new table */
	public int CreateTable(TableFeature characteristic) {
		if (tableStructIndex.containsKey(characteristic.tableName)) {
			System.out.println("Table: (" + characteristic.tableName
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

	private int deleteAllBlkOfTable(int firstBlk){
		System.out.println("deleteAllBlkOfTable called");
		int blk=firstBlk;
		while (blk != -1)
			blk = DeleteBlkOfTable(blk);			
		return 0;
	}
	
	private int DeleteBlkOfTable(int blk)  {
		String totalBuf;
		byte[] buf = new byte[4096];
		mydiskMgr.ReadPage(blk, buf);
		byte[] temp = new byte[4];
		for (int j = 0; j < 4; j++)
			temp[j] = buf[4088 + j];
		int nextblk = ByteToInt(temp);
		System.out.println("DeleteBlkOfTable: "+blk);
		freePage(blk);
		return nextblk;
	}
	/* delete table */
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

		byte[] buf = new byte[realBlkSize];
		if (mydiskMgr.ReadPage(tableAddr / realBlkSize, buf) == false)
			return -1;
		byte[] numOfS = new byte[4];
		numOfS[0] = buf[0];
		numOfS[1] = buf[1];
		numOfS[2] = 0;
		numOfS[3] = 0;
		int numOfS1 = ByteToInt(numOfS);
		int blkToDel=StringToTableFt((String)tableStructIndex.get(tableName)).firstPage;
		if(blkToDel!=-1) deleteAllBlkOfTable(blkToDel);
		if (numOfS1 == 1
				&& nextFreeTableStruct / realBlkSize != tableAddr / realBlkSize
				&& tableAddr / realBlkSize != bitmapSize + 1) {
			System.out.println("freepage: " + tableAddr / realBlkSize);
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
			int lastS1 = ByteToInt(lastS);
			int nextS1 = ByteToInt(nextS);
			System.out.println("WriteData: intolast-" + nextS1 + " "
					+ " intonext-" + lastS1);
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
			ShortToByte(mark, mark1);
			buf[tableAddr % realBlkSize + 2] = mark1[0];
			buf[tableAddr % realBlkSize + 3] = mark1[1];
			numOfS1--;
			IntToByte(numOfS1, numOfS);
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
	public int InsertRecord(String tableName, Record record) {
		String tableStr = (String) tableStructIndex.get(tableName);
		if (tableStr == null) {
			System.out.println("tableStructIndex get " + tableName + " error");
			return -1;
		}
		TableFeature tf = StringToTableFt(tableStr);
//		System.out.println(tf.size);
//		System.out.println(tf.curPos);
//		System.out.println(tf.firstPage);
//		System.out.println(tf.nextTable);
		String [] rString=RecordToString(record,tableName);
		if (tf.nextTable == -1) {
			if(rString[0].length()>4082){System.out.println("Current record length exceeds a block");return -1;}
			if(tf.firstPage==-1){
				System.out.println("tf.firstPage==-1");
				tf.firstPage = getNextFreeBlk(-1);
				tf.curPos=tf.firstPage*4096+2;
			}
			try {
				insertRecordIntoATable(tf, rString[0]);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			updateTableFt((Integer)tableAddrIndex.get(tableName),TableFtToString(tf));
			tableStructIndex.put(tableName, TableFtToString(tf));
		} else {
			TableFeature[] tfList = getTableFt(tableName);
			if (tfList.length < 1 || tfList[0] == null) {
				System.out.println("InsertRecord: Trying to getTableFt err");
				return -1;
			}
			for(int i=0;i<tfList.length ;i++){
				if(tfList[i].firstPage==-1){
					tfList[i].firstPage = getNextFreeBlk(-1);
					tfList[i].curPos=tfList[i].firstPage*4096+2;
				}
				try {
					insertRecordIntoATable(tfList[i], rString[i]);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i==0){
					updateTableFt((Integer)tableAddrIndex.get(tableName),TableFtToString(tfList[i]));
					tableStructIndex.put(tableName, TableFtToString(tfList[i]));
				}
				else
					updateTableFt(tfList[i-1].nextTable,TableFtToString(tfList[i]));

			}
		}
		return 0;
	}

	int updateTableFt(int pos,String input){
		byte[] buf=new byte[4096];
		if(mydiskMgr.ReadPage(pos/4096, buf)==false) {System.out.println("updateTableFt: ReadPage err "+pos); return -1;}
		try{
		String bufStr=new String(buf,"8859_1");
		int relative=pos%4096;
		byte[] tmp=new byte[4];
		tmp[2]=0;
		tmp[3]=0;
		tmp[0]=buf[relative];
		tmp[1]=buf[relative+1];
		int start=ByteToInt(tmp);
		tmp[0]=buf[relative+2];
		tmp[1]=buf[relative+3];
		int end=ByteToInt(tmp);
		int oriLength=end-start;
		int newLength=input.length();
		if(newLength>oriLength){
			System.out.println("updateTableFt: newLength>oriLength"); return -1;
		}
//		String oriStr=bufStr.substring(start, end);
//		bufStr=bufStr.replaceFirst(oriStr, input);
//		System.out.println(StringToTableFt(bufStr.substring(start, end)).curPos);
		if(mydiskMgr.WriteData((pos/4096)*4096+start, input)==false) {System.out.println("updateTableFt: WritePage err "+pos); return -1;}
		}catch(Exception e){
			System.out.println("unsupported encode");
			e.printStackTrace();
		}
		return 0;
	}
	int insertRecordIntoATable(TableFeature tf, String record)
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
			ShortToByte(sh, ptr);
		}
		ptr[2] = 0;
		ptr[3] = 0;
		int start;
		if (tf.curPos % realBlkSize != 2) {

			start = ByteToInt(ptr);
			byte[] numOfT = new byte[4];
			numOfT[0] = buf[0];
			numOfT[1] = buf[1];
			numOfT[2] = 0;
			numOfT[3] = 0;
			numOfRecord = ByteToInt(numOfT);
			int relativePos = tf.curPos % realBlkSize;
			byte[] end = new byte[2];
			byte[] st = new byte[4];
			st[2] = 0;
			st[3] = 0;
			;
			int i = 0;
//			System.out.println(relativePos);
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
					start = ByteToInt(st);
				}
			}
		} else {
			int next = -1;
			byte[] tempo = new byte[4];
			IntToByte(next, tempo);
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
			tf.curPos = getNextFreeBlk(tf.curPos
					/ realBlkSize)
					* realBlkSize + 2;
			byte[] temp1 = new byte[4];
			IntToByte(tf.curPos / realBlkSize, temp1);
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
		ShortToByte(curPtr, curB);
		int[] numOfT = new int[1];
		numOfT[0] = numOfRecord;
		numOfT[0]++;
		byte[] numOfT1 = new byte[4];
		IntToByte(numOfT, numOfT1);
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
			tf.curPos = getNextFreeBlk(tf.curPos
					/ realBlkSize)
					* realBlkSize + 2;
			byte[] temp2 = new byte[4];
			IntToByte(tf.curPos / realBlkSize, temp2);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = temp2[i];
			}
		} else
			tf.curPos += 4;

		String byteBuffer = new String(buf, "8859_1");
		mydiskMgr.WritePage(oriAddr, byteBuffer);
		return 0;
	}

	public Record[] getRecord(String tableName){
		TableFeature[] tft=getTableFt(tableName);
		Record[][] myRecord=null;
		if(tft==null) {System.out.println("getRecord: "+tableName+" doesn't exist"); return null;}
		if(tft[0]!=null){
			myRecord=new Record[tft.length][tft[0].tableCount];
		}
		else{
			System.out.println("getRecord: "+tableName+" doesn't exist"); return null;
		}
		for(int i=0;i<tft.length;i++){
			//for(int j=0; j<tft[0].tableCount; ){
					myRecord[i]=getRecordOfATable(tft[i].firstPage,tft[i]);
	//				System.out.println("getRecord");
					
			//}
		}
		return combineRecord(myRecord);
	}
	
	Record[] combineRecord(Record[][] rList){
		Record[] result=new Record[rList[0].length];
		int length=0;
		for(int j=0;j<rList.length;j++)
			length+=rList[j][0].attrName.length;
		for(int i=0;i<result.length;i++){
			String []name=new String[length];
			String []value=new String[length];
			int index=0;
			for(int j=0;j<rList.length;j++){
				for(int n=0;n<rList[j][i].attrName.length;n++){
					name[index]=rList[j][i].attrName[n];
					value[index]=rList[j][i].attrValue[n];
					index++;
				}
			}
			result[i]=new Record(name,value);
		}
		return result;
	}
	
	Record[] getRecordOfATable(int firstBlk,TableFeature tf){
		ArrayList<Record> rList=new ArrayList<Record>();
		int[] blk = new int[1];
		blk[0] = firstBlk;
		while (blk[0] != -1) {
			getRecordOfBlk(blk,rList,tf);
		}

		Record[] rArray=new Record[rList.size()];
		for(int i=0;i<rList.size();i++)
			rArray[i]=rList.get(i);
		return rArray;
	}
	
	int getRecordOfBlk(int [] blk,ArrayList<Record> rList,TableFeature ft){
		try{
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
			tableNum = ByteToInt(temp);
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
				start = ByteToInt(temp);
				end = ByteToInt(temp1);
				if (!(temp1[0] == -1 && temp1[1] == -1)) {
					count++;
					rList.add( StringToRecord( totalBuf
							.substring(start, end),ft));
				}
				i++;
			}
			for (int j = 0; j < 4; j++)
				temp[j] = buf[4088 + j];
			blk[0] = ByteToInt(temp);
		}catch(Exception e){
			System.out.println(" getRecordOfBlk err");
		}
			return 0;	
	}
	/* Delete a tuple from a table */
	public int DeleteRecord(String tableName, Condition condition) {
		return 0;
	}
	
	public int DeleteRecord(String tableName, int [] delete) {
		
		return 0;
	}
	

	/* Search all tuples from tableName that satisfy condition */
	public Record[] FindRecord(String tableName, Condition condition) {
		return null;
	}
	
	/*
	 * Search all tuples from tableName that satisfy condition , Update them as
	 * record describes
	 */
	public int UpdateRecord(String tableName, Record record, Condition condition) {
		return 0;
	}

	/* Add new columns to Table tableName */
	public int UpdateAddColumn(String tableName, TableFeature tf) {
		return 0;
	}

	/* File a table to take less space and in better order */
	public int TrimTable(String tableName) {
		return 0;
	}

	/* File the whole database */
	public int ToBetterDatabase() {
		return 0;
	}

	/* Free a page,need to reset all bit */
	int freePage(int pageNum) {
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
		}
		if (pageNum < nextFreeBlk &&pageNum>0)
			nextFreeBlk = pageNum;
		return 0;
	}

	int getNextFreeTableStruct(TableFeature t)
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
			ShortToByte(sh, ptr);
		}
		ptr[2] = 0;
		ptr[3] = 0;
		int start;
		if (nextFreeTableStruct % realBlkSize != 2) {

			start = ByteToInt(ptr);
			byte[] numOfT = new byte[4];
			numOfT[0] = buf[0];
			numOfT[1] = buf[1];
			numOfT[2] = 0;
			numOfT[3] = 0;
			numOfTable = ByteToInt(numOfT);
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
					start = ByteToInt(st);
				}
			}
		} else {
			int next = -1;
			byte[] tempo = new byte[4];
			IntToByte(next, tempo);
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
			IntToByte(nextFreeTableStruct / realBlkSize, temp1);
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
		ShortToByte(curPtr, curB);
		int[] numOfT = new int[1];
		numOfT[0] = numOfTable;
		numOfT[0]++;
		byte[] numOfT1 = new byte[4];
		IntToByte(numOfT, numOfT1);
		buf[0] = numOfT1[0];
		buf[1] = numOfT1[1];
		for (int i = 0; i < 4; i++) {
			buf[nextFreeTableStruct % realBlkSize + i] = curB[i];
		}
		for (int i = 0; i < cur.length; i++) {
			buf[((int) curPtr[0] + i)] = cur[i];
		}
		int oriAddr = nextFreeTableStruct / realBlkSize;
		tableAddrIndex.put(t.tableName, nextFreeTableStruct);
		tableStructIndex.put(t.tableName, s);
		if (nextFreeTableStruct % realBlkSize + 4 + length == start) {
			nextFreeTableStruct = getNextFreeBlk(nextFreeTableStruct
					/ realBlkSize)
					* realBlkSize + 2;
			byte[] temp2 = new byte[4];
			IntToByte(nextFreeTableStruct / realBlkSize, temp2);
			for (int i = 0; i < 4; i++) {
				buf[4088 + i] = temp2[i];
			}
		} else
			nextFreeTableStruct += 4;

		String byteBuffer = new String(buf, "8859_1");
		mydiskMgr.WritePage(oriAddr, byteBuffer);

		return 0;
	}

	/* Find the address to store a vchar */
	/*
	 * int getNextFreeVchar(int length) { int addrInBlk = nextFreeVchar %
	 * realBlkSize; if (length > realBlkSize) {
	 * System.out.println("Vchar length can't exceed " + realBlkSize); return
	 * -1; } if ((realBlkSize - addrInBlk) < length) { int newBlk =
	 * getNextFreeBlk(nextFreeVchar/realBlkSize); if (length == realBlkSize)
	 * nextFreeBlk = getNextFreeBlk() * realBlkSize; else nextFreeBlk = newBlk *
	 * realBlkSize + length; return newBlk * realBlkSize; } else { if
	 * ((realBlkSize - addrInBlk) == length) { nextFreeBlk = getNextFreeBlk() *
	 * realBlkSize; } else nextFreeBlk += length; return nextFreeVchar; } }
	 */
	/* Get the next free block */
	int getNextFreeBlk(int last) {
		int first_bitmap;
		int i, j;
		byte cur, cur2, n, mask;
		int next_blk = nextFreeBlk;
		System.out.println("getNextFreeBlk: "+next_blk);
		int actualBlkSize = blkSize * blkCount;
		mask = 0x1;
		if (next_blk == 0||next_blk<0) {
			System.out.println("All block has been allocated\n");
			System.exit(-1);
		}
		nextFreeBlk = next_blk + 1;
		if (nextFreeBlk >= sizeOfDatabase / (actualBlkSize)) {
			nextFreeBlk = 0;
			byte[] lastBlk = new byte[4];
			byte[] nextBlk = new byte[4];
			int next = -1;
			IntToByte(last, lastBlk);
			IntToByte(next, nextBlk);
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
				IntToByte(last, lastBlk);
				IntToByte(next, nextBlk);
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
					IntToByte(last, lastBlk);
					IntToByte(next, nextBlk);
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
		IntToByte(last, lastBlk);
		IntToByte(next, nextBlk);
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

	void ShortToByte(short[] curPtr, byte[] arr) {
		for (int i = 0; i < curPtr.length; i++) {
			arr[0 + 2 * i] = (byte) (curPtr[i]);
			arr[1 + 2 * i] = (byte) (curPtr[i] >>> 8);
		}
	}

	void IntToByte(int[] a, byte[] arr) {
		for (int i = 0; i < a.length; i++) {
			arr[0 + 4 * i] = (byte) (a[i]);
			arr[1 + 4 * i] = (byte) (a[i] >> 8);
			arr[2 + 4 * i] = (byte) (a[i] >> 16);
			arr[3 + 4 * i] = (byte) (a[i] >> 24);
		}
	}

	void IntToByte(int a, byte[] arr) {

		arr[0] = (byte) (a);
		arr[1] = (byte) (a >> 8);
		arr[2] = (byte) (a >> 16);
		arr[3] = (byte) (a >> 24);

	}

	int ByteToInt(byte[] arr) {
		int a = (((int) arr[0]) & 0xff) * 1 + (((int) arr[1]) & 0xff) * 256
				+ (((int) arr[2]) & 0xff) * 256 * 256 + (((int) arr[3]) & 0xff)
				* 256 * 256 * 256;
		return a;
	}

	private CONSTRAINT getConstraint(String con) {
		if (con.equals("PK"))
			return CONSTRAINT.PK;
		else if (con.equals("Key"))
			return CONSTRAINT.Key;
		else if (con.equals("NotNull"))
			return CONSTRAINT.NotNull;
		return null;
	}

	/* primary key, key ,NOT NULL and so on */
	enum CONSTRAINT {
		PK, Key, NotNull
	};

	TableFeature StringToTableFt(String s) {
		String[] spl = s.split("#");
		TableFeature result = null;
		try {
			byte[] temp1 = spl[5].getBytes("8859_1");
			if (temp1.length != 4)
				System.out.println("To TableFeature error");
			int size = ByteToInt(temp1);
			result = new TableFeature(size);
		} catch (Exception e) {
			System.out.println("getBytes error");
		}
		result.tableName = spl[0];
		byte[] tmp = new byte[4];
		int j = 1;
		try {
			for (; j < 6; j++) {
				byte[] temp = spl[j].getBytes("8859_1");
				if (temp.length != 4)
					System.out.println("To TableFeature error");
				int ans = ByteToInt(temp);

				if (j == 1) {
					result.firstPage = ans;
	//				 System.out.println("StringToTableFt firstPage: "+ans);
				} else if (j == 2) {
					 System.out.println("StringToTableFt curPos: "+ans);
					result.curPos = ans;
				} else if (j == 3) {
					result.nextTable = ans;

				} else if (j == 4) {
					result.tableCount = ans;
				} else if (j == 5) {
					result.size = ans;
				}
			}
		} catch (Exception e) {
			System.out.println("getBytes error");
		}
		j = 6;
		for (int i = 0; i < result.size; i++, j += 4) {
			result.type[i] = Integer.parseInt(spl[j]);
			result.attrName[i] = spl[j + 1];
			result.defaultValue[i] = spl[j + 2];
			result.constraint[i] = getConstraint(spl[j + 3]);
		}
		return result;
	}

	String TableFtToString(TableFeature t) {
		String s = new String();
		s = s.concat(t.tableName);
		s = s.concat("#");
		try {
			byte[] tmp = new byte[4];
			IntToByte(t.firstPage, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			s = s.concat("#");
			IntToByte(t.curPos, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			s = s.concat("#");
			IntToByte(t.nextTable, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			s = s.concat("#");
			IntToByte(t.tableCount, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			s = s.concat("#");
			IntToByte(t.size, tmp);
			s = s.concat(new String(tmp, "8859_1"));
			s = s.concat("#");
		} catch (Exception e) {
			System.out.println("ToString error");
		}
		for (int i = 0; i < t.size; i++) {
			s = s.concat(String.valueOf(t.type[i]));
			s = s.concat("#");
			s = s.concat(t.attrName[i]);
			s = s.concat("#");
			s = s.concat(t.defaultValue[i]);
			s = s.concat("#");
			s = s.concat(String.valueOf(t.constraint[i]));
			s = s.concat("#");
		}
		return s;
	}

	/* Describe a table when inserted */
	public static class TableFeature {
		String tableName;
		int[] type;
		String[] attrName;
		String[] defaultValue;
		CONSTRAINT[] constraint;
		int firstPage = -1;
		int curPos = -1;// The addr to insert a new record
		int nextTable = -1;// Whether the table has other column
		int tableCount = 1;
		int size;

		public TableFeature(String tableName1, int[] mytype, String[] name,
				String[] dValue, CONSTRAINT[] myConstraint) {
			tableName = tableName1;
			size = mytype.length;
			type = mytype;
			attrName = name;
			defaultValue = dValue;
			constraint = myConstraint;
			firstPage = -1;
			curPos = -1;
			nextTable = -1;
			tableCount = 1;
		}

		public TableFeature(int size) {
			type = new int[size];
			attrName = new String[size];
			defaultValue = new String[size];
			constraint = new CONSTRAINT[size];
		}
	}

	TableFeature[] getTableFt(String tableName) {
		String tableStr = (String) tableStructIndex.get(tableName);
		if(tableStr==null) {System.out.println("getTableFt: tableName:"+tableName+"doesn't existt"); return null;}
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
				int start = ByteToInt(tmp);
				tmp[0] = buf[relative + 2];
				tmp[1] = buf[relative + 3];
				int end = ByteToInt(tmp);
				table[i + 1] = totalStr.substring(start, end);
				tableFt[i + 1] = StringToTableFt(table[i + 1]);
			}
		} catch (Exception e) {
			System.out.println("getTableFt err");
		}
		return tableFt;
	}

	String[] RecordToString(Record r, String tableName) {
		String tableStr = (String) tableStructIndex.get(tableName);
		if(tableStr==null){System.out.println("RecordToString:  tableStructIndex.get(tableName) err"); return null;}
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
				int start = ByteToInt(tmp);
				tmp[0] = buf[relative + 2];
				tmp[1] = buf[relative + 3];
				int end = ByteToInt(tmp);
				table[i + 1] = totalStr.substring(start, end);
				tableFt[i + 1] = StringToTableFt(table[i + 1]);
			}
			for (int i = 0; i < tf.tableCount; i++) {
				result[i] = "";
				for (int j = 0; j < tableFt[i].size; j++) {
					boolean exist = false;
					for (int k = 0; k < r.attrName.length; k++) {
						if (tableFt[i].attrName[j].equals(r.attrName[k])) {
							result[i] += r.attrValue[k];
							exist = true;
						}
					}
					if (!exist)
						result[i] += tableFt[i].defaultValue[j];
					result[i] += "#";
				}
			}
		} catch (Exception e) {
			System.out.println("RecordToString err");
		}
		return result;
	}

	Record StringToRecord(String str, TableFeature tf) {
		String[] aName = new String[tf.size];
		String[] aValue = new String[tf.size];
		String[] tmp = str.split("#");
		if (tmp.length != tf.size) {
			System.out.println("StringToRecord unmatched err");
			return null;
		}
		aName = tf.attrName;
		for (int i = 0; i < tmp.length; i++) {
			aValue[i] = tmp[i];
		}
		Record r = new Record(aName, aValue);
		return r;
	}

	/* Describe a tuple when inserted */
	public static class Record {
		String[] attrName;
		String[] attrValue;

		public Record(String[] name, String[] value) {
			attrName = name;
			attrValue = value;
		}

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
