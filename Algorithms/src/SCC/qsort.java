package SCC;

public class qsort {
	private int [] $a;
	qsort(int []a){
		$a=a;
	}
	public static void quicksort(int[][] a, int left, int right) {
        if (left<right){
       // 	partition(a, left, right);
        	partition(a, left, right);
        	int mid=(left+right)/2;
            quicksort(a, left, mid-1);
            quicksort(a, mid+1, right);
        }
        
    }

    private static int partition(int[][] a, int left, int right) {
        int pivot=a[left][1];
        int l=left;        
        int r=right;
        while(l<r){
        	while ((l<=r)&&(a[l][1]<=pivot)){
        //		System.out.println(a[l][1]);
        		l++;
       // 		System.out.println("l "+l);
        	}
        	
        	while((r>=l)&&(a[r][1]>pivot)){
        		r--;
        	}
        	if(l<r){
        		int temp1=a[l][1];
                int temp2=a[l][0];
            	a[l][1] = a[r][1];
            	a[l][0] = a[r][0];
                a[r][1] = temp1;
                a[r][0] = temp2;
        	}
        	
        }
        int mid=r;
        int temp1=a[l-1][1];
        int temp2=a[l-1][0];
    	a[l-1][1] = a[mid][1];
    	a[l-1][0] = a[mid][0];
        a[mid][1] = temp1;
        a[mid][0] = temp2;
        return mid;
    }

    
}
