package exec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			String filePath_std = "D:/Documents/workspace/evaluator/opentest/jinzhengri_192_CRF.txt";
			//String filePath_std = "D:\\Programming\\taggingkit\\corpus\\º«º®VS·½ÖÛ×Ó\\output_test.txt";
			//String filePath_std = "D:\\Programming\\taggingkit\\exec\\output_temp.txt";
			String filePath_res = "D:/Documents/workspace/evaluator/opentest/open_all-column4.txt";
			//String filePath_res = "D:\\Programming\\taggingkit\\exec\\result_close.txt";
			int count_std = 0;
			int count_res = 0;
			int count_ok = 0;
			double precision = 0;
			double recall = 0;
			String str_std = null;
			String str_res = null;
			boolean status = false;
			BufferedReader reader_std = new BufferedReader(new FileReader(filePath_std));
			BufferedReader reader_res = new BufferedReader(new FileReader(filePath_res));
			while ((str_res = reader_res.readLine()) != null)
			{
				str_std = reader_std.readLine();
				String tag_std[] = str_std.split(" ");
				String tag_res[] = str_res.split("\t");
				if (tag_std[tag_std.length - 1].startsWith("S"))
				{
					count_std++;
					if (tag_res[tag_res.length - 1].startsWith("S"))
						count_res++;
					if (tag_std[tag_std.length - 1].equals(tag_res[tag_res.length - 1]))
						count_ok++;
					continue;
				}
				if (tag_std[tag_std.length - 1].startsWith("B"))
				{
					status = true;
					count_std++;
				}
				if (tag_res[tag_res.length - 1].startsWith("B"))
					count_res++;
				
				if (!tag_std[tag_std.length - 1].equals(tag_res[tag_res.length - 1]))
					status = false;
				
				if (tag_std[tag_std.length - 1].startsWith("E"))
				{
					if (status)
						count_ok++;
					status = false;
				}
			}
			precision = count_ok  * 1.0 / count_res;
			recall = count_ok * 1.0 / count_std;
			System.out.println(count_ok + " " + count_res + " " + count_std);
			System.out.println("Precision: " + precision);
			System.out.println("Recall: " + recall);
			reader_std.close();
			reader_res.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
