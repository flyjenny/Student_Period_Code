package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Weibo.WeiboInitial;

public class Run {
	public static void main(String[] args) {
		Thread desktopServerThread = new Thread(new Server());
		Thread flashWeibo = new Thread(new FlashWeibo());
//		 Thread flashFligh = new Thread(new FlashFligh());
		desktopServerThread.start();
		flashWeibo.start();
//		flashFligh.start();
	}

	public static void Initial() {
		String token = "4aa705a76866ceef3ca3a858a9684592";
		String tokenSecret = "986423bdb97ab11e5b0a89a3a2c2e274";
		WeiboInitial getfriends = new WeiboInitial(token, tokenSecret);
		getfriends.storeFriendsToDb();
		getfriends.storeLastStatusIdToDb();
		getfriends.updateFriendsToDb();
		getfriends.updateLastStatusIdToDb();
	}

	public void writeObject(String outFile, Object object) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(outFile)));
			out.writeObject(object);
			out.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	public Object readObject(String filePath) {
		File inFile = new File(filePath);
		Object o = null;
		try {
			ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(inFile)));
			o = in.readObject();
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return o;
	}
}
