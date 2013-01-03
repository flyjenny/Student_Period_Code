
import java.io.*;
import java.text.NumberFormat;


public class Main {
	static int N,to;
	static int a[],b[];	
	static Complex w;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Loading data from datafile...");
		loadData();	
		System.out.println("Data load complete!\n");

		int i;
		for(i=N;i<2*N;i++){
			a[i] = b[i] = 0;	
		}
		
		w = new Complex(Math.cos(Math.PI/N),Math.sin(Math.PI/N));

		Complex inverse_w = (new Complex(1, 0)).divide(w);
			
		Complex Va[] = new Complex[2*N];
		Complex Vb[] = new Complex[2*N];
		System.out.println("Fast Fourier Transform to Polynomial a...");
		FFT(2*N, a, w, Va);
		System.out.println("Fast Fourier Transform to Polynomial b...");
		FFT(2*N, b, w, Vb);

		Complex Vab[] = new Complex[2*N];
		Complex Vc[] = new Complex[2*N];
		for(i=0;i<2*N;i++){
			Vab[i] = Va[i].multi(Vb[i]);
		}
		System.out.println("Inverse Fast Fourier Transform...");
		FFT(2*N, Vab, inverse_w, Vc);
		
		double c[] = new double[2*N];
		NumberFormat formatter = NumberFormat.getNumberInstance();   
		formatter.setMaximumFractionDigits(2);
		System.out.println("Result:");
		for(i=0;i<2*to-1;i++){
			c[i] = Double.parseDouble(formatter.format(Vc[i].real/(2*N)));
			if(Math.abs(c[i])<0.0000000001)	c[i] = 0;
			System.out.println(i+": "+c[i]);
		}
		
		System.out.println("\nOutput result to file\"result5.txt\"");
		outputToFile(c);
		System.out.println("Output compelte!");
	}
	

	static void loadData() {
		try{
			RandomAccessFile f = new RandomAccessFile("data5.dat","r");
			String str = f.readLine();
			to = Integer.valueOf(str)+1;
			N=1;
			while (N<to) N*=2;
			System.out.println(to);
			a = new int[2*N];
			b = new int[2*N];
			String[] splited_str = new String[to];
			
			str=f.readLine();
			splited_str = str.split(",");
			int i;
			for(i=0;i<to;i++){
				a[i] = Integer.valueOf(splited_str[to-1-i]);
				System.out.print(a[i]+" ");
			}
			System.out.println();
			
			str=f.readLine();
			splited_str = str.split(",");
			for(i=0;i<to;i++){
				b[i] = Integer.parseInt(splited_str[to-1-i]);
				System.out.print(b[i]+" ");
			}
			for (i=to;i<N;i++){
				a[i]=0;
				b[i]=0;
				//System.out.print(b[i]+" ");
			}
			
			System.out.println();
			f.close();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	

	static void outputToFile(double d[]) throws IOException {
			PrintWriter out=new PrintWriter("result5.txt");
			int i;
			for(i=2*to-2;i>0;i--){
				out.print(String.valueOf((int)d[i])+" ");
			}
			out.println(String.valueOf((int)d[i]));
			out.close();

	}
	

	static void FFT(int n, int a[], Complex w, Complex V[]){
		if(n == 1){
			V[0] = new Complex(a[0], 0);
		}
		else{
			int a1[] = new int[n/2];
			int a2[] = new int[n/2];
			int i;
			for(i=0;i<n/2;i++){
				a1[i] = a[2*i];
				a2[i] = a[2*i+1];
			}
			Complex U[] = new Complex[n/2];
			Complex W[] = new Complex[n/2];
			FFT(n/2, a1, w.multi(w), U);
			FFT(n/2, a2, w.multi(w), W);
			Complex tmp = new Complex(1, 0);
			for(i=0;i<n/2;i++){
				V[i] = U[i].plus(tmp.multi(W[i]));
				V[i+n/2] = U[i].minus(tmp.multi(W[i]));
				tmp = tmp.multi(w);	//tmp=tmp*w
			}
		}
	}
	

	static void FFT(int n, Complex a[], Complex w, Complex V[]){
		if(n == 1){
			V[0] = a[0];
		}
		else{
			Complex a1[] = new Complex[n/2];
			Complex a2[] = new Complex[n/2];
			int i;
			for(i=0;i<n/2;i++){
				a1[i] = a[2*i];
				a2[i] = a[2*i+1];
			}
			Complex U[] = new Complex[n/2];
			Complex W[] = new Complex[n/2];
			FFT(n/2, a1, w.multi(w), U);
			FFT(n/2, a2, w.multi(w), W);
			Complex tmp = new Complex(1, 0);
			for(i=0;i<n/2;i++){
				V[i] = U[i].plus(tmp.multi(W[i]));
				V[i+n/2] = U[i].minus(tmp.multi(W[i]));
				tmp = tmp.multi(w);
			}
		}
	}

}
