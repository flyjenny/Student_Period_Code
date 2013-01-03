package Demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import edu.sjtu.lt_lab.input.FormatInput;
import edu.sjtu.lt_lab.mark.CutSentence;
import edu.sjtu.lt_lab.mark.MarkEmotion;
import edu.sjtu.lt_lab.mark.MarkSentenceStructure;
import edu.sjtu.lt_lab.mark.MarkWordStructure;
import edu.sjtu.lt_lab.mark.ReadAndWriteWeibo;

public class Exec {
	public static void main(String[] args) throws IOException{
		
		File fs =new File("temp.txt");//ʵ����ӹ���
		fs.delete();
		fs =new File("crf.txt");//ʵ����ӹ���
		fs.delete();
		fs =new File("crf_result.txt");//ʵ����ӹ���
		fs.delete();
		
		
		String inputPath="input.data";//�����ı�·��
		String readPath = "temp.txt";//������Ԥ��������·������Ϊ��ע����
		String writePath = "crf.txt";//��ע�����·������ΪCRF��ȡ����
		String crfResult="crf_result.txt";//CRF��ȡ�����·��
		
		String dicPath = "EmotionDic.txt";//����ֵ�·��
		String auth = "zqchenbin@gmail.com:20120419";//������䷨������
		
		
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(inputPath),"UTF8"));
		String str_in;
	
		FormatInput fi=new FormatInput("userdic.txt","temp.txt");
		String tmpOutput="";
		//��ʽ������
		int i=1;
		while ((str_in=reader.readLine()) != null ) {
			tmpOutput+=fi.format(str_in.replace("/", ""));
			
			System.out.println(i+++"\t"+str_in);
			
		}
		fi.writeFile(tmpOutput);
		
		//���
		
		

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
		
		
		//����CRF
		Process p = Runtime.getRuntime().exec("./crf/crf_test.exe -m ./crf/model_1217_all " + "crf.txt");
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
