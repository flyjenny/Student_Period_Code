package Tree;

import java.util.ArrayList;


public class ExpList {
	public Expv head;
	public ExpList tail;

	public ExpList(Expv h, ExpList t) {
		head = h;
		tail = t;
	}	
	
	public ExpList reverse() {
	   ExpList answer = null;
       ArrayList<Tree.Expv> record = new ArrayList<Tree.Expv>();
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
