package Frame;
import java.util.ArrayList;

public class AccessList {
	public Access head;
	public AccessList tail;
	public AccessList(Access h,AccessList t){
		head = h; tail = t;
	}
	public AccessList reverse() {
		 AccessList answer = null;
         ArrayList<Access> record = new ArrayList<Access>();
         AccessList pointer = this;
         while(pointer != null){
        	 record.add(pointer.head);
        	 pointer = pointer.tail;
         }
         
         for(int i = 0; i != record.size(); ++i)
        	 answer = new AccessList(record.get(i) ,answer);
         return answer;
	}
}
