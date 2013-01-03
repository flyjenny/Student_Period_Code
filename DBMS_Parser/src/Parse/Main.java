package Parse;

public class Main {

	public static void main(String argv[]) {
		String filename = argv[0];
		Parse parser = new Parse(filename);
		Absyn.Absyn root = parser.result;
		Absyn.List li=(Absyn.List)root;
		Absyn.SqlExpList sqlli=(Absyn.SqlExpList)li;
		Absyn.SqlExp sexp=sqlli.head;
		if(sexp instanceof Absyn.SelectExp){
			System.out.println("Absyn.SelectExp");
	
			if(sexp instanceof Absyn.SelectBinExp){
				System.out.println("Absyn.SelectBinExp");
			}
			if(sexp instanceof Absyn.SelectOp){
				System.out.println("Absyn.SelectOp");
				Absyn.SelectOp sop=(Absyn.SelectOp)sexp;
				if(sop.whereExp!=null){
					System.out.println("whereExp!=null");
				}
				else{
					Absyn.WhereExp whereExp=sop.whereExp;
					if(whereExp.cond!=null){
						Absyn.WhereCondExp whereCondExp=whereExp.cond;
						/*深度搜索是否存在SelectExp的对象*/
					}
					else{
						
					}
				}
			}
		}
		else if(sexp instanceof Absyn.CreateExp){
			System.out.println("Absyn.CreateExp");
		}
		else if(sexp instanceof Absyn.InsertExp){
			System.out.println("Absyn.InsertExp");
		}
		else if(sexp instanceof Absyn.DropExp){
			System.out.println("Absyn.DropExp");
		}
		else if(sexp instanceof Absyn.DeleteExp){
			System.out.println("Absyn.DeleteExp");
		}
		else if(sexp instanceof Absyn.UpdateExp){
			System.out.println("Absyn.UPdateExp");
		}
		else if(sexp instanceof Absyn.AlterExp){
			System.out.println("Absyn.AlterExp");
		}
		
		
		
		
	//	Absyn.SelectOp sop=(Absyn.SelectOp)sqlli.head;
		
	/*
		System.out.println(sop.cList.print(""));
		System.out.println("****************************");
		System.out.println(sop.tList.print(""));
		System.out.println("****************************");
		System.out.println(sop.whereExp.print(""));
	*/	
		System.out.println(root.print(""));
	}

}