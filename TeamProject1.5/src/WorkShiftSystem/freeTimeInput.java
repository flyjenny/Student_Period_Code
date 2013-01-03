package WorkShiftSystem;
import infoManage.UserInfoList;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Iterator;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import UserInterface.MainJFrame;

import com.mysql.jdbc.ResultSet;

import FileSystem.FileSystem;



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
public class freeTimeInput extends javax.swing.JFrame {
	private Connection con;
	private WorkShiftList workList;
	private JLabel jLabel1;
	private JComboBox jComboBox1;
	private JCheckBox jCheckBox1;
	private JCheckBox jCheckBox2;
	private JLabel jLabel10;
	private JLabel jLabel9;
	private JLabel jLabel5;
	private JLabel jLabel8;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JButton jButton2;
	private JButton jButton1;
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
	//private UserInfoList userList;

	/**
	* Auto-generated main method to display this JFrame
	*/
/*	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				freeTimeInput inst = new freeTimeInput();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}*/
	
	public freeTimeInput(WorkShiftList List) {
		super();
		workList = List;
		try {
			//-----------建立连接-------------
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/base?user=root");
			//----------------------------------
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Connecting error");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Class not found");
		}
		initGUI();
	}
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 20, 7, 7, 7};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {20, 20, 20, 20, 7, 7, 7, 7, 20, 20, 20};
			getContentPane().setLayout(thisLayout);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, new GridBagConstraints(4, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("\u7a7a\u95f2\u65f6\u95f4\u5f55\u5165");
			}
			{
				UserInfoList userList = new UserInfoList();
				String[] tempStr = new String[userList.getList().size()];
				Iterator itr = userList.getList().entrySet().iterator();
				int i = 0;
				while (itr.hasNext()){
					Map.Entry entry = (Map.Entry)itr.next();
					tempStr[i++] = userList.getUserName((String)entry.getKey());
				}
				ComboBoxModel jComboBox1Model = 
					new DefaultComboBoxModel(tempStr);
				jComboBox1 = new JComboBox();
				getContentPane().add(jComboBox1, new GridBagConstraints(6, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jComboBox1.setModel(jComboBox1Model);
				if (MainJFrame.Type == 0){
					jComboBox1.setVisible(false);
				}
			}
			{
				jCheckBox1 = new JCheckBox();
				getContentPane().add(jCheckBox1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox2 = new JCheckBox();
				getContentPane().add(jCheckBox2, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox3 = new JCheckBox();
				getContentPane().add(jCheckBox3, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox4 = new JCheckBox();
				getContentPane().add(jCheckBox4, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox5 = new JCheckBox();
				getContentPane().add(jCheckBox5, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox6 = new JCheckBox();
				getContentPane().add(jCheckBox6, new GridBagConstraints(7, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox7 = new JCheckBox();
				getContentPane().add(jCheckBox7, new GridBagConstraints(8, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox8 = new JCheckBox();
				getContentPane().add(jCheckBox8, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox9 = new JCheckBox();
				getContentPane().add(jCheckBox9, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox10 = new JCheckBox();
				getContentPane().add(jCheckBox10, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox11 = new JCheckBox();
				getContentPane().add(jCheckBox11, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox12 = new JCheckBox();
				getContentPane().add(jCheckBox12, new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox13 = new JCheckBox();
				getContentPane().add(jCheckBox13, new GridBagConstraints(7, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jCheckBox14 = new JCheckBox();
				getContentPane().add(jCheckBox14, new GridBagConstraints(8, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(9, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("\u7a7a\u95f2\u65f6\u95f4\u5f55\u5165");
				jButton1.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton1ActionPerformed(evt); }});
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2, new GridBagConstraints(9, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton2.setText("\u7279\u6b8a\u65f6\u95f4\u5f55\u5165");
				jButton2.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton2ActionPerformed(evt); }});
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText("\u5468\u4e00");
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel3.setText("\u5468\u4e8c");
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel4.setText("\u5468\u4e09");
			}
			{
				jLabel5 = new JLabel();
				getContentPane().add(jLabel5, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel5.setText("\u5468\u56db");
			}
			{
				jLabel6 = new JLabel();
				getContentPane().add(jLabel6, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel6.setText("\u5468\u4e94");
			}
			{
				jLabel7 = new JLabel();
				getContentPane().add(jLabel7, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel7.setText("\u5468\u516d");
			}
			{
				jLabel8 = new JLabel();
				getContentPane().add(jLabel8, new GridBagConstraints(8, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel8.setText("\u5468\u65e5");
			}
			{
				jLabel9 = new JLabel();
				getContentPane().add(jLabel9, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel9.setText("\u4e0b\u5348");
			}
			{
				jLabel10 = new JLabel();
				getContentPane().add(jLabel10, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel10.setText("\u665a\u4e0a");
			}
			pack();
			setSize(700, 500);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	private void jButton1ActionPerformed(ActionEvent evt)//空闲时间录入
    {
		boolean[] select=new boolean[14];//共14个班次
 		if(jCheckBox1.isSelected()){select[0]=true;jCheckBox1.setSelected(false);}
	       if(jCheckBox2.isSelected()){select[1]=true;jCheckBox2.setSelected(false);}
	       if(jCheckBox3.isSelected()){select[2]=true;jCheckBox3.setSelected(false);}
	       if(jCheckBox4.isSelected()){select[3]=true;jCheckBox4.setSelected(false);}
	       if(jCheckBox5.isSelected()){select[4]=true;jCheckBox5.setSelected(false);}
	       if(jCheckBox6.isSelected()){select[5]=true;jCheckBox6.setSelected(false);}
	       if(jCheckBox7.isSelected()){select[6]=true;jCheckBox7.setSelected(false);}
	       if(jCheckBox8.isSelected()){select[7]=true;jCheckBox8.setSelected(false);}
	       if(jCheckBox9.isSelected()){select[8]=true;jCheckBox9.setSelected(false);}
	       if(jCheckBox10.isSelected()){select[9]=true;jCheckBox10.setSelected(false);}
	       if(jCheckBox11.isSelected()){select[10]=true;jCheckBox11.setSelected(false);}
	       if(jCheckBox12.isSelected()){select[11]=true;jCheckBox12.setSelected(false);}
	       if(jCheckBox13.isSelected()){select[12]=true;jCheckBox13.setSelected(false);}
	       if(jCheckBox14.isSelected()){select[13]=true;jCheckBox14.setSelected(false);}
			try {
				String focusedID = null;
				if (MainJFrame.Type > 0){
					Statement stmt = con.createStatement();
					ResultSet rs = (ResultSet) stmt.executeQuery("select id from userinfo where name = '"+jComboBox1.getSelectedItem()+"'");
					if(rs.next()){
						focusedID = rs.getString(1);
						//System.out.println("ID = "+focusedID);
					}
				}
				else {
					focusedID = MainJFrame.stuNo;
					//System.out.println("not focused");
				}
				if (focusedID!=null){
					workList.setFreeTime_Usual(focusedID, select);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Class not found");
			}
	     /*  Iterator itr = userList.getList().entrySet().iterator();
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
	       System.out.println();*/
    }
	private void jButton2ActionPerformed(ActionEvent evt)//特殊时间录入，如果已经生成排班表显示
    {
		boolean[] select=new boolean[WorkShiftList.SHIFTTOTAL];//共14个班次
 		if(jCheckBox1.isSelected()){select[0]=true;jCheckBox1.setSelected(false);}
	       if(jCheckBox2.isSelected()){select[1]=true;jCheckBox2.setSelected(false);}
	       if(jCheckBox3.isSelected()){select[2]=true;jCheckBox3.setSelected(false);}
	       if(jCheckBox4.isSelected()){select[3]=true;jCheckBox4.setSelected(false);}
	       if(jCheckBox5.isSelected()){select[4]=true;jCheckBox5.setSelected(false);}
	       if(jCheckBox6.isSelected()){select[5]=true;jCheckBox6.setSelected(false);}
	       if(jCheckBox7.isSelected()){select[6]=true;jCheckBox7.setSelected(false);}
	       if(jCheckBox8.isSelected()){select[7]=true;jCheckBox8.setSelected(false);}
	       if(jCheckBox9.isSelected()){select[8]=true;jCheckBox9.setSelected(false);}
	       if(jCheckBox10.isSelected()){select[9]=true;jCheckBox10.setSelected(false);}
	       if(jCheckBox11.isSelected()){select[10]=true;jCheckBox11.setSelected(false);}
	       if(jCheckBox12.isSelected()){select[11]=true;jCheckBox12.setSelected(false);}
	       if(jCheckBox13.isSelected()){select[12]=true;jCheckBox13.setSelected(false);}
	       if(jCheckBox14.isSelected()){select[13]=true;jCheckBox14.setSelected(false);}
	       boolean[] free_usual = workList.getFreeTimeList().getFreeTime_Usual(MainJFrame.stuNo);
	       for (int i = 0; i < WorkShiftList.SHIFTTOTAL; i++){
	    	   free_usual[i] = free_usual[i]&(!select[i]);
	       }
	       workList.setFreeTime_Final(MainJFrame.stuNo+"", free_usual);
    }
}
