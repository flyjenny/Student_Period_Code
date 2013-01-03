package tagging;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.*;
import org.dom4j.io.*;

public class MyXMLReader {
	private String xmlPath;
	private int id;
	private SAXReader reader;
	private Document doc;
	private Element root;
	private List list;
	private Iterator iter;
	public MyXMLReader(String Path)
	{
		try {
			xmlPath = Path;
			reader = new SAXReader();
			doc = reader.read(new File(Path));
			root = doc.getRootElement();
			list = root.elements("RECORD");
			iter = list.iterator();
			id = 0;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String ReadNext()
	{
		if (!iter.hasNext())
			return null;
		Element node = (Element)iter.next();
		id = Integer.parseInt(node.elementTextTrim("id"));
		return node.elementTextTrim("article");
	}
	public String ReadID(int num)
	{
		while (iter.hasNext())
		{
			Element node = (Element)iter.next();
			id = Integer.parseInt(node.elementTextTrim("id"));
			if (id == num)
				return node.elementTextTrim("article");
			else if (id > num)
				return null;
		}
		return null;
	}
	public void WriteLog()
	{
		try {
			FileWriter writer = new FileWriter("recent.log");
			writer.write(xmlPath + "\r\n");
			writer.write(id + "\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
