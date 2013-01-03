package Absyn;

import java.util.ArrayList;

public class DecList {
   public Dec head;
   public DecList tail;
   public DecList(Dec h, DecList t) {head=h; tail=t;}
   
   public static DecList createDecList(FieldList params, ExpList expList) {
	   FieldList fieldPointer = params;
	   ExpList expPointer = expList;
	   ArrayList<VarDec> decArray = new ArrayList();
	   while(fieldPointer != null){
	      decArray.add(new VarDec(fieldPointer.name,expPointer.head));
	      fieldPointer = fieldPointer.tail;
	      expPointer = expPointer.tail;
	   }
	   
	   DecList ans = null;
	   for(int i = decArray.size() -1; i >= 0; --i)
		   ans = new DecList(decArray.get(i),ans);
	   
	   return ans;
   }
}
