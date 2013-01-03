package QueryManager.Plans;

public class Driver {
	public static void print(LogicalQueryPlan lqp){
		
		if (lqp.plans.size() == 0) {
			System.out.println("whereCondBinExpCoun: "
					+ lqp.preTreeToPlan.whereCondBinExpCount);
			System.out.println("**********PROJECTION**************");
			System.out.println("\n" + lqp.projection.print("") + "\n");
			System.out.println("**********PRODUCTION**************");
			System.out.println("\n" + lqp.production.print("") + "\n");
			System.out.println("**********CONDITION***************");
			System.out.println("\n" + lqp.condition.print("") + "\n");

		} else {
			for (int i = 0; i < lqp.plans.size(); i++) {
				print(lqp.plans.get(i));
				System.out.println("___________DIVIDE LINE____________");
			}
			System.out.println("whereCondBinExpCoun: "
					+ lqp.preTreeToPlan.whereCondBinExpCount);
			System.out.println("**********PROJECTION**************");
			System.out.println("\n" + lqp.projection.print("") + "\n");
			System.out.println("**********PRODUCTION**************");
			System.out.println("\n" + lqp.production.print("") + "\n");
			System.out.println("**********CONDITION***************");
			System.out.println("\n" + lqp.condition.print("") + "\n");

		}
	}
	public static void main(String args[]){
		PreTreeToPlan ttp=new PreTreeToPlan("D:\\Documents\\workspace\\testcases\\mytest.sql");
		LogicalQueryPlan lqp=new LogicalQueryPlan(ttp);
		print(lqp);
		
	}
}
