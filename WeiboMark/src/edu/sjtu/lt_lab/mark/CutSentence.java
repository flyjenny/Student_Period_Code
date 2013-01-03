package edu.sjtu.lt_lab.mark;


import java.util.ArrayList;
import edu.hit.ir.ltpService.Word;
import edu.sjtu.lt_lab.mark.ReadAndWriteWeibo;


/**
 * @author BennyChan
 * 分句，由于HIT开源的句法分析需要分句得到比较准确句法分析，为了配合使用必须先对自己的微博进行分句
 */
public class CutSentence {

	private ReadAndWriteWeibo $weibo = null;

	private String $weiboContent = null;
	
	private String[] $weiboWords = null;
	private String[] $weiboPos = null;
	private String[] $weiboModifiedPos = null;
	private String[] $weiboEmotion = null;
	private String[] $weiboWordStructure = null;
	private String[] $weiboResult = null;

	private int $weiboWordCount = 0;
	private int $sentNum = 0;

	private ArrayList<ArrayList<Word>> $sentences = null;//分句后的句子

	private ArrayList<String> $tar = null;
	private ArrayList<ArrayList<String>> $tarSent = null; 

	public CutSentence(ReadAndWriteWeibo weibo) {
		$weibo = weibo;
	}
	
	/**
	 * 赋值每条微博
	 */
	public void assignWeibo(){
		$weiboContent = $weibo.reduction();
		$weiboWords = $weibo.getWords();
		$weiboPos = $weibo.getPos();
		$weiboEmotion = $weibo.getEmotion();
		$weiboWordStructure = $weibo.getWordStructure();
		$weiboResult = $weibo.getResult();
		$weiboWordCount = $weibo.getWordCount();

		$weiboModifiedPos = new String[$weiboWordCount];

		$sentences = new ArrayList<ArrayList<Word>>();
		$tarSent = new ArrayList<ArrayList<String>>();
		$tar = new ArrayList<String>();
	}
	/**
	 * 执行分句
	 * @return true为分句成功，反之false
	 */
	public boolean actCut() {
/*		try {
			$weibo.readNext();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ($weibo.getWeibo().equals("")) {
			return false;
		}
		
*/		assignWeibo();
		if($weiboContent==null||$weiboContent.trim().equals("")){
			return false;
		}
//		System.out.println("I am in actCut after");
		
		
		ArrayList<Word> wordlist = new ArrayList<Word>();//一个句子
		Word word = null;
		int sentNum = 1;
		for (int i = 0; i < $weiboWordCount; i++) {
			if (!judgeSymbol($weiboWords[i])) {//不是符合分句的标记
				word = new Word();

				convertString(i);

				word.setWS($weiboWords[i]);
				word.setPOS($weiboModifiedPos[i]);
				wordlist.add(word);
				if (i == $weiboWordCount - 1) {
					$sentences.add(wordlist);
					$tarSent.add($tar);
				}
			} else {//符合分句的标记
				word = new Word();

				convertString(i);

				word.setWS($weiboWords[i]);
				word.setPOS($weiboModifiedPos[i]);
				wordlist.add(word);

				$sentences.add(wordlist);
				$tarSent.add($tar);

				if (i != ($weiboWordCount - 1)) {
					sentNum++;
					wordlist = new ArrayList<Word>();
					$tar = new ArrayList<String>();
				}
			}
		}
		$sentNum = sentNum;
		return true;
	}

	/**
	 * 判断标点是否符合分句
	 * @param symbol
	 * @return
	 */
	public boolean judgeSymbol(String symbol) {
		if (symbol.equals(".") || symbol.equals("。") || symbol.equals("?")
				|| symbol.equals("？") || symbol.equals("!")
				|| symbol.equals("！") || symbol.equals(";")
				|| symbol.equals("；") || symbol.equals("……")
				|| symbol.equals("…") || symbol.equals("：")
				|| symbol.equals(":")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改pos使之匹配HIT的分词标注集
	 * @param position
	 */
	public void convertString(int position) {

		if ($weiboPos[position].startsWith("w")) {
			$weiboModifiedPos[position] = "wp";
		} else if ($weiboPos[position].startsWith("a")
				|| $weiboPos[position].startsWith("z")) {
			if ($weiboWords[position].length() < 4) {
				$weiboModifiedPos[position] = "a";
			} else {
				$weiboModifiedPos[position] = "i";
			}
		} else if ($weiboPos[position].startsWith("b")) {
			$weiboModifiedPos[position] = "b";
		} else if ($weiboPos[position].startsWith("c")) {
			$weiboModifiedPos[position] = "c";
		} else if ($weiboPos[position].startsWith("m")) {
			$weiboModifiedPos[position] = "m";
		} else if ($weiboPos[position].startsWith("nr")) {
			$weiboModifiedPos[position] = "nh";
		} else if ($weiboPos[position].startsWith("ns")) {
			$weiboModifiedPos[position] = "ns";
		} else if ($weiboPos[position].equals("nt")) {
			$weiboModifiedPos[position] = "ni";
		} else if ($weiboPos[position].startsWith("n")) {
			$weiboModifiedPos[position] = "n";
		} else if ($weiboPos[position].equals("f")) {
			$weiboModifiedPos[position] = "nd";
		} else if ($weiboPos[position].equals("s")) {
			$weiboModifiedPos[position] = "nl";
		} else if ($weiboPos[position].equals("tar")) {
			$tar.add($weiboWords[position]);
			$weiboModifiedPos[position] = "nh";
		} else if ($weiboPos[position].startsWith("t")) {
			$weiboModifiedPos[position] = "nt";
		} else if ($weiboPos[position].startsWith("p")) {
			$weiboModifiedPos[position] = "p";
		} else if ($weiboPos[position].startsWith("q")) {
			$weiboModifiedPos[position] = "q";
		} else if ($weiboPos[position].startsWith("r")) {
			$weiboModifiedPos[position] = "r";
		} else if ($weiboPos[position].startsWith("u")
				|| $weiboPos[position].equals("y")) {
			$weiboModifiedPos[position] = "u";
		} else if ($weiboPos[position].startsWith("v")) {
			$weiboModifiedPos[position] = "v";
		} else if ($weiboPos[position].startsWith("x")) {
			$weiboModifiedPos[position] = "ws";
		} else {
			$weiboModifiedPos[position] = $weiboPos[position];
		}
	}

	public ReadAndWriteWeibo getWeibo() {
		return $weibo;
	}

	public String getWeiboContent() {
		return $weiboContent;
	}

	public String[] getWords() {
		return $weiboWords;
	}

	public String[] getPos() {
		return $weiboPos;
	}

	public String[] getModifiedPos() {
		return $weiboModifiedPos;
	}

	public String[] getEmotion() {
		return $weiboEmotion;
	}

	public String[] getWordStructure() {
		return $weiboWordStructure;
	}

	public String[] getWeiboResult() {
		return $weiboResult;
	}

	public int getWeiboWordCount() {
		return $weiboWordCount;
	}

	public int getSentNum() {
		return $sentNum;
	}

	public ArrayList<ArrayList<Word>> getSentences() {
		return $sentences;
	}

	public ArrayList<ArrayList<String>> getTarSent() {
		return $tarSent;
	}

}
