package resultDetect;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author BennyChan
 * 分析CRF抽取结果，组合评论词，找出评论对象
 */
public class Detection {
	public static void main(String[] args) throws IOException {
		String readPath = "金陵_comment.txt";
		Detection dt = new Detection(readPath);
		while (dt.ReadNext()) {
			dt.AnalysisResult();
		}
/*		HashMap<String, Integer> statistics = new HashMap<String, Integer>();

		ArrayList<String> wordStructure = dt.GetWordStructure();
		for (int i = 0; i < wordStructure.size(); i++) {
			// System.out.println(wordStructure.get(i));
			String[] cut = wordStructure.get(i).split(",");
			if (statistics.containsKey(cut[1])) {
				int count = statistics.get(cut[1]) + 1;
				statistics.put(cut[1], count);
			} else {
				statistics.put(cut[1], 1);
			}
		}
		Iterator<String> iter = statistics.keySet().iterator();
		while (iter.hasNext()) {
			String type = iter.next();
			int count = statistics.get(type);
			System.out.println(type + ":," + count);
		}
*/		
		HashMap<String, ArrayList<String>> tarAndWords=dt.GetTarAndWords();
		Iterator<String> tar = tarAndWords.keySet().iterator();
		while(tar.hasNext()){
			String tarWord=tar.next();
			ArrayList<String> comments=tarAndWords.get(tarWord);
			System.out.print(tarWord+":\t");
			for(int i=0;i<comments.size();i++){
				System.out.print(comments.get(i)+", ");
			}
			System.out.println();
		}
	}

	private String $readPath = null;
	private long $currentPoint = 0;
	private String $weibo = "";
	private int $wordCount = 0;
	private String[] $word = null;
	private String[] $pos = null;
	private String[] $SentenceStruture = null;
	private String[] $result = null;
	private HashMap<String, ArrayList<String>> $tarAndWords = new HashMap<String, ArrayList<String>>();
	private ArrayList<String> $noTarWords = new ArrayList<String>();
	private ArrayList<String> $wordStructure = new ArrayList<String>();

	public Detection(String readPath) {
		$readPath = readPath;
	}

	public HashMap<String, ArrayList<String>> GetTarAndWords() {
		return $tarAndWords;
	}

	public ArrayList<String> GetNoTarWords() {
		return $noTarWords;
	}

	public ArrayList<String> GetWordStructure() {
		return $wordStructure;
	}

	/**
	 * 分析CRF抽取结果，组合评论词，找出评论对象
	 */
	public void AnalysisResult() {
		// ArrayList<String> words = new ArrayList<String>();

		for (int i = 0; i < $wordCount; i++) {
			if ($result[i].equals("S")) {
				// words.add($word[i]);
				String tar = FindTar(i);

				if (tar.equals("")) {
					$noTarWords.add($word[i]);
				} else {
					if ($tarAndWords.containsKey(tar)) {
						$tarAndWords.get(tar).add($word[i]);
					} else {
						ArrayList<String> correspondingWords = new ArrayList<String>();
						correspondingWords.add($word[i]);
						$tarAndWords.put(tar, correspondingWords);
					}
				}
				// System.out.println(tar + ":\t" + $word[i]);
			} else if ($result[i].endsWith("B")) {
				String word = $word[i];
				String wordPos = $pos[i];
				int step = LookForward(i, 5);

				boolean mark = false;
				if (step == 1) {
					mark = true;

				} else if (step > 1) {
					for (int j = i + 1; j < i + step; j++) {
						if ($result[j].equals("I")) {
							mark = true;
							word += $word[j];
							wordPos += " " + $pos[j];
						} else {
							mark = false;
							break;
						}
					}
				}
				if (mark) {
					word += $word[i + step];
					wordPos += " " + $pos[i + step];
					$wordStructure.add(word + "," + wordPos);
					// words.add(word);
					String tar = FindTar(i);
					if (tar.equals("")) {
						$noTarWords.add(word);
					} else {
						if ($tarAndWords.containsKey(tar)) {
							$tarAndWords.get(tar).add(word);
						} else {
							ArrayList<String> correspondingWords = new ArrayList<String>();
							correspondingWords.add(word);
							$tarAndWords.put(tar, correspondingWords);
						}
					}
					// System.out.println(tar + ":\t" + word);
					i += step;
				}
			}
		}
	}

	/**
	 * 根据评论词位置找到评论对象
	 * @param position
	 * @return
	 */
	public String FindTar(int position) {
		String tar = "";
		if ($SentenceStruture[position].equals("Wi_arg1")
				|| $SentenceStruture[position].equals("Wi_it_arg1")
				|| $SentenceStruture[position].equals("Wi_arg")
				|| $SentenceStruture[position].equals("Wi_cp_arg1")
				|| $SentenceStruture[position].equals("arg1_v_Wi")) {
			// 往前找最近的tar
			for (int i = position; i >= 0; i--) {
				if ($pos[i].equals("tar")) {
					tar = $word[i];
					break;
				}
			}
		} else if ($SentenceStruture[position].equals("Wi_arg2")) {
			// 往后找最近的tar
			for (int i = position; i < $wordCount; i++) {
				if ($pos[i].equals("tar")) {
					tar = $word[i];
					break;
				}
			}
		} else {
			// 找最近的Tar
			int i = position;
			int j = position;
			while (i > -1 && j < $wordCount) {
				if ($pos[i].equals("tar")) {
					tar = $word[i];
					break;
				}
				if ($pos[j].equals("tar")) {
					tar = $word[j];
					break;
				}
				i--;
				j++;
			}
			if (i < 0) {
				for (int p = j; p < $wordCount; p++) {
					if ($pos[p].equals("tar")) {
						tar = $word[p];
						break;
					}
				}
			} else if (j >= $wordCount) {
				for (int p = i; p > -1; p--) {
					if ($pos[p].equals("tar")) {
						tar = $word[p];
						break;
					}
				}
			}
		}
		return tar;
	}

	/**
	 * 往后看词语
	 * @param current
	 * @param distance
	 * @return
	 */
	public int LookForward(int current, int distance) {
		int step = 0;
		try {
			for (int i = current + 1; i < current + distance; i++) {
				step++;
				if ($result[i].equals("E")) {
					return step;
				}
			}
			return -1;
		} catch (NullPointerException e) {
			return -1;
		}
	}

	/**
	 * 读取下一条微博
	 * @return
	 * @throws IOException
	 */
	public boolean ReadNext() throws IOException {
		$weibo = ReadAppointedWeibo($currentPoint);
		if ($weibo == null || $weibo.equals("")) {
			return false;
		}
		String[] eachLine = $weibo.split("\r");

		$wordCount = eachLine.length;
		$word = new String[$wordCount];
		$pos = new String[$wordCount];
		$SentenceStruture = new String[$wordCount];
		$result = new String[$wordCount];

		for (int i = 0; i < $wordCount; i++) {
			String[] eachChar = eachLine[i].split("\t");
			int last = eachChar.length - 1;
			$word[i] = eachChar[0];
			$pos[i] = eachChar[1];
			$SentenceStruture[i] = eachChar[4];
			$result[i] = eachChar[last];
		}
		return true;
	}

	/**
	 * 读取CRF测试结果
	 * @param point 读取位置
	 * @return
	 * @throws IOException
	 */
	public String ReadAppointedWeibo(long point) throws IOException {
		RandomAccessFile fileOb = new RandomAccessFile($readPath, "rw");
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
}
