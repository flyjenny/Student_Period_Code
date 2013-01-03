package UserInterface;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	private JButton SavejButton1;
	private JTable UserInfojTable1;
	private JTextField stuNoJTextField1;
	private JLabel stuNoJLabel2;
	private JLabel jLabel1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GetAndEditInfoJFrm inst = new GetAndEditInfoJFrm();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public GetAndEditInfoJFrm() {
		super();
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
				jPanel1Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.rowHeights = new int[] {7, 7, 7, 7};
				jPanel1Layout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.columnWidths = new int[] {7, 7, 7, 7};
				jPanel1.setLayout(jPanel1Layout);
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText("\u4fe1\u606f\u67e5\u8be2\u53ca\u4fee\u6539");
				}
				{
					stuNoJLabel2 = new JLabel();
					jPanel1.add(stuNoJLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					stuNoJLabel2.setText("\u5b66\u53f7");
				}
				{
					stuNoJTextField1 = new JTextField();
					jPanel1.add(stuNoJTextField1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					UserInfojTable1Model = 
						new DefaultTableModel(
								new String[][] { { "姓名", "One" }, { "学号", "Two" }, {"手机","Three"}, {"住址", "Four"}, {"身份", "Five"}, {"是否贫困", "Six"} },
								new String[] { "Column 1", "Column 2" }
								){
						public boolean isCellEditable(int row, int col){
							if (col == 0){
								return false;
							}
							return true;
						}
						
					};
					UserInfojTable1 = new JTable();
					jPanel1.add(UserInfojTable1, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					UserInfojTable1.setModel(UserInfojTable1Model);
				}
				{
					SavejButton1 = new JButton();
					jPanel1.add(SavejButton1, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					SavejButton1.setText("\u4fdd\u5b58");
					SavejButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("SavejButton1.actionPerformed, event="+evt);
							//TODO add your code for SavejButton1.actionPerformed
							int type;
							if (((String)UserInfojTable1Model.getValueAt(4, 1)).equals("主管")){
								type = 2;
							}
							else if (((String)UserInfojTable1Model.getValueAt(4, 1)).equals("经理")){
								type = 1;
							}
							else type = 0;
							userInfo.editUser(Integer.parseInt((String)UserInfojTable1Model.getValueAt(1, 1)), 
									(String)UserInfojTable1Model.getValueAt(0, 1), 
									(String)UserInfojTable1Model.getValueAt(2, 1), 
									(String)UserInfojTable1Model.getValueAt(3, 1), 
									((String)UserInfojTable1Model.getValueAt(5, 1)).equals("是")?true:false, 
									type,  null);
							userInfo.setUserList();
						}
					});
				}
				{
					BackjButton1 = new JButton();
					jPanel1.add(BackjButton1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					BackjButton1.setText("\u8fd4\u56de");
					BackjButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("BackjButton1.actionPerformed, event="+evt);
							//TODO add your code for BackjButton1.actionPerformed
							GetAndEditInfoJFrm.this.dispose();
						}
					});
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jButton1.setText("\u67e5\u627e");
					jButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("jButton1.actionPerformed, event="+evt);
							//TODO add your code for jButton1.actionPerformed
							if (!stuNoJTextField1.getText().isEmpty()){
								int id = Integer.parseInt(stuNoJTextField1.getText());
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
								switch (userInfo.getUserType(id)){
								case 1:{
									UserInfojTable1Model.setValueAt("经理", 4, 1);
									break;
								}
								case 2:{
									UserInfojTable1Model.setValueAt("主管" , 4, 1);
									break;
								}
								default:{
									UserInfojTable1Model.setValueAt("普通员工" , 4, 1);
									break;
								}
								}
								UserInfojTable1Model.setValueAt(userInfo.isPoverty(id)?"是":"否" , 5, 1);
							}
						}
					});
				}
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
