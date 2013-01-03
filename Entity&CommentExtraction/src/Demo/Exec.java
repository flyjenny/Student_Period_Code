package Demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import tagging.MainFrame;

import edu.sjtu.lt_lab.input.FormatInput;
import edu.sjtu.lt_lab.mark.CutSentence;
import edu.sjtu.lt_lab.mark.MarkEmotion;
import edu.sjtu.lt_lab.mark.MarkSentenceStructure;
import edu.sjtu.lt_lab.mark.MarkWordStructure;
import edu.sjtu.lt_lab.mark.ReadAndWriteWeibo;

public class Exec {
	public static void main(String[] args) throws IOException{
		
		
		String inputPath="test200.data";//语料文本路径
		String readPath = "./tmp/comment_temp.txt";//对语料预处理后输出路径，作为标注输入
		String writePath = "./tmp/comment_crf.txt";//标注后输出路径，作为CRF抽取输入
		String crfResult="result_comment.txt";//CRF抽取后输出路径
		
		String userdic="./tmp/userdic.txt";
		String dicPath = "./init/EmotionDic.txt";//情感字典路径
		String auth = "zqchenbin@gmail.com:20120419";//哈工大句法分析器
		
		String entity="result_entity.txt";
		
		File fs =new File(readPath);
		fs.delete();
		fs =new File(writePath);
		fs.delete();
		fs =new File(crfResult);
		fs.delete();
		fs =new File("./tmp/entity_output.txt");
		fs.delete();
		
		
		MainFrame.autoExec(inputPath,userdic,entity);
		
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(inputPath),"UTF8"));
		String str_in;
		
		FormatInput fi=new FormatInput(userdic,readPath);
		String tmpOutput="";
		//格式化输入
		int i=1;
		while ((str_in=reader.readLine()) != null ) {
			str_in=str_in.trim().replace("/", "").replace(" ", ",").replace("　", ".");
			tmpOutput+=fi.format(str_in);
			
			System.out.println(i+++"\t"+str_in);
			
		}
		fi.writeFile(tmpOutput);
		
		//标记
		
		

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
			weibo.WriteWeibo(actedMarkSentenceStructure,true);
		}
		
		
		//运行CRF
		Process p = Runtime.getRuntime().exec("./crf/crf_test.exe -m ./crf/model_comment " + writePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		FileWriter writer = new FileWriter(crfResult);
		String message = br.readLine();
		while (message != null)
		{
			//System.out.println(message);
			writer.write(message+"\r\n");
			message = br.readLine();
		}
		writer.flush();
		writer.close();

	}
}
