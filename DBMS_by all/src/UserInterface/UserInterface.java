package UserInterface;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import DriverManager.DriverManager;

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
public class UserInterface extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public File importfile;
	private DriverManager dm;
	private static String show;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel jPanel;
	private JScrollPane jScrollPane1;
	private JButton jButton1;
	private JScrollPane jScrollPane2;
	private JTextPane jTextPane1;
	private JPanel jPanel2;
	private JPanel jPanel1;
	private JButton jButton3;
	private JButton jButton2;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UserInterface inst = new UserInterface();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public UserInterface() {
		super();
		importfile = null;
		show = "";
		initGUI();
		// 启动DriverManager
		dm = new DriverManager();
	}

	public static void changeshow(String str) {
		show = show + "\n" + str;
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel = new JPanel();
				getContentPane().add(jPanel, BorderLayout.CENTER);
				TableLayout jPanelLayout = new TableLayout(new double[][] {
						{ 251.0, 244.0, 153.0, TableLayoutConstants.FILL },
						{ 36.0, 121.0, 58.0, TableLayoutConstants.FILL } });
				jPanelLayout.setHGap(5);
				jPanelLayout.setVGap(5);
				jPanel.setLayout(jPanelLayout);
				jPanel.setPreferredSize(new java.awt.Dimension(610, 472));
				{
					jScrollPane1 = new JScrollPane();
					jPanel.add(jScrollPane1, "0, 1, 1, 3");
					{
						jScrollPane2 = new JScrollPane();
						jScrollPane1.setViewportView(jScrollPane2);
						{
							StyleContext context = new StyleContext();// 实例化一个样式池
							StyledDocument document = new DefaultStyledDocument(
									context);// 创建有样式的文档实例
							Style style = context
									.getStyle(StyleContext.DEFAULT_STYLE);// 从样式池获取默认的样式

							jTextPane1 = new JTextPane();
							jScrollPane2.setViewportView(jTextPane1);
							jTextPane1.setStyledDocument(document);

							StyleConstants.setFontSize(style, 20);// 设置字体大小

							/*
							 * StyleConstants.setFontFamily(style, "宋体");// 设置字体
							 * StyleConstants.setForeground(style,
							 * Color.BLACK);// 设置颜色
							 * StyleConstants.setBold(style, false);// 取消加粗
							 * StyleConstants.setBold(style, true);// 设置加粗
							 * StyleConstants.setItalic(style, false);// 取消斜体
							 * StyleConstants.setItalic(style, true);// 设置斜体
							 * StyleConstants.setUnderline(style, false);//
							 * 取消下划线 StyleConstants.setUnderline(style, true);//
							 * 设置下划线
							 */
						}
					}
				}
				{
					jButton3 = new JButton();
					jPanel.add(jButton3, "2, 2");
					jButton3.setText("Import");
					jButton3.addActionListener(new Button3Act());

				}
				{
					jPanel1 = new JPanel();
					TableLayout jPanel1Layout = new TableLayout(new double[][] {
							{ TableLayoutConstants.FILL,
									TableLayoutConstants.FILL,
									TableLayoutConstants.FILL,
									TableLayoutConstants.FILL },
							{ TableLayoutConstants.FILL,
									TableLayoutConstants.FILL,
									TableLayoutConstants.FILL,
									TableLayoutConstants.FILL } });
					jPanel1Layout.setHGap(5);
					jPanel1Layout.setVGap(5);
					jPanel1.setLayout(jPanel1Layout);
					jPanel.add(jPanel1, "2, 1");
					{
						jButton2 = new JButton();
						jPanel1.add(jButton2, "0, 0, 3, 1");
						jButton2.setText("Execute");
						jButton2.addActionListener(new Button2Act());
					}
				}
				{
					jPanel2 = new JPanel();
					TableLayout jPanel2Layout = new TableLayout(new double[][] {
							{ TableLayoutConstants.FILL,
									TableLayoutConstants.FILL,
									TableLayoutConstants.FILL,
									TableLayoutConstants.FILL },
							{ TableLayoutConstants.FILL,
									TableLayoutConstants.FILL,
									TableLayoutConstants.FILL,
									TableLayoutConstants.FILL } });
					jPanel2Layout.setHGap(5);
					jPanel2Layout.setVGap(5);
					jPanel2.setLayout(jPanel2Layout);
					jPanel.add(jPanel2, "2, 3");
					{
						jButton1 = new JButton();
						jPanel2.add(jButton1, "0, 2, 3, 2");
						jButton1.setText("Exit");
						jButton1.addActionListener(new Button1Act());
					}
				}
			}
			pack();
			this.setSize(734, 560);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	class Button3Act implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			FileChooser filechooser = new FileChooser();
			if (filechooser.file != null) {
				importfile = filechooser.file;
				String content = dm.openFile(importfile.getAbsolutePath(),
						"utf-8");
				jTextPane1.setText(content);
			}
		}
	}

	class Button2Act implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String test = jTextPane1.getText();
			if (test.length() != 0) {
				dm.runSqlList(test);
				ResultShow inst = new ResultShow(show);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				// Do Text SQL

			} else if (importfile != null) {
				// Do importfile SQL
				dm.runSqlFile(importfile.getAbsolutePath());
			} else {
				// Error
			}

		}
	}
}

class Button1Act implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}
}
