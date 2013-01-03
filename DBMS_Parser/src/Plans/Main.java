package Plans;

public class Main {
	public static void main(String args[]){
		PreTreeToPlan ttp=new PreTreeToPlan("D:\\Documents\\workspace\\testcases\\mytest.sql");
		PreTreeToPlan ttp2=new PreTreeToPlan("D:\\Documents\\workspace\\testcases\\mytest.sql");
		LogicalQueryPlan lqp=new LogicalQueryPlan(ttp2);
		Absyn.WhereCondBinExp abc;
//		ttp.print();
/*		System.out.println("******************************");
		System.out.println(ttp.whereExp.print(""));
		
		System.out.println("selectBinExpCount= "+ttp.selectBinExpCount);
		System.out.println("whereCondBinExpCount= "+ttp.whereCondBinExpCount);
		System.out.println("******************************");
		

		System.out.println("******************************");
		System.out.println(lqp.condition.get(0).print(""));
	*/	
		
		ttp=lqp.preTreeToPlan;
		System.out.println(ttp.whereCondBinExpCount);
		int i;
/*		for(i=0;i<ttp.whereCondBinExpCount;i++){
			abc=ttp.whereCondBinExpList.get(i);
			System.out.println("**************"+i+"******************");
			System.out.println(abc.print(""));
			System.out.println("WhereCondBinExpCount= "+ttp.WhereCondBinExpCount(abc,0));
			
		}
*/		System.out.println("-------------------------------------");
//		lqp.withCondBinToConditon();
		System.out.println(lqp.withCondBinToConditon().print(""));
		System.out.println("-------------------------------------");
		System.out.println(lqp.condition.size());
		for(i=0;i<lqp.condition.size();i++){
//		System.out.println("**************"+i+"******************");
		System.out.println(lqp.condition.get(i).print(""));
		}
		
		
		/*if(ttp.whereCondBinExpList.get(0)==ttp.whereCondBinExpList.get(1).left){
			System.out.println("yes");
		}*/
//		System.out.println("ttp.whereCondBinExpCount= "+lqp.preTreeToPlan.WhereCondBinExpCount(lqp.preTreeToPlan.whereCondBinExpList.get(2)));
	}
}
