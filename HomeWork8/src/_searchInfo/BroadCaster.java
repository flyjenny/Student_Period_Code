package _searchInfo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

public class BroadCaster extends javax.swing.JFrame {

	/**
	* Auto-generated main method to display this JFrame
	*/
	
	
	private JList jList1;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JButton jButton1;
	private JTextPane jTextPane2;
	private JScrollPane jScrollPane3;
	private JTextPane jTextPane1;
	private JButton jButton2;
	DefaultListModel listModel1 = new DefaultListModel();
	private JLabel jLabel1;
	Field[] $attrib;
	Method[] $method;
	Object $obj;
	int choose;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BroadCaster inst = new BroadCaster();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	
	//�÷�����ƻ��������ã���ķ���method����ĳ�Աattrib
	public void get()
	{
		Class volun=null;
	try {
		volun=Class.forName("_searchInfo.BroadCasterClass");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Constructor[] con= volun.getDeclaredConstructors();   //���������ʾ����Ĺ��췽��
	Class[]  p2=con[0].getParameterTypes();   //�õ������б������
	Constructor ctor = null;
	Class[] ptypes = new Class[] { int.class, String.class,String.class };
	try {
		ctor = volun.getConstructor(ptypes);
	} catch (SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchMethodException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Object obj = null;
	Object[] arg = new Object[] {26, "Lily","F"}; //�����ʼ������
	try {
		obj = ctor.newInstance(arg);			//�������������
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (InstantiationException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		e.printStackTrace();
	}
	$obj=obj;
	Field[] attrib=volun.getDeclaredFields();   //�����ʾ����ĳ�Ա
	$attrib=attrib;

	Method[] method=volun.getDeclaredMethods();  //�����ʾ����ķ���
	$method=method;

}
	
	public BroadCaster() {
		super();
		get();
		initGUI();
	}
	
	public void jButton1ActionPerform(ActionEvent evt)
	{
//		System.out.println(choose);
//		System.out.println($attrib.length);
		if (choose<$attrib.length)  //ѡ��JList�й��ڳ�Ա��ѡ��
		{
			
		Scanner scan=new Scanner($attrib[choose].toString());
		String a=scan.next();   //�õ���Ա�����ԣ���public��private
	//	System.out.println(a);
		if (a.equals("public"))   //����ѯ����public���Եĳ�Ա
		{
		$attrib[choose].setAccessible(true);
		try {
			try {
				jTextPane1.setText((String) $attrib[choose].get($obj)+"\n");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			}
		}
		//��ѯ����private���Եĳ�Ա
		else if(a.equals("private")) jTextPane1.setText("��Ȩ����"+"\n");
		}
		else if (choose>=$attrib.length)  //������JList�з�����ѡ��
		{
		//	System.out.println("HELOO");
			Scanner scan=new Scanner($method[choose-$attrib.length].toString());
			String b=scan.next();
			if (b.equals("private")) jTextPane1.setText("��Ȩ����"+"\n");  //private���Եķ����޷�����
			else if(b.equals("public"))			//��Ȩ����public�ķ���
			{
				
			//	$method[choose-$attrib.length].setAccessible(true);
				try {
					jTextPane1.setText((String)$method[choose-$attrib.length].invoke($obj, jTextPane2.getText())); //�������ò����
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					System.out.println("IllegalArgumentException");
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					System.out.println("IllegalAccessException");
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					System.out.println("InvocationTargetException");
				}
			}
		}
	}
	
	
	//����������Interface�Ķ���
	public void jButton2ActionPerform(ActionEvent evt)
	{
		Interface inter=new Interface();
		inter.setLocationRelativeTo(null);
		inter.setVisible(true);
		this.setVisible(false);
	}
	
	
	//��JList�е������ѡ��ڼ���ѡ��
	public void mouseClick(MouseEvent e) 
	{	
		choose=jList1.getSelectedIndex();
//		System.out.println(choose);
		
		if (choose>=$attrib.length)
		{
			Scanner scan=new Scanner($method[choose-$attrib.length].toString());
			String a=scan.next();
			if (a.equals("public")) jTextPane2.setEditable(true);   //����public����ǰ����������Ҫ��ѯ������
			else if(a.equals("private")) 
					jTextPane2.setEditable(false);
		}
	
	}

	//������ӻ�����
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.0, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {52, 7, 7, 7};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {7, 7, 7, 7};
			
			
			getContentPane().setLayout(thisLayout);
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2, new GridBagConstraints(3, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton2.setText("\u8fd4\u56de");
				jButton2.setPreferredSize(new java.awt.Dimension(60, 27));
				jButton2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton2ActionPerform(evt);
						
					}
				});
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1, new GridBagConstraints(0, 0, 2, 4, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				
			//		ListModel jList1Model = 
			//			new DefaultComboBoxModel(
			//					new String[] { "Item One", "Item Two" });
					jList1 = new JList(listModel1);
					jScrollPane1.setViewportView(jList1);
			//		jList1.setModel(jList1Model);
					
					jScrollPane1.setViewportView(jList1);
					//jList1.setModel(jList1Model);
					jList1.setPreferredSize(new java.awt.Dimension(306, 464));
					jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					MouseListener mouseListener = new MouseAdapter() {				//JList����궯������
				        public void mouseClicked(MouseEvent e) {mouseClick(e);}
				       };
				   
					
					jList1.addMouseListener(mouseListener);
				    
				    for (int i=0;i<$attrib.length;i++)
				    listModel1.addElement($attrib[i].getName());
				    for (int j=0;j<$method.length;j++)
				    listModel1.addElement($method[j].getName());
				
			
			}
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("\u67e5\u8be2\u5217\u8868");
				jLabel1.setPreferredSize(new java.awt.Dimension(64, 20));
				
			}
			{
				jScrollPane2 = new JScrollPane(jTextPane1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				getContentPane().add(jScrollPane2, new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				{
					jTextPane1 = new JTextPane();
					jTextPane1.setEditable(false);
					jScrollPane2.setViewportView(jTextPane1);
				}
			}
			{
				jScrollPane3 = new JScrollPane(jTextPane2,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);   //���Ϲ�����
				getContentPane().add(jScrollPane3, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				{
					jTextPane2 = new JTextPane();
					jTextPane2.setEditable(false);
					jScrollPane3.setViewportView(jTextPane2);
				}
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("\u67e5\u8be2");
				jButton1.setPreferredSize(new java.awt.Dimension(60, 27));
				jButton1.addActionListener(new ActionListener(){   			//��ť��������
					public void actionPerformed(ActionEvent evt){
						jButton1ActionPerform(evt);
						
					}
				});
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}


}
