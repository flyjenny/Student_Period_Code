package _calendar;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.ButtonGroup;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

//import _test.Student;



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


class MyCalendarInfo implements Serializable	//����һ������û�����������Ϣ�Զ�����������ļ��д��档
{
	MyCalendarInfo(String them,String conten,String dat,String tim,int  priorit,
					boolean remin,int comboindex1,int comboindex2,boolean repea,
					int mod,String repeatStartDat,int repeatmod,int repeattime,
					String repeatEndDat,String searchDat)
					{
						theme=them;content=conten;date=dat;time=tim;priority=priorit;
						remind=remin;comboBoxindex1=comboindex1;comboBoxindex2=comboindex2;
						repeat=repea;mode=mod;repeatStartDate=repeatStartDat;repeatmode=repeatmod;
						repeattimes=repeattime;repeatEndDate=repeatEndDat;searchDate=searchDat;
					}
	String theme;		// �ճ�����
	String content;		//�ճ�����
	String date;		//�ճ���������
	String time;//		�ճ�����ʱ��
	int  priority; //	�ճ����ȼ�
	boolean remind;//	�Ƿ�����
	int comboBoxindex1;//ѡ�����ѵĵ�λ����Сʱ�����ߡ����ӡ�
	int comboBoxindex2;//�����ѵ�λǰ����ѡ��������Сʱ��Ӧ��0~23�������Ӷ�Ӧ��0~59��
	boolean repeat;//	//�����Ƿ��ظ�
	int mode;//			�����ظ���ʱ�䣬ÿ�졢ÿ�ܡ�ÿ�»���û��
	String repeatStartDate;//�����ظ���ʼʱ��
	int repeatmode;//		����ѡ���ظ�����ģʽ����ѡ���ظ�ʱ���ֹģʽ
	int repeattimes;//		�ظ�����
	String repeatEndDate;//�ظ���ֹʱ��
	String searchDate;//	��ѯ�ճ̵�ʱ��
//	boolean delete;
}
/*
class DataOut
{
	DataOut(MyCalendar calendar)
	{
		MyCalendarInfo cal=new MyCalendarInfo(calendar.theme,calendar.content,calendar.date,calendar.time,calendar.priority,calendar.remind,calendar.comboBoxindex1,
				calendar.comboBoxindex2,calendar.repeat,calendar.mode,calendar.repeatStartDate,calendar.repeatmode,
				calendar.repeattimes,calendar.repeatEndDate,calendar.searchDate);
	}

	public void Out(){
	try  {
		FileOutputStream fo = new FileOutputStream("data.txt");
		ObjectOutputStream so = new ObjectOutputStream(fo);
		so.writeObject(cal);
		so.close();
		}
	catch(Exception e) {
		System.err.println(e);
		}
	}
	MyCalendarInfo cal;
}
*/

//�Զ��������ļ���������Ϣ
class DataIn
{
	public void In()
	{
	MyCalendarInfo temp=null;
    try {
	      FileInputStream fi = new FileInputStream("data.txt");
	      ObjectInputStream si = new ObjectInputStream(fi);
	      temp = (MyCalendarInfo)si.readObject();
	      si.close();
	    } catch(Exception e) 
	    {
	      System.out.println(e);
	    }
	    System.out.println(temp.theme+temp.content+temp.date+temp.time+
	    				   temp.priority+temp.priority+temp.remind+
	    				   temp.comboBoxindex1+temp.comboBoxindex2+
	    				   temp.repeat+temp.mode+temp.repeatStartDate+
	    				   temp.repeatmode+temp.repeattimes+
	    				   temp.repeatEndDate+temp.searchDate);
	}
}

