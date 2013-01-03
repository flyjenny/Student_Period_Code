package weibo4j.examples.account;

import weibo4j.Account;
import weibo4j.Weibo;
import weibo4j.examples.Log;
import weibo4j.model.RateLimitStatus;
import weibo4j.model.WeiboException;

public class GetAccountRateLimitStatus {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Weibo weibo = new Weibo();
		String access_token = "2.00eRwluCqpVBZC9c41bf53913WmLeB";
		weibo.setToken(access_token);
		Account am = new Account();
		try {
            RateLimitStatus json = am.getAccountRateLimitStatus();
			Log.logInfo(json.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
