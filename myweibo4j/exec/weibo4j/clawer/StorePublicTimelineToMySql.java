package weibo4j.clawer;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import weibo4j.Timeline;
import weibo4j.Weibo;
import weibo4j.http.HttpClient;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

public class StorePublicTimelineToMySql implements Runnable {
	private String $accessToken = null;// 如果授权可以设置
	private String $weiboTableName = null;
	private String $weiboRepostFromTableName = null;
	private long $refleshTime = 5000;
	private int $totalCount = 0;
	private Weibo $weibo = null;
	private StoreToMySql $store2db = null;
	private Timeline $timeline = null;
	private List<Status> $status = null;

	public static Logger logger = Logger.getLogger("StoreToMySql");

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public StorePublicTimelineToMySql() {
		this.$weibo = new Weibo();
		this.$store2db = new StoreToMySql();
		this.$timeline = new Timeline();
		this.$weiboTableName = DbConfig.getValue("db_weibo_tableName");
		this.$weiboRepostFromTableName = DbConfig
				.getValue("db_weiborepostfrom_tableName");
		this.$refleshTime = Long.parseLong(DbConfig.getValue("reflesh_time"));
	}

	public void setAccessToken(String accessToken) {
		this.$accessToken = accessToken;
		$weibo.setToken(accessToken);
	}

	public void execStore(int count) {
		String str = null;
		try {
			$status = $timeline.getPublicTimeline(count, 0);
			$store2db.setDuplicateCount();
			$totalCount = 0;
			for (Status s : $status) {
				str = constructSqlStr(s, true);
				// System.out.println(str);
				$store2db.storeToDb(str);
				$totalCount++;
			}
			logger.info("TotalCount:" + $totalCount + " DuplicateCount:"
					+ $store2db.getDupblicteCount());
		} catch (WeiboException e) {
			logger.debug(e.getErrorCode()+" "+e.getError());
			e.printStackTrace();
		}
	}

	public String constructSqlStr(Status status, boolean mark) {
		User user = status.getUser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long retweetedStatus = -1;
		char isVerified = 'n';
		String sqlStr = null;
		try {
			if (status.getRetweetedStatus() != null) {
				retweetedStatus = status.getRetweetedStatus().getIdstr();
				String retweetedStr = constructSqlStr(
						status.getRetweetedStatus(), false);
				// System.out.println("retweeted:"+retweetedStr);
				$store2db.storeToDb(retweetedStr);
			}
			if (user.isVerified()) {
				isVerified = 'y';
			}
		} catch (java.lang.NullPointerException e) {
			logger.debug(e.getMessage());
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
		if (mark) {
			sqlStr = "insert into " + $weiboTableName + " values(";
		} else {
			sqlStr = "insert into " + $weiboRepostFromTableName + " values(";
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

	public void run() {
		long beginTime = 0;
		long endTime = 0;
		int i = 1;
		String accessToken = "";// 可以授权设置
		while (true) {
			beginTime = System.currentTimeMillis();
			try{
				execStore(200);
			}catch(java.lang.NullPointerException e){
				logger.debug(e.getMessage());
			}
			endTime = System.currentTimeMillis();
			logger.info((i++) + " RunningTime:" + (endTime - beginTime) + "ms");
			try {
				Thread.sleep($refleshTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.debug("InterruptedException:"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
}