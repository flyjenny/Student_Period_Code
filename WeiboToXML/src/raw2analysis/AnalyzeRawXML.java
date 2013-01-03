package raw2analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class AnalyzeRawXML {
	private String $article = "";
	private String $mention = "";
	private String $url = "0";
	private String $expression = "";
	private String $topic = "";

	private String $time = "";
	private String $person_name = "";
	private String $discuss = "0";
	private String $transmit = "0";
	private String $retweeted = "-1";
	private Document $document=null;
	private Element $event=null;
	
	private String $inputPath=null;
	private String $outputPath=null;
	
	private int $count=1;

	public static void main(String[] args) throws FileNotFoundException, IOException, JDOMException {
		AnalyzeRawXML ana1=new AnalyzeRawXML("D:/WeiboData/event01.xml","D:/WeiboData/event01_output.xml");
		ana1.readAndWriteXml();
		ana1.wirteXML();
		AnalyzeRawXML ana2=new AnalyzeRawXML("D:/WeiboData/event02.xml","D:/WeiboData/event02_output.xml");
		ana2.readAndWriteXml();
		ana2.wirteXML();
		AnalyzeRawXML ana3=new AnalyzeRawXML("D:/WeiboData/event03.xml","D:/WeiboData/event03_output.xml");
		ana3.readAndWriteXml();
		ana3.wirteXML();
		AnalyzeRawXML ana4=new AnalyzeRawXML("D:/WeiboData/event04.xml","D:/WeiboData/event04_output.xml");
		ana4.readAndWriteXml();
		ana4.wirteXML();
		AnalyzeRawXML ana5=new AnalyzeRawXML("D:/WeiboData/event05.xml","D:/WeiboData/event05_output.xml");
		ana5.readAndWriteXml();
		ana5.wirteXML();
		AnalyzeRawXML ana6=new AnalyzeRawXML("D:/WeiboData/event06.xml","D:/WeiboData/event06_output.xml");
		ana6.readAndWriteXml();
		ana6.wirteXML();
		AnalyzeRawXML ana7=new AnalyzeRawXML("D:/WeiboData/event07.xml","D:/WeiboData/event07_output.xml");
		ana7.readAndWriteXml();
		ana7.wirteXML();
		AnalyzeRawXML ana8=new AnalyzeRawXML("D:/WeiboData/event08.xml","D:/WeiboData/event08_output.xml");
		ana8.readAndWriteXml();
		ana8.wirteXML();
		AnalyzeRawXML ana9=new AnalyzeRawXML("D:/WeiboData/event09.xml","D:/WeiboData/event09_output.xml");
		ana9.readAndWriteXml();
		ana9.wirteXML();
		AnalyzeRawXML ana10=new AnalyzeRawXML("D:/WeiboData/event10.xml","D:/WeiboData/event10_output.xml");
		ana10.readAndWriteXml();
		ana10.wirteXML();
	}
	
	public AnalyzeRawXML(String inputPath,String outputPath){
		$document = new Document();
        //创建根元素
        $event = new Element("event");
        //把根元素加入到document中
        $document.addContent($event);
        
        $inputPath=inputPath;
        $outputPath=outputPath;
	}
	public void readAndWriteXml() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build($inputPath);
		Element root = document.getRootElement();
		List recordlist = root.getChildren("RECORD");
		for (Iterator iter = recordlist.iterator(); iter.hasNext();) {
			Element record = (Element) iter.next();
//			System.out.println(record.getChildTextTrim("article"));
			$time=record.getChildTextTrim("time");
			$person_name=record.getChildTextTrim("person_name");
			$discuss=record.getChildTextTrim("discuss");
			$transmit =record.getChildTextTrim("transmit");
			$retweeted=record.getChildTextTrim("retweeted");
			
			analyse(record.getChildTextTrim("article"));
		}
		
		
	}
	public void wirteXML() throws FileNotFoundException, IOException{
		//设置xml输出格式
        Format format = Format.getPrettyFormat();
        format.setEncoding("utf-8");//设置编码
        format.setIndent("\t");//设置缩进
        
        //得到xml输出流
        XMLOutputter out = new XMLOutputter(format);
        //把数据输出到xml中
        out.output($document, new FileOutputStream($outputPath));//或者FileWriter
	}
	public Element produceRecord(){
		Element record = new Element("RECORD");
		
		Element id = new Element("id");
		id.setText(($count++)+"");
        record.addContent(id);
        
		Element article = new Element("article");
        article.setText($article);
        record.addContent(article);
        
        Element mention = new Element("mention");
        mention.setText($mention);
        record.addContent(mention);
        
        Element url = new Element("url");
        url.setText($url);
        record.addContent(url);
        
        Element expression = new Element("expression");
        expression.setText($expression);
        record.addContent(expression);
        
        Element topic = new Element("topic");
        topic.setText($topic);
        record.addContent(topic);
        
        Element time = new Element("time");
        time.setText($time);
        record.addContent(time);
        
        Element person_name = new Element("person_name");
        person_name.setText($person_name);
        record.addContent(person_name);
        
        Element discuss = new Element("discuss");
        discuss.setText($discuss);
        record.addContent(discuss);
        
        Element transmit = new Element("transmit");
        transmit.setText($transmit);
        record.addContent(transmit);
        
        Element retweeted = new Element("retweeted");
        retweeted.setText($retweeted);
        record.addContent(retweeted);
		
		return record;
	}
	
	public void analyse(String content) {
		String[] contentlist = content.split("//@");
		int i = 0;
		while (i < contentlist.length) {
			if (i == 0) {
				analyseContent(contentlist[i]);
				$event.addContent(produceRecord());
			} else {
				try{
					analyseTransmit(contentlist[i]);
				}catch(ArrayIndexOutOfBoundsException e){
					analyseContent(contentlist[i]);
				}
				$event.addContent(produceRecord());
			}
			i++;
		}
	}

	public void analyseTransmit(String content) {
		String personName = null;// 用户名
		Object[] subcontent = Arrays.asList(
				Pattern.compile(":").split(content, 2)).toArray();
		personName = (String) subcontent[0];
		
		$person_name=personName;
		$time = "";
		$discuss = "";
		$transmit = "";
		try{
			analyseContent((String) subcontent[1]);// 分析正文
		}catch(ArrayIndexOutOfBoundsException e){
			Object[] subcontentTmp = Arrays.asList(
					Pattern.compile(" ").split(content, 2)).toArray();
			personName = (String) subcontentTmp[0];
			$person_name=personName;
			analyseContent((String) subcontentTmp[1]);// 分析正文
		}
	}

	public void analyseContent(String content) {
		int urlCount = 0; // URL数量
		int expressionCount = 0; // 表情数量
		int topicCount = 0; // 话题数量
		int mentionCount = 0; // 提到的人数量

		String clearURL = null; // 过滤url后字符串
		String clearexpression = null;// 过滤表情后的字符串
		String clearTopic = null; // 过滤主题后的字符串
		String clearMention = null; // 过滤提到的人后的字符串
		String pureText = null; // 纯正文字符串

		String expressions = ""; // 表情
		String topics = ""; // 话题
		String mentions = ""; // 提到的人

		String urlPattern = "http://[a-zA-z0-9]+.[a-zA-z0-9]+/[a-zA-z0-9]+";
		Pattern pattern = Pattern.compile(urlPattern);
		Matcher matcher = pattern.matcher(content);
		int start = 0;
		int end = -1;
		while (matcher.find()) {
			/*
			 * start = matcher.start(); end = matcher.end(); String mache =
			 * content.substring(start,end); System.out.println (mache);
			 */urlCount++;
		}
		clearURL = content.replaceAll(urlPattern, "");

		String expressionPattern = "(\\[)(.)+?(\\])";
		pattern = Pattern.compile(expressionPattern);
		matcher = pattern.matcher(clearURL);

		while (matcher.find()) {
			start = matcher.start();
			end = matcher.end();
			String mache = clearURL.substring(start, end);
			expressions = expressions + mache + " ";
			expressionCount++;
		}
		clearexpression = clearURL.replaceAll(expressionPattern, "");

		String topicPattern = "(#)(.)+?(#)";
		pattern = Pattern.compile(topicPattern);
		matcher = pattern.matcher(clearexpression);
		while (matcher.find()) {
			start = matcher.start();
			end = matcher.end();
			String mache = clearexpression.substring(start, end);
			topics = topics + mache + " ";
			topicCount++;
		}
		clearTopic = clearexpression.replaceAll(topicPattern, "");

		String mentionPattern = "@(.)+?(\\s+|\\s+$)";
		pattern = Pattern.compile(mentionPattern);
		matcher = pattern.matcher(clearTopic);
		while (matcher.find()) {
			start = matcher.start();
			end = matcher.end();
			String mache = clearTopic.substring(start, end);
			mentions = mentions + mache.trim() + " ";
			mentionCount++;
		}
		clearMention = clearTopic.replaceAll(mentionPattern, "");

		pureText = clearMention;

		$article = pureText;
		$mention = mentions;
		$url = urlCount+"";
		$expression = expressions;
		$topic = topics;

	}
}
