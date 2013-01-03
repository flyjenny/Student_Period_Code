package edu.sjtu.lt_lab.mark;

import java.io.IOException;

public class ExecMark {
	public static void main(String[] args) throws IOException {
		String readPath = "E:/workplace/template/temp_金正日.txt";
		String writePath = "E:/workplace/template/temp_金正日_crf.txt";
		String dicPath = "e:/workplace/tsinghua.dic.gb.txt";
		String auth = "zqchenbin@gmail.com:20120419";

		ReadAndWriteWeibo weibo = new ReadAndWriteWeibo(readPath, writePath, 0);
		MarkEmotion me = new MarkEmotion(weibo, dicPath);
		MarkWordStructure mws = new MarkWordStructure(weibo);
		CutSentence cs = new CutSentence(weibo);
		MarkSentenceStructure sp = new MarkSentenceStructure(cs, auth);
		boolean actedMarkSentenceStructure = false;
		
		while (weibo.ReadNext()) {
			me.markEmotion();
			mws.markWordStructure();
			cs.actCut();
			actedMarkSentenceStructure = sp.markSentenceStructure();
			weibo.WriteWeibo(true,false);
		}
	}
}
