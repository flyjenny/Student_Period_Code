package UserInterface;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
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
public class EditPasswordJFrame extends javax.swing.JFrame {
	private UserInfoList userInfo;
	
	private JPanel jPanel1;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JOptionPane MessagejOptionPane1;
	private JPasswordField confirmNPassjPasswordField1;
	private JPasswordField newPassjPasswordField1;
	private JPasswordField OldPassjPasswordField1;
	private JButton BackjButton1;
	private JButton SavejButton1;
	private JLabel jLabel2;
	private JTextField stuNojTextField1;
	private JLabel jLabel1;
	private int stuNo;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EditPasswordJFrame inst = new EditPasswordJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public EditPasswordJFrame() {
		super();
		userInfo = new UserInfoList();
		initGUI();
	}
	
	public EditPasswordJFrame(int id) {
		super();
		stuNo=id;
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
				jPanel1Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.rowHeights = new int[] {7, 7, 7, 7, 7, 7};
				jPanel1Layout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.columnWidths = new int[] {7, 7, 7, 7};
				jPanel1.setLayout(jPanel1Layout);
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText("\u5b66\u53f7\uff1a");
				}
				{
					stuNojTextField1 = new JTextField();
					jPanel1.add(stuNojTextField1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel2.setText("\u65e7\u5bc6\u7801\uff1a");
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel3.setText("\u65b0\u5bc6\u7801\uff1a");
				}
				{
					jLabel4 = new JLabel();
					jPanel1.add(jLabel4, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel4.setText("\u4fee\u6539\u5bc6\u7801");
				}
				{
					jLabel5 = new JLabel();
					jPanel1.add(jLabel5, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel5.setText("\u786e\u8ba4\u65b0\u5bc6\u7801\uff1a");
				}
				{
					SavejButton1 = new JButton();
					jPanel1.add(SavejButton1, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					SavejButton1.setText("\u786e\u8ba4");
					SavejButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("SavejButton1.actionPerformed, event="+evt);
							//TODO add your code for SavejButton1.actionPerformed
							if(stuNojTextField1.getText()=="") JOptionPane.showMessageDialog(null,"用户名输入为空，登录失败，重新输入");
							if (userInfo.getList().get(Integer.parseInt(stuNojTextField1.getText()))!=null){
								if (userInfo.checkPassword(Integer.parseInt(stuNojTextField1.getText()), OldPassjPasswordField1.getText())){
									if (newPassjPasswordField1.getText().equals(confirmNPassjPasswordField1.getText())){
										userInfo.editUser(Integer.parseInt(userInfo.getUserID(Integer.parseInt(stuNojTextField1.getText()))), 
												userInfo.getUserName(Integer.parseInt(stuNojTextField1.getText())), 
												userInfo.getUserTel(Integer.parseInt(stuNojTextField1.getText())), 
												userInfo.getUserAddr(Integer.parseInt(stuNojTextField1.getText())), 
												userInfo.isPoverty(Integer.parseInt(stuNojTextField1.getText())), 
												userInfo.getUserType(Integer.parseInt(stuNojTextField1.getText())), 
												newPassjPasswordField1.getText());
										userInfo.setUserList();
										JOptionPane.showMessageDialog(null,"密码修改成功");
									}
									else System.out.println("两次密码不一致");
								}
								else System.out.println("密码错误");
							}
							else JOptionPane.showMessageDialog(null,"无此用户修改失败,重新输入");//用户不存在
						}
					});
				}
				{
					BackjButton1 = new JButton();
					jPanel1.add(BackjButton1, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					BackjButton1.setText("\u8fd4\u56de");
					BackjButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("BackjButton1.actionPerformed, event="+evt);
							//TODO add your code for BackjButton1.actionPerformed
							EditPasswordJFrame.this.dispose();
						}
					});
				}
				{
					OldPassjPasswordField1 = new JPasswordField();
					jPanel1.add(OldPassjPasswordField1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					newPassjPasswordField1 = new JPasswordField();
					jPanel1.add(newPassjPasswordField1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					confirmNPassjPasswordField1 = new JPasswordField();
					jPanel1.add(confirmNPassjPasswordField1, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					MessagejOptionPane1 = new JOptionPane();
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
