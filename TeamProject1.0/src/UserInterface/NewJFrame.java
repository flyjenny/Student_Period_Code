package UserInterface;
import infoManage.UserInfoList;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;
import WorkShiftSystem.WorkShiftList;


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
public class NewJFrame extends javax.swing.JFrame {
	private JTable jTable1;
	private JScrollPane jScrollPane1;
	private JLabel jLabel11;
	private JLabel jLabel10;
	private JLabel jLabel9;
	private JLabel jLabel8;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JLabel jLabel5;
	private JCheckBox jCheckBox2;
	private JCheckBox jCheckBox17;
	private JCheckBox jCheckBox16;
	private JCheckBox jCheckBox15;
	private JLabel jLabel20;
	private JComboBox jComboBox1;
	private JButton jButton5;
	private JButton jButton4;
	private JCheckBox jCheckBox28;
	private JCheckBox jCheckBox27;
	private JCheckBox jCheckBox26;
	private JCheckBox jCheckBox25;
	private JCheckBox jCheckBox24;
	private JCheckBox jCheckBox23;
	private JCheckBox jCheckBox22;
	private JCheckBox jCheckBox21;
	private JCheckBox jCheckBox20;
	private JCheckBox jCheckBox19;
	private JCheckBox jCheckBox18;
	private JLabel jLabel19;
	private JLabel jLabel18;
	private JLabel jLabel17;
	private JLabel jLabel16;
	private JLabel jLabel15;
	private JLabel jLabel14;
	private JLabel jLabel13;
	private JLabel jLabel12;
	private JLabel jLabel1;
	private JButton jButton3;
	private JCheckBox jCheckBox14;
	private JCheckBox jCheckBox13;
	private JCheckBox jCheckBox12;
	private JCheckBox jCheckBox11;
	private JCheckBox jCheckBox10;
	private JCheckBox jCheckBox9;
	private JCheckBox jCheckBox8;
	private JCheckBox jCheckBox7;
	private JCheckBox jCheckBox6;
	private JCheckBox jCheckBox5;
	private JCheckBox jCheckBox4;
	private JCheckBox jCheckBox3;
	private JCheckBox jCheckBox1;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JButton jButton1;
	private JButton jButton2;
	private JLabel jLabel2;
	private UserInfoList userList;
	private int focusedID;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NewJFrame inst = new NewJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setTitle("排班信息");
			}
		});
	}
	
	public NewJFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0};
			thisLayout.rowHeights = new int[] {44, 7, 22, 29, 32, 20, 21, 27, 25, 315, 20, 7};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {20, 7, 7, 7, 7, 7, 7, 7, 20};
			getContentPane().setLayout(thisLayout);
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1, new GridBagConstraints(0, 9, 8, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				{
					TableModel jTable1Model = 
						new DefaultTableModel(
								new String[][] { {"陈斌", "王晓东","关晟","张建涛","王玮","孟翔宇","赵恒"},
										{ "陈斌", "王晓东","关晟","张建涛","王玮","孟翔宇","赵恒" } ,
										{ null, null,null,null,null,null,null },
										{"周一晚上", "周二晚上","周三晚上","周四晚上","周五晚上","周六晚上","周日晚上 "} 
										,{ null, null,null,null,null,null,null }
										,{ null, null,null,null,null,null,null }
										,{ null, null,null,null,null,null,null }},
										new String[] { "周一下午", "周二下午","周三下午","周四下午","周五下午","周六下午",
										"周日下午 "});
					jTable1 = new JTable();
					jScrollPane1.setViewportView(jTable1);
					jTable1.setModel(jTable1Model);
					jTable1.setPreferredSize(new java.awt.Dimension(382, 405));
				}
			}
			
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(8, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("\u786e\u5b9a");
				jButton1.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton1ActionPerformed(evt); }});
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText("\u7a7a\u95f2\u65f6\u95f4\u5f55\u5165");
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2, new GridBagConstraints(8, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton2.setText("\u4fdd\u5b58");
				jButton2.setPreferredSize(new java.awt.Dimension(71, 87));
				jButton2.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton2ActionPerformed(evt); }});
			//if(普通员工){jButton2.setVisible(false);}//如果为管理员，则显示该按钮
			}
		
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel3.setText("\u5468\u4e00");
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel4.setText("\u5468\u4e8c");
			}
			{
				jLabel5 = new JLabel();
				getContentPane().add(jLabel5, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel5.setText("\u5468\u4e09");
			}
			{
				jLabel6 = new JLabel();
				getContentPane().add(jLabel6, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel6.setText("\u5468\u56db");
			}
			{
				jLabel7 = new JLabel();
				getContentPane().add(jLabel7, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel7.setText("\u5468\u4e94");
			}
			{
				jLabel8 = new JLabel();
				getContentPane().add(jLabel8, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel8.setText("\u5468\u516d");
			}
			{
				jLabel9 = new JLabel();
				getContentPane().add(jLabel9, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel9.setText("\u5468\u65e5");
			}
			{
				jLabel10 = new JLabel();
				getContentPane().add(jLabel10, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel10.setText("\u4e0b\u5348");
			}
			{
				jLabel11 = new JLabel();
				getContentPane().add(jLabel11, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel11.setText("\u665a\u4e0a");
			}
			{
				jCheckBox1 = new JCheckBox();
				getContentPane().add(jCheckBox1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox2 = new JCheckBox();
				getContentPane().add(jCheckBox2, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox3 = new JCheckBox();
				getContentPane().add(jCheckBox3, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox4 = new JCheckBox();
				getContentPane().add(jCheckBox4, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox5 = new JCheckBox();
				getContentPane().add(jCheckBox5, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox6 = new JCheckBox();
				getContentPane().add(jCheckBox6, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox7 = new JCheckBox();
				getContentPane().add(jCheckBox7, new GridBagConstraints(7, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox8 = new JCheckBox();
				getContentPane().add(jCheckBox8, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox9 = new JCheckBox();
				getContentPane().add(jCheckBox9, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox10 = new JCheckBox();
				getContentPane().add(jCheckBox10, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox11 = new JCheckBox();
				getContentPane().add(jCheckBox11, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox12 = new JCheckBox();
				getContentPane().add(jCheckBox12, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox13 = new JCheckBox();
				getContentPane().add(jCheckBox13, new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox14 = new JCheckBox();
				getContentPane().add(jCheckBox14, new GridBagConstraints(7, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jButton3 = new JButton();
				getContentPane().add(jButton3, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton3.setText("\u6392\u73ed\u8868");
				jButton3.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton3ActionPerformed(evt); }});
			}
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("\u7279\u6b8a\u65f6\u95f4\u8f93\u5165");
			}
			{
				jLabel12 = new JLabel();
				getContentPane().add(jLabel12, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel12.setText("\u5468\u4e00");
			}
			{
				jLabel13 = new JLabel();
				getContentPane().add(jLabel13, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel13.setText("\u5468\u4e8c");
			}
			{
				jLabel14 = new JLabel();
				getContentPane().add(jLabel14, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel14.setText("\u5468\u4e09");
			}
			{
				jLabel15 = new JLabel();
				getContentPane().add(jLabel15, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel15.setText("\u5468\u56db");
			}
			{
				jLabel16 = new JLabel();
				getContentPane().add(jLabel16, new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel16.setText("\u5468\u4e94");
			}
			{
				jLabel17 = new JLabel();
				getContentPane().add(jLabel17, new GridBagConstraints(6, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel17.setText("\u5468\u516d");
			}
			{
				jLabel18 = new JLabel();
				getContentPane().add(jLabel18, new GridBagConstraints(7, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel18.setText("\u5468\u65e5");
			}
			{
				jLabel19 = new JLabel();
				getContentPane().add(jLabel19, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel19.setText("\u4e0b\u5348");
			}
			{
				jLabel20 = new JLabel();
				getContentPane().add(jLabel20, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel20.setText("\u665a\u4e0a");
			}
			{
				jCheckBox15 = new JCheckBox();
				getContentPane().add(jCheckBox15, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox16 = new JCheckBox();
				getContentPane().add(jCheckBox16, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox17 = new JCheckBox();
				getContentPane().add(jCheckBox17, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox18 = new JCheckBox();
				getContentPane().add(jCheckBox18, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox19 = new JCheckBox();
				getContentPane().add(jCheckBox19, new GridBagConstraints(5, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox20 = new JCheckBox();
				getContentPane().add(jCheckBox20, new GridBagConstraints(6, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox21 = new JCheckBox();
				getContentPane().add(jCheckBox21, new GridBagConstraints(7, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox22 = new JCheckBox();
				getContentPane().add(jCheckBox22, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox23 = new JCheckBox();
				getContentPane().add(jCheckBox23, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox24 = new JCheckBox();
				getContentPane().add(jCheckBox24, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox25 = new JCheckBox();
				getContentPane().add(jCheckBox25, new GridBagConstraints(4, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox26 = new JCheckBox();
				getContentPane().add(jCheckBox26, new GridBagConstraints(5, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox27 = new JCheckBox();
				getContentPane().add(jCheckBox27, new GridBagConstraints(6, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox28 = new JCheckBox();
				getContentPane().add(jCheckBox28, new GridBagConstraints(7, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jButton4 = new JButton();
				getContentPane().add(jButton4, new GridBagConstraints(8, 9, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton4.setText("\u751f\u6210\u6392\u73ed\u8868");
				jButton4.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton4ActionPerformed(evt); }});
			}
			{
				jButton5 = new JButton();
				getContentPane().add(jButton5, new GridBagConstraints(8, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton5.setText("\u786e\u5b9a");
				jButton5.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton5ActionPerformed(evt); }});
			}
			{
				userList = new UserInfoList();
				String[] tempStr = new String[userList.getList().size()];
				Iterator itr = userList.getList().entrySet().iterator();
				int i = 0;
				while (itr.hasNext()){
					Map.Entry entry = (Map.Entry)itr.next();
					tempStr[i++] = userList.getUserName((Integer)entry.getKey());
				}
				ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(tempStr);
				jComboBox1 = new JComboBox();
				getContentPane().add(jComboBox1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jComboBox1.setModel(jComboBox1Model);
			}
			pack();
			setSize(800, 700);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	private void jButton1ActionPerformed(ActionEvent evt)//空闲时间录入，如果已经生成排班表显示
    {
		boolean[] select=new boolean[14];//共14个班次
       if(jCheckBox1.isSelected()){select[0]=true;}
       if(jCheckBox2.isSelected()){select[1]=true;}
       if(jCheckBox3.isSelected()){select[2]=true;}
       if(jCheckBox4.isSelected()){select[3]=true;}
       if(jCheckBox5.isSelected()){select[4]=true;}
       if(jCheckBox6.isSelected()){select[5]=true;}
       if(jCheckBox7.isSelected()){select[6]=true;}
       if(jCheckBox8.isSelected()){select[7]=true;}
       if(jCheckBox9.isSelected()){select[8]=true;}
       if(jCheckBox10.isSelected()){select[9]=true;}
       if(jCheckBox11.isSelected()){select[10]=true;}
       if(jCheckBox12.isSelected()){select[11]=true;}
       if(jCheckBox13.isSelected()){select[12]=true;}
       if(jCheckBox14.isSelected()){select[13]=true;}
       Iterator itr = userList.getList().entrySet().iterator();
       while (itr.hasNext()){
    	   Map.Entry entry = (Map.Entry)itr.next();
    	   if (((String)jComboBox1.getSelectedItem()).equals(userList.getUserName((Integer)entry.getKey()))){
    		   focusedID = (Integer)entry.getKey();
    		   break;
    	   }
       }
       WorkShiftList temp = new WorkShiftList();
       temp.setFreeTime(focusedID, select);
       temp.setWorkShift();
       System.out.println();
    }
	private void jButton2ActionPerformed(ActionEvent evt)//保存排班表
    { 
		   WorkShiftList temp = new WorkShiftList();
		String[][] duty=temp.getWorkShift();
	   for(int i=0;i<3;i++){
		   for(int j=0;j<7;j++){duty[i][j]=(String) jTable1.getValueAt(i, j); }		   
	   }
	   for(int i=4;i<7;i++){
		   for(int j=0;j<7;j++){duty[i-4][j+7]=(String) jTable1.getValueAt(i, j); }		   
	   }
	   for(int i=0;i<3;i++){  for(int j=0;j<14;j++){System.out.println(duty[i][j]);}}
	   temp.creatLabor();
	   for (int i = 0; i < 14; i++){
	   System.out.println(duty[0][i]);
	   }
	   temp.setWorkShift();
    }
	
	
	private void jButton3ActionPerformed(ActionEvent evt)//显示排班表
    {	String[][] duty=new WorkShiftList().getWorkShift();
		   for(int i=0;i<3;i++){
			   for(int j=0;j<7;j++){ jTable1.setValueAt(duty[i][j],i, j); }		   
		   }
		   for(int i=4;i<7;i++){
			   for(int j=0;j<7;j++){jTable1.setValueAt(duty[i-4][j],i, j); }		   
		   }
		   System.out.println();
    }
	private void jButton4ActionPerformed(ActionEvent evt)//生成排班表
    {	
		WorkShiftList temp = new WorkShiftList();
		temp.createWorkShiftSchedule();
		temp.setWorkShift();
    }
	private void jButton5ActionPerformed(ActionEvent evt)//特殊时间输入
    {	
		WorkShiftList temp = new WorkShiftList();
	       if(jCheckBox15.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 0);}
	       if(jCheckBox16.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 1);}
	       if(jCheckBox17.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 2);}
	       if(jCheckBox18.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 3);}
	       if(jCheckBox19.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 4);}
	       if(jCheckBox20.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 5);}
	       if(jCheckBox21.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 6);}
	       if(jCheckBox22.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 7);}
	       if(jCheckBox23.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 8);}
	       if(jCheckBox24.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 9);}
	       if(jCheckBox25.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 10);}
	       if(jCheckBox26.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 11);}
	       if(jCheckBox27.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 12);}
	       if(jCheckBox28.isSelected()){temp.deleFreeTime(MainJFrame.stuNo, 13);}
	       temp.setWorkShift();
    }
}
