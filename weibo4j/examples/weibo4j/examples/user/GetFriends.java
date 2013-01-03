package weibo4j.examples.user;

import java.io.FileWriter;
import java.util.List;

import weibo4j.Weibo;
import weibo4j.User;

/**
 * @author sina
 * 
 */
public class GetFriends {

	/**
	 * Usage: java -DWeibo4j.oauth.consumerKey=[consumer key]
	 * -DWeibo4j.oauth.consumerSecret=[consumer secret]
	 * Weibo4j.examples.GetFriends [accessToken] [accessSecret]
	 * 
	 * @param args
	 *            message
	 */
	public static void main(String[] args) {
		try {
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
			System.setProperty("weibo4j.oauth.consumerSecret",
					Weibo.CONSUMER_SECRET);
			Weibo weibo = new Weibo();
			weibo.setToken(args[0], args[1]);
			User user = weibo.showUser("benny小斌");
			FileWriter writer=new FileWriter("r:/a.txt",true);
			int count=user.getFriendsCount();
			count+=20;
			try {
				for(int i=0;i<count;i+=20){
					List<User> list = weibo.getFriendsStatuses(i);
					System.out.println(list.size());
					for(int j=0;j<list.size();j++)
						writer.write(list.get(j)+"\r\n");
					writer.flush();
				}
				writer.close();
//				System.out.println("Successfully get Friends to [" + list
//						+ "].");
//	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.exit(0);
		} catch (Exception ioe) {
			System.out.println("Failed to read the system input.");
			System.exit(-1);
		}
	}
}
