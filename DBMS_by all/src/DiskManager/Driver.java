package DiskManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class Driver {
	public static final Semaphore semp = new Semaphore(1);
	public int i = 2;
	public Timer timer;
	public String[] str;

	public Driver() {
		timer = new Timer();
		str = new String[50];
		for (int i = 0; i < 50; i++) {
			str[i] = "test" + i;
		}
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					semp.acquire();
					change1();
					for (int i = 0; i < 5; i++)
						System.out.println("IN thread a");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				semp.release();
			}
		}, 0, 500);
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					semp.acquire();
					change2();
					for (int i = 0; i < 5; i++)
						System.out.println("IN thread b");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				semp.release();
			}
		}, 0, 750);

	}

	public void change1() {
		i++;
		System.out.println(Thread.currentThread().getName() + " " + i);

	}

	public void change2() {
		i--;
		System.out.println(Thread.currentThread().getName() + " " + i);
	}

	public void change3() {
		try {
			semp.acquire();
			i += 2;
			for (int i = 0; i < 20; i++)
				System.out.println(Thread.currentThread().getName() + " " + i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		semp.release();

	}

	public static void main(String[] args) throws InterruptedException,
			IOException {
/*		BufferManager bm = new BufferManager("myfile");
		String str = "tttttttttttttttttttttttttttttttttttttttttt";
		String display = null;
		byte buffer[] = new byte[4096];
		Random a = new Random(10);

		System.out.println(bm.$totalfilesize);
		OutputStreamWriter file1 = new OutputStreamWriter(new FileOutputStream(
				"data1.txt"));
		OutputStreamWriter file2 = new OutputStreamWriter(new FileOutputStream(
				"data2.txt"));
		for (int i = 0; i < 500; i++) {
			int ra = a.nextInt(500);

			bm.bufferwritepage(ra, ra + " " + str);
			file1.write(ra + " " + str + "\r\n");

			bm.bufferreadpage(ra, buffer);
			display = new String(buffer, "8859_1");
			// System.out.println(display);
			file2.write(display + "\r\n");

		}
		file1.flush();
		file1.close();
		file2.flush();
		file2.close();
		bm.timer.cancel();
		bm.TimeToWrite();
*/
		OutputStreamWriter file1 = new OutputStreamWriter(new FileOutputStream(
		"data1.txt"));
		OutputStreamWriter file2 = new OutputStreamWriter(new FileOutputStream(
		"data2.txt"));
		byte buffer[] = new byte[4096];
		String str = "binchen";
		String display = null;
		DiskManager dm=new DiskManager("myfile");
		dm.OpenFile("test1");
		for(int i=0;i<50;i++){
			dm.WritePage(i, i+" "+str);
			file1.write(i+" "+str+"\r\n");
			dm.ReadPage(i, buffer);
			display = new String(buffer, "8859_1");
			file2.write(display + "\r\n");	
		}
/*		for(int i=0;i<50;i++){
			dm.ReadPage(i, buffer);
			display = new String(buffer, "8859_1");
			file2.write(display + "\r\n");	
		}
*/		file1.flush();
		file1.close();
		file2.flush();
		file2.close();
//		dm.$buffermanager.timer.cancel();
//		dm.$buffermanager.TimeToWrite();
		
	}
}
