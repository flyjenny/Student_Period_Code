package edu.sjtu.lt_lab.mark;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * @author BennyChan
 * 读入情感字典，标注情感特征
 */
public class MarkEmotion {
	private ReadAndWriteWeibo $weibo = null;
	private ArrayList<String> $dic = new ArrayList<String>();// 情感字典
	private String $dicPath = null;

	public MarkEmotion(ReadAndWriteWeibo weibo, String dicPath) {
		$weibo = weibo;
		$dicPath = dicPath;
		try {
			readDic();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读取情感字典
	 * 
	 * @param path
	 *            情感字典位置
	 * @throws IOException
	 */
	public void readDic() throws IOException {
		RandomAccessFile fileOb = new RandomAccessFile($dicPath, "r");
		String word = fileOb.readLine();
		while (word != null) {
			word = new String(word.getBytes("8859_1"), "gbk");
			$dic.add(word.trim());
			word = fileOb.readLine();
		}
	}

	/**
	 * 标记情感字典，1为在字典中，0为不在字典中
	 */
	public void markEmotion() {
		int wordCount = $weibo.getWordCount();
		for (int i = 0; i < wordCount; i++) {
			for (int j = 0; j < $dic.size(); j++) {
				if ($weibo.getWords()[i].equals($dic.get(j))) {
					$weibo.getEmotion()[i] = "1";
					break;
				}
			}
		}
	}
}