public class MyCalendar extends javax.swing.JFrame {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JTabbedPane jTabbedPane1;
	private JPanel jPanel1;
	private JComboBox jComboBox7;
	private JComboBox jComboBox6;
	private JLabel jLabel10;
	private JCheckBox jCheckBox1;
	private JCheckBox jCheckBox2;
	private JComboBox jComboBox1;
	private JLabel jLabel9;
	private JButton jButton5;
	private JButton jButton2;
	private JTextArea jTextArea2;
	private JScrollPane jScrollPane2;
	private JLabel jLabel6;
	private JTextField jTextField2;
	private JFormattedTextField jFormattedTextField5;
	private JLabel jLabel4;
	private JTextArea jTextArea1;
//	private ButtonGroup buttonGroup1;
	private JScrollPane jScrollPane1;
	private JFormattedTextField jFormattedTextField4;
	private JFormattedTextField jFormattedTextField3;
	private JFormattedTextField jFormattedTextField2;
	private JFormattedTextField jFormattedTextField1;
	private JRadioButton jRadioButton6;
	private JLabel jLabel15;
	private JSpinner jSpinner1;
	private JRadioButton jRadioButton5;
	private JLabel jLabel11;
	private JRadioButton jRadioButton4;
	private JRadioButton jRadioButton3;
	private JRadioButton jRadioButton2;
	private JRadioButton jRadioButton1;
	private JButton jButton1;
	private JLabel jLabel7;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JTextField jTextField1;
	private JLabel jLabel1;
	private JPanel jPanel3;
	private JPanel jPanel2;
	
	
	String theme;
	String content;
	String date;
	String time;
	int  priority; 
	boolean remind;
	int comboBoxindex1;
	int comboBoxindex2;
	boolean repeat;
	int mode;
	String repeatStartDate;
	int repeatmode;
	int repeattimes;
	String repeatEndDate;
	String searchDate;
	private JButton jButton4;
	private JButton jButton3;
//	boolean delete;
	
