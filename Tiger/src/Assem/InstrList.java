package Assem;

import java.util.ArrayList;

public class InstrList {
  public Instr head;
  public InstrList tail;
  public InstrList(Instr h, InstrList t) {
    head=h; tail=t;
  }
  
  public void display(){
	  head.display();
	  if(tail != null)
		  tail.display();
  }

  public void attach(Instr add) {
	  InstrList pointer = this;
	  while(pointer.tail != null)
		  pointer = pointer.tail;
	  pointer.tail = new InstrList(add,null);
  }

  public InstrList reverse() {
		 InstrList answer = null;
         ArrayList<Instr> record = new ArrayList<Instr>();
         InstrList pointer = this;
         while(pointer != null){
        	 record.add(pointer.head);
        	 pointer = pointer.tail;
         }
         
         for(int i = 0; i != record.size(); ++i)
        	 answer = new InstrList(record.get(i) ,answer);
         return answer;

  }
}
