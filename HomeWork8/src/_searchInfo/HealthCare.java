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

public class HealthCare extends javax.swing.JFrame {

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
				HealthCare inst = new HealthCare();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	//获得类的引用以及方法、数据成员
	public void get()
	{
		Class volun=null;
	try {
		volun=Class.forName("_searchInfo.HealthCareClass");		//获得类的引用
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Constructor[] con= volun.getDeclaredConstructors();
	Class[]  p2=con[0].getParameterTypes();
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
	Object[] arg = new Object[] {28, "Emily","F"}; //初始化对象
	try {
		obj = ctor.newInstance(arg);
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
	Field[] attrib=volun.getDeclaredFields();	//得到数据成员数组
	$attrib=attrib;

	Method[] method=volun.getDeclaredMethods();		//得到方法的数组
	$method=method;

}
	
	public HealthCare() {
		super();
		get();
		initGUI();
	}
	
	public void jButton1ActionPerform(ActionEvent evt)
	{
//		System.out.println(choose);
//		System.out.println($attrib.length);
		if (choose<$attrib.length)		//选择访问数据成员
		{			
			Scanner scan=new Scanner($attrib[choose].toString());		
			String a=scan.next();		//获得是public或private。
	//		System.out.println(a);
		if (a.equals("public"))			//public属性的成员授权访问
		{
		$attrib[choose].setAccessible(true);
		try {
			try {
				jTextPane1.setText((String) $attrib[choose].get($obj)+"\n");	//调用数据成员并显示
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			}
		}
		else if(a.equals("private")) jTextPane1.setText("无权访问"+"\n");		//private属性的成员无法访问
		}
		else if (choose>=$attrib.length)		//选择访问方法
		{
		//	System.out.println("HELOO");
			Scanner scan=new Scanner($method[choose-$attrib.length].toString());
			String b=scan.next();
			if (b.equals("private")) jTextPane1.setText("无权访问"+"\n");		//private属性无法访问
			else if(b.equals("public"))										//public属性方法可以访问
			{
				
			//	$method[choose-$attrib.length].setAccessible(true);
				try {
					jTextPane1.setText((String)$method[choose-$attrib.length].invoke($obj, jTextPane2.getText()));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("IllegalArgumentException");
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("IllegalAccessException");
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("InvocationTargetException");
				}
			}
		}
	}
	
	//返回主界面Interface
	public void jButton2ActionPerform(ActionEvent evt)
	{
		Interface inter=new Interface();
		inter.setLocationRelativeTo(null);
		inter.setVisible(true);
		this.setVisible(false);
	}
	
	public void mouseClick(MouseEvent e) 
	{	
		choose=jList1.getSelectedIndex();
//		System.out.println(choose);
		
		if (choose>=$attrib.length)
		{
			Scanner scan=new Scanner($method[choose-$attrib.length].toString());
			String a=scan.next();
			if (a.equals("public")) jTextPane2.setEditable(true);
			else if(a.equals("private")) 
					jTextPane2.setEditable(false);
		}
	
	}

	//构造可视化界面
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
					MouseListener mouseListener = new MouseAdapter() {
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
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
				jButton1.addActionListener(new ActionListener(){
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
