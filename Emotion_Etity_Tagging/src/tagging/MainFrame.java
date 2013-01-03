package tagging;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
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
	private JButton jButton1;
	private JButton jButton4;
	private JComboBox jComboBox1;
	private JTextField jTextField1;
	private JScrollPane jScrollPane1;
	private JButton jButton3;
	private JButton jButton2;
	private JPanel jPanel2;
	private JFileChooser jFileChooser1;
	
	private BufferedReader reader;
	private BufferedReader gdReader;
	private String input_filePath;
	private String gd_filePath;
	private int weiboID;
	private int segID;
	private String pureText;

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
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				GridLayout jPanel1Layout = new GridLayout(1, 1);
				jPanel1Layout.setHgap(5);
				jPanel1Layout.setVgap(5);
				jPanel1Layout.setColumns(1);
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				{
					jScrollPane1 = new JScrollPane();
					jPanel1.add(jScrollPane1);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(950, 127));
					jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						jTextField1 = new JTextField();
						jTextField1.setFont(new Font("Î¢ÈíÑÅºÚ",0,20));
						jScrollPane1.setViewportView(jTextField1);
						if (loadLog())
							update();
					}
				}
			}
			{
				jPanel2 = new JPanel();
				getContentPane().add(jPanel2, BorderLayout.SOUTH);
				{
					jButton1 = new JButton();
					jPanel2.add(jButton1);
					jButton1.setText("Load");
					jButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButton1ActionPerformed(evt);
						}
					});
				}
				{
					jButton2 = new JButton();
					jPanel2.add(jButton2);
					jButton2.setText("Next");
					jButton2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButton2ActionPerformed(evt);
						}
					});
				}
				{
					jButton3 = new JButton();
					jPanel2.add(jButton3);
					jButton3.setText("Confirm");
					jButton3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButton3ActionPerformed(evt);
						}
					});
				}
				{
					ComboBoxModel jComboBox1Model = 
						new DefaultComboBoxModel(
								new String[] { "POS", "NEG", "NEU" });
					jComboBox1 = new JComboBox();
					jPanel2.add(jComboBox1);
					jComboBox1.setModel(jComboBox1Model);
				}
				{
					jButton4 = new JButton();
					jPanel2.add(jButton4);
					jButton4.setText("Exit");
					jButton4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jButton4ActionPerformed(evt);
						}
					});
				}
			}
			pack();
			setSize(1000, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private boolean loadLog()
	{
		try {
			BufferedReader logReader = new BufferedReader(new InputStreamReader(new FileInputStream("Recent.log"), "GB2312"));
			input_filePath = logReader.readLine();
			gd_filePath = input_filePath.substring(0, input_filePath.lastIndexOf('\\')+1)+"guandian.csv";
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(input_filePath), "GB2312"));
			gdReader = new BufferedReader(new InputStreamReader(new FileInputStream(gd_filePath), "GB2312"));
			weiboID = Integer.parseInt(logReader.readLine());
			segID = Integer.parseInt(logReader.readLine());
			logReader.close();
			if (!update(weiboID, segID))
			{
				System.out.println("Bad log!");
				return false;
			}
			return true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("No log found!");
			weiboID = 0;
			segID = 0;
			return false;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	private void update()
	{
		try {
			String temp_str;
			while ((temp_str = reader.readLine()) != null)
			{
				String gd_arr[] = gdReader.readLine().split(",");
				if (gd_arr[2].equals("Y"))
				{
					System.out.println(gd_arr[0]+"/"+gd_arr[1]+" "+gd_arr[2]);
					break;
				}
			}
			String temp_arr[] = temp_str.split(",");//reader.readLine().split(",");
			weiboID = Integer.parseInt(temp_arr[0]);
			segID = Integer.parseInt(temp_arr[1]);
			pureText = temp_arr[2].substring(1, temp_arr[2].length()-1);
			System.out.println(weiboID + "/" + segID +" "+pureText);
			jTextField1.setText(pureText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean update(int w, int s)
	{
		String str_raw;
		//System.out.println(w+"/"+s);
		try {
			while ((str_raw = reader.readLine()) != null)
			{
				gdReader.readLine();
				String temp_str[] = str_raw.split(",");
				weiboID = Integer.parseInt(temp_str[0]);
				segID = Integer.parseInt(temp_str[1]);
				if (weiboID == w && segID == s)
					return true;
			}
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//Button: load
	private void jButton1ActionPerformed(ActionEvent evt) {
		System.out.println("jButton1.actionPerformed, event="+evt);
		//TODO add your code for jButton1.actionPerformed
		jFileChooser1 = new JFileChooser();
		jFileChooser1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jFileChooser1ActionPerformed(evt);
			}
		});
		jFileChooser1.showDialog(this, "´ò¿ª");
	}
	private void jFileChooser1ActionPerformed(ActionEvent evt) {
		System.out.println("jFileChooser1.actionPerformed, event="+evt);
		//TODO add your code for jFileChooser1.actionPerformed
		input_filePath = jFileChooser1.getSelectedFile().getAbsolutePath();
		gd_filePath = input_filePath.substring(0, input_filePath.lastIndexOf('\\')+1)+"guandian.csv";
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(input_filePath), "GB2312"));
			gdReader = new BufferedReader(new InputStreamReader(new FileInputStream(gd_filePath), "GB2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		update();
	}
	
	//Button: Next
	private void jButton2ActionPerformed(ActionEvent evt) {
		System.out.println("jButton2.actionPerformed, event="+evt);
		//TODO add your code for jButton2.actionPerformed
		update();
	}
	
	//Button: Confirm
	private void jButton3ActionPerformed(ActionEvent evt) {
		System.out.println("jButton3.actionPerformed, event="+evt);
		//TODO add your code for jButton3.actionPerformed
		try {
			FileWriter writer = new FileWriter("pingjiaduixiang.csv", true);
//			writer.write(new String((weiboID+","+segID+",").getBytes(), "unicode"));
			writer.write(weiboID+","+segID+",");
			System.out.print("start: "+jTextField1.getSelectionStart()+"	");
			System.out.println("end: "+(jTextField1.getSelectionEnd()-1));
//			writer.write(new String((jTextField1.getSelectionStart()+","+(jTextField1.getSelectionEnd()-1)+",").getBytes(), "unicode"));
			writer.write(jTextField1.getSelectionStart()+","+(jTextField1.getSelectionEnd()-1)+",");
//			writer.write(new String(((String)jComboBox1.getSelectedItem()+"\r\n").getBytes(), "unicode"));
			writer.write((String)jComboBox1.getSelectedItem()+"\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Button: Exit
	private void jButton4ActionPerformed(ActionEvent evt) {
		System.out.println("jButton4.actionPerformed, event="+evt);
		//TODO add your code for jButton4.actionPerformed
		try {
			FileWriter writer = new FileWriter("Recent.log");
			writer.write(input_filePath+"\r\n"+weiboID+"\r\n"+segID+"\r\n");
			writer.flush();
			writer.close();
			reader.close();
			gdReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			this.dispose();
		}
		this.dispose();
	}

}
