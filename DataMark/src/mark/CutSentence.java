package mark;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class CutSentence {
	public static void main(String[] args){
		CutSentence cs=new CutSentence("test200.data","fenju.csv");
		cs.ReadAndAct();
	}
	private String $readPath=null;
	private String $writePath=null;
	
	public CutSentence(String readPath,String writePath){
		$readPath=readPath;
		$writePath=writePath;
	}
	public  void ReadAndAct() {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile($readPath, "r");
			String temp = null;
			int id=1;
			int cutedCount=0;
			while (true) {
				temp = raf.readLine();
				if (temp == null) {
					break;
				}
				temp = new String(temp.getBytes("8859_1"), "gbk");
				ArrayList<String> cutedSent=cut(temp);
				String outContent="";
				String guandian="";
				cutedCount=cutedSent.size();
				for(int i=0;i<cutedCount;i++){
					outContent+=(id)+","+(i+1)+",{"+cutedSent.get(i)+"}\r";
					guandian+=(id)+","+(i+1)+",\r";
				}
				id++;
				writeFile(outContent);
				writeFile2(guandian);
			}
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void writeFile(String content) throws IOException {
		FileWriter fw = new FileWriter($writePath, true);
		fw.write(content);
		fw.flush();
		fw.close();
	}
	public void writeFile2(String content) throws IOException {
		FileWriter fw = new FileWriter("guandian.csv", true);
		fw.write(content);
		fw.flush();
		fw.close();
	}
	public ArrayList<String> cut(String inSent) {
		ArrayList<String> sentences = new ArrayList<String>();
		String sentence = "";
		int length = inSent.length();
		for (int i = 0; i < length; i++) {
			if (!JudgeSymbol(Character.toString(inSent.charAt(i)))) {
				if (Character.toString(inSent.charAt(i)).equals("h")) {
					String tmp = inSent.substring(i, length);
					if (tmp.startsWith("http://t.cn")) {
						sentence += "http://t.cn";
						i += 10;
						continue;
					}
				}
				sentence += Character.toString(inSent.charAt(i));
				if (i == length - 1) {
					sentences.add(sentence);
				}
			} else {
				if (Character.toString(inSent.charAt(i)).equals("/")) {
					if (i < length - 1
							&& Character.toString(inSent.charAt(i + 1)).equals(
									"/")) {
						sentence += Character.toString(inSent.charAt(i));
						sentence += Character.toString(inSent.charAt(++i));
						sentences.add(sentence);
						if (i != (length - 1)) {
							sentence = new String();
						}
						continue;
					} else {
						sentence += Character.toString(inSent.charAt(i));
						continue;
					}
				}
				sentence += Character.toString(inSent.charAt(i));

				if (i < length - 1
						&& JudgeSymbol(Character.toString(inSent.charAt(i + 1)))) {
					continue;
				}
				sentences.add(sentence);
				if (i != (length - 1)) {
					sentence = new String();
				}
			}
		}
		return sentences;
	}

	public boolean JudgeSymbol(String symbol) {
		if (symbol.equals(".") || symbol.equals("¡£") || symbol.equals("?")
				|| symbol.equals("£¿") || symbol.equals("!")
				|| symbol.equals("£¡") || symbol.equals(";")
				|| symbol.equals("£»") || symbol.equals("¡­¡­")
				|| symbol.equals("¡­") || symbol.equals("£º")
				|| symbol.equals(":") || symbol.equals("¡¡")
				|| symbol.equals("/")) {
			return true;
		} else {
			return false;
		}
	}
}
