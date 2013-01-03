package edu.sjtu.lt_lab.manualMarkUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;

import edu.sjtu.lt_lab.mark.ReadAndWriteWeibo;


/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class Weibo extends javax.swing.JFrame {
	private JPanel jPanel;
	private JButton Next;
	private JTextPane jTextPane1;
	private JPanel jPanel1;
	private JTable jTable;
	private JScrollPane jScrollPane;
	private JLabel jLabel1;
	private JTextPane jTextPane2;
	private JPanel jPanel2;

	private JButton Save;

	private String readpath = "E:/workplace/newData/temp_金正日_outWithFeatures.txt";
	private String writePath = "E:/workplace/newData/金正日_crf.txt";
	private ReadAndWriteWeibo $weibo;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Weibo inst = new Weibo();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public Weibo() {
		super();
		

		long point = 0;
		String data = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("point.txt")));
			while ((data = br.readLine()) != null) {
				point = Long.parseLong(data);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(point);
		$weibo = new ReadAndWriteWeibo(readpath, writePath, point);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout(1, 1);
			thisLayout.setColumns(1);
			thisLayout.setHgap(5);
			thisLayout.setVgap(5);
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel = new JPanel();
				GridBagLayout jPanelLayout = new GridBagLayout();
				jPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.1 };
				jPanelLayout.rowHeights = new int[] { 168, 127, 104, 7 };
				jPanelLayout.columnWeights = new double[] { 0.1, 0.0, 0.0, 0.1 };
				jPanelLayout.columnWidths = new int[] { 7, 533, 102, 7 };
				jPanel.setLayout(jPanelLayout);
				getContentPane().add(jPanel);
				jPanel.setPreferredSize(new java.awt.Dimension(778, 428));
				{
					Next = new JButton();
					jPanel.add(Next, new GridBagConstraints(2, 3, 1, 1, 0.0,
							0.0, GridBagConstraints.EAST,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,
									0), 0, 0));
					Next.setText("Next");
					Next.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent evt) {
							NextActionPerformed(evt);
						}
					});

				}
				{
					Save = new JButton();
					jPanel.add(Save, new GridBagConstraints(3, 3, 1, 1, 0.0,
							0.0, GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,
									0), 0, 0));
					Save.setText("Save");
					Save.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent evt) {
							SaveActionPerformed(evt);
						}
					});
				}
				$weibo.ReadNext();

				String weibo[] = $weibo.getWords();
				String pos[] = $weibo.getPos();
				String emotion[] = $weibo.getEmotion();
				String wordStructure[] = $weibo.getWordStructure();
				String sentStructure[]= $weibo.getSentenceStructure();

				int wordCount = $weibo.getWordCount();
				String input[] = new String[wordCount];
				String column[] = new String[wordCount];
				for (int i = 0; i < wordCount; i++) {
					column[i] = (i + 1) + "";
				}
				{
					jScrollPane = new JScrollPane(jTable);
					jScrollPane
							.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					jPanel.add(jScrollPane, new GridBagConstraints(0, 0, 4, 1,
							0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
							0));
					{
						TableModel jTableModel = new DefaultTableModel(
								new Object[][] { weibo, pos, emotion,
										wordStructure,sentStructure, input }, column);
						jTable = new JTable();
						jScrollPane.setViewportView(jTable);

						jTable.setModel(jTableModel);

						jTable.addKeyListener(new KeyListener() {
							@Override
							public void keyTyped(KeyEvent e) {
								if (e.getKeyChar() == '\n') {
									Next.doClick();
								}
							}

							@Override
							public void keyPressed(KeyEvent e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void keyReleased(KeyEvent e) {
								// TODO Auto-generated method stub

							}
						});

						fitTableColumns(jTable);
						makeFace(jTable);
					}

				}
				{
					jPanel1 = new JPanel();
					jPanel.add(jPanel1, new GridBagConstraints(0, 2, 3, 1, 0.0,
							0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
							0));
					{
						jTextPane1 = new JTextPane();
						jPanel1.add(jTextPane1);
						jTextPane1.setText($weibo.reduction());
						jTextPane1.setPreferredSize(new java.awt.Dimension(680,
								104));
					}
				}
				{
					jPanel2 = new JPanel();
					jPanel.add(jPanel2, new GridBagConstraints(0, 1, 4, 1, 0.0,
							0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
							0));
					{
						jTextPane2 = new JTextPane();
						jPanel2.add(jTextPane2);
						jTextPane2
								.setText("1:B 评论词前缀\r2:I 评论词中间\r3:E 评论词后缀\r4:S 单独评论词");
						jTextPane2.setPreferredSize(new java.awt.Dimension(747,
								115));
					}
				}
				{
					jLabel1 = new JLabel();
					jPanel.add(jLabel1, new GridBagConstraints(3, 2, 1, 1, 0.0,
							0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0,
							0));
					jLabel1.setText("");
				}

			}
			pack();
			this.setSize(794, 466);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	private void NextActionPerformed(ActionEvent evt) {
		// System.out.println("Next.actionPerformed, event="+evt);
		System.out.println($weibo.getCurrentPoint());
		FileWriter fw;
		try {
			fw = new FileWriter("point.txt");
			fw.write($weibo.getCurrentPoint() + "");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		storeWeibo();
		updateTable();
		jTextPane1.setText($weibo.reduction());
		jLabel1.setText("Next");

		JScrollBar jScrollBar = jScrollPane.getHorizontalScrollBar();
		if (jScrollBar != null) {
			jScrollBar.setValue(jScrollBar.getMinimum());
		}
	}

	private void SaveActionPerformed(ActionEvent evt) {
		// System.out.println("Save.actionPerformed, event="+evt);
		System.out.println($weibo.getCurrentPoint());
		FileWriter fw;
		try {
			fw = new FileWriter("point.txt");
			fw.write($weibo.getCurrentPoint() + "");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		storeWeibo();
		jLabel1.setText("Save");
	}

	public void storeWeibo() {
		String newWeibo = "";
		String mark = "o";

		for (int i = 0; i < $weibo.getWordCount(); i++) {
			if (jTable.getValueAt(5, i) == null
					|| jTable.getValueAt(5, i).equals("")) {
				mark = "o";
			} else if (jTable.getValueAt(5, i).equals("1")) {
				mark = "B";
			} else if (jTable.getValueAt(5, i).equals("2")) {
				mark = "I";
			} else if (jTable.getValueAt(5, i).equals("3")) {
				mark = "E";
			} else if (jTable.getValueAt(5, i).equals("4")) {
				mark = "S";
			} else {
				mark = "o";
			}
			/*
			 * if (jTable.getValueAt(2, i) == null || jTable.getValueAt(2,
			 * i).equals("")) { mark = "o"; } else if (jTable.getValueAt(2,
			 * i).toString().equals("1")) { mark = "Wi_arg1"; } else if
			 * (jTable.getValueAt(2, i).equals("2")) { mark = "Wi_arg2"; } else
			 * if (jTable.getValueAt(2, i).equals("3")) { mark = "Wi_it_arg1"; }
			 * else if (jTable.getValueAt(2, i).equals("4")) { mark = "Wi_arg1";
			 * } else if (jTable.getValueAt(2, i).equals("5")) { mark =
			 * "Wi_arg"; } else if (jTable.getValueAt(2, i).equals("6")) { mark
			 * = "Wi_cp_arg1"; } else if (jTable.getValueAt(2, i).equals("7")) {
			 * mark = "arg1_v_Wi"; } else if (jTable.getValueAt(2,
			 * i).equals("`1")) { mark = "neg_Wi_arg1"; } else if
			 * (jTable.getValueAt(2, i).equals("`2")) { mark = "neg_Wi_arg2"; }
			 * else if (jTable.getValueAt(2, i).equals("`3")) { mark =
			 * "neg_Wi_it_arg1"; } else if (jTable.getValueAt(2,
			 * i).equals("`4")) { mark = "neg_Wi_arg1"; } else if
			 * (jTable.getValueAt(2, i).equals("`5")) { mark = "neg_Wi_arg"; }
			 * else if (jTable.getValueAt(2, i).equals("`6")) { mark =
			 * "neg_Wi_cp_arg1"; } else if (jTable.getValueAt(2,
			 * i).equals("`7")) { mark = "neg_arg1_v_Wi"; } else { mark = "o"; }
			 */
/*			newWeibo = newWeibo + $weibo.getEachLine()[i] + " " + mark
					+ "\r";
*/			 newWeibo += $weibo.getWords()[i] + " " + $weibo.getPos()[i] + " " + $weibo.getEmotion()[i] + " "
				+ $weibo.getWordStructure()[i] + " " + $weibo.getSentenceStructure()[i] + " "
				+ mark+"\r";
		}
		System.out.print(newWeibo + "\r");
		try {
			writeFile(newWeibo + "\r");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateTable() {
		try {
			$weibo.ReadNext();
			// System.out.println($weibo.getWeibo());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String weibo[] = $weibo.getWords();
		String pos[] = $weibo.getPos();
		String emotion[] = $weibo.getEmotion();
		String wordStructure[] = $weibo.getWordStructure();
		String sentStructure[]= $weibo.getSentenceStructure();
		
		int wordCount = $weibo.getWordCount();
		String input[] = new String[wordCount];
		input[0] = "";
		for (int i = 1; i < wordCount; i++) {
			if ((pos[i - 1].equals("tar") || weibo[i - 1].startsWith("我"))
					&& pos[i].startsWith("a")) {
				input[i] = "4";
			} else {
				input[i] = "";
			}
		}
		String column[] = new String[wordCount];
		for (int i = 0; i < wordCount; i++) {
			column[i] = (i + 1) + "";
		}
		TableModel jTableModel = new DefaultTableModel(new Object[][] { weibo,
				pos, emotion, wordStructure,sentStructure, input }, column);
		jTable.setModel(jTableModel);
		makeFace(jTable);
	}

	public void fitTableColumns(JTable myTable) {
		myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int columnCount = myTable.getColumnCount();
		int rowCount = myTable.getRowCount();

		int totalWidth = 0;
		for (int col = 0; col < columnCount; col++) {
			int width = myTable.getColumnModel().getColumn(col)
					.getPreferredWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable
						.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable,
								myTable.getValueAt(row, col), false, false,
								row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			TableColumn column = myTable.getColumnModel().getColumn(col);
			myTable.getTableHeader().setResizingColumn(column); 
																// column.setWidth(width
																// +
																// myTable.getIntercellSpacing().width
																// * 2);
			totalWidth += width + myTable.getIntercellSpacing().width * 2;
		}
		if (myTable.getParent() == null) {
			return;
		}
		
		if (totalWidth < myTable.getParent().getPreferredSize().width) {
			myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
		return;
	}

	public void makeFace(JTable table) {

		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					if (table.getValueAt(1, column).equals("tar")
							|| table.getValueAt(0, column).toString()
									.startsWith("我")) {
						setBackground(Color.red);
					} else {
						setBackground(new Color(206, 231, 255));
					}
					return super.getTableCellRendererComponent(table, value,
							isSelected, hasFocus, row, column);
				}
			};
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void writeFile(String content) throws IOException {
		FileWriter fw = new FileWriter(
				writePath, true);
		fw.write(content);
		fw.flush();
		fw.close();

	}
}
