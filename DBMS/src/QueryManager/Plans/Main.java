package QueryManager.Plans;

public class Main {
	public static void main(String args[]){
		
		Plan plan=new Plan("D:\\Documents\\workspace\\testcases\\mytest.sql");
		System.out.println(plan.type);
		System.out.println(plan.createexp.print(""));
		System.out.println(plan.lqplist.size());
//		System.out.println(plan.lqplist.get(0).projection.print(""));
//		System.out.println("_____________________________________");
//		System.out.println(plan.lqplist.get(0).condition.print(""));
		System.out.println("_____________________________________");
		System.out.println(plan.lqplist.get(0).production.print(""));
		
	}
}
