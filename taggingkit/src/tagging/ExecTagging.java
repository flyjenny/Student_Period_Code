package tagging;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import ICTCLAS.kevin.zhang.*;

public class ExecTagging {

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sInput = "【领导者的眼光】某日，金正日同志到一哨所视察。途经一片竹林，他笑容满面地问：“这里的竹子能有多少？” 随行人员回答：“大约200-300棵。” 金将军说：“依我看，有360多棵……”哨所长惊喜地回答说：“这里的竹子共有365棵。” 霎时，爆发了赞叹声，大家都由衷地佩服金将军非凡的眼光。";
		split(sInput);
	}*/
	public static HashSet PreName;
	public static HashSet SufName;
	public static String outputPath;
	
	public static String split(String sInput)
	{
		try {
			ICTCLAS2011 tool = new ICTCLAS2011();
			String argu = ".";
			System.out.println("ICTCLAS_Init");
			if (tool.ICTCLAS_Init(argu.getBytes("GB2312"),0) == false)
			{
				System.out.println("Init Fail!");
				return null;
			}
			
			tool.ICTCLAS_SetPOSmap(0);
			int nCount = tool.ICTCLAS_ImportUserDict("entity_Dic.txt".getBytes("GB2312"));
			byte nativeBytes[] = tool.ICTCLAS_ParagraphProcess(sInput.getBytes("GB2312"), 1);
			String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");
			System.out.println(nativeStr);
			return nativeStr;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static boolean writeToFile(String[][] sTable, int total)
	{
		try {
			outputPath = "output.txt";
			FileWriter writer = new FileWriter(outputPath, true);
			for (int i = 0; i < total; i++)
//				if (isNE(sTable[1][i]))
			{
				if (sTable[1][i].equals("nx"))
				{
					if (sTable[0][i].length() == 1)
						writer.write(sTable[0][i] + " x s O 1 S-no\r\n");
					else {
						for (int j = 0; j < sTable[0][i].length(); j++)
						{
							writer.write(sTable[0][i].charAt(j));
							if (j == 0)
								writer.write(" x b O 1 B-no\r\n");
							else if (j == sTable[0][i].length() - 1)
								writer.write(" x e O 1 E-no\r\n");
							else writer.write(" x i O 1 I-no\r\n");
						}
					}
				}
				else {
					for (int j = 0; j < sTable[0][i].length(); j++)
					{
						writer.write(sTable[0][i].charAt(j) + " " + sTable[1][i]);
						
						if (sTable[0][i].length() == 1)
							writer.write(" s");
						else if (j == 0)
							writer.write(" b");
						else if (j == sTable[0][i].length() - 1)
							writer.write(" e");
						else writer.write(" i");
						
						if ((j == 0) && (PreName.contains(sTable[0][i].charAt(0)+"")))
							writer.write(" pre ");
						else if ((j == sTable[0][i].length() - 1) && (SufName.contains(sTable[0][i].charAt(j)+"")))
							writer.write(" suf ");
						else writer.write(" O ");
						
						if (sTable[1][i].startsWith("w"))
							writer.write("2");
						else writer.write("0");
						
						if (isNE(sTable[1][i]))
						{
						if (sTable[0][i].length() == 1)
							writer.write(" S-");
						else if (j == 0)
							writer.write(" B-");
						else if (j == sTable[0][i].length() - 1)
							writer.write(" E-");
						else writer.write(" I-");
						
						writer.write(trimPOS(sTable[1][i]) + "\r\n");
						}
					else writer.write(" O\r\n");
					}
				}
			}
/*				else {
					for (int j = 0; j < sTable[0][i].length(); j++)
						writer.write(sTable[0][i].charAt(j) + " " + sTable[1][i] + " O 0 O\r\n");
				}*/
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private static String trimPOS(String POS)
	{
		if (POS.startsWith("nr"))
			return "nr";
		else if (POS.startsWith("ns"))
			return "ns";
		else if (POS.startsWith("nz"))
			return "no";
		else return POS;
	}
	
	public static boolean writeToIntermedia(String[][] sTable, int count)
	{
		try {
			FileWriter writer = new FileWriter("temp.txt", true);
			for (int i = 0; i < count; i++)
			{
				writer.write(sTable[0][i]);
				if (isNE(sTable[1][i]))
					writer.write(" tar\r\n");
				else
				{
					writer.write(" " + sTable[1][i] + "\r\n");
				}
			}
			writer.write("\r\n");
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private static boolean isNE(String str)
	{
		if (str.startsWith("nr"))
			return true;
		else if (str.startsWith("ns"))
			return true;
		else if (str.startsWith("nt"))
			return true;
		else if (str.startsWith("no"))
			return true;
		else if (str.startsWith("nx"))
			return true;
		else if (str.startsWith("nz"))
			return true;
		return false;
	}
	
	public static boolean loadPres()
	{
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("pres.txt"))));
			String firstName = null;
			PreName = new HashSet();
			while ((firstName = reader.readLine()) != null)
			{
				//System.out.println(firstName);
				PreName.add(firstName);
				//PreName.add(firstName);
			}
			reader.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static boolean loadSufs()
	{
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("sufs.txt"))));
			String lastName = null;
			SufName = new HashSet();
			while ((lastName = reader.readLine()) != null)
			{
				//System.out.println(lastName);
				SufName.add(lastName);
				//PreName.add(firstName);
			}
			reader.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
