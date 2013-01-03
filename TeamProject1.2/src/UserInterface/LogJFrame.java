package UserInterface;
import infoManage.*;
import UserInterface.MainJFrame.*;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import java.io.FileOutputStream;



import javax.imageio.ImageIO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Random;


import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;


//生成验证码的类
class VerCodePro {
	public static String CHECK=""; //生成的验证码
	 private int wordHeight = 20;
	 private int wordWidth = 20;
	 private int fontSize = 40;
	 private  static final int MAX_CHARCOUNT = 16;
	 private final int initypos = 15;
	 private int charCount = 0;
	 private static final Color[] CHAR_COLOR = {Color.RED,Color.orange,Color.GREEN,Color.pink};
	 private Random r = new Random();
	 public static String GRAPHIC_JPEG = "JPEG";
	 public static String GRAPHIC_PNG = "PNG";
	 protected VerCodePro(int charCount){
	  this.charCount = charCount;
	 }
	 //返回一个对象
	 public static VerCodePro createInstance(int charCount) throws Exception{
	  if (charCount < 1 || charCount > MAX_CHARCOUNT){
	   throw new Exception("Invalid parameter charCount,charCount should between in 1 and 16");
	  }
	  return new VerCodePro(charCount);
	 }
	 //根据随机生成的数字串产生验证码
	 public BufferedImage drawNumber(String graphicFormat,OutputStream out,String s) throws IOException{
	  String charValue = "";
	  charValue = randNumber();
	  CHECK=charValue;
	  return draw(charValue,graphicFormat,out); 
	 }
// //根据随机生成的字母串产生验证码
	 public BufferedImage drawAlpha(String graphicFormat,OutputStream out,String s) throws IOException{
	  String charValue = "";
	  charValue = randAlpha();
	  CHECK=charValue;
	  return draw(charValue,graphicFormat,out);  
	 }
	 
	 //根据字符串的值产生验证码
	 protected BufferedImage draw(String charValue,String graphicFormat,OutputStream out) throws IOException{ 
	  //计算图像的宽度和高度
	  int w = (charCount+2) * wordWidth;
	  int h = wordHeight * 3;
	  BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_3BYTE_BGR);
	  Graphics2D g = bi.createGraphics(); 
	  //设置背景色
	  Color backColor = Color.yellow;
	  g.setBackground(backColor);
	  g.fillRect(0,0,w,h); 
	  //产生1000条不同颜色长度干扰线
	  g.setColor(Color.BLACK);   
	  for (int i = 0; i < 300; i++) { 
		  if(i>150)
			  g.setColor(Color.yellow);
	      int x = r.nextInt(w);   
	      int y = r.nextInt(h);   
	      int xl = r.nextInt(12);   
	      int yl = r.nextInt(12);   
	      g.drawLine(x, y, x + xl, y + yl);   
	  }   
	  //设置字体
	  g.setFont(new Font("TimesRoman",Font.BOLD,fontSize));
	  //绘制charValue,每个字符颜色随机
	  for(int i = 0; i < charCount; i++){
	   String c = charValue.substring(i,i+1);
	   Color color =  CHAR_COLOR[randomInt(0,CHAR_COLOR.length)];
	   g.setColor(color);
	   int xpos = (i+1) * wordWidth;
	   int ypos = randomInt(initypos+wordHeight,initypos+wordHeight*2);
	   g.drawString(c,xpos,ypos);
	  }
	  g.dispose();
	  bi.flush();
	  // 输出到流
	  ImageIO.write(bi,graphicFormat,out); 
	  return bi;
	 } 
	 protected String randNumber(){
	  String charValue = "";
	  //生成随机数字串
	  for (int i = 0; i < charCount; i++){
	   charValue += String.valueOf(randomInt(0,10));
	  }
	  return charValue;
	 }
	 private String randAlpha(){
	  String charValue = "";
	  //生成随机字母串
	  for (int i = 0; i < charCount; i++){
	   char c = (char) (randomInt(0,26)+'a');
	   charValue += String.valueOf(c);
	  }
	  return charValue;
	 }
	 protected int randomInt(int from,int to){
	  return from+r.nextInt(to-from);
	 }
}
//用户对象，对象流使用
class User implements Serializable
{
	String userName="";
	String password="";

	User(String userName,String password)
	{
		this.userName=userName;
		this.password=password;
	}
}
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
public class LogJFrame extends javax.swing.JFrame {
	
	private JPanel jPanel1;
	private JLabel jLabel3;
	private JButton jButton1;
	private JLabel jLabel5;
	private JButton jButton3;
	private JPasswordField jPasswordField1;
	private JOptionPane jOptionPane1;
	private JButton jButton2;
	private JLabel jLabel4;
	private JComboBox jComboBox1;
	private JTextField jTextField3;
	private JTextField jTextField1;
	private JLabel jLabel2;
	private JLabel jLabel1;
	MainJFrame mai;
	
	static String check="";//当前的验证码


