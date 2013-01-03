package outputFormatter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MyFormatter {

	private HashMap<String, int[]> wordTable;
	private BufferedReader reader;
	public MyFormatter()
	{
		//String filePath  = "D:\\Programming\\taggingkit\\exec\\results\\韩寒VS方舟子\\ALL features\\result_open.txt";
		//String filePath  = "D:\\Programming\\taggingkit\\exec\\results\\金正日逝世\\ALL features\\result_open.txt";
		//String filePath = "D:\\Programming\\taggingkit\\exec\\result.txt";
		String filePath = frames.MainFrame.entityPath;
		wordTable = new HashMap<String, int[]>();
		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public HashMap<String, int[]> execFormat()
	{
		String str_in;
		String word = "";
		try {
			while ((str_in = reader.readLine())!= null)
			{
				String[] parts = str_in.split("\t");
				if (parts[parts.length - 1].startsWith("B"))
					word = parts[0];
				else if (parts[parts.length - 1].startsWith("I"))
					word = word.concat(parts[0]);
				else if (parts[parts.length - 1].startsWith("E"))
				{
					insert2Table(parts, word);
				}
				else if (parts[parts.length - 1].startsWith("S"))
				{
					word = "";
					insert2Table(parts, word);
				}
			}
			return wordTable;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public void printTable()
	{
		Iterator iter = wordTable.entrySet().iterator();
		while (iter.hasNext())
		{
			Entry entry = (Entry)iter.next();
			System.out.println((String)entry.getKey() + " " + (Integer)entry.getValue());
		}
	}
	
	private int tagConvert(String tag_str)
	{
		if (tag_str.equals("nr"))
			return 0;
		else if (tag_str.equals("ns"))
			return 1;
		else if (tag_str.equals("nt"))
			return 2;
		else if (tag_str.endsWith("no"))
			return 3;
		else return -1;
	}
	
	private void insert2Table(String[] line, String word)
	{
		int []data = new int[2];
		word = word.concat(line[0]);
		if (wordTable.containsKey(word))
		{
			data = wordTable.get(word);
			data[0] += 1;
			wordTable.put(word, data);
		}
		else
		{
			data[0] = 1;
			data[1] = tagConvert(line[line.length - 1].substring(2));
			wordTable.put(word, data);
		}
	}
}
