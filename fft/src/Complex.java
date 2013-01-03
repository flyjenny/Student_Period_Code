public class Complex {
	double real,imag;
	
	public Complex(double a, double b){
		this.real = a;
		this.imag = b;
	}
	
	public Complex plus(Complex c){
		return new Complex(this.real + c.real, this.imag + c.imag);
	}

	public Complex minus(Complex c){
		return new Complex(this.real - c.real, this.imag - c.imag);
	}
	
	public Complex multi(Complex c){
		double newReal = this.real * c.real - this.imag * c.imag;
		double newImag = this.real * c.imag + this.imag * c.real;
		return new Complex(newReal, newImag);
	}
	
	public Complex divide(Complex c){
		double d = c.real * c.real + c.imag * c.imag;
		double newReal = (this.real * c.real + this.imag * c.imag)/d;
		double newImag = (this.imag * c.real - this.real * c.imag)/d;
		return new Complex(newReal, newImag);
	}
	
	public String toString(){
		if(this.imag>0){
			return (this.real+"+"+this.imag+"i");
		}
		else if(this.imag==0){
			return (String.valueOf(this.real));
		}
		else{
			return (this.real+""+this.imag+"i");
		}
	}

}