	/**
	* Auto-generated main method to display this JFrame
	 * @throws IOException 
	*/
	public static void main(String[] args)  {
		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LogJFrame inst = new LogJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public LogJFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				GridBagLayout jPanel1Layout = new GridBagLayout();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.1,0.1};
				jPanel1Layout.rowHeights = new int[] {97, 41, 44, 41, 41,41 ,7, 7};
				jPanel1Layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.1, 0.1};
				jPanel1Layout.columnWidths = new int[] {76, 78, 125, 7, 7};
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setPreferredSize(new java.awt.Dimension(492, 446));
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText("\u7528\u6237\u540d\uff1a");
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel2.setText("\u5bc6\u7801\uff1a");
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel3.setText("\u9a8c\u8bc1\u7801\uff1a");
				}
				{
					jTextField3 = new JTextField();
					jPanel1.add(jTextField3, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					ComboBoxModel jComboBox1Model = 
						new DefaultComboBoxModel(
								new String[] { "普通用户", "管理员","超级管理员" });
					jComboBox1 = new JComboBox();
					jPanel1.add(jComboBox1, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jComboBox1.setModel(jComboBox1Model);
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jButton1.setText("\u767b\u9646");
					class Button1Listener implements ActionListener//为登录按钮添加监听器和事件处理程序
					{
						public void actionPerformed(ActionEvent e)
						{
							//获得用户输入的信息
							int type= jComboBox1.getSelectedIndex();
							int id=Integer.parseInt(jTextField1.getText());
			
							@SuppressWarnings("deprecation")
							String pass=jPasswordField1.getText();
							String check1=jTextField3.getText();
							check1.trim();
				
                            UserInfoList userL=new UserInfoList();
                            
							
						    if(userL.getUserID(id)==null||userL.getUserType(id)!=type)  JOptionPane.showMessageDialog(null,"无此用户，登录失败,重新输入");//用户不存在
						    else
						    {
						    	if(!(check.equals(check1)))
						    		JOptionPane.showMessageDialog(null,"验证码错误,登录失败，重新输入" );
						    	else
						    	{
						    		
						    		if(!(userL.checkPassword(id,pass)))
						    				JOptionPane.showMessageDialog(null,"密码错误,登录失败，重新输入" );
						    		else
						    		{
						    			mai=new MainJFrame(Integer.parseInt(userL.getUserID(id)));
						    		    mai.setVisible(true);
						    		    LogJFrame.this.setVisible(false);
						    		}
						    			
						    	}
						    }
						    
						    
						    

						
						}
					}
					jButton1.addActionListener(new Button1Listener());
				}
				{
					jLabel4 = new JLabel();
					jPanel1.add(jLabel4, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel4.setText("\u767b\u5f55\u7cfb\u7edf");
				}
				{
					jLabel5 = new JLabel();
					jPanel1.add(jLabel5, new GridBagConstraints(3, 3, 2, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					jButton2 = new JButton();
					jPanel1.add(jButton2, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jButton2.setText("\u6362\u4e00\u5f20");
					//为验证码更换按钮增添监听器和事件处理程序
					jButton2.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							BufferedImage b = null;
							try {
								VerCodePro dr=null;
								Random rand=new Random();
							    int i=rand.nextInt(2);
							    //随机产生数字或字母验证码
								if(i==0){dr=VerCodePro.createInstance(4);
								b=dr.drawAlpha(VerCodePro.GRAPHIC_PNG,new FileOutputStream("myimg.png"),check);
								check=dr.CHECK;
								}
								else {
									dr=VerCodePro.createInstance(4);b=dr.drawNumber(VerCodePro.GRAPHIC_PNG,new FileOutputStream("myimg.png"),check);
								check=dr.CHECK;
								}
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							jLabel5.setIcon(new ImageIcon(b));
						}
					});
				}
				
				{//密码输入框
					jPasswordField1 = new JPasswordField();
					jPanel1.add(jPasswordField1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					jTextField1 = new JTextField();
					jPanel1.add(jTextField1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					jButton3 = new JButton();
					jPanel1.add(jButton3, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jButton3.setText("\u5e2e\u52a9");
					jButton3.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							JOptionPane.showMessageDialog(null,"最初运行以管理员登陆：用户名adm,密码123456；以普通用户登录，用户名gUser,密码123456" );
						}});
				}
			}
			//产生随机的数字或字母验证码
			BufferedImage a;
			VerCodePro dr=null;
			Random rand=new Random();
		    int i=rand.nextInt(2);
			if(i==0){dr=VerCodePro.createInstance(4);
			a=dr.drawAlpha(VerCodePro.GRAPHIC_PNG,new FileOutputStream("myimg.png"),check);
			check=dr.CHECK;
			}
			else {
				dr=VerCodePro.createInstance(4);a=dr.drawNumber(VerCodePro.GRAPHIC_PNG,new FileOutputStream("myimg.png"),check);
			check=dr.CHECK;
			}
			jLabel5.setIcon(new ImageIcon(a));
			pack();
			this.setSize(500, 479);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
