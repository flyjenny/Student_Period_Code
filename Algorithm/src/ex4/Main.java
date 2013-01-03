package ex4;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		long beginTime =System.currentTimeMillis();
		FFT fft1 = new FFT("input4_1.dat","result4_1.dat");
		fft1.read();
		fft1.work();
		fft1.Write();
		
		FFT fft2 = new FFT("input4_2.dat","result4_2.dat");
		fft2.read();
		fft2.work();
		fft2.Write();
		
		FFT fft3 = new FFT("input4_3.dat","result4_3.dat");
		fft3.read();
		fft3.work();
		fft3.Write();
		
		long endTime =System.currentTimeMillis();
//		System.out.println("RunningTime:"+ (endTime - beginTime) + "ms");
	}
}
