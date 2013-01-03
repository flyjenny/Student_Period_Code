package io;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import jminidb.JMiniDB;

public class Page {
	public static final int BLOCK_SIZE = 400;

	public static final int INT_SIZE = Integer.SIZE / Byte.SIZE;
	public static final int FLOAT_SIZE = Double.SIZE / Byte.SIZE;

	public static final int STR_SIZE(int n) {
		float bytesPerChar = Charset.defaultCharset().newEncoder()
				.maxBytesPerChar();
		bytesPerChar = 1;
		return INT_SIZE + (n * (int) bytesPerChar);
	}

	public static final int STR_SIZE(String s) {
		return STR_SIZE(s.length());
	}

	private ByteBuffer contents = ByteBuffer.allocateDirect(BLOCK_SIZE);
	private DBFiles file = JMiniDB.getDBFile();

	public synchronized void read(Block blk) {
		file.read(blk, contents);
	}

	/**
	 * Writes the contents of the page to the specified disk block.
	 * 
	 * @param blk
	 *            a reference to a disk block
	 */
	public synchronized void write(Block blk) {
		file.write(blk, contents);
	}

	/**
	 * Appends the contents of the page to the specified file.
	 * 
	 * @param filename
	 *            the name of the file
	 * @return the reference to the newly-created disk block
	 */
	public synchronized Block append(String filename) {
		return file.append(filename, contents);
	}

	/**
	 * Returns the integer value at a specified offset of the page. If an
	 * integer was not stored at that location, the behavior of the method is
	 * unpredictable.
	 * 
	 * @param offset
	 *            the byte offset within the page
	 * @return the integer value at that offset
	 */
	public synchronized int getInt(int offset) {
		contents.position(offset);
		return contents.getInt();
	}

	/**
	 * Writes an integer to the specified offset on the page.
	 * 
	 * @param offset
	 *            the byte offset within the page
	 * @param val
	 *            the integer to be written to the page
	 */
	public synchronized void setInt(int offset, int val) {
		contents.position(offset);
		contents.putInt(val);
	}

	public synchronized void setFloat(int offset, double val) {
		contents.position(offset);
		contents.putDouble(val);

	}

	public synchronized double getFloat(int offset) {
		contents.position(offset);
		return contents.getDouble();
	}

	/**
	 * Returns the string value at the specified offset of the page. If a string
	 * was not stored at that location, the behavior of the method is
	 * unpredictable.
	 * 
	 * @param offset
	 *            the byte offset within the page
	 * @return the string value at that offset
	 */
	public synchronized String getString(int offset) {
		contents.position(offset);
		int len = contents.getInt();
		byte[] byteval = new byte[len];
		contents.get(byteval);
		return new String(byteval);
	}

	/**
	 * Writes a string to the specified offset on the page.
	 * 
	 * @param offset
	 *            the byte offset within the page
	 * @param val
	 *            the string to be written to the page
	 */
	public synchronized void setString(int offset, String val) {
		contents.position(offset);
		byte[] byteval = val.getBytes();
		contents.putInt(byteval.length);
		contents.put(byteval);
	}
}
