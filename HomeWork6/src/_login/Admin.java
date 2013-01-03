package _login;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

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
public class Admin extends javax.swing.JFrame {
	private JScrollPane jScrollPane1;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JButton jButton1;
	private JLabel jLabel1;
	private JTextPane jTextPane1;
	private JButton jButton4;
	private JButton jButton3;
	String[] filename;	//当前目录下文件名
	int j;
	String name;		//用户名
	String code;		//密码
	String content;		//内容
	private JLabel jLabel3;
	private JLabel jLabel2;
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Admin inst = new Admin();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public Admin() {
		super();
		initGUI();
	}
	
	//监听保存按钮把信息以对象流输出到文件
	public void jButton3ActionPerform(ActionEvent evt)
	{
		UserInfo admin=new UserInfo(name,code,jTextPane1.getText());
		try  {
			FileOutputStream fo = new FileOutputStream(name+".txt");
			ObjectOutputStream so = new ObjectOutputStream(fo);
			so.writeObject(admin);
			so.close();
	//		jLabel4.setText("添加成功");
			}
		catch(Exception e) 
			{
			System.err.println(e);
			}
	}
	
	//监听修改按钮使得用户可编辑
	public void jButton1ActionPerform(ActionEvent evt)
	{
		jTextPane1.setEditable(true);
	}
	
	//监听添加用户按钮进入到添加用户界面
	public void jButton4ActionPerform(ActionEvent evt)
	{
		AddUser add=new AddUser();
		add.setLocationRelativeTo(null);
		add.setVisible(true);
	//	this.setVisible(false);
	}
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.1, 0.0, 0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {20, 45, 20, 7, 7, 7, 7};
			thisLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.1};
			thisLayout.columnWidths = new int[] {124, 134, 77, 7};
			getContentPane().setLayout(thisLayout);
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("\u4fee\u6539");
				jButton1.setPreferredSize(new java.awt.Dimension(72, 22));
				jButton1.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton1ActionPerform(evt);
						
					}
				});
			}
			{				
				jButton3 = new JButton();
				getContentPane().add(jButton3, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton3.setText("\u4fdd\u5b58");
				jButton3.setPreferredSize(new java.awt.Dimension(75, 22));
				jButton3.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton3ActionPerform(evt);
						
					}
				});
			}
			{
				Login log=new Login();
				filename=log.filename;
				j=log.j;
				name=log.name;
				code=log.code;
				content=log.content;
	//			System.out.println(log.content);
	//			System.out.println(content);
				jButton4 = new JButton();
				getContentPane().add(jButton4, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton4.setText("\u6dfb\u52a0\u7528\u6237");
				jButton4.setPreferredSize(new java.awt.Dimension(87, 22));
				jButton4.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton4ActionPerform(evt);
						
					}
				});
			}
			{
				
				jScrollPane1 = new JScrollPane(jTextPane1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				getContentPane().add(jScrollPane1, new GridBagConstraints(0, 0, 3, 7, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				{
					jTextPane1 = new JTextPane();
					jScrollPane1.setViewportView(jTextPane1);
					jTextPane1.setText(content);
					jTextPane1.setPreferredSize(new java.awt.Dimension(328, 361));
					jTextPane1.setEditable(false);
				}
			}
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("\u7ba1\u7406\u5458");
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText(name);
			}
			{
				jLabel3 = new JLabel();
		//		jLabel3.setText(name);
				getContentPane().add(jLabel3, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				jLabel3.setText(code);
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jLabel4.setText("Name：" +name);
			}
			{
				jLabel5 = new JLabel();
				getContentPane().add(jLabel5, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jLabel5.setText("Code： "+code);
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void jButton2ActionPerformed(ActionEvent evt) {
		System.out.println("jButton2.actionPerformed, event="+evt);
		//TODO add your code for jButton2.actionPerformed
	}

}
