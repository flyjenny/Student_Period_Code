package weibo4j.clawer;


public class MainExecute {
	public static void main(String[] args){
		Thread storeThread = new Thread(new StorePublicTimelineToMySql());
		storeThread.start();
	}
}
