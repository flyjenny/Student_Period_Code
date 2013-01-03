package Translate;

public class StringFrag extends Frag{
	Temp.Label label;
	String value;
	
	public StringFrag(Temp.Label l,String v,StringFrag s){
		label = l;
		value = v;
		tail = s;
	}
	
	public void display(){
		System.out.println(label.toString()+":"+value);
	}
}
