/**
 * 
 */
package weibo4j.examples.statuses;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import weibo4j.Status;
import weibo4j.Weibo;

/**
 * @author sina
 *
 */
public class UpdateStatus {

	/**
	 * 发布一条微博信息 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			System.out.println("Input Your Status:");
			String mystatus=br.readLine();
        	Status status = weibo.updateStatus(mystatus);
        	System.out.println(status.getId() + " : "+ status.getText()+"  "+status.getCreatedAt());
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
