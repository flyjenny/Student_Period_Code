package DiskManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/**
 * @author BennyChan 缓存，使用1M空间进行数据库的缓存，使用老化算法进行页面替换。
 */
public class BufferManager extends Thread {
	public HashMap<Integer, Integer> $record = new HashMap<Integer, Integer>();
	public HashMap<Integer, Integer> $reverserecord = new HashMap<Integer, Integer>();
	public byte $diskbuffer[][];
	public boolean $readrecord[];
	public boolean $writerecord[];
	public int $used;
	public char $age[];
	public UnderlyingOperating $mydisk;
	public int $totalfilesize;
	public Timer timer;
	private final int timeToReflash = 1;
	private final int timeToWritePage = 1;
	
	public static  Semaphore semp = new Semaphore(1);
	public int i=0;
	public int j=0;
	public int k=0;
	public int p=0;
	
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
		$age = new char[256];
		int i = 0;
		for (i = 0; i < 256; i++) {
			bufferreadpage(i, $diskbuffer[i]);
		}
		for (i = 0; i < 256; i++) {
			$readrecord[i] = false;
			$writerecord[i] = false;
			$age[i] = 0x0000;
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
        try{
        semp.acquire();
				changeAge();
      } catch (InterruptedException e) {
					e.printStackTrace();
				}
				semp.release();
			}
		}, timeToReflash * 1000, timeToReflash * 1000);
		
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					semp.acquire();
					TimeToWrite();
//					System.out.println("disk has been write to disk");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				semp.release();
			}
		}, timeToWritePage * 1000, timeToWritePage * 1000);

	}

	/**
	 * @param page
	 * @param bytebuffer
	 * @return true for successfully readpage,false for failure.
	 *         缓冲读操作，先检查缓存是否存在需要读入的块，不存在访问磁盘
	 */
	public boolean bufferreadpage(int page, byte[] bytebuffer) {
		
		try {
//			System.out.println(page);
			
			semp.acquire();
			
			if ($record.containsKey(page)) {// find the page in the buffer
//				System.out.println((i++)+"read from buffer");
				int value = $record.get(page);
				System.arraycopy($diskbuffer[value], 0, bytebuffer, 0,
						$diskbuffer[value].length);
				$readrecord[value] = true;
				semp.release();
				return true;

			} else {// not find the page in the buffer
				if ($used < 256) {// these exist empty buffer
					if ($mydisk.read_page(page, $diskbuffer[$used]) < 0) {
						
						semp.release();
						return false;
					}
					
					$record.put(page, $used);
					$reverserecord.put($used, page);
					System.arraycopy($diskbuffer[$used], 0, bytebuffer, 0,
							$diskbuffer[$used].length);
					$readrecord[$used] = true;
					$used++;
					
				} else {// All the buffer has been used
//					System.out.println((i++)+"read from disk");
					int pagenum = findAgingPage($age, 0, 255);
					if ($writerecord[pagenum]) {
						try {
							String tmp = new String($diskbuffer[pagenum],
									"8859_1");
							$mydisk.write_page($reverserecord.get(pagenum), tmp);
							$writerecord[pagenum] = false;

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
		
					}
					if ($mydisk.read_page(page, $diskbuffer[pagenum]) < 0){
						semp.release();
						return false;
					}
					System.arraycopy($diskbuffer[pagenum], 0, bytebuffer, 0,
							$diskbuffer[pagenum].length);
					$readrecord[pagenum] = true;
					$record.remove($reverserecord.get(pagenum));
					$reverserecord.remove(pagenum);
					$record.put(page, pagenum);
					$reverserecord.put(pagenum, page);
				}
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
//		System.out.println((k++)+"read Release");
		
		semp.release();
		return true;
	}

	/**
	 * @param page
	 * @param bytebuffer
	 * @return true for successfully writepage,false for failure.
	 * @throws UnsupportedEncodingException
	 * @throws InterruptedException
	 *             缓冲写，先存在缓存，等待延迟写
	 */
	public boolean bufferwritepage(int page, String bytebuffer)
			throws UnsupportedEncodingException, InterruptedException {
		semp.acquire();
//		System.out.println(page);
		if ($record.containsKey(page)) {
//			System.out.println((j++)+"write to buffer");
			int value = $record.get(page);

			$diskbuffer[value] = bytebuffer.getBytes("8859_1");
			$writerecord[value] = true;

			$readrecord[value] = true;
		} else {
			if ($used < 256) {
				$diskbuffer[$used] = bytebuffer.getBytes("8859_1");
				$record.put(page, $used);
				$reverserecord.put($used, page);
				$readrecord[$used] = true;
				$writerecord[$used] = true;
			} else {
//				System.out.println((j++)+"write to disk");
				int pagenum = findAgingPage($age, 0, 255);
				if ($writerecord[pagenum]) {
					try {
						String tmp = new String($diskbuffer[pagenum], "8859_1");
						$mydisk.write_page($reverserecord.get(pagenum), tmp);

					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					$writerecord[pagenum]=false;

				}
				if (!$writerecord[pagenum]) {

					$diskbuffer[pagenum] = bytebuffer.getBytes("8859_1");
//					System.arraycopy(bytebuffer.getBytes("8859_1"), 0, $diskbuffer[pagenum], 0,
//							bytebuffer.getBytes("8859_1").length);
					$writerecord[pagenum] = true;
					$readrecord[pagenum] = true;
					$record.remove($reverserecord.get(pagenum));
					$reverserecord.remove(pagenum);
					$record.put(page, pagenum);
					$reverserecord.put(pagenum, page);
				}
			}
		}
//		System.out.println((p++)+"write Release");
		semp.release();
		return true;
	}

	/**
	 * 更改$age，定时进行老化算法的计数。
	 */
	public void changeAge() {
		for (int i = 0; i < 256; i++) {
			if ($readrecord[i]) {
				$age[i] = (char) ($age[i] >>> 1);
				$age[i] = (char) ($age[i] | 0x8000);
				$readrecord[i] = false;
			} else {
				$age[i] = (char) ($age[i] >>> 1);
			}
		}
	}

	/**
	 * 已更改的缓冲块写入磁盘，定时执行。
	 */
	public void TimeToWrite() {
		String tmp;
		for (int i = 0; i < 256; i++) {
			if ($writerecord[i]) {
//				System.out.println(i+" has been write");
				try {
					tmp = new String($diskbuffer[i], "8859_1");
					$mydisk.write_page($reverserecord.get(i), tmp);
					$writerecord[i] = false;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param age
	 * @param left
	 * @param right
	 * @return 最近未被使用的缓存块号。
	 */
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
		BufferManager bm = new BufferManager("myfile");

		/*
		 * Scanner input = new Scanner(System.in);
		 * System.out.print("Please input: "); byte b[] = new byte[4096]; int i
		 * = input.nextInt();
		 */
		/*
		 * while (i != -1) { bm.bufferreadpage(i, b); String tmp = new String(b,
		 * "8859_1"); System.out.println(tmp);
		 * 
		 * for(j=0;j<6;j++) System.out.println((int)bm.$age[0]);
		 * System.out.print("Please input: "); i = input.nextInt(); }
		 */

	}
}
