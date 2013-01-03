package _login;
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
import javax.swing.JTextField;
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
public class User extends javax.swing.JFrame {
	private JScrollPane jScrollPane1;
	private JButton jButton1;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JButton jButton5;
	private JLabel jLabel1;
	private JButton jButton4;
	private JTextField jTextField1;
	private JButton jButton3;
	private JTextPane jTextPane1;
	String[] filename;			//文件名数组
	int j;						
	String name;				//用户名
	String code;				//密码
	String content;				//内容

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				User inst = new User();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	
	public User() {
		super();
		initGUI();
	}
	
	//按下修改按钮，设置用户可编辑
	public void jButton1ActionPerform(ActionEvent evt)
	{
		jTextPane1.setEditable(true);
	}
	
	//监听保存按钮，将用户修改后的信息输出到文件中
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
	
	//监听修改密码按钮，设置新密码框可输入
	public void jButton4ActionPerform(ActionEvent evt)
	{
		jLabel1.setEnabled(true);
		jTextField1.setEnabled(true);
	}
	//确认修改新密码
	public void jButton5ActionPerform(ActionEvent evt)
	{
		code=jTextField1.getText();
		jLabel4.setText(code);
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
	
	//可视化图形界面
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 20, 7, 7, 20};
			thisLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.1};
			thisLayout.columnWidths = new int[] {107, 127, 171, 7};
			getContentPane().setLayout(thisLayout);
			{
				jScrollPane1 = new JScrollPane(jTextPane1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				getContentPane().add(jScrollPane1, new GridBagConstraints(0, 0, 3, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				{
					jTextPane1 = new JTextPane();
					jTextPane1.setEditable(false);
					jScrollPane1.setViewportView(jTextPane1);
				}
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("\u4fee\u6539");
				jButton1.setPreferredSize(new java.awt.Dimension(69, 22));
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
				jButton3.setPreferredSize(new java.awt.Dimension(61, 22));
				jButton3.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton3ActionPerform(evt);
						
					}
				});
			}
			{
				jTextField1 = new JTextField();
				jTextField1.setEnabled(false);
				getContentPane().add(jTextField1, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				Login log=new Login();
				filename=log.filename;
				j=log.j;
				name=log.name;
				code=log.code;
				content=log.content;
				jButton4 = new JButton();
				getContentPane().add(jButton4, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton4.setText("\u4fee\u6539\u5bc6\u7801");
				jButton4.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton4ActionPerform(evt);
						
					}
				});
			}
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("\u65b0\u5bc6\u7801\uff1a");
				jLabel1.setEnabled(false);
			}
			{
				jButton5 = new JButton();
				getContentPane().add(jButton5, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton5.setText("\u786e\u8ba4");
				jButton5.setPreferredSize(new java.awt.Dimension(64, 22));
				jButton5.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton5ActionPerform(evt);
						
					}
				});
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText("\u666e\u901a\u7528\u6237");
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jLabel3.setText(name);
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jLabel4.setText(code);
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
