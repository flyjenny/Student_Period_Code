package _acessfile;

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
		str=new String[50];
		for(int i=0;i<50;i++){
			str[i]="test"+i;
		}
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					semp.acquire();
					change1();
					for(int i=0;i<5;i++)
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
					for(int i=0;i<5;i++)
						System.out.println("IN thread b");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				semp.release();
			}
		}, 0, 750);
		
	}
	public void change1(){
		i++;
		System.out.println(Thread.currentThread().getName()+" "+i);

	}
	public void change2(){
		i--;
		System.out.println(Thread.currentThread().getName()+" "+i);
	}
	public void change3(){
		try {
			semp.acquire();
			i+=2;
			for(int i=0;i<20;i++)
				System.out.println(Thread.currentThread().getName()+" "+i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		semp.release();
		
	}
	public static void main(String[] args) {
		Driver a=new Driver();
//			a.change3();
		for(int i=0;i<50;i++){
			System.out.println(a.str[i]);
		}
	}

}
