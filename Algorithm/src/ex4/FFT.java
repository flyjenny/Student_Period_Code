package ex4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class FFT {
	private int num;
	private int[] a,b;		// 储存输入
	private int[] c;	
	private Complex w;
	String $filename;
	String $outfile;
	FFT(String filename,String outfile){$filename=filename; $outfile=outfile;}
	
	public void work() {
		int i;
		w = new Complex(Math.cos(Math.PI/num),Math.sin(Math.PI/num));

		Complex inverse_w = (new Complex(1, 0)).Div(w);//  1/w
			
		Complex Va[] = new Complex[2*num];
		Complex Vb[] = new Complex[2*num];
		FastFT(2*num, a, w, Va);//FFT of a
		FastFT(2*num, b, w, Vb);//FFT of b

		Complex Vab[] = new Complex[2*num];
		Complex Vc[] = new Complex[2*num];
		for(i=0;i<2*num;i++){
			Vab[i] = Va[i].Mult(Vb[i]);  //得到结果多项式经过的点
		}
		FastFT(2*num, Vab, inverse_w, Vc);//逆FFT
		
		c = new int[2*num];
		for(i=0;i<2*num-1;i++){ 
			c[i]=(int) Math.rint(Vc[i].r/(2*num));	//傅里叶变换会产生很接近的近似值，故四舍五入处理
			//System.out.println(i+": "+c[i]);
		}

	}


	public void FastFT(int n, Complex[] vab, Complex inverseW,
			Complex[] vc) {
		// TODO Auto-generated method stub
		if(n == 1){
			vc[0] = vab[0];
		}
		else{
			Complex vab1[] = new Complex[n/2];
			Complex vab2[] = new Complex[n/2];
			int i;
			for(i=0;i<n/2;i++){
				vab1[i] = vab[2*i];
				vab2[i] = vab[2*i+1];
			}
			Complex U[] = new Complex[n/2];
			Complex W[] = new Complex[n/2];
			FastFT(n/2, vab1, inverseW.Mult(inverseW), U);
			FastFT(n/2, vab2, inverseW.Mult(inverseW), W);
			Complex tmp = new Complex(1, 0);
			for(i=0;i<n/2;i++){
				vc[i] = U[i].Plus(tmp.Mult(W[i]));
				vc[i+n/2] = U[i].Minus(tmp.Mult(W[i]));
				tmp = tmp.Mult(inverseW);
			}
		}
	}

	private void FastFT(int n, int[] a, Complex w2, Complex[] va) {
		// TODO Auto-generated method stub
		if(n == 1){
			va[0] = new Complex(a[0], 0);
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
			FastFT(n/2, a1, w2.Mult(w2), U);
			FastFT(n/2, a2, w2.Mult(w2), W);
			Complex tmp = new Complex(1, 0);
			for(i=0;i<n/2;i++){
				va[i] = U[i].Plus(tmp.Mult(W[i]));
				va[i+n/2] = U[i].Minus(tmp.Mult(W[i]));
				tmp = tmp.Mult(w2);	
			}
		}
	}
	

	public void read() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader($filename));
		num = Integer.parseInt(in.readLine())+1;
		a = new int[num*2];
		b = new int[num*2];
		String str = in.readLine();
		String[] list;
		list = str.split(" ");
		for(int i=0;i<num;i++){
			a[i]=Integer.parseInt(list[num-i-1]);
		}
		str = in.readLine();
		list = str.split(" ");
		for(int i=0;i<num;i++){
			b[i]=Integer.parseInt(list[num-i-1]);
		}
		for(int i=num;i<2*num;i++){
			a[i]=0;
			b[i]=0;
		}
		//for(int i=0;i<2*num;i++)
			//System.out.print(a[i]);
	}
	public void Write() throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = new PrintWriter(new File($outfile));
//		System.out.println("Result is ");
		for(int i=2*num-2;i>-1;i--){
	//		System.out.print(c[i]+" ");
			out.print(c[i]+",");
		}
	//	System.out.print(c[0]);
	//	out.print(c[0]);
		out.close();
	}
}
