package chenbin.test;

public class InsertionSort {
	public static void main(String[] args){
		int source[]={1,27,0,3,2,5,7,10};
		int tmp=0;
		for(int j=1;j<source.length;j++){
			tmp=source[j];
			int i=j-1;
			while(i>=0&&tmp<source[i]){
				source[i+1]=source[i];
				i--;
			}
			source[i+1]=tmp;
		}
		for(int i=0;i<source.length;i++){
			System.out.println(i+1+" "+source[i]);
		}
	}
}
