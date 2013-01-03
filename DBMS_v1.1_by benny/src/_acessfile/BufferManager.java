package _acessfile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class BufferManager {
	public HashMap<Integer, Integer> $record = new HashMap<Integer, Integer>();
	public HashMap<Integer, Integer> $reverserecord = new HashMap<Integer, Integer>();
	public byte $diskbuffer[][];
	public boolean $readrecord[];
	public boolean $writerecord[];
	public int $used;
	public char $age[];
	public UnderlyingOperating $mydisk;
	public int $totalfilesize;

	public BufferManager(String name) {
		$mydisk = new UnderlyingOperating(name);
		try {
			$totalfilesize = $mydisk.disk_open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		$diskbuffer = new byte[256][4096];
		$readrecord = new boolean[256];
		$writerecord = new boolean[256];
		$used = 0;
		int i = 0;
		for (i = 0; i < 256; i++) {
			bufferreadpage(i, $diskbuffer[i]);
		}
		for (i = 0; i < 256; i++) {
			$readrecord[i] = false;
			$writerecord[i] = false;
			$age[i] = 0x0000;
		}

	}

	public boolean bufferreadpage(int page, byte[] bytebuffer) {
		if ($record.containsKey(page)) {// find the page in the buffer
			int value = $record.get(page);
			System.arraycopy($diskbuffer[value], 0, bytebuffer, 0, 4096);
			$readrecord[value] = true;
			return true;

		} else {// not find the page in the buffer
			if ($used < 256) {// these exist empty buffer
				if ($mydisk.read_page(page, $diskbuffer[$used - 1]) < 0)
					return false;
				$record.put(page, $used - 1);
				$reverserecord.put($used - 1, page);
				System.arraycopy($diskbuffer[$used - 1], 0, bytebuffer, 0, 4096);
				$readrecord[$used - 1] = true;
				$used++;
			} else {// All the buffer has been used
				int pagenum = findAgingPage($age, 0, 255);
				if ($writerecord[pagenum]) {
					try {
						String tmp = new String($diskbuffer[pagenum], "8859_1");
						$mydisk.write_page($record.get(pagenum), tmp);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					$writerecord[pagenum] = false;
				}
				if ($mydisk.read_page(page, $diskbuffer[pagenum]) < 0)
					return false;
				System.arraycopy($diskbuffer[pagenum], 0, bytebuffer, 0, 4096);
				$readrecord[pagenum] = true;
				$record.remove($reverserecord.get(pagenum));
				$reverserecord.remove(pagenum);
				$record.put(page, pagenum);
				$reverserecord.put(pagenum, page);
			}
		}
		return true;
	}

	public boolean bufferwritepage(int page, String bytebuffer)
			throws UnsupportedEncodingException {
		if ($record.containsKey(page)) {
			int value = $record.get(page);
			$diskbuffer[value] = bytebuffer.getBytes("8859_1");
			$writerecord[value] = true;
			$readrecord[value] = true;
		} else {
			if ($used < 256) {
				$diskbuffer[$used - 1] = bytebuffer.getBytes("8859_1");
				$record.put(page, $used - 1);
				$reverserecord.put($used - 1, page);
				$readrecord[$used - 1] = true;
				$writerecord[$used - 1] = true;
			} else {
				int pagenum = findAgingPage($age, 0, 255);
				if ($writerecord[pagenum]) {
					try {
						String tmp = new String($diskbuffer[pagenum], "8859_1");
						$mydisk.write_page($record.get(pagenum), tmp);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					$writerecord[pagenum] = false;
				}
				if (!$writerecord[pagenum]) {
					$diskbuffer[pagenum] = bytebuffer.getBytes("8859_1");
					$readrecord[pagenum] = true;
					$writerecord[pagenum] = true;
					$record.remove($reverserecord.get(pagenum));
					$reverserecord.remove(pagenum);
					$record.put(page, pagenum);
					$reverserecord.put(pagenum, page);
				}
			}
		}
		return true;
	}

	public void changeAge() {
		for (int i = 0; i < 256; i++) {
			if ($readrecord[i]) {
				$age[i] = (char) ($age[i] >>> 1);
				$age[i] = (char) ($age[i] | 0x8000);
			} else {
				$age[i] = (char) ($age[i] >>> 1);
			}
		}
	}

	public int findAgingPage(char age[], int left, int right) {
		if (left >= right) {
			return ((int) (age[left]) < (int) (age[right]) ? left : right);
		} else {
			int mid = (left + right) / 2;
			int l = findAgingPage(age, left, mid);
			int r = findAgingPage(age, mid + 1, right);
			return ((int) (age[l]) < (int) (age[r]) ? l : r);
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		char i = 0x0002;
		System.out.println((int) i);
		i = (char) (i >>> 1);
		i = (char) (i | 0x8000);

		System.out.println((int) i);
		i = 0x0002;
		i = (char) (i >>> 1);
		// i=(char)(i&0x7FFF);
		System.out.println((int) i);
	}

	class testthread implements Runnable {
		private String $a;

		testthread(String a) {
			$a = a;
		}

		public void run() {
			for (int i = 0; i < 10; i++) {
				System.out.println($a + i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
