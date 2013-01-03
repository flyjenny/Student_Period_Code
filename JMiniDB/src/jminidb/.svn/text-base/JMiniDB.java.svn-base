package jminidb;

import java.io.IOException;

import tx.Transaction;
import metadata.MetadataMgr;
import buffer.BufferManger;
import io.DBFiles;

public class JMiniDB {

	private static BufferManger buffermrg;
	private static MetadataMgr mdMgr;
	public static final int BUFF_SIZE = 1000;

	static public void init(String fn) {
		dbfile = new DBFiles(fn);
		buffermrg = new buffer.BufferManger(BUFF_SIZE);

		Transaction tx = new Transaction();
		boolean isnew = dbfile.isNew();
		mdMgr = new MetadataMgr(isnew, tx);
		if (isnew) {
			// System.out.println("creating new database");
		} else {
			// System.out.println("recovering existing database");
			// tx.recover();
		}
		// tx.commit();
	}

	static public void initTest() {
		init("d:\\dbtest");
	}

	static private DBFiles dbfile;

	static public DBFiles getDBFile() {
		return dbfile;

	}

	static public BufferManger getBufferMrg() {
		return buffermrg;
	}

	static public MetadataMgr getMdMgr() {
		return mdMgr;
	}

	static public void Close() {
		if (null != buffermrg)
			buffermrg.flushAll();
		try {
			if (null != dbfile)
				dbfile.Close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
