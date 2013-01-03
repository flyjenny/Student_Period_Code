package WorkShiftSystem;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;

import FileSystem.FileSystem;
import UserInterface.MainJFrame;




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
public class scheduledTable extends javax.swing.JFrame {
	private WorkShiftList workList;
	private JButton jButton1;
	private JButton jButton4;
	private JScrollPane jScrollPane1;
	private JTable jTable1;
	private JButton jButton3;
	private JButton jButton2;
	private JButton jButton6;
	private JButton jButton5;
	private JButton jButton7;
	private Connection con;

	/**
	* Auto-generated main method to display this JFrame
	*/
/*	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scheduledTable inst = new scheduledTable();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}*/
	
	public scheduledTable(WorkShiftList List) {
		super();
		workList = List;
		try {
			//-----------建立连接-------------
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
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.0};
			thisLayout.rowHeights = new int[] {20, 7, 7, 88};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {20, 7, 7, 7, 7, 20};
			getContentPane().setLayout(thisLayout);
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("\u6392\u73ed\u8868");
				jButton1.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton1ActionPerformed(evt); }});
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton2.setText("\u4e2a\u4eba\u6392\u73ed\u8868");
				jButton2.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton2ActionPerformed(evt); }});
			}
			{
				jButton3 = new JButton();
				getContentPane().add(jButton3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton3.setText("\u751f\u6210\u6392\u73ed\u8868");
				jButton3.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton3ActionPerformed(evt); }});
				if (MainJFrame.Type == 0){
					jButton3.setEnabled(false);
				}
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1, new GridBagConstraints(1, 1, 4, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 1), 0, 0));
				{
					String[] head=new String[] { "周一下午", "周二下午","周三下午","周四下午","周五下午","周六下午",
					"周日下午 "};
					TableModel jTable1Model = 
						new DefaultTableModel(
								new String[][] { {"陈斌", "王晓东","关晟","张建涛","王玮","孟翔宇","赵恒"},
										{ "陈斌", "王晓东","关晟","张建涛","王玮","孟翔宇","赵恒" } ,
										{ null, null,null,null,null,null,null },
										{"周一晚上", "周二晚上","周三晚上","周四晚上","周五晚上","周六晚上","周日晚上 "} 
										,{ null, null,null,null,null,null,null }
										,{ null, null,null,null,null,null,null }
										,{ null, null,null,null,null,null,null }},
										head);
					jTable1 = new JTable(jTable1Model)	{public boolean isCellEditable(int row, int column) { 
					    if(row==3){return false;}
					    return true;
					   };};
			
					   DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				            public Component getTableCellRendererComponent(JTable table, Object value,
				                    boolean isSelected, boolean hasFocus, int row, int column) 
				            {
				                Component cell = super.getTableCellRendererComponent  
				                        (table, value, isSelected, hasFocus, row, column);
				                if(row==3  && cell.isBackgroundSet())//设置变色的单元格
				                    cell.setBackground(Color.GRAY);
				                else
				                    cell.setBackground(Color.WHITE);
				                return cell;
				            }
				        };
				        
				        //设置列表现器------------------------//
				        for(int i = 0; i < head.length; i++) 
				        { jTable1.getColumn(head[i]).setCellRenderer(tcr); }
					   //根据权限设置可以编辑否
				        jTable1.setEnabled(true);
					jScrollPane1.setViewportView(jTable1);
					jTable1.setRowHeight(50);
					jTable1.setRowHeight(3, 18);
				}
			}
			{
				jButton4 = new JButton();
				getContentPane().add(jButton4, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton4.setText("\u5bfc\u51faexcel");
				jButton4.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton4ActionPerformed(evt); }});
			}
			{
				jButton5 = new JButton();
				getContentPane().add(jButton5, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton5.setText("\u4fdd\u5b58");
				jButton5.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						System.out.println("jButton5.actionPerformed, event="+evt);
						//TODO add your code for jButton5.actionPerformed
							String[][] duty = workList.getWorkShift();
						   for(int i=0;i<3;i++){
							   for(int j=0;j<7;j++){duty[i][j]=(String) jTable1.getValueAt(i, j); }		   
						   }
						   for(int i=4;i<7;i++){
							   for(int j=0;j<7;j++){duty[i-4][j+7]=(String) jTable1.getValueAt(i, j); }		   
						   }
						   for(int i=0;i<3;i++){  for(int j=0;j<14;j++){System.out.println(duty[i][j]);}}
						   workList.creatLabor();
						   for (int i = 0; i < 14; i++){
						   System.out.println(duty[0][i]);
						   }
						   workList.setWorkShift();
						workList.setWorkShift();
					}
				});
			}
			{
				jButton6 = new JButton();
				getContentPane().add(jButton6, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton6.setText("\u8fd4\u56de");
			}
			{
				jButton7 = new JButton();
				getContentPane().add(jButton7, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton7.setText("\u4e00\u952e\u8fd8\u539f");
				jButton7.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton7ActionPerformed(evt); }});
				if (MainJFrame.Type == 0){
					jButton3.setEnabled(false);
				}
			}
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(600,500);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	private void jButton3ActionPerformed(ActionEvent evt)//生成排班表
    {
		workList.createWorkShiftSchedule();
    }
	private void jButton1ActionPerformed(ActionEvent evt)//显示所有人员排班表
    {
		String[][] duty=workList.getWorkShift();
		   for(int i=0;i<3;i++){
			   for(int j=0;j<7;j++){ jTable1.setValueAt(duty[i][j],i, j); }		   
		   }
		   for(int i=4;i<7;i++){
			   for(int j=0;j<7;j++){jTable1.setValueAt(duty[i-4][j+7],i, j); }
		   }
    }
	private void jButton2ActionPerformed(ActionEvent evt)//显示个人排班表
    {
		String[][] duty = workList.getWorkShift();
		   for(int i=0;i<3;i++){
			   if (duty[i] != null)
			   for(int j=0;j<7;j++){
				   if (duty[i][j]!=null){
			   		if(duty[i][j].equals(MainJFrame.Name)){ jTable1.setValueAt(duty[i][j],i, j); }
			   		else jTable1.setValueAt(" ", i, j);
				   }
			    }		   
		   }
		   for(int i=4;i<7;i++){
			   if (duty[i-4]!=null)
			   for(int j=0;j<7;j++){
				   if (duty[i-4][j+7]!=null){
			   		if(duty[i][j].equals(MainJFrame.Name)){jTable1.setValueAt(duty[i-4][j+7],i, j); }
			   		else jTable1.setValueAt(" ", i, j);
				   }
			   }		   
		   }
		   System.out.println();
    }
	private void jButton4ActionPerformed(ActionEvent evt)//导出excel
    {
		JFrame f = new SystemOS();
	    f.setSize(200,150);
	    f.setVisible(true);
		SystemOS.table=jTable1;	
		SystemOS.filename="排班表";
    }
	private void jButton7ActionPerformed(ActionEvent evt)//导出excel
    {	

		try {
			Statement stmt = con.createStatement();
	    	ResultSet rs = stmt.executeQuery("select id from userinfo");
	    	rs.last();
	    	int tot = rs.getRow();
	    	rs.first();
	    	if (tot > 0){
	    		int j = 0;
	    		do{//遍历所有员工
	    			workList.setFreeTime_Final(rs.getString(1),workList.getFreeTimeList().getFreeTime_Usual(rs.getString(1)));
	        	}while(rs.next());
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Querry Freetime Fail");
		}
    }
}
