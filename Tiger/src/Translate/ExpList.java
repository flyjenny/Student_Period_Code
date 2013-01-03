package Translate;

import java.util.ArrayList;

public class ExpList {
	public Exp head;
	public ExpList tail;

	public ExpList(Exp h, ExpList t) {
		head = h;
		tail = t;
	}	
	
	public ExpList reverse() {
		   ExpList answer = null;
	       ArrayList<Exp> record = new ArrayList<Exp>();
	       ExpList pointer = this;
	       while(pointer != null){
	      	 record.add(pointer.head);
	      	 pointer = pointer.tail;
	       }
	       
	       for(int i = 0; i != record.size(); ++i)
	      	 answer = new ExpList(record.get(i) ,answer);
	       return answer;
		}

}