	String $theme;
	String $content;
	String $date;
	String $time;
	int  $priority; 
	boolean $remind;
	int $comboBoxindex1;
	int $comboBoxindex2;
	boolean $repeat;
	int $mode;
	String $repeatStartDate;
	int $repeatmode;
	int $repeattimes;
	String $repeatEndDate;
	String $searchDate;
	int j=1;


	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MyCalendar inst = new MyCalendar();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
	//			inst.Input();
	/*			System.out.println(inst.theme+inst.content+inst.date+inst.time+
	    				   inst.priority+inst.priority+inst.remind+
	    				   inst.comboBoxindex1+inst.comboBoxindex2+
	    				   inst.repeat+inst.mode+inst.repeatStartDate+
	    				   inst.repeatmode+inst.repeattimes+
	    				   inst.repeatEndDate+inst.searchDate);
				DataOut output=new DataOut(inst);
				output.Out();
	*/		}
		});
		
	    
	}
	//�Զ��������ļ���������Ϣ
	public void Input(String filename)
	{
	MyCalendarInfo temp=null;
    try {
	      FileInputStream fi = new FileInputStream(filename);
	      ObjectInputStream si = new ObjectInputStream(fi);
	      temp = (MyCalendarInfo)si.readObject();
	      si.close();
	    } catch(Exception e) 
	    {
	      System.out.println(e);
	    }
	    $theme=temp.theme;
	    $content=temp.content;
	    $date=temp.date;
	    $time=temp.time;
	    $priority=temp.priority;
	    $remind=temp.remind;
	    $comboBoxindex1=temp.comboBoxindex1;
	    $comboBoxindex2=temp.comboBoxindex2;
	    $repeat= temp.repeat;
	    $mode=temp.mode;
	    $repeatStartDate=temp.repeatStartDate;
	    $repeatmode=temp.repeatmode;
	    $repeattimes=temp.repeattimes;
	    $repeatEndDate= temp.repeatEndDate;
	    $searchDate=temp.searchDate;
	}
	
	public MyCalendar() {
		super();
		initGUI();
	}
	
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setPreferredSize(new java.awt.Dimension(520, 449));
			{
				jTabbedPane1 = new JTabbedPane();
				getContentPane().add(jTabbedPane1, BorderLayout.NORTH);
				jTabbedPane1.setPreferredSize(new java.awt.Dimension(510, 355));
				{
					//��������ճ̽���
					jPanel1 = new JPanel();
					GridBagLayout jPanel1Layout = new GridBagLayout();
					jTabbedPane1.addTab("����ճ�", null, jPanel1, null);
					jPanel1Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.1, 0.1};
					jPanel1Layout.rowHeights = new int[] {43, 111, 35, 35, 20, 20};
					jPanel1Layout.columnWeights = new double[] {0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
					jPanel1Layout.columnWidths = new int[] {7, 97, 30, 65, 38, 53, 20};
					jPanel1.setLayout(jPanel1Layout);
					jPanel1.setPreferredSize(new java.awt.Dimension(488, 354));
					{
						jLabel1 = new JLabel();
						jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel1.setText("\u65e5\u7a0b\u6807\u9898");
					}
					{
						jTextField1 = new JTextField(23);
						jPanel1.add(jTextField1, new GridBagConstraints(1, 0, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						jTextField1.setText("");
	//					theme=jTextField1.getText();
					}
					{
						jLabel2 = new JLabel();
						jPanel1.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel2.setText("\u65e5\u7a0b\u5185\u5bb9");
					}
					{
						jLabel3 = new JLabel();
						jPanel1.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel3.setText("\u65e5\u671f");
					}
					{
						jLabel7 = new JLabel();
						jPanel1.add(jLabel7, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel7.setText("\u65f6\u95f4");
					}
					{
						jCheckBox1 = new JCheckBox();
						jPanel1.add(jCheckBox1, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jCheckBox1.setText("\u63d0\u9192");
			//			jCheckBox1.isSelected();
						ComboBoxModel jComboBox7Model = 
							new DefaultComboBoxModel(
									new String[] { "","Сʱ", "����" });
						jComboBox7 = new JComboBox();
						jComboBox6 = new JComboBox();
						jPanel1.add(jComboBox7, new GridBagConstraints(4, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jPanel1.add(jComboBox6, new GridBagConstraints(2, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						jComboBox7.setModel(jComboBox7Model);
						jComboBox7.setEnabled(false);
						jComboBox6.setEnabled(false);
						
						jCheckBox1.addActionListener(new ActionListener()
						{
							public void actionPerformed(final ActionEvent e)
							{
								boolean selectRemind=false;
								selectRemind=jCheckBox1.isSelected();
								jComboBox7.setEnabled(selectRemind);
								jComboBox6.setEnabled(selectRemind);
								
							}
						});
						jComboBox7.addActionListener(new ActionListener() 
						{
						      public void actionPerformed(final ActionEvent e) 
						      {
						    	 ComboBoxModel jComboBox1Model = new DefaultComboBoxModel();
						        int index = jComboBox7.getSelectedIndex();
						        if (index==0)
						        {// ==0��ʾѡ�е��µ�һ��
						        	jComboBox6.setEnabled(false);
						        }
						        if (index ==1)
						        { // ==1��ʾѡ�е��µڶ���
						        	jComboBox6.setModel(jComboBox1Model);
						        	jComboBox6.setEnabled(true);
						        	for(int i=1;i<=23;i++)
										jComboBox6.addItem(i);
						        }
						        if (index ==2) 
						        { // ==3��ʾѡ�е��µ�����
						        	jComboBox6.setModel(jComboBox1Model);
						        	jComboBox6.setEnabled(true);
						        	for(int j=1;j<=59;j++)
										jComboBox6.addItem(j);
						        }
						        
						      }
						  });

	//					jComboBox7.setEnabled(false);
						
						
					}
					{
						jLabel10 = new JLabel();
						jPanel1.add(jLabel10, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel10.setText("\u63d0\u524d");
					}
					{
						DateFormat format=new SimpleDateFormat("yyyy-MM-dd");//��ʽ������Date
						jFormattedTextField1 = new JFormattedTextField(format);
						
						jPanel1.add(jFormattedTextField1, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						jFormattedTextField1.setText("yyyy-mm-dd");
					}
					{
						DateFormat format1=new SimpleDateFormat("HH-mm");
						jFormattedTextField2 = new JFormattedTextField(format1);
						jPanel1.add(jFormattedTextField2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						jFormattedTextField2.setText("HH-mm");
					}
					{
						jTextArea1 = new JTextArea();
						jTextArea1.setLineWrap(true);
						jScrollPane1 = new JScrollPane(jTextArea1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			//			content=jTextArea1.getText();
						jPanel1.add(jScrollPane1, new GridBagConstraints(1, 1, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						{
							
							jScrollPane1.setViewportView(jTextArea1);
							jTextArea1.setText("");
						}
						{
		//					jTextPane1 = new JTextPane();
						}
						
					}
					{
						jLabel9 = new JLabel();
						jPanel1.add(jLabel9, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel9.setText("\u4f18\u5148\u7ea7");
					}
					{
						ComboBoxModel jComboBox1Model = 
							new DefaultComboBoxModel(
									new String[] { "��", "��","��" });
						jComboBox1 = new JComboBox();
						jPanel1.add(jComboBox1, new GridBagConstraints(4, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jComboBox1.setModel(jComboBox1Model);
						jComboBox1.setPreferredSize(new java.awt.Dimension(50, 22));
					}
				}
				{
					//�����ظ����ý���
					jPanel2 = new JPanel();
					GridBagLayout jPanel2Layout = new GridBagLayout();
					jTabbedPane1.addTab("�ظ�����", null, jPanel2, null);
					jPanel2Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1};
					jPanel2Layout.rowHeights = new int[] {35, 35, 35, 50, 60, 20, 20};
					jPanel2Layout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
					jPanel2Layout.columnWidths = new int[] {7, 7, 7, 7, 20, 20, 20, 20};
					jPanel2.setLayout(jPanel2Layout);
					jPanel2.setPreferredSize(new java.awt.Dimension(507, 353));
					{
						jRadioButton1 = new JRadioButton("\u6bcf\u5929",true);
						jRadioButton1.setEnabled(false);
						jPanel2.add(jRadioButton1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
	//					jRadioButton1.setText("\u6bcf\u5929");

					}
					{
						jRadioButton2 = new JRadioButton("\u6bcf\u5468",false);
						jRadioButton2.setEnabled(false);
						jPanel2.add(jRadioButton2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			//			jRadioButton2.setText("\u6bcf\u5468");
					}
					{
						jRadioButton3 = new JRadioButton("\u6bcf\u6708",false);
						jRadioButton3.setEnabled(false);
						jPanel2.add(jRadioButton3, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//						jRadioButton3.setText("\u6bcf\u6708");
					}
					{
						jRadioButton4 = new JRadioButton("\u6bcf\u5e74",false);
						jRadioButton4.setEnabled(false);
						jPanel2.add(jRadioButton4, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			//			jRadioButton4.setText("\u6bcf\u5e74");
					}
					{
						   ButtonGroup group=new ButtonGroup();//����ѡ��ť���һ��
						   group.add(jRadioButton1);
						   group.add(jRadioButton2);
						   group.add(jRadioButton3);
						   group.add(jRadioButton4);

					}
					
					{
						jLabel11 = new JLabel();
						jLabel11.setEnabled(false);
						jPanel2.add(jLabel11, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel11.setText("\u91cd\u590d\u5f00\u59cb\u65e5\u671f");
					}
					{
						jRadioButton5 = new JRadioButton("\u91cd\u590d\u6b21\u6570",true);
						jRadioButton5.setEnabled(false);
						jPanel2.add(jRadioButton5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		//				jRadioButton5.setText("\u91cd\u590d\u6b21\u6570");
						jRadioButton5.setActionCommand("button5");
						jRadioButton5.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								
									jSpinner1.setEnabled(true);
									jFormattedTextField4.setEnabled(false);
								
							}
						});
					}
					{
						
						SpinnerNumberModel jSpinner1Model = 
							new SpinnerNumberModel(1,1,10000,1);
						jSpinner1 = new JSpinner();
						jSpinner1.setEnabled(false);
						jPanel2.add(jSpinner1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						jSpinner1.setModel(jSpinner1Model);
				//		jSpinner1.setEnabled(false);
					}
					{
						jLabel15 = new JLabel();
						jPanel2.add(jLabel15, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel15.setText("\u6b21");
					}
					{
						jRadioButton6 = new JRadioButton();
						jRadioButton6.setEnabled(false);
						jPanel2.add(jRadioButton6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jRadioButton6.setText("\u91cd\u590d\u622a\u6b62");
						jRadioButton6.setActionCommand("button6");
						
						jRadioButton6.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
									jFormattedTextField4.setEnabled(true);
									jSpinner1.setEnabled(false);
									
								
							}
						});
					
					}
					{
						ButtonGroup group=new ButtonGroup();//����ѡ��ť���һ��
						group.add(jRadioButton5);
						group.add(jRadioButton6);
						group.getSelection();
						
						
					}
					{
						DateFormat format2=new SimpleDateFormat("yyyy-MM-dd");
						jFormattedTextField3 = new JFormattedTextField(format2);
						jFormattedTextField3.setEnabled(false);
						jPanel2.add(jFormattedTextField3, new GridBagConstraints(1, 3, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						jFormattedTextField3.setText("yyyy-MM-dd");
					}
					{
						DateFormat format3=new SimpleDateFormat("yyyy-MM-dd");
						jFormattedTextField4 = new JFormattedTextField(format3);
						jFormattedTextField4.setEnabled(false);
						jPanel2.add(jFormattedTextField4, new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						jFormattedTextField4.setText("yy-MM-dd");
					}
					{
						jCheckBox2 = new JCheckBox("\u91cd\u590d",false);
						jPanel2.add(jCheckBox2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
	//					jCheckBox2.setText("\u91cd\u590d");
						jCheckBox2.addActionListener(new ActionListener()
						{
							public void actionPerformed(final ActionEvent e)
							{
								boolean selectRepeat=false;
								selectRepeat=jCheckBox2.isSelected();
								jRadioButton1.setEnabled(selectRepeat);
								jRadioButton2.setEnabled(selectRepeat);
								jRadioButton3.setEnabled(selectRepeat);
								jRadioButton4.setEnabled(selectRepeat);
								jRadioButton5.setEnabled(selectRepeat);
								jRadioButton6.setEnabled(selectRepeat);
								jSpinner1.setEnabled(selectRepeat);
								jFormattedTextField3.setEnabled(selectRepeat);
								jLabel11.setEnabled(selectRepeat);
							}
						});
					}
				}
				{
					//�����ճ̲�ѯ����
					jPanel3 = new JPanel();
					GridBagLayout jPanel3Layout = new GridBagLayout();
					jTabbedPane1.addTab("�ճ̲�ѯ", null, jPanel3, null);
					jPanel3Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.1};
					jPanel3Layout.rowHeights = new int[] {53, 29, 107, 71, 20};
					jPanel3Layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.1};
					jPanel3Layout.columnWidths = new int[] {123, 155, 171, 7};
					jPanel3.setLayout(jPanel3Layout);
					jPanel3.setPreferredSize(new java.awt.Dimension(508, 334));
					{
						jLabel4 = new JLabel();
						jPanel3.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel4.setText("\u67e5\u8be2\u65e5\u671f");
						jLabel4.setPreferredSize(new java.awt.Dimension(52, 15));
					}
					{
						DateFormat format3=new SimpleDateFormat("yyyy-MM-dd");
						jFormattedTextField5 = new JFormattedTextField(format3);
						jPanel3.add(jFormattedTextField5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
						jFormattedTextField5.setText("yyyy-MM-dd");
					}
					{
						jTextField2 = new JTextField();
						jTextField2.setEditable(false);
						jPanel3.add(jTextField2, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					}
					{
						jLabel6 = new JLabel();
						jPanel3.add(jLabel6, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel6.setText("\u65e5\u7a0b");
					}
					{
						jScrollPane2 = new JScrollPane(jTextArea2,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//				jScrollPane2 = new JScrollPane();
						jPanel3.add(jScrollPane2, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						{
							jTextArea2 = new JTextArea();
							jTextArea2.setLineWrap(true);
							jTextArea2.setEditable(false);
							jScrollPane2.setViewportView(jTextArea2);
						}
					}
					{
						{
							jButton3 = new JButton();
							jPanel3.add(jButton3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jButton3.setText("\u4e0a\u4e00\u6761");
							jButton3.setEnabled(false);
						}
						{
							jButton4 = new JButton();
							jPanel3.add(jButton4, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jButton4.setText("\u4e0b\u4e00\u6761");
							jButton4.setEnabled(false);
						}
						jButton2 = new JButton();
						jPanel3.add(jButton2, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jButton2.setText("\u67e5\u8be2");
						jButton2.setPreferredSize(new java.awt.Dimension(71, 22));
						jButton2.addActionListener(new ActionListener()
						{
							
							
						//	������ѯ��ť
							public void actionPerformed(final ActionEvent e)
							{
								
								File file = new File(".");
								final String[]  filename  =  file.list(new FilenameFilter()
					        	{  
					            public  boolean  accept(File  dir,  String  name){  
					                        if(  name.endsWith(jFormattedTextField5.getText()+".txt"))  {  //�ҵ��������ڵ��ļ�
					                                    return  true;  
					                        }    
					                        return  false  ;  
					            } 
					        });
								
								int lenght=filename.length;
								if (filename.length==0) jTextField2.setText("������������");
								else if(filename.length==1){jButton3.setEnabled(false);jButton4.setEnabled(false);}
								else if(filename.length>1)
								{
									Input(filename[0]);
									jTextArea2.setText("����: "+$theme+"\n"+"ʱ��: "+$time+"\n"+"����: "+$content+"\n");
									j=1;
									jButton3.setEnabled(true);
									jButton4.setEnabled(true);
									jButton5.setEnabled(true);
									//������һ����ť���Զ���ʾ�����ճ���һ��
									jButton4.addActionListener(new ActionListener()
									{																			
										public void actionPerformed(final ActionEvent e)
										{										
											Input(filename[j]);
											//�Ѳ�ѯ���ڵ��ճ����
											jTextArea2.setText("����: "+$theme+"\n"+"ʱ��: "+$time+"\n"+"����: "+$content+"\n");
											jButton3.setEnabled(true);
											j++;
							//				System.out.println(j);
											if (j==filename.length)jButton4.setEnabled(false);
										}
									
										
									});
									
									//������һ����ť����ʾ����������һ��
									jButton3.addActionListener(new ActionListener()
									{																			
										public void actionPerformed(final ActionEvent e)
										{										
											Input(filename[j-2]);
											jTextArea2.setText("����: "+$theme+"\n"+"ʱ��: "+$time+"\n"+"����: "+$content+"\n");
											jButton4.setEnabled(true);
											j--;
											System.out.println(j);
											if (j==1)jButton3.setEnabled(false);
										}
										
										
									});
									
									//����ɾ����ť
									jButton5.addActionListener(new ActionListener()
									{
										public void actionPerformed(final ActionEvent e)
										{											
											File file=new File(filename[j-1]);
											if(file.exists() && file.isFile()) {
												if(file.delete()) {	//ɾ����ǰ�ļ�
												jTextField2.setText("ɾ���ɹ�");
											//	return true;
												} else {
													jTextField2.setText("ɾ��ʧ��");
											//	return false;
												}
												}
										}
									});
								
		/*						for(int  i  =  0;i<filename.length;i++)  
					        	{   
									Input(filename[i]);
									jTextArea2.append("����: "+$theme+"\n"+"ʱ��: "+$time+"\n"+"����: "+$content+"\n");
					        	}
		*/						}
		//						Input(jFormattedTextField5.getText());
		//						jTextField2.setText($theme);
								
		//						jTextField3.setText($time);
							}
						});
					}
					{
						jButton5 = new JButton();
						jPanel3.add(jButton5, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jButton5.setText("\u5220\u9664");
						jButton5.setPreferredSize(new java.awt.Dimension(60, 22));
						jButton5.setEnabled(false);
					}
					
				}
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("\u786e\u5b9a");
				jButton1.setPreferredSize(new java.awt.Dimension(512, 18));
				
				//����ȷ����ť�����û���������е���Ϣ�ö������ķ�ʽ���浽��ʱ��+_+����.txt"���ļ���
				jButton1.addActionListener(new ActionListener()
				{
					public void actionPerformed(final ActionEvent e)
					{
						  date=jFormattedTextField1.getText();
						  theme=jTextField1.getText();
						  content=jTextArea1.getText();
						  time=jFormattedTextField2.getText();
						  priority=jComboBox1.getSelectedIndex();
						  remind=jCheckBox1.isSelected();
						  comboBoxindex1=jComboBox7.getSelectedIndex();
						  comboBoxindex2=jComboBox6.getSelectedIndex();
						  repeat=jCheckBox2.isSelected();
						  if (jRadioButton1.isSelected()) mode=1;
						  if (jRadioButton2.isSelected()) mode=2;
						  if (jRadioButton3.isSelected()) mode=3;
						  if (jRadioButton4.isSelected()) mode=4;
						  repeatStartDate=jFormattedTextField3.getText();
						  if (jRadioButton5.isSelected()) repeatmode=1;
						  if (jRadioButton6.isSelected()) repeatmode=2;
						  repeattimes=(Integer) jSpinner1.getValue();
						  repeatEndDate=jFormattedTextField4.getText();
						  searchDate=jFormattedTextField5.getText();
						  MyCalendarInfo info=new MyCalendarInfo(theme,content,date,time,priority,remind,comboBoxindex1,
									comboBoxindex2,repeat,mode,repeatStartDate,repeatmode,
									repeattimes,repeatEndDate,searchDate);
						  
						  if (date.length() != 0&&date!="yyyy-MM-dd"&time.length()!=0&&time!="hh-mm")//��ӛ�¿�������������x�����ڄt����ӛ�n��
						  {
								try  {
									FileOutputStream fo = new FileOutputStream(time+"_"+date+".txt");
									ObjectOutputStream so = new ObjectOutputStream(fo);
									so.writeObject(info);
									so.close();
									}
								catch(Exception e1) 
									{
									System.err.println(e1);
									}
						  }
					}
						  
					
			
				});
			}
			pack();
			this.setSize(520, 449);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	

}
