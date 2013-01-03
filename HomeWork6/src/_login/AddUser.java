package _login;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
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
public class AddUser extends javax.swing.JFrame {
	private JLabel jLabel1;
	private JTextField jTextField1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JButton jButton1;
	private JLabel jLabel4;
	private JTextField jTextField2;
	String[] filename;
	int j;
	String name;
	String code;
	String content;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AddUser inst = new AddUser();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	
	public void Input(String filename)
	{
		UserInfo temp=null;
		try {
			FileInputStream fi = new FileInputStream(filename);
			ObjectInputStream si = new ObjectInputStream(fi);
			temp = (UserInfo)si.readObject();
			si.close();
	    	} catch(Exception e) 
	    	{
	    		System.out.println(e);
	    	}
	    name=temp.$name;
	    code=temp.$code;
	    content=temp.$content;
	}
	
	public AddUser() {
		super();
		initGUI();
	}
	//监听确定按钮确定添加新用户
	public void jButton1ActionPerform(ActionEvent evt){
	//	jTextField1.getText();
		File file = new File(".");
		filename  =  file.list(new FilenameFilter()
    	{  
        public  boolean  accept(File  dir,  String  name){  
                    if(name.endsWith(".txt"))  //以对象流输入文件名
                    {  
                       return  true;  
                    }    
                       return  false  ;  
        } 
    	});
		boolean mark=false;
		for (int i=0;i<filename.length;i++)
		{
			Input(filename[i]);
			if (name.equals(jTextField1.getText()))	//判断是否已经有重名用户存在
			{
				mark=true;
			}
		}
		if (mark)jLabel4.setText("此用户已存在");
		else		//不存在同名用户，以对象流把信息输出到以用户名命名的文件中储存
		{
		UserInfo u=new UserInfo(jTextField1.getText(),jTextField2.getText(),"");
		if(!jTextField1.getText().isEmpty()&&!jTextField2.getText().isEmpty())
		{
		try  {
			FileOutputStream fo = new FileOutputStream(jTextField1.getText()+".txt");
			ObjectOutputStream so = new ObjectOutputStream(fo);
			so.writeObject(u);
			so.close();
			jLabel4.setText("添加成功");
			}
		catch(Exception e) 
			{
			System.err.println(e);
			}
		}
		//用户名或者密码输入为空提示错误
		else jLabel4.setText("请输入用户名和密码");
		}
	}
	
	//添加用户可视化界面
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 7, 7};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {7, 7, 7, 7};
			getContentPane().setLayout(thisLayout);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("\u7528\u6237\u540d");
			}
			{
				jTextField1 = new JTextField();
				getContentPane().add(jTextField1, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText("\u5bc6\u7801");
			}
			{
				jTextField2 = new JTextField();
				getContentPane().add(jTextField2, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel3.setText("\u6dfb\u52a0\u7528\u6237");
			}
			{
	//			Login login=new Login();
	//			filename=login.filename;
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("\u786e\u8ba4");
				jButton1.setPreferredSize(new java.awt.Dimension(62, 22));
				jButton1.addActionListener(new ActionListener(){		//监听jButton1动作
					public void actionPerformed(ActionEvent evt){
						jButton1ActionPerform(evt);
						
					}
				});
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
