package QueryManager.Plans;

public class Main {
	public static void main(String args[]){
		PreTreeToPlan ttp=new PreTreeToPlan("D:\\Documents\\workspace\\testcases\\mytest.sql");
		LogicalQueryPlan lqp=new LogicalQueryPlan(ttp);

		
		System.out.println(lqp.plans.size());

		System.out.println(lqp.plans.get(0).production.print(""));
		System.out.println("________________________________________");
		System.out.println(lqp.plans.get(1).production.print(""));
		System.out.println("________________________________________");
		System.out.println(lqp.plans.get(2).production.print(""));
		System.out.println("________________________________________");
		System.out.println(lqp.production.print(""));
		
		
	}
}