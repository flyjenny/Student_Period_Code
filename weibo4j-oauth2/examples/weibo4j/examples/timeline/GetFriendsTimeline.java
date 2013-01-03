package weibo4j.examples.timeline;

import java.util.List;

import weibo4j.Timeline;
import weibo4j.Weibo;
import weibo4j.examples.Log;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

public class GetFriendsTimeline {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		Weibo weibo = new Weibo();
		weibo.setToken(access_token);
		Timeline tm = new Timeline();
		try {
			List<Status> status = tm.getFriendsTimeline();
//			Status s=status.get(0);
			for(Status s : status){
//				System.out.println(s.toString());
//				Log.logInfo(s.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
