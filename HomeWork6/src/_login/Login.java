package _login;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Random;
import javax.imageio.ImageIO;




//���������֤��
class VerifyCodeServlet {
//	  VerifyCodeServlet(Random rand){r=rand;};
	 private int wordHeight = 20;
	 private int wordWidth = 30;
	 private int fontSize = 50;
	 private  static final int MAX_CHARCOUNT = 16;
	 private final int initypos = 5;
	 private int charCount = 0;
	 String image;
	 private static final Color[] CHAR_COLOR = {Color.RED,Color.BLUE,Color.GREEN,Color.MAGENTA};
	 private Random r=new Random();
	 public static String GRAPHIC_JPEG = "JPEG";
	 public static String GRAPHIC_PNG = "PNG";
	 protected VerifyCodeServlet(int charCount){
	  this.charCount = charCount;
	 }
	 
	 public static VerifyCodeServlet createInstance(int charCount) throws Exception{
	  if (charCount < 1 || charCount > MAX_CHARCOUNT){
	   throw new Exception("Invalid parameter charCount,charCount should between in 1 and 16");
	  }
	  return new VerifyCodeServlet(charCount);
	 }
	 
	 public String drawNumber(String graphicFormat,OutputStream out) throws IOException{
	//  ������ɵĴ���ֵ
	  String charValue = "";
	  charValue = randNumber();
	  return draw(charValue,graphicFormat,out); 
	 }

