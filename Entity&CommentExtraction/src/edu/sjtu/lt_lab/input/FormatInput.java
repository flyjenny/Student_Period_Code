package edu.sjtu.lt_lab.input;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import ICTCLAS.kevin.zhang.ICTCLAS2011;

/**
 * @author BennyChan
 * 格式化输入
 */
public class FormatInput {
	public static void main(String[] args) throws IOException{
		FormatInput fi=new FormatInput("userdic.txt","temp.txt");
		String input="请问 如何设置在论文中的引用文献的数字标号为上标呀!";
		String output=fi.format(input);
		System.out.println(output);
//		fi.writeFile(output);
		
	}

	private String $userDicPath = "";
	private String $outputPath = "";
	
	public FormatInput(String userDicPath, String outputPath) {
		$userDicPath = userDicPath;
		$outputPath = outputPath;
	}

	public String generatePos(String content) {
		ICTCLAS2011 ictcla = new ICTCLAS2011();
		String argu = "";
		String result = "";
		try {
			ictcla.ICTCLAS_Init(argu.getBytes("GB2312"), 0);
			ictcla.ICTCLAS_SetPOSmap(0);
			int nCount = ictcla.ICTCLAS_ImportUserDict($userDicPath.getBytes("GB2312"));
			byte[] nativeBytes = ictcla.ICTCLAS_ParagraphProcess(
					content.getBytes("GB2312"), 1);
			result = new String(nativeBytes, 0, nativeBytes.length-1, "GB2312");
			ictcla.ICTCLAS_Exit();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(result);
		return result;
	}
	public String doFormat(String input){
		String output="";
		String regex="\\s{1,}";
		String[] words=input.split(regex);
		for(int i=0;i<words.length;i++){
			output=output+words[i].replace("/", " ")+"\r";
		}
		return output+"\r";
	}
	public String format(String input){
		String tmp=generatePos(input);
		String result=doFormat(tmp);
		return result;
	}
	public void writeFile(String content) throws IOException {
		FileWriter fw = new FileWriter($outputPath, false);
		fw.write(content);
		fw.flush();
		fw.close();
	}
}
