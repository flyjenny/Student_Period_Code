package test.buff;

import io.Block;
import io.Page;

import org.junit.Assert;
import org.junit.Test;

import buffer.Buffer;
import buffer.BufferManger;
import buffer.PageFormatter;
import buffer.ZeroPageFormatter;
import jminidb.JMiniDB;

public class TestBuffer {
	@Test
	public void testBuffFunc() {

		JMiniDB.initTest();
		BufferManger bm = JMiniDB.getBufferMrg();

		PageFormatter pf = new PageFormatter() {

			@Override
			public void format(Page contents) {
				int recsize = Page.STR_SIZE("abc");
				for (int i = 0; i + recsize <= Page.BLOCK_SIZE; i += recsize)
					contents.setString(i, "abc");

			}

		};

		Buffer buff = bm.allocateNew("junk", pf);
		String s = buff.getString(0); // will be "abc"
		int blknum = buff.block().number();
		bm.free(buff);
		// System.out.println("The first value in block " + blknum + " is " +
		// s);
		JMiniDB.Close();
		Assert.assertEquals(s, "abc");
	}

	@Test
	public void testBuffFlush1() {
		JMiniDB.initTest();
		BufferManger bm = JMiniDB.getBufferMrg();
		Buffer buff = bm.allocateNew("flush", new ZeroPageFormatter());
		buff.setString(0, "hellox");
		bm.free(buff);

		JMiniDB.Close();
	}

	@Test
	public void testBuffFlush2() {
		JMiniDB.initTest();
		BufferManger bm = JMiniDB.getBufferMrg();
		int sz = JMiniDB.getDBFile().fileSize("flush");
		Block blk = new Block("flush", sz - 1);
		Buffer buff = bm.allocate(blk);
		String w = buff.getString(0);
		bm.free(buff);
		JMiniDB.Close();
		Assert.assertEquals(w, "hellox");
	}

}
