package edu.sjtu.lt_lab.mark;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author BennyChan
 * ��д΢��
 */
public class ReadAndWriteWeibo {
	public static void main(String[] args) throws IOException {
		String readPath = "E:/workplace/newData/����������_HIT_crf - ����.txt";
		String writePath = "E:/workplace/newData/event1_3Feature_new.txt";

		ReadAndWriteWeibo raw = new ReadAndWriteWeibo(readPath, writePath, 0);

		raw.ReadNext();
		System.out.println(raw.reduction());
		raw.print();
		// raw.markMorpheme();
	}

	private String $readPath = null; // �ļ�����λ��
	private String $writePath = null; // �ļ����λ��
	private long $currentPoint = 0; // ��ǰ��ȡ����λ��
	private String $weibo = null; // ��ǰһ��΢��
	// private int $featureCount = 2;
	private int $wordCount = 0; // ��ǰһ��΢���Ĵ���

	private String $words[] = null; // ����
	private String $pos[] = null; // ��������
	private String $emotion[] = null; // �������
	private String $wordStructure[] = null; // �ʼ�ṹ
	private String $sentenceStructure[] = null; // �䷨�ṹ

	private String $result[] = null; // ���

	private String $eachLine[] = null; // ��Ӧһ��΢���ִʺ�ÿ��

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
	 * @return ��ǰһ��΢������
	 */
	public String reduction() {
		String content = "";
		for (int i = 0; i < $wordCount; i++) {
			content = content + $words[i];
		}
		return content;
	}

	/**
	 * ��ȡ��һ��΢����ÿ��΢���Կ��и���
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
				$pos[i] = eachChar[1]; // ��������
				$emotion[i] = eachChar[2]; // �������
				$wordStructure[i] = eachChar[3]; // �ʼ�ṹ
				$sentenceStructure[i] = eachChar[4]; // �䷨�ṹ
				$result[i] = eachChar[5]; // ���
			} catch (java.lang.ArrayIndexOutOfBoundsException e) {

			}
		}
		return true;
	}

	/**
	 * ��ȡ�ض�һ��΢��
	 * 
	 * @param readPate
	 *            ��ȡ�ļ�·��
	 * @param point
	 *            ��ȡ�ļ�λ��
	 * @return ����΢��������ԭ�������ʽ
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
	 * д�ļ�
	 * 
	 * @param content
	 *            �ļ�����
	 * @throws IOException
	 */
	public void writeFile(String content) throws IOException {
		FileWriter fw = new FileWriter($writePath, true);
		fw.write(content);
		fw.flush();
		fw.close();
	}
}
