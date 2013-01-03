package outputFormatter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MyFormatter {

	private HashMap<String, Integer> wordTable;
	private BufferedReader reader;
	public MyFormatter()
	{
		//String filePath  = "D:\\Programming\\taggingkit\\exec\\results\\韩寒VS方舟子\\ALL features\\result_open.txt";
		String filePath  = "D:\\Programming\\taggingkit\\exec\\results\\金正日逝世\\ALL features\\result_open.txt";
		filePath="result.txt";
		//String filePath = "D:\\Programming\\taggingkit\\exec\\result.txt";
		wordTable = new HashMap<String, Integer>();
		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public HashMap<String, Integer> execFormat()
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
					word = word.concat(parts[0]);
					if (wordTable.containsKey(word))
					{
						int count = wordTable.get(word);
						wordTable.put(word, count+1);
					}
					else
						wordTable.put(word, 1);
				}
				else if (parts[parts.length - 1].startsWith("S"))
				{
					word = parts[0];
					if (wordTable.containsKey(word))
					{
						int count = wordTable.get(word);
						wordTable.put(word, count+1);
					}
					else
						wordTable.put(word, 1);
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
}
