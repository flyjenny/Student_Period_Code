package frames;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.Map.Entry;
import javax.swing.GroupLayout;
import javax.swing.JComponent;

import javax.swing.JPanel;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import outputFormatter.Detection;
import outputFormatter.MyFormatter;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MainFrame extends javax.swing.JFrame {
	private HashMap<String, int[]> wordTable;
	private HashMap<String, ArrayList<String>> commentTable;
	private HashMap<String, HashMap<String, Integer>> commentTable_ult;
	private String[] words;
	private int[] sizes;
	private int[] ord_x;
	private int[] ord_y;
//	private String[] fontList;
	private Color[] colorList;
	private MyFormatter dataFormatter;
	private int selectedID;
	private static Random seed = new Random();
	private static Color[] colorLib = {new Color(0,0,0), new Color(255,0,0), new Color(0,255,0), new Color(0,0,255)/*, new Color(255,255,0), new Color(0,255,255), new Color(0,0,255)*/};
//	private static String[] fontLib = {"ËÎÌå", "Ó×Ô²", "Á¥Êé", "ºÚÌå", "¿¬Ìå"};
	
	public static int MINPOND = 12;
	public static int MAXPOND = 50;
	public static int COMMENTPOND = 15;
	public static int MARGINX = 5;
	public static int MARGINY = 3;
	public static int COMMENTWIDTH = 100;
	public static int MAXCOMMENTNUM = 15;
	
	public static String commentPath = "result_comment.txt";
	public static String entityPath = "result_entity.txt";
	
	private JPanel jPanel1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public MainFrame() {
		super();
		dataFormatter = new MyFormatter();
		wordTable = dataFormatter.execFormat();
		design();
		selectedID = -1;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				this.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jPanel1MouseClicked(evt);
					}
				});
				jPanel1 = new JPanel();
				//commentTable = new HashMap<String, ArrayList<String>>();
				//String commentPath = "test.txt";
				Detection dt = new Detection(commentPath);
				while(dt.ReadNext()){
					dt.AnalysisResult();
				}
				commentTable = dt.GetTarAndWords();
				commentInit();
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.CENTER);
			}
			pack();
			this.setSize(900, 700);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		int i = 0;
		for (i = 0; i < sizes.length; i++)
		{
			//g.setFont(new Font("Î¢ÈíÑÅºÚ",0,nomalize(sizes[i])));
			g.setFont(new Font(/*fontList[i]*/"Ó×Ô²", 1, nomalize(sizes[i])));
			g.setColor(colorList[i]);
			g.drawString(words[i], ord_x[i], ord_y[i]);
		}
		if (selectedID != -1)
		{
			System.out.println("hit!");
			String word = Util.getWord(selectedID);
			//ArrayList<String> comments = commentTable.get(word);
			if (!commentTable_ult.containsKey(word))
			{
				System.out.println("no commnets");
				return;
			}
			ArrayList<Map.Entry<String, Integer>> comments =
			    new ArrayList<Map.Entry<String, Integer>>(commentTable_ult.get(word).entrySet());
			//sort
			Collections.sort(comments, new Comparator<Map.Entry<String, Integer>>() {   
			    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
			        return (o2.getValue() - o1.getValue()); 
			        //return (o1.getKey()).toString().compareTo(o2.getKey());
			    }
			}); 
			int baseX = Util.getX2(selectedID);
			int baseY = Util.getY1(selectedID);
			int totalLine = comments.size();
			if (totalLine > MAXCOMMENTNUM)
				totalLine = MAXCOMMENTNUM;
			if (baseX + COMMENTWIDTH > Util.MAXX)
				baseX = Util.getX1(selectedID) - COMMENTWIDTH;
			if (baseY + totalLine*(COMMENTPOND+MARGINY) > Util.MAXY)
				baseY = Util.getY2(selectedID) - totalLine*(COMMENTPOND+MARGINY);
			g.setColor(new Color(0,0,0));
			g.fillRect(baseX, baseY, COMMENTWIDTH, totalLine*(COMMENTPOND+MARGINY)+MARGINY*2);
			g.setColor(new Color(255,255,255));
			g.fillRect(baseX+1, baseY+1, COMMENTWIDTH-2, totalLine*(COMMENTPOND+MARGINY)+MARGINY*2-2);
			g.setColor(new Color(0,0,0));
			g.setFont(new Font("Î¢ÈíÑÅºÚ",0,COMMENTPOND));
			for (int j = 0; j < totalLine && j < MAXCOMMENTNUM; j++)
			{
				g.drawString(comments.get(j).getKey(), baseX+MARGINX, baseY+(COMMENTPOND+MARGINY)*(j+1));
				System.out.println("\t"+comments.get(j).getKey()+" "+comments.get(j).getValue());
			}
			System.out.println();
		}
	}
	
	private void commentInit()
	{
		commentTable_ult = new HashMap<String, HashMap<String, Integer>>();
		Iterator iter = commentTable.entrySet().iterator();
		while (iter.hasNext())
		{
			//named-entity insertion
			Entry entry = (Entry)iter.next();
			String key = (String)entry.getKey();
			commentTable_ult.put(key, new HashMap<String, Integer>());
			//comment insertion
			{
				//data copy
				/*Iterator inner_iter = ((HashMap<String, Integer>)entry.getValue()).entrySet().iterator();
				while (inner_iter.hasNext())
				{
					Entry inner_entry = (Entry)inner_iter.next();
					String comment = (String)inner_entry.getKey();
					if (commentTable_ult.get(key).containsKey(comment))
						commentTable_ult.get(key).put(comment, commentTable_ult.get(key).get(comment)+1);
					else
						commentTable_ult.get(key).put(comment, 1);
				}*/
				ArrayList<String> commentsList = (ArrayList<String>)entry.getValue();
				for (int i = 0; i < commentsList.size(); i++)
				{
					String comment = commentsList.get(i);
					if (commentTable_ult.get(key).containsKey(comment))
						commentTable_ult.get(key).put(comment, commentTable_ult.get(key).get(comment)+1);
					else
						commentTable_ult.get(key).put(comment, 1);
				}
				//sort
				/*List<Map.Entry<String, Integer>> infoIds =
				    new ArrayList<Map.Entry<String, Integer>>(commentTable_ult.get(key).entrySet());*/
			}
		}
	}
	
	private void design()
	{
		Iterator iter = wordTable.entrySet().iterator();
		words = new String[wordTable.size()];
		sizes = new int[wordTable.size()];
		ord_x = new int[wordTable.size()];
		ord_y = new int[wordTable.size()];
		colorList = new Color[wordTable.size()];
//		fontList = new String[wordTable.size()];
		int i = 0;
		while (iter.hasNext())
		{
			Entry entry = (Entry)iter.next();
			words[i] = (String)entry.getKey();
			sizes[i] = ((int[])entry.getValue())[0];
			colorList[i] = colorLib[((int[])entry.getValue())[1]];
			i++;
		}
		qsort(0, sizes.length - 1);
		ArrayList<Integer> position;
		for (i = 0; i < sizes.length && i < Util.MAXWORDNUM; i++)
		{
			System.out.println(words[i] + " " + nomalize(sizes[i]) + " " + sizes[i]);
			position = Util.findBlank(words[i].length()*nomalize(sizes[i]), nomalize(sizes[i]), words[i]);
			ord_x[i] = position.get(0);
			ord_y[i] = position.get(1);
//			colorList[i] = colorLib[Math.abs(seed.nextInt()%colorLib.length)];
//			fontList[i] = fontLib[Math.abs(seed.nextInt()%fontLib.length)];
		}
	}
	private void qsort(int l, int r)
	{
		int x = (l+r)/2;
		int y = sizes[x];
		int i = l;
		int j = r;
		while (i <= j)
		{
			while ((i < r) && (sizes[i] > y)) i++;
			while ((j > l) && (sizes[j] < y)) j--;
			if (i <= j)
			{
				int temp_int = sizes[i];
				sizes[i] = sizes[j];
				sizes[j] = temp_int;
				String temp_str = words[i];
				words[i] = words[j];
				words[j] = temp_str;
				Color tem_col = colorList[i];
				colorList[i] = colorList[j];
				colorList[j] = tem_col;
				i++;
				j--;
			}
		}
		if (l < j) qsort(l, j);
		if (i < r) qsort(i, r);
	}
	private int nomalize(int n)
	{
		return (int)Math.round((MINPOND + (n * 1.0 / sizes[0]) * (MAXPOND - MINPOND)));
	}
	
	private void jPanel1MouseClicked(MouseEvent evt) {
		System.out.println("jPanel1.mouseClicked, event="+evt);
		//TODO add your code for jPanel1.mouseClicked
		//System.out.print("Mouse position (" + evt.getX() + "," + evt.getY() + ")");
		if ((selectedID = Util.hitWord(evt.getX(), evt.getY())) != -1)
		{
			repaint();
		}
		else
			System.out.println("not hit!");
	}
}
