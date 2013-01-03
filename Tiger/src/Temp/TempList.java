package Temp;

import java.util.ArrayList;


public class TempList {
   public Temp head;
   public TempList tail;
   public TempList(Temp h, TempList t) {head=h; tail=t;}
   
   public void display(){
	   head.display();
   }
   
   public TempList reverse() {
	   TempList answer = null;
       ArrayList<Temp> record = new ArrayList<Temp>();
       TempList pointer = this;
       while(pointer != null){
      	 record.add(pointer.head);
      	 pointer = pointer.tail;
       }
       
       for(int i = 0; i != record.size(); ++i)
      	 answer = new TempList(record.get(i) ,answer);
       return answer;

}

}

