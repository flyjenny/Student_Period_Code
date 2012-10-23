package City;

public class building {
	private int left;
	private int high;
	private int right;
	public building(int l, int h, int r){
		if(l>r)
			System.out.println("error building.");
		else{
			left = l;
			high = h;
			right = r;
		}	
	}
	public int getleft(){
		return left;
	}
	public int gethigh(){
		return high;
	}
	public int getright(){
		return right;
	}

}
