package UserInterface;
import infoManage.UserInfoList;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;


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
public class MyInfoSetJFrame extends javax.swing.JFrame {
	private JPanel jPanel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JButton jButton1;
	private JButton savejButton1;
	private JTextField povjTextField1;
	private JTextField statjTextField1;
	private JTextField addrjTextField1;
	private JLabel addrjLabel2;
	private JFormattedTextField telejFormattedTextField1;
	private JLabel telejLabel2;
	private JTextField genderjTextField1;
	private JLabel genderjLabel2;
	private JTextField namejTextField1;
	private JLabel namejLabel2;
	private JTextField stuNojTextField1;
	private JLabel stuNojLabel2;
	private JLabel jLabel1;
	String ID;
	InfoManageJFrame WINDOW;
	

	/**
	* Auto-generated main method to display this JFrame
	*/
/*	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MyInfoSetJFrame inst = new MyInfoSetJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}*/
	
	public MyInfoSetJFrame(String id, InfoManageJFrame window) {
		super();
		ID=id;
		WINDOW=window;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				GridBagLayout jPanel1Layout = new GridBagLayout();
				jPanel1Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7, 7, 7};
				jPanel1Layout.columnWeights = new double[] {0.0, 0.1, 0.1, 0.1};
				jPanel1Layout.columnWidths = new int[] {66, 7, 7, 7};
				jPanel1.setLayout(jPanel1Layout);
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText("\u6211\u7684\u4e2a\u4eba\u8bbe\u5b9a");
				}
				{
					stuNojLabel2 = new JLabel();
					jPanel1.add(stuNojLabel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					stuNojLabel2.setText("\u5b66\u53f7\uff1a");
				}
				{
					stuNojTextField1 = new JTextField();
					jPanel1.add(stuNojTextField1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					stuNojTextField1.setText(ID);
					stuNojTextField1.setEditable(false);
				}
				{
					namejLabel2 = new JLabel();
					jPanel1.add(namejLabel2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					namejLabel2.setText("\u59d3\u540d\uff1a");
				}
				{
					namejTextField1 = new JTextField();
					jPanel1.add(namejTextField1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					namejTextField1.setText(new UserInfoList().getUserName(ID));
					namejTextField1.setEditable(false);
				}
				{
					genderjLabel2 = new JLabel();
					jPanel1.add(genderjLabel2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					genderjLabel2.setText("\u6027\u522b\uff1a");
				}
				{
					genderjTextField1 = new JTextField();
					jPanel1.add(genderjTextField1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					genderjTextField1.setText((new UserInfoList().getUserGender(ID))?"男":"女");
					genderjTextField1.setEditable(false);
				}
				{
					telejLabel2 = new JLabel();
					jPanel1.add(telejLabel2, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					telejLabel2.setText("\u624b\u673a\uff1a");
				}
				{
					telejFormattedTextField1 = new JFormattedTextField(new MaskFormatter("###########"));
					jPanel1.add(telejFormattedTextField1, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					telejFormattedTextField1.setText(new UserInfoList().getUserTel(ID));
				}
				{
					addrjLabel2 = new JLabel();
					jPanel1.add(addrjLabel2, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					addrjLabel2.setText("\u4f4f\u5740\uff1a");
				}
				{
					addrjTextField1 = new JTextField();
					jPanel1.add(addrjTextField1, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					addrjTextField1.setText(new UserInfoList().getUserAddr(ID));
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel2.setText("\u8eab\u4efd\uff1a");
				}
				{
					statjTextField1 = new JTextField();
					jPanel1.add(statjTextField1, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				    int sta=new UserInfoList().getUserType(ID);
				    switch(sta){
				    case 0:
				    	statjTextField1.setText("普通员工");
				    	break;
				    case 1:
				    	statjTextField1.setText("经理");
				    	break;
				    case 2:
				    	statjTextField1.setText("管理员");
				    	break;
				    case 3:
				    	statjTextField1.setText("超级管理员");
				    	break;
				    default:
				    	statjTextField1.setText("显示错误");
				    	break;
				    }
				    statjTextField1.setEditable(false);
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel3.setText("\u662f\u5426\u8d2b\u56f0\uff1a");
				}
				{
					povjTextField1 = new JTextField();
					jPanel1.add(povjTextField1, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					boolean po=new UserInfoList().isPoverty(ID);
					povjTextField1.setText(po?"是":"否");
					povjTextField1.setEditable(false);
				}
				{
					savejButton1 = new JButton();
					jPanel1.add(savejButton1, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					savejButton1.setText("\u4fdd\u5b58\u8bbe\u5b9a");
					savejButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							String tele=telejFormattedTextField1.getText();
							String addr=addrjTextField1.getText();
							if(tele.length()<11||tele.indexOf(" ")!=-1) 
							{
								JOptionPane.showMessageDialog(null, "手机号码格式不正确！重新输入"); 
								telejFormattedTextField1.setText(null);
							}
							else
							{
								try {
							    Statement stm= WINDOW.conn.createStatement();
							
								stm.executeUpdate("update userinfo set phone_num='"+tele+"' , address='"+addr+"' where id= "+"'"+ID+"'");
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "修改成功"); 
							}
							
						}
					});
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jButton1.setText("\u8fd4\u56de");
					jButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							WINDOW.setVisible(true);
							MyInfoSetJFrame.this.dispose();
							
						}
					});
				}
			}
			pack();
			this.setSize(443, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
