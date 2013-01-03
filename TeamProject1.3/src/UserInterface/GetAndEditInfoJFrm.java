package UserInterface;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;

import infoManage.*;

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
public class GetAndEditInfoJFrm extends javax.swing.JFrame {
	UserInfoList userInfo;
	TableModel UserInfojTable1Model;
	
	private JPanel jPanel1;
	private JButton BackjButton1;
	private JButton jButton1;
	private JComboBox genderjComboBox1;
	private JTextField genderjTextField3;
	private JTextField jTextField2;
	private JTextField jTextField1;
	private JComboBox povertyjComboBox1;
	private JComboBox statusjComboBox1;
	private JLabel jLabel2;
	private JButton SavejButton1;
	private JTable UserInfojTable1;
	private JTextField stuNoJTextField1;
	private JLabel stuNoJLabel2;
	private JLabel jLabel1;
	InfoManageJFrame window;
	String idFound;
	/**
	* Auto-generated main method to display this JFrame
	*/
	/*public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GetAndEditInfoJFrm inst = new GetAndEditInfoJFrm();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	*/
	public GetAndEditInfoJFrm(InfoManageJFrame w) {
		super();
		window=w;
		userInfo = new UserInfoList();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				GridBagLayout jPanel1Layout = new GridBagLayout();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1Layout.rowWeights = new double[] {0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.1};
				jPanel1Layout.rowHeights = new int[] {7, 43, 46, 37, 146, 35, 34, 7, 7};
				jPanel1Layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.1};
				jPanel1Layout.columnWidths = new int[] {1, 8, 236, 7};
				jPanel1.setLayout(jPanel1Layout);
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText("\u4fe1\u606f\u67e5\u8be2\u53ca\u4fee\u6539");
				}
				{
					stuNoJLabel2 = new JLabel();
					jPanel1.add(stuNoJLabel2, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					stuNoJLabel2.setText("\u5b66\u53f7");
				}
				{
					stuNoJTextField1 = new JTextField();
					jPanel1.add(stuNoJTextField1, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
				}
				{
					UserInfojTable1Model = 
						new DefaultTableModel
						(
								new String[][] { { "姓名", "" }, { "学号", "" }, {"手机",""}, {"住址", ""} },
								new String[] { "Column 1", "Column 2" }
								){
						public boolean isCellEnabled(int row, int col){
							if (col == 0){
								return false;
							}
							return true;
						}
						
					};
					UserInfojTable1 = new JTable();
					jPanel1.add(UserInfojTable1, new GridBagConstraints(2, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
					UserInfojTable1.setModel(UserInfojTable1Model);
					UserInfojTable1.setRowHeight(36);
				}
				{
					SavejButton1 = new JButton();
					jPanel1.add(SavejButton1, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					SavejButton1.setText("\u4fdd\u5b58");
					SavejButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//TODO add your code for SavejButton1.actionPerformed
							String name1=(String)UserInfojTable1Model.getValueAt(0, 1);
							String id1=(String)UserInfojTable1Model.getValueAt(1, 1);
							id1.trim();
							String tele1=(String)UserInfojTable1Model.getValueAt(2, 1);
							tele1.trim();
							String addr1=(String)UserInfojTable1Model.getValueAt(3, 1);
							boolean ge=genderjComboBox1.getSelectedIndex()==0?true:false;
							int st=statusjComboBox1.getSelectedIndex();
							boolean po=povertyjComboBox1.getSelectedIndex()==0?true:false;
							if(id1.length()!=10||tele1.length()!=11)
							{
								JOptionPane.showMessageDialog(null,"学号或手机号长度不正确，重新输入 ");
								return;
							}
							try {
								
								Statement stat=InfoManageJFrame.conn.createStatement();
								stat.executeUpdate("update userinfo set id='"+id1+"',name='"+name1+"',password='"+id1+"',gender="+ge+",status="+
										st+",poverty="+po+",phone_num='"+tele1+"',address='"+addr1+"' where id="+idFound);
								JOptionPane.showMessageDialog(null,"修改成功 ");
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
				}
				{
					BackjButton1 = new JButton();
					jPanel1.add(BackjButton1, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					BackjButton1.setText("\u8fd4\u56de");
					BackjButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							window.setVisible(true);
							GetAndEditInfoJFrm.this.dispose();
						}
					});
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1, new GridBagConstraints(2, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jButton1.setText("\u67e5\u627e");
					jButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (!stuNoJTextField1.getText().isEmpty()){
								String id = stuNoJTextField1.getText();
								idFound=id;
								if(userInfo.getList().get(id)==null){
									UserInfo_User_not_found newWindow = new UserInfo_User_not_found(GetAndEditInfoJFrm.this);
									GetAndEditInfoJFrm.this.setEnabled(false);
									newWindow.setVisible(true);
									newWindow.setLocationRelativeTo(null);
									return;
								}
								UserInfojTable1Model.setValueAt(userInfo.getUserName(id), 0, 1);
								UserInfojTable1Model.setValueAt(userInfo.getUserID(id), 1, 1);
								UserInfojTable1Model.setValueAt(userInfo.getUserTel(id), 2, 1);
								UserInfojTable1Model.setValueAt(userInfo.getUserAddr(id), 3, 1);					
								statusjComboBox1.setSelectedIndex(userInfo.getUserType(id));
								genderjComboBox1.setSelectedIndex(userInfo.getUserGender(id)==true?0:1);
								povertyjComboBox1.setSelectedIndex(userInfo.isPoverty(id)==true?0:1);
								
							}
						}
					});
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2, new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel2.setText("\u6839\u636e\u5b66\u53f7\u67e5\u627e");
				}
				{
					ComboBoxModel statusjComboBox1Model = 
						new DefaultComboBoxModel(
								new String[] { "普通员工", "经理","管理员","超级管理员" });
					statusjComboBox1 = new JComboBox();
					jPanel1.add(statusjComboBox1, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					statusjComboBox1.setModel(statusjComboBox1Model);
				}
				{
					ComboBoxModel povertyjComboBox1Model = 
						new DefaultComboBoxModel(
								new String[] { "是", "否" });
					povertyjComboBox1 = new JComboBox();
					jPanel1.add(povertyjComboBox1, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					povertyjComboBox1.setModel(povertyjComboBox1Model);
				}
				{
					jTextField1 = new JTextField();
					jPanel1.add(jTextField1, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					jTextField1.setText("\u8eab\u4efd");
					jTextField1.setEditable(false);
				}
				{
					jTextField2 = new JTextField();
					jPanel1.add(jTextField2, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					jTextField2.setText("\u662f\u5426\u8d2b\u56f0");
					jTextField2.setEditable(false);
				}
				{
					genderjTextField3 = new JTextField();
					jPanel1.add(genderjTextField3, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					genderjTextField3.setText("\u6027\u522b");
					genderjTextField3.setEditable(false);
				}
				{
					ComboBoxModel genderjComboBox1Model = 
						new DefaultComboBoxModel(
								new String[] { "男", "女" });
					genderjComboBox1 = new JComboBox();
					jPanel1.add(genderjComboBox1, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					genderjComboBox1.setModel(genderjComboBox1Model);
				}
			}
			pack();
			this.setSize(505, 490);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
