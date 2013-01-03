package edu.sjtu.lt_lab.mark;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author BennyChan
 * 读写微博
 */
public class ReadAndWriteWeibo {
	public static void main(String[] args) throws IOException {
		String readPath = "E:/workplace/newData/韩寒方舟子_HIT_crf - 副本.txt";
		String writePath = "E:/workplace/newData/event1_3Feature_new.txt";

		ReadAndWriteWeibo raw = new ReadAndWriteWeibo(readPath, writePath, 0);

		raw.ReadNext();
		System.out.println(raw.reduction());
		raw.print();
		// raw.markMorpheme();
	}

	private String $readPath = null; // 文件读入位置
	private String $writePath = null; // 文件输出位置
	private long $currentPoint = 0; // 当前读取到的位置
	private String $weibo = null; // 当前一条微博
	// private int $featureCount = 2;
	private int $wordCount = 0; // 当前一条微博的词数

	private String $words[] = null; // 文字
	private String $pos[] = null; // 词性特征
	private String $emotion[] = null; // 情感特征
	private String $wordStructure[] = null; // 词间结构
	private String $sentenceStructure[] = null; // 句法结构

	private String $result[] = null; // 结果

	private String $eachLine[] = null; // 相应一条微博分词后每行

	public ReadAndWriteWeibo(String readPath, String writePath, long point) {
		$readPath = readPath;
		$writePath = writePath;
		$currentPoint = point;

	}

	public String[] getEachLine() {
		return $eachLine;
	}

	public String[] getWords() {
		return $words;
	}

	public String[] getPos() {
		return $pos;
	}

	public String[] getEmotion() {
		return $emotion;
	}

	public String[] getWordStructure() {
		return $wordStructure;
	}

	public String[] getSentenceStructure() {
		return $sentenceStructure;
	}

	public String[] getResult() {
		return $result;
	}

	public long getCurrentPoint() {
		return $currentPoint;
	}

	public void setCurrentPoint(long point) {
		$currentPoint = point;
	}

	public String getWeibo() {
		return $weibo;
	}

	public int getWordCount() {
		return $wordCount;
	}

	public void print() {
		for (int i = 0; i < $wordCount; i++) {
			System.out.println($words[i] + " " + $pos[i] + " " + $emotion[i]
					+ " " + $wordStructure[i] + " " + $sentenceStructure[i]
					+ " " + $result[i]);
		}
	}

	/**
	 * @return 当前一条微博文字
	 */
	public String reduction() {
		String content = "";
		for (int i = 0; i < $wordCount; i++) {
			content = content + $words[i];
		}
		return content;
	}

	/**
	 * 读取下一条微博，每条微博以空行隔开
	 * 
	 * @throws IOException
	 */
	public boolean ReadNext() throws IOException {
		$weibo = ReadAppointedWeibo($readPath, $currentPoint);
		if ($weibo == null || $weibo.equals("")) {
			return false;
		}
		String eachLine[] = $weibo.split("\r");
		$eachLine = eachLine;
		$wordCount = eachLine.length;
		$words = new String[$wordCount];

		$pos = new String[$wordCount];
		$emotion = new String[$wordCount];
		$wordStructure = new String[$wordCount];
		$sentenceStructure = new String[$wordCount];
		$result = new String[$wordCount];

		for (int i = 0; i < $wordCount; i++) {
			$emotion[i] = "0";
			$wordStructure[i] = "0";
			$sentenceStructure[i] = "0";
			$result[i] = "o";
		}

		for (int i = 0; i < $wordCount; i++) {
			String eachChar[] = eachLine[i].split(" ");

			$words[i] = eachChar[0];
			try {
				$pos[i] = eachChar[1]; // 词性特征
				$emotion[i] = eachChar[2]; // 情感特征
				$wordStructure[i] = eachChar[3]; // 词间结构
				$sentenceStructure[i] = eachChar[4]; // 句法结构
				$result[i] = eachChar[5]; // 结果
			} catch (java.lang.ArrayIndexOutOfBoundsException e) {

			}
		}
		return true;
	}

	/**
	 * 读取特定一条微博
	 * 
	 * @param readPate
	 *            读取文件路径
	 * @param point
	 *            读取文件位置
	 * @return 整条微博，按照原来读入格式
	 * @throws IOException
	 */
	public String ReadAppointedWeibo(String readPath, long point)
			throws IOException {
		RandomAccessFile fileOb = new RandomAccessFile(readPath, "rw");
		String content;
		StringBuilder sb = new StringBuilder();
		fileOb.seek(point);
		content = fileOb.readLine();
		while (content != null && content.compareTo("") != 0) {
			content = new String(content.getBytes("8859_1"), "gbk");//
			sb.append(content + "\r");
			content = fileOb.readLine();
		}
		$currentPoint = fileOb.getFilePointer();
		return sb.toString();
	}

	public void WriteWeibo(boolean toWrite, boolean isMark) {
		
		if (toWrite) {
			String newWeibo = "";
			if (isMark) {
				for (int i = 0; i < $wordCount; i++) {
					newWeibo += $words[i] + " " + $pos[i] + " " + $emotion[i]
							+ " " + $wordStructure[i] + " "
							+ $sentenceStructure[i] + "\r\n";
				}
			} else {
				for (int i = 0; i < $wordCount; i++) {
					newWeibo += $words[i] + " " + $pos[i] + " " + $emotion[i]
							+ " " + $wordStructure[i] + " "
							+ $sentenceStructure[i] + " " + $result[i] + "\r\n";
				}
			}
			System.out.println(newWeibo);
			try {
				writeFile(newWeibo + "\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 写文件
	 * 
	 * @param content
	 *            文件内容
	 * @throws IOException
	 */
	public void writeFile(String content) throws IOException {
		FileWriter fw = new FileWriter($writePath, true);
		fw.write(content);
		fw.flush();
		fw.close();
	}
}
