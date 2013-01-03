package Server;

import java.sql.SQLException;

import java.util.List;

import weibo4j.WeiboException;
import Weibo.Filter;

public class FlashWeibo implements Runnable {
	// private List<String> keywords=null;
	private Filter filter = new Filter();

	public FlashWeibo() {
		String token = "4aa705a76866ceef3ca3a858a9684592";
		String tokenSecret = "986423bdb97ab11e5b0a89a3a2c2e274";
		// WeiboInitial getfriends = new WeiboInitial(token, tokenSecret);

	}

	public void run() {
		int weiborecord=1;
		while (true) {
			System.out.println("weibo reflash times: "+(weiborecord++));
			try {
				if (Global.$friend_keywords != null) {
					
					filter.filterToDb(Global.$friend_keywords);
				}
				
				// weiboToBeSent=getToBeSent();
			} catch (WeiboException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(Global.$weiboflash);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
}