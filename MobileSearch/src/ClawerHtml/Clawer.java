package ClawerHtml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class Clawer {
	public static void main(String[] args) throws XPatherException {
		String url = "http://wap.ip138.com/sim_search.asp?mobile=15121025925";

	}

	private String encode = "UTF-8";

	public String getHTML(String pageURL) throws ConnectException {
		StringBuilder pageHTML = new StringBuilder();
		try {
			URL url = new URL(pageURL);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestProperty("User-Agent", "MSIE 7.0");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encode));
			String line = null;
			while ((line = br.readLine()) != null) {
				pageHTML.append(line);
				pageHTML.append("\r\n");
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageHTML.toString();
	}

	public String getSourceUrl(String html) throws XPatherException {
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode rootNode = cleaner.clean(html);
		String XPath = "/body/div/div";
		try {
			Object[] subNodes = rootNode.evaluateXPath(XPath);
			String text = null;
			TagNode inputNode = (TagNode) subNodes[0];
			text = inputNode.getText().toString();
			return text;
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
		}
		return null;
	}

	public String getLocation(String input) {
		String location = "";
		try {
			if (null != input) {
				String[] tmp = input.split("\r");
				if (tmp.length > 2) {
					int length = tmp[1].length();
					location = tmp[1].split(" ")[1];
				}
			}
		} catch (NullPointerException e) {
			return location;
		} catch (ArrayIndexOutOfBoundsException e1){
			return location;
		}
		return location;
	}
}
