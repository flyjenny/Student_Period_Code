package weibo4j.Clawer;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import weibo4j.Timeline;
import weibo4j.Weibo;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class StorePublicTimelineToMySql {
	private String $accessToken = null;
	private Weibo $weibo = null;
	private StoreToMySql $store2db = null;
	private Timeline $timeline = null;
	private List<Status> $status = null;
	
	public static Logger logger = Logger.getLogger("StoreToMySql");
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		long beginTime =0;
		long endTime=0;
		int i=1;
		String accessToken = "2.00eRwluCqpVBZC9c41bf53913WmLeB";
		StorePublicTimelineToMySql spt = new StorePublicTimelineToMySql();
		while(true){
			beginTime=System.currentTimeMillis();
			spt.execStore(200);
			endTime =System.currentTimeMillis();
			logger.info((i++)+" RunningTime:"+ (endTime - beginTime) + "ms");
			Thread.sleep(4000);
		}
	}

	public void setAccessToken(String accessToken) {
		this.$accessToken = accessToken;
		$weibo.setToken(accessToken);
	}

	public StorePublicTimelineToMySql() {
		this.$weibo = new Weibo();
		this.$store2db = new StoreToMySql();
		this.$timeline = new Timeline();
	}

	public void execStore(int count) {
		String str = null;
		int i = 1;
		try {
			$status = $timeline.getPublicTimeline(count, 0);
			for (Status s : $status) {
				str = constructSqlStr(s,true);
//				System.out.println((i++) + " " + str);
				$store2db.storeToDb(str);
			}
		} catch (WeiboException e) {
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		}
	}

	public String constructSqlStr(Status status,boolean mark) {
		User user = status.getUser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long retweetedStatus = -1;
		char isVerified = 'n';
		String sqlStr = null;
		if (status.getRetweetedStatus() != null) {
			retweetedStatus = status.getRetweetedStatus().getIdstr();
			String retweetedStr=constructSqlStr(status.getRetweetedStatus(),false);
//			System.out.println("retweeted:"+retweetedStr);
			$store2db.storeToDb(retweetedStr);
		}
		if (user.isVerified()) {
			isVerified = 'y';
		}
		/*
		 * sqlStr = "insert into weibo.weibo1 values(" + status.getIdstr() +
		 * ",'" + sdf.format(status.getCreatedAt()) + "','" + status.getText() +
		 * "'," + status.getInReplyToStatusId() + "," +
		 * status.getInReplyToUserId() + ",'" + status.getInReplyToScreenName()
		 * + "'," + retweetedStatus + "," + status.getRepostsCount() + "," +
		 * status.getCommentsCount() + ",'" + user.getId() + "','" +
		 * user.getScreenName() + "'," + user.getProvince() + "," +
		 * user.getCity() + ",'" + user.getLocation() + "','" +
		 * user.getDescription() + "','" + user.getGender() + "'," +
		 * user.getFollowersCount() + "," + user.getFriendsCount() + "," +
		 * user.getStatusesCount() + "," + user.getbiFollowersCount() + ",'" +
		 * sdf.format(user.getCreatedAt()) + "','" + isVerified + "'," +
		 * user.getverifiedType() + ",'" + user.getVerified_reason() + "')";
		 */
		if(mark){
			sqlStr = "insert into weibo.weibo1 values(";
		}
		else{
			sqlStr = "insert into weibo.weibo1_repostfrom values(";
		}	
		sqlStr += status.getIdstr() + ",\"" + sdf.format(status.getCreatedAt())
				+ "\",\"" + status.getText().replace("\"", "\\\"") + "\","
				+ status.getInReplyToStatusId() + ","
				+ status.getInReplyToUserId() + ",\""
				+ status.getInReplyToScreenName() + "\"," + retweetedStatus
				+ "," + status.getRepostsCount() + ","
				+ status.getCommentsCount() + ",\"" + user.getId() + "\",\""
				+ user.getScreenName() + "\"," + user.getProvince() + ","
				+ user.getCity() + ",\"" + user.getLocation() + "\",\""
				+ user.getDescription().replace("\"", "\\\"") + "\",\""
				+ user.getGender() + "\"," + user.getFollowersCount() + ","
				+ user.getFriendsCount() + "," + user.getStatusesCount() + ","
				+ user.getbiFollowersCount() + ",\""
				+ sdf.format(user.getCreatedAt()) + "\",\"" + isVerified
				+ "\"," + user.getverifiedType() + ",\""
				+ user.getVerified_reason().replace("\"", "\\\"") + "\")";
		return sqlStr;
	}
}