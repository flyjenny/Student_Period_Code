package RecordManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author wyj
 *	short,int,float,double,long和byte相互转换
 */
public class Transform {
	public Transform(){
		
	}
	public void ShortToByte(short[] curPtr, byte[] arr) {
		for (int i = 0; i < curPtr.length; i++) {
			arr[0 + 2 * i] = (byte) (curPtr[i]);
			arr[1 + 2 * i] = (byte) (curPtr[i] >>> 8);
		}
	}

	public void IntToByte(int[] a, byte[] arr) {
		for (int i = 0; i < a.length; i++) {
			arr[0 + 4 * i] = (byte) (a[i]);
			arr[1 + 4 * i] = (byte) (a[i] >> 8);
			arr[2 + 4 * i] = (byte) (a[i] >> 16);
			arr[3 + 4 * i] = (byte) (a[i] >> 24);
		}
	}

	public void IntToByte(int a, byte[] arr) {

		arr[0] = (byte) (a);
		arr[1] = (byte) (a >> 8);
		arr[2] = (byte) (a >> 16);
		arr[3] = (byte) (a >> 24);

	}
	
	public byte[] FloatToByte(float a){
		byte[] arr=new byte[4];
		int i=Float.floatToIntBits(a);
		IntToByte(i,arr);
		return arr;
	}
	
	public float ByteToFloat(byte[] arr){
		int i=ByteToInt(arr);
		Float s=Float.intBitsToFloat(i);
		return s;
	}
	
	  public  byte[] DoubleToByte(double  d){
		    byte[] b=new  byte[8];
		    long  l=Double.doubleToLongBits(d);
		    for (int  i=0;i<b.length;i++){
		      b[i]=new  Long(l).byteValue();
		      l=l>>8;


		    }
		    return  b;
		  }
	  
	  public   double  ByteToDouble(byte[] b){
		    long  l;

		    l=b[0];
		    l&=0xff;
		    l|=((long)b[1]<<8);
		    l&=0xffff;
		    l|=((long)b[2]<<16);
		    l&=0xffffff;
		    l|=((long)b[3]<<24);
		    l&=0xffffffffl;
		    l|=((long)b[4]<<32);
		    l&=0xffffffffffl;

		    l|=((long)b[5]<<40);
		    l&=0xffffffffffffl;
		    l|=((long)b[6]<<48);
		    l&=0xffffffffffffffl;

		    l|=((long)b[7]<<56);
		    return  Double.longBitsToDouble(l);
		  }

	public int ByteToInt(byte[] arr) {
		int a = (((int) arr[0]) & 0xff) * 1 + (((int) arr[1]) & 0xff) * 256
				+ (((int) arr[2]) & 0xff) * 256 * 256 + (((int) arr[3]) & 0xff)
				* 256 * 256 * 256;
		return a;
	}
	
	public byte[] LongToByte(long x) { 
		byte[] bb=new byte[8];
        bb[ 0] = (byte) (x >> 56); 
        bb[ 1] = (byte) (x >> 48); 
        bb[ 2] = (byte) (x >> 40); 
        bb[ 3] = (byte) (x >> 32); 
        bb[ 4] = (byte) (x >> 24); 
        bb[ 5] = (byte) (x >> 16); 
        bb[ 6] = (byte) (x >> 8); 
        bb[ 7] = (byte) (x >> 0); 
        return bb;
  } 

 public long ByteToLong(byte[] bb) { 
       return ((((long) bb[ 0] & 0xff) << 56) 
               | (((long) bb[ 1] & 0xff) << 48) 
               | (((long) bb[ 2] & 0xff) << 40) 
               | (((long) bb[ 3] & 0xff) << 32) 
               | (((long) bb[ 4] & 0xff) << 24) 
               | (((long) bb[ 5] & 0xff) << 16) 
               | (((long) bb[ 6] & 0xff) << 8) | (((long) bb[ 7] & 0xff) << 0)); 
  } 
public static byte[] intToByteArray1(int i) {   
  byte[] result = new byte[4];   
  result[0] = (byte)((i >> 24) & 0xFF);
  result[1] = (byte)((i >> 16) & 0xFF);
  result[2] = (byte)((i >> 8) & 0xFF); 
  result[3] = (byte)(i & 0xFF);
  return result;
 }
	
	public static void main(String []args){
//		double d=1327214234.0;String s;
		Transform trans=new Transform();
//		byte[] tmp=trans.DoubleToByte(d);
//		double d1=trans.ByteToDouble(tmp);
//		System.out.println(s=String.valueOf(d1));
//		double d2=Double.parseDouble(s);
//		System.out.println(d2);
//		if(d==d2) System.out.println("true");
//		if(d1<0) System.out.println("How come");
//		String retValue = null; 
//		DecimalFormat df = new DecimalFormat("#,##"); 
//		df.setGroupingUsed(false);
//		df.setMinimumFractionDigits(1); 
//		df.setMaximumFractionDigits(5);  //保留小数点后3位（四舍五入） 
//		retValue = df.format(d1); 
//		System.out.println(retValue);
//		
//		NumberFormat nf= NumberFormat.getInstance()
//		;
//		nf.setGroupingUsed(false);
//		System.out.println(nf.format(d1));
		long s=2;
		byte [] tmp=trans.LongToByte(s);
		long s1=trans.ByteToLong(tmp);
		System.out.println(s1);
		
	}
}
