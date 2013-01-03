package RegAlloc;

import java.util.HashMap;
import java.util.Iterator;

public class MapDisplay {

	public static String tempMap(Temp.Temp temp) {
		StringBuffer tempString = new StringBuffer();
		if (temp == Mips.MipsFrame.instance.at)
			tempString.append("$at");
		if (temp == Mips.MipsFrame.instance.fp)
			tempString.append("$fp");
		if (temp == Mips.MipsFrame.instance.gp)
			tempString.append("$gp");
		if (temp == Mips.MipsFrame.instance.k0)
			tempString.append("$k0");
		if (temp == Mips.MipsFrame.instance.k1)
			tempString.append("$k1");
		if (temp == Mips.MipsFrame.instance.ra)
			tempString.append("$ra");
		if (temp == Mips.MipsFrame.instance.sp)
			tempString.append("$sp");
		if (temp == Mips.MipsFrame.instance.zero)
			tempString.append("$0");
		if (temp == Mips.MipsFrame.instance.v0)
			tempString.append("$v0");
		if (temp == Mips.MipsFrame.instance.v1)
			tempString.append("$v1");
		int i;
		Temp.TempList pointer;

		for (pointer = Mips.MipsFrame.instance.argRegs, i = 0; pointer != null; pointer = pointer.tail, ++i)
			if (temp == pointer.head)
				tempString.append("$a" + i);

		for (pointer = Mips.MipsFrame.instance.calleeSaveRegs, i = 0; pointer != null; pointer = pointer.tail, ++i)
			if (temp == pointer.head)
				tempString.append("$s" + i);

		for (pointer = Mips.MipsFrame.callerSaveRegs, i = 0; pointer != null; pointer = pointer.tail, ++i)
			if (temp == pointer.head)
				tempString.append("$t" + i);

		return tempString.toString();
	}

	public static void display(HashMap<Temp.Temp,Temp.Temp> map){
		Iterator<Temp.Temp> itr = map.keySet().iterator();
		while(itr.hasNext()){
			Temp.Temp key = itr.next();
			Temp.Temp value = map.get(key);
			if(value != null){
				   key.display();
				   System.out.print("  ");
				   value.display();
				   System.out.print("-->"+tempMap(value));
				   System.out.println();				
			}else{
				   key.display();
				   System.out.print("  null");
				   System.out.println();		
			}
		}
	}

	public static void display2(HashMap<Graph.Node, ReachInfo> map) {
		Iterator<Graph.Node> itr = map.keySet().iterator();
		while(itr.hasNext()){
			Graph.Node key = itr.next();
			ReachInfo value = map.get(key);
			if(value != null){
				   key.display();
				   System.out.print("  ");
				   value.display();
				   System.out.println();				
			}else{
				   key.display();
				   System.out.print("  null");
				   System.out.println();		
			}
		}		
	}
}
