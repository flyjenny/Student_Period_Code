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
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
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
public class  SetNewUserFrm extends javax.swing.JFrame {
	private UserInfoList userInfo;
	
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JComboBox genderjComboBox1;
	private JLabel genderjLabel8;
	private JButton ExitJButton2;
	private JButton addjButton1;
	private JComboBox atatusSel;
	private JLabel jLabel7;
	private JComboBox povertySel;
	private JLabel jLabel6;
	private JTextField addr;
	private JFormattedTextField telejFormattedTextField1;
	private JFormattedTextField stuNojFormattedTextField1;
	private JTextField name;
	InfoManageJFrame window;

	/**
	* Auto-generated main method to display this JFrame
	*/
/*	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SetNewUserFrm inst = new SetNewUserFrm();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	*/
	public SetNewUserFrm(InfoManageJFrame w) {
		super();
		window=w;
		userInfo = new UserInfoList();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7, 7, 7};
			thisLayout.columnWeights = new double[] {0.0, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {46, 7, 7, 7};
			getContentPane().setLayout(thisLayout);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("\u65b0\u589e\u7528\u6237");
				jLabel1.setPreferredSize(new java.awt.Dimension(62, 15));
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText("\u59d3\u540d");
			}
			{
				name = new JTextField();
				getContentPane().add(name, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				name.setPreferredSize(new java.awt.Dimension(118, 22));
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel3.setText("\u5b66\u53f7");
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel4.setText("\u624b\u673a\u53f7\u7801");
			}
			{
				jLabel5 = new JLabel();
				getContentPane().add(jLabel5, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel5.setText("\u4f4f\u5740");
			}
			{
				addr = new JTextField();
				getContentPane().add(addr, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				addr.setPreferredSize(new java.awt.Dimension(118, 22));
			}
			{
				jLabel6 = new JLabel();
				getContentPane().add(jLabel6, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel6.setText("\u662f\u5426\u8d2b\u56f0");
			}
			{
				ComboBoxModel povertyJLabelModel = 
					new DefaultComboBoxModel(
							new String[] { "是", "否" });
				povertySel = new JComboBox();
				getContentPane().add(povertySel, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				povertySel.setModel(povertyJLabelModel);
			}
			{
				jLabel7 = new JLabel();
				getContentPane().add(jLabel7, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel7.setText("\u8eab\u4efd");
			}
			{
				ComboBoxModel jComboBox1Model = 
					new DefaultComboBoxModel(
							new String[] {"普通用户", "经理","管理员", "超级管理员"});
				atatusSel = new JComboBox();
				getContentPane().add(atatusSel, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				atatusSel.setModel(jComboBox1Model);
			}
			{
				addjButton1 = new JButton();
				getContentPane().add(addjButton1, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				addjButton1.setText("\u6dfb\u52a0\u8be5\u7528\u6237");
				addjButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
					String id1=stuNojFormattedTextField1.getText();	
					String name1= name.getText();
					name1.trim();
					boolean ge=(genderjComboBox1.getSelectedIndex()==0?true:false);
					String tele1=telejFormattedTextField1.getText();
					String addr1=addr.getText();
					boolean po=(povertySel.getSelectedIndex()==0?true:false);
					int st1=atatusSel.getSelectedIndex();
			    	if(id1.indexOf(" ")!=-1||tele1.indexOf(" ")!=-1)
					{
						JOptionPane.showMessageDialog(null,"学号或手机号码格式不正确，请重新输入 ");
						stuNojFormattedTextField1.setText("");
						telejFormattedTextField1.setText("");
					}
					else
						if(userInfo.getUserName(id1)!=null) 
						JOptionPane.showMessageDialog(null,"该用户已存在，请重新输入 ");
					else
					{
						try {
							Statement stat=InfoManageJFrame.conn.createStatement();
							stat.executeUpdate("insert into userinfo values('"+id1+"','"+name1+"','"+id1+"',"+ge+","+
									st1+","+po+",'"+tele1+"','"+addr1+"')");
							JOptionPane.showMessageDialog(null,"添加成功 ");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					}
				});
			}
			{
				ExitJButton2 = new JButton();
				getContentPane().add(ExitJButton2, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				ExitJButton2.setText("\u8fd4\u56de");
				ExitJButton2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						window.setVisible(true);
						SetNewUserFrm.this.dispose();
					}
				});
			}
			{
				stuNojFormattedTextField1 = new JFormattedTextField(new MaskFormatter("##########"));
				getContentPane().add(stuNojFormattedTextField1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				stuNojFormattedTextField1.setPreferredSize(new java.awt.Dimension(118, 22));
			}
			{
				telejFormattedTextField1 = new JFormattedTextField(new MaskFormatter("###########"));
				getContentPane().add(telejFormattedTextField1, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				telejFormattedTextField1.setPreferredSize(new java.awt.Dimension(118, 22));
			}
			{
				genderjLabel8 = new JLabel();
				getContentPane().add(genderjLabel8, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				genderjLabel8.setText("\u6027\u522b");
			}
			{
				ComboBoxModel genderjComboBox1Model = 
					new DefaultComboBoxModel(
							new String[] { "男", "女" });
				genderjComboBox1 = new JComboBox();
				getContentPane().add(genderjComboBox1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				genderjComboBox1.setModel(genderjComboBox1Model);
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
