package QueryManager.Plans;

public class Driver {
	public static void print(LogicalQueryPlan lqp){
		if(lqp.preTreeToPlan.sqlExpType==Marks.SELECTEXP){
			if (lqp.plans.size() == 0) {
				System.out.println("whereCondBinExpCoun: "
						+ lqp.preTreeToPlan.whereCondBinExpCount);
				System.out.println("SelectDistinct: "+lqp.selectDist);
				System.out.println("SelectALL:      "+lqp.selectAll);
				if(lqp.projection!=null){
					System.out.println("**********PROJECTION**************");
					System.out.println("\n" + lqp.projection.print("") + "\n");
				}
				if(lqp.production!=null){
					System.out.println("**********PRODUCTION**************");
					System.out.println("\n" + lqp.production.print("") + "\n");
				}
				if(lqp.condition!=null){
					System.out.println("**********CONDITION***************");
					System.out.println("\n" + lqp.condition.print("") + "\n");
				}
	
			} else {
				for (int i = 0; i < lqp.plans.size(); i++) {
					print(lqp.plans.get(i));
					System.out.println("\n___________DIVIDE LINE____________\n");
				}
				System.out.println("whereCondBinExpCoun: "
						+ lqp.preTreeToPlan.whereCondBinExpCount);
				System.out.println("SelectDistinct: "+lqp.selectDist);
				System.out.println("SelectALL:      "+lqp.selectAll);
				if(lqp.projection!=null){
					System.out.println("**********PROJECTION**************");
					System.out.println("\n" + lqp.projection.print("") + "\n");
				}
				if(lqp.production!=null){
					System.out.println("**********PRODUCTION**************");
					System.out.println("\n" + lqp.production.print("") + "\n");
				}
				if(lqp.condition!=null){
					System.out.println("**********CONDITION***************");
					System.out.println("\n" + lqp.condition.print("") + "\n");
				}
	
			}
		}
		else{
			System.out.println(lqp.preTreeToPlan.sqlExp.print(""));
		}
	}
	public static void main(String args[]){
		PreTreeToPlan ttp=new PreTreeToPlan("D:\\Documents\\workspace\\testcases\\mytest.sql");
		LogicalQueryPlan lqp=new LogicalQueryPlan(ttp);
		print(lqp);
		System.out.println("________________________________\n");
		for(int i=0;i<lqp.preTreeToPlan.whereCondBinExpCount;i++){
			System.out.println(lqp.preTreeToPlan.whereCondBinExpList.get(i).print(""));
			System.out.println("##########################################");
		}
//		System.out.println(lqp.checkProjection());
	}
}