	 public String drawAlpha(String graphicFormat,OutputStream out) throws IOException{
	//  ������ɵĴ���ֵ
	  String charValue = "";
	  charValue = randAlpha();
	  return draw(charValue,graphicFormat,out);  
	 }
	 
	 
	 protected String draw(String charValue,String graphicFormat,OutputStream out) throws IOException{ 
	  //����ͼ��Ŀ�Ⱥ͸߶�
	  int w = (charCount+2) * wordWidth;
	  int h = wordHeight * 3;
	  //�����ڴ�ͼ����
	  BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_3BYTE_BGR);
	  Graphics2D g = bi.createGraphics(); 
	  //���ñ���ɫ
	  Color backColor = Color.WHITE;
	  g.setBackground(backColor);
	  g.fillRect(0,0,w,h); 
	  //200��������
	  g.setColor(Color.BLACK);   
	  for (int i = 0; i < 200; i++) {   
	      int x = r.nextInt(w);   
	      int y = r.nextInt(h);   
	      int xl = r.nextInt(12);   
	      int yl = r.nextInt(12);   
	      g.drawLine(x, y, x + xl, y + yl);   
	  }   
	  //����font
	  g.setFont(new Font(null,Font.BOLD,fontSize));
	  //����charValue,ÿ���ַ���ɫ���
	  for(int i = 0; i < charCount; i++){
	   String c = charValue.substring(i,i+1);
	   Color color =  CHAR_COLOR[randomInt(0,CHAR_COLOR.length)];
	   g.setColor(color);
	   int xpos = (i+1) * wordWidth;
	   //��ֱ���������
	   int ypos = randomInt(initypos+wordHeight,initypos+wordHeight*2);
	   g.drawString(c,xpos,ypos);
	  }
	  g.dispose();
	  bi.flush();
	  // �������
	  ImageIO.write(bi,graphicFormat,out); 
	  return charValue;
	  
	 } 
	 protected String randNumber(){
	  String charValue = "";
	  //����������ִ�
	  for (int i = 0; i < charCount; i++){
	   charValue += String.valueOf(randomInt(0,10));
	  }
	  return charValue;
	 }
	 private String randAlpha(){
	  String charValue = "";
	  //���������ĸ��
	  for (int i = 0; i < charCount; i++){
	   char c = (char) (randomInt(0,26)+'a');
	   charValue += String.valueOf(c);
	  }
	  return charValue;
	 }
	 protected int randomInt(int from,int to){
	  return from+r.nextInt(to-from);
	 }
	public void make() throws FileNotFoundException, IOException, Exception
	{
		
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
public class Login extends javax.swing.JFrame {
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPasswordField jPasswordField1;
	private JLabel jLabel5;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JLabel jLabel4;
	private JButton jButton2;
	private JTextField jTextField2;
	private JLabel jLabel3;
	private JTextField jTextField1;
	static String imgnumber;	//���������֤��
	static String name;			//�û���
	static String code;			//����
	static String content;		//����
	static int j=-1;			//��ǵڼ�������
	static String[]  filename;	//��ǰĿ¼���ļ���
	static int length;			//��¼���ٸ�txt�ļ�
	

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Login inst = new Login();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public Login() {
		super();
		initGUI();
	}
	
	//�ö��������ļ�������������Ϣ
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
	    content=temp.$content;
	}
	

	public void jButton2ActionPerform(ActionEvent evt)
	{
				
		File file = new File(".");
		filename  =  file.list(new FilenameFilter()
    	{  
        public  boolean  accept(File  dir,  String  name){  
                    if(name.endsWith(".txt"))  		//ɸѡ��txt���ļ�
                    {  
                       return  true;  
                    }    
                       return  false  ;  
        } 
    	});
		boolean judge=false;
		length=filename.length;
		for (int i=0;i<filename.length;i++)
		{
				Input(filename[i]);
	//			System.out.println(content);
			if (name.equals(jTextField1.getText()))	//�ҵ����û�������û���һ�����ļ�
				{
					judge=true;
					j=i;			
				}
		}
//		System.out.println(jTextField1.getText());
//		System.out.println(j);
		
	//	System.out.println(judge);
		if (judge==false)jLabel5.setText("�޴��û�");	//�Ҳ������û���ͬ�����ļ���˵��û�д��û�
		else 
		{
			Input(filename[j]);
			if (judge&&code.equals(jPasswordField1.getText())&&imgnumber.equals(jTextField2.getText()))
			{
		//		System.out.println(jTextField1.getText());
				if (jTextField1.getText().equals("admin"))//����Ա��½���������Ա����
				{
					Admin admin=new Admin();
					admin.setLocationRelativeTo(null);
					admin.setVisible(true);
					this.setVisible(false);
				}
				else									//��ͨ�û���½��������ͨ�û�����
				{	
					User user=new User();
					user.setLocale(null);
					user.setVisible(true);
					this.setVisible(false);
				}
			}
			else if(!code.equals(jPasswordField1.getText()))	//�������������ʾ
			{
				jLabel5.setText("�������");
			}	
			else  if(!imgnumber.equals(jTextField2.getText()))	//�������֤�������ʾ
			{
				jLabel5.setText("��֤�����");
			}
		}
	}
	
	
	//���ӻ���¼��¼����
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 7, 7};
			thisLayout.columnWeights = new double[] {0.1, 0.0, 0.0, 0.1};
			thisLayout.columnWidths = new int[] {7, 113, 138, 7};
			getContentPane().setLayout(thisLayout);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("\u7528\u6237\u540d");
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText("\u5bc6\u7801");
			}
			{
				jTextField1 = new JTextField();
				getContentPane().add(jTextField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jPasswordField1 = new JPasswordField();
				getContentPane().add(jPasswordField1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel3.setText("\u9a8c\u8bc1\u7801");
			}
			{
				jTextField2 = new JTextField();
				getContentPane().add(jTextField2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				imgnumber=VerifyCodeServlet.createInstance(4).drawAlpha(VerifyCodeServlet.GRAPHIC_PNG,new FileOutputStream("myimg.png"));
				Image image = Toolkit.getDefaultToolkit().getImage("myimg.png");
				Icon icon = new ImageIcon(image);
				jLabel4 = new JLabel();
//				System.out.println(imgnumber);
				jLabel4.setIcon(icon);
				getContentPane().add(jLabel4, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton2.setText("\u767b\u9646");
				jButton2.setPreferredSize(new java.awt.Dimension(61, 22));
				jButton2.setMnemonic(KeyEvent.VK_ENTER);
				jButton2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton2ActionPerform(evt);
						
					}
				});
			}
			{
				jLabel5 = new JLabel();
				getContentPane().add(jLabel5, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jLabel6 = new JLabel();
				getContentPane().add(jLabel6, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jLabel6.setText("\u7ba1\u7406\u5458\u7528\u6237\u540d:admin");
			}
			{
				jLabel7 = new JLabel();
				getContentPane().add(jLabel7, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel7.setText("\u5bc6\u7801:1234");
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
}
class UserInfo implements Serializable	//Ϊ�������������û���Ϣ��
{
	UserInfo(String name,String code,String content)
	{
		$name=name;
		$code=code;
		$content=content;
	}
	String $name;
	String $code;
	String $content;
}

