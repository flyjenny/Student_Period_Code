package ex4;

public class Complex {
	public double r,i;
	public Complex(double a,double b){
		r=a;
		i=b;
	}
	public Complex Plus(Complex e){
		return new Complex(r+e.r,i+e.i);
	}
	public Complex Minus(Complex e){
		return new Complex(r-e.r,i-e.i);
	}
	public Complex Mult(Complex e){
		double r1=r*e.r-i*e.i;
		double i1=r*e.i+i*e.r;
		return new Complex(r1,i1);
	}
	public Complex Div(Complex e){
		double r1=(r*e.r+i*e.i)/(e.i*e.i+e.r*e.r);
		double i1=(i*e.r-r*e.i)/(e.i*e.i+e.r*e.r);
		return new Complex(r1,i1);
	}

}
