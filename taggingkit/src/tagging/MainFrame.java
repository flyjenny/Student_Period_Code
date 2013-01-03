package tagging;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import javax.swing.DebugGraphics;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;


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
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private JButton jButton1;
	private JScrollPane jScrollPane2;
	private JFileChooser jFileChooser1;
	private JButton jButton5;
	private JButton jButton4;
	private JTable jTable2;
	private JTable jTable1;
	private JButton jButton3;

	//private String[][] sTable;
	//private String[] sLable;
	private int count, ncount;
	private MyXMLReader reader;
	private int id;
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
/*		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame inst = new MainFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});*/
		autoExec();
	}
	public static void autoExec()
	{
		try{
			String input_filePath = "input.data";
			String str_in;
			//FileReader freader = new FileReader(input_filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input_filePath), "UTF8"));
			//FileWriter writer = new FileWriter(output_filePath);
			ExecTagging.loadPres();
			ExecTagging.loadSufs();
			while ((str_in = reader.readLine()) != null)
			{
			//	System.out.println(str_in);
				String sOutput = ExecTagging.split(str_in);
				String[] sRawList = sOutput.split(" ");
				String[][] sList = new String[2][sRawList.length];
				int i = 0;
				int count = 0;
				while (i < sRawList.length)
				{
					if(sRawList[i].indexOf("/") == -1)
					{
						i++;
						continue;
					}
					sList[0][count] = sRawList[i].substring(0, sRawList[i].indexOf("/"));
					sList[1][count] = sRawList[i].substring(sRawList[i].indexOf("/") + 1, sRawList[i].length());
					count++;
					i++;
				}
				ExecTagging.writeToFile(sList, count);
				//ExecTagging.writeToIntermedia(sList, count);
			}
			//writer.flush();
			reader.close();
			//writer.close();
			Process p = Runtime.getRuntime().exec(".\\exec\\crf_test.exe -m .\\exec\\model " + ExecTagging.outputPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			FileWriter writer_res = new FileWriter("result.txt");
			FileWriter writer_mid = new FileWriter("userdic.txt");
			String message = br.readLine();
			String word = "";
			while (message != null)
			{
				//System.out.println(message);
				//output to result
				writer_res.write(message+"\r\n");
				//output to intermediate result
				String[] parts = message.split("\t");
				if (parts[parts.length - 1].startsWith("B"))
					word = parts[0];
				else if (parts[parts.length - 1].startsWith("I"))
					word = word.concat(parts[0]);
				else if (parts[parts.length - 1].startsWith("E"))
				{
					word = word.concat(parts[0]);
					writer_mid.write(word+" tar\r\n");
				}
				else if (parts[parts.length - 1].startsWith("S"))
				{
					word = parts[0];
					writer_mid.write(word+" tar\r\n");
				}
				
				message = br.readLine();
			}
			writer_res.flush();
			writer_res.close();
			writer_mid.flush();
			writer_mid.close();
		} catch (Exception e){e.printStackTrace();}
	}
	
	public MainFrame() {
		super();
		ExecTagging.loadPres();
		ExecTagging.loadSufs();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.SOUTH);
				jPanel1.setPreferredSize(new java.awt.Dimension(384, 50));
				{
					jButton4 = new JButton();
					jPanel1.add(jButton4);
					jButton4.setText("\u6253\u5f00");
					jButton4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButton4ActionPerformed(evt);
						}
					});
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1);
					jButton1.setText("\u5408\u5e76");
					jButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButton1ActionPerformed(evt);
						}
					});
				}
				{
					jButton3 = new JButton();
					jPanel1.add(jButton3);
					jButton3.setText("\u4fdd\u5b58");
					jButton3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButton3ActionPerformed(evt);
						}
					});
				}
				{
					jButton5 = new JButton();
					jPanel1.add(jButton5);
					jButton5.setText("\u4e0b\u4e00\u6761");
					jButton5.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButton5ActionPerformed(evt);
						}
					});
				}
				{
					jFileChooser1 = new JFileChooser();
					jPanel1.add(jFileChooser1);
					jFileChooser1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jFileChooser1ActionPerformed(evt);
						}
					});
				}
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1, BorderLayout.NORTH);
				jScrollPane1.setPreferredSize(new java.awt.Dimension(384, 111));
				jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				{
					jTable1 = new JTable();
					jScrollPane1.setViewportView(jTable1);
					jTable1.setDebugGraphicsOptions(DebugGraphics.BUFFERED_OPTION);
					jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					jTable1.setColumnSelectionAllowed(true);
					jTable1.setCellSelectionEnabled(true);
				}
			}
			{
				jScrollPane2 = new JScrollPane();
				getContentPane().add(jScrollPane2, BorderLayout.CENTER);
				jScrollPane2.setPreferredSize(new java.awt.Dimension(384, 102));
				jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				{
					jTable2 = new JTable();
					jScrollPane2.setViewportView(jTable2);
					jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					jTable2.setColumnSelectionAllowed(true);
					jTable2.setCellSelectionEnabled(true);
				}
			}
			loadRecent();
			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void jButton4ActionPerformed(ActionEvent evt) {
		System.out.println("jButton4.actionPerformed, event="+evt);
		//TODO add your code for jButton4.actionPerformed
		jFileChooser1.showDialog(jPanel1, "Input File");
	}
	
	private void jFileChooser1ActionPerformed(ActionEvent evt) {
		System.out.println("jFileChooser1.actionPerformed, event="+evt);
		//TODO add your code for jFileChooser1.actionPerformed
		System.out.println(jFileChooser1.getSelectedFile().getAbsolutePath());
		reader = new MyXMLReader(jFileChooser1.getSelectedFile().getAbsolutePath());
		update(reader.ReadNext());
	}
	
	private void jButton5ActionPerformed(ActionEvent evt) {
		System.out.println("jButton5.actionPerformed, event="+evt);
		//TODO add your code for jButton5.actionPerformed
		id++;
		System.out.println(id);
		update(reader.ReadNext());
	}
	
	private void loadRecent()
	{
		try {
			BufferedReader logReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("recent.log"))));
			String path = logReader.readLine();
			reader = new MyXMLReader(path);
			id = Integer.parseInt(logReader.readLine());
			System.out.println(id);
			update(reader.ReadID(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("no recent access");
		}
	}
	private void setRecent()
	{
		reader.WriteLog();
	}
	
	private void update(String sInput)
	{
		String sOutput = ExecTagging.split(sInput);
		String sRawList[] = sOutput.split(" ");
		String sList[] = new String[sRawList.length];
		String sTag[] = new String[sRawList.length];
		String sLable[] = new String[sRawList.length];
		//System.out.println(sRawList.length);
		int i = 0;
		count = 0;
		ncount = 0;
		while (i < sRawList.length)
		{
			if(sRawList[i].indexOf("/") == -1)
			{
				i++;
				continue;
			}
			sList[count] = sRawList[i].substring(0, sRawList[i].indexOf("/"));
			sTag[count] = sRawList[i].substring(sRawList[i].indexOf("/") + 1, sRawList[i].length());
			if (sTag[count].startsWith("n") || (sTag[count].equals("x"))) ncount++;
			count++;
			i++;
		}
		for (i = 0; i < count; i++)
		{
			sLable[i] = i + "";
		}
		TableModel jTable1Model = 
			new DefaultTableModel(
					new String[][] { sList, sTag },
					sLable);
		jTable1.setModel(jTable1Model);
		
		String nsList[] = new String[ncount];
		String nsTag[] = new String[ncount];
		String nsLable[] = new String[ncount];
		int j = 0;
		for (i = 0; i < count; i++)
		{
			if (sTag[i].startsWith("n") || (sTag[i].equals("x")))
			{
				nsList[j] = sList[i];
				nsTag[j] = sTag[i];
				nsLable[j++] = sLable[i];
			}
		}
		TableModel jTable2Model = 
			new DefaultTableModel(
					new String[][] { nsList, nsTag },
					nsLable);
		jTable2.setModel(jTable2Model);
	}
	
	private void jButton3ActionPerformed(ActionEvent evt) {
		System.out.println("jButton3.actionPerformed, event="+evt);
		//TODO add your code for jButton3.actionPerformed
		String sTable[][] = new String[2][count];
		int i;
		for (i = 0; i < count; i++)
		{
			sTable[0][i] = (String)jTable1.getValueAt(0, i);
			sTable[1][i] = (String)jTable1.getValueAt(1, i);
		}
		for (i = 0; i < ncount; i++)
		{
			sTable[0][Integer.parseInt(jTable2.getModel().getColumnName(i))] = (String)jTable2.getValueAt(0, i);
			sTable[1][Integer.parseInt(jTable2.getModel().getColumnName(i))] = (String)jTable2.getValueAt(1, i);
		}
		ExecTagging.writeToFile(sTable, count);
		ExecTagging.writeToIntermedia(sTable, count);
		setRecent();
	}
	
	
	private void jButton1ActionPerformed(ActionEvent evt) {
		System.out.println("jButton1.actionPerformed, event="+evt);
		//TODO add your code for jButton1.actionPerformed
		int arr[] = jTable1.getSelectedColumns();
		String sList[] = new String[count];
		String sTag[] = new String[count];
		int i = 0;
		for (i = 0; i < arr.length; i++)
			System.out.print(arr[i] + " ");
		System.out.println();
		//check
		if (arr.length < 2) return;
		for (i = 0; i < arr.length - 1; i++)
			if (arr[i + 1] - arr[i] != 1)
				return;
		//merge
		for (i = 0; i < count; i++)
		{
			sList[i] = (String)jTable1.getValueAt(0, i);
			sTag[i] = (String)jTable1.getValueAt(1, i);
		}
		for (i = 1; i < arr.length; i++)
		{
			sList[arr[0]] += sList[arr[i]];
		}
		String nsList[] = new String[count - arr.length + 1];
		String nsTag[] = new String[count - arr.length + 1];
		String nsLable[] = new String[count - arr.length + 1];
		for (i = 0; i < arr[0]; i++)
		{
			nsList[i] = sList[i];
			nsTag[i] = sTag[i];
		}
		nsList[arr[0]] = sList[arr[0]];
		nsTag[arr[0]] = "no";
		for (i = arr[0] + 1; i < nsList.length; i++)
		{
			nsList[i] = sList[i + arr.length - 1];
			nsTag[i] = sTag[i + arr.length - 1];
		}
		ncount = 0;
		for (i = 0; i < count - arr.length + 1; i ++)
		{
			nsLable[i] = i+"";
			if (nsTag[i].startsWith("n") || nsTag[i].equals("x"))
				ncount++;
		}
		TableModel jTable1Model = 
			new DefaultTableModel(
					new String[][] { nsList, nsTag },
					nsLable);
		jTable1.setModel(jTable1Model);
		count -= arr.length - 1;
		String nnsList[] = new String[ncount];
		String nnsTag[] = new String[ncount];
		String nnsLable[] = new String[ncount];
		int j = 0;
		System.out.println("ncount:"+ncount);
		System.out.println("count:"+count);
		for (i = 0; i < count; i++)
		{
			if (nsTag[i].startsWith("n") || nsTag[i].equals("x"))
			{
				nnsList[j] = nsList[i];
				nnsTag[j] = nsTag[i];
				nnsLable[j++] = nsLable[i];
			}
		}
		TableModel jTable2Model = 
			new DefaultTableModel(
					new String[][] { nnsList, nnsTag },
					nnsLable);
		jTable2.setModel(jTable2Model);
	}
}
