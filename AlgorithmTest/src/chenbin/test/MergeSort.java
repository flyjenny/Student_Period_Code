package chenbin.test;

import java.util.ArrayList;

public class MergeSort {
	public static void main(String[] args){
/*		int source[]={1,4,2,45,10,32,54,32,0};
		mergeSort(0,source.length-1,source);
		for(int i=0;i<9;i++){
			System.out.println(source[i]);
		}
*/	
		ArrayList list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.remove(1);
		System.out.println(list.get(0));
	}
	public static void mergeSort(int left,int right,int[] source){
		if(right<=left){
			return;
		}
		else{
			int mid=(left+right)/2;
			
			mergeSort(left,mid-1,source);
			mergeSort(mid,right,source);
			merge(left,mid,right,source);
			return;
		}
	}
	public static void merge(int left,int mid,int right,int[] source){
		int[] l=new int[mid-left+1];
		int[] r=new int[right-mid+2];
		for(int i=0;i<mid-left;i++){
			l[i]=source[left+i];	
		}
		
		for(int i=0;i<right-mid+1;i++){
			r[i]=source[mid+i];
		}
		l[mid-left]=10000;
		r[right-mid+1]=10000;
		for(int i=0,j=0,k=left;k<right+1;k++){
			if(r[j]>l[i]){
				source[k]=l[i];
				i++;
			}
			else{
				source[k]=r[j];
				j++;
			}
		}
	}
}
