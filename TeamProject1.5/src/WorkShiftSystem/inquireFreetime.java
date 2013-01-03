package WorkShiftSystem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;
import java.sql.*;

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
public class inquireFreetime extends javax.swing.JFrame {
	static public JTable freetimeTable;
	private WorkShiftList workList;
	private Connection con;
	private JButton jButton1;
	private JButton jButton3;
	private JScrollPane jScrollPane1;
	private JButton jButton2;
	private JComboBox jComboBox1;
	static TableModel freetimeTableModel ;
	//UserInfoList userInfo;
	/**
	* Auto-generated main method to display this JFrame
	*/
/*	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				inquireFreetime inst = new inquireFreetime();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}*/
	
	public inquireFreetime(WorkShiftList List) {
		super();
		workList = List;
		try {
			//-----------��������-------------
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database?user=root");
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
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7, 7};
			thisLayout.columnWeights = new double[] {0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0};
			thisLayout.columnWidths = new int[] {7, 20, 7, 7, 7, 7, 7};
			getContentPane().setLayout(thisLayout);
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1, new GridBagConstraints(1, 1, 5, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				jScrollPane1.setPreferredSize(new java.awt.Dimension(727, 263));
				{
					freetimeTableModel = 
						new DefaultTableModel(
								new String[][] { {""},{""},{""},{""},{""},{}},
								new String[] {"����", "��һa", "��һb" ,"�ܶ�a", "�ܶ�b" ,"����a", "����b" ,"����a", "����b" ,"����a", "����b" ,"����a", "����b" ,"����a", "����b" });
					freetimeTable = new JTable();
					freetimeTable.setRowHeight(40);
					freetimeTable .getTableHeader().setReorderingAllowed(false);
					jScrollPane1.setViewportView(freetimeTable);
					freetimeTable.setModel(freetimeTableModel);
					freetimeTable.setPreferredSize(new java.awt.Dimension(748, 391));
				}
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("\u67e5\u8be2\u6240\u6709\u7a7a\u95f2\u65f6\u95f4");
				jButton1.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton1ActionPerformed(evt); }});
			}
			{
				ComboBoxModel jComboBox1Model = 
					new DefaultComboBoxModel(
							new String[] { "��һ����", "��һ����","�ܶ�����", "�ܶ�����", "��������", "��������","��������", "��������" ,"��������", "��������","��������", "��������", "��������", "��������"});
				jComboBox1 = new JComboBox();
				getContentPane().add(jComboBox1, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jComboBox1.setModel(jComboBox1Model);
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton2.setText("\u73ed\u6b21\u67e5\u8be2\u7a7a\u95f2\u65f6\u95f4");
				jButton2.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton2ActionPerformed(evt); }});
			}
			{
				jButton3 = new JButton();
				getContentPane().add(jButton3, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton3.setText("\u5bfc\u51faexcel");
				jButton3.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton3ActionPerformed(evt); }});
			}
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(900, 600);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	private void jButton1ActionPerformed(ActionEvent evt)//��ѯ���п���ʱ��
    {
		try {
			Statement stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery("select id from userinfo");
	    	rs.last();
	    	int tot = rs.getRow();
	    	rs.first();
	    	if (tot > 0){
	    		int j = 0;
	    		do{//��������Ա��
	    			for(int i=0;i<workList.getFreeTimeList().getFreeTime_Final(rs.getString(1)).length; i++){//����ĳһԱ���Ŀ���ʱ��
	    				freetimeTable.setValueAt(true, j, workList.getFreeTimeList().getFreeTime_Final(rs.getString(1))[i]+1);
	    				j++;
	    			}  
	        	}while(rs.next());
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Querry Freetime Fail");
		}
    }
	private void jButton2ActionPerformed(ActionEvent evt)//��ʾ�Ű����β�ѯ����ʱ��
    {
		try {
			int t=jComboBox1.getSelectedIndex();
			Statement stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery("select id, name from userinfo");
	    	rs.last();
	    	int tot = rs.getRow();
	    	if (tot>0){
	    		int k = 0;
	    		do{//��������Ա��
	    			if (workList.getFreeTimeList().getFreeTime_Final_bool(rs.getString(1))[t]){
			         		freetimeTable.setValueAt(true, k, t);
			         		k++;
			         	}  
	    		}while(rs.next());
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Querry Single Freetime Fail");
		}
    }
	private void jButton3ActionPerformed(ActionEvent evt)//����excel���
    {
	    JFrame f = new SystemOS();
	    f.setSize(200,150);
	    f.setVisible(true);	 
	    SystemOS.table=freetimeTable;
	    SystemOS.filename="����ʱ���";
    }
}
