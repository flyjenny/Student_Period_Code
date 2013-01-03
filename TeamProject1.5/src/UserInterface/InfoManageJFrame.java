package UserInterface;
import infoManage.*;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


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
public class InfoManageJFrame extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane mainTabbedPane1;
	private JButton deleteUserjButton;
	private JButton SuperAdmjButton1;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JButton exitjButton1;
	private JButton telejButton1;
	private JButton jButton5;
	private JButton jButton4;
	private JButton jButton3;
	private JButton jButton2;
	private JButton jButton1;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JButton setBasicInfojButton1;
	private JButton ChanggePassjButton2;
	private JButton mySetjButton1;
	private JButton addNewUserjButton;
	private JPanel WagesjPanel;
	private JPanel InfojPanel;
	private JPanel WorkjPanel;
	String stuNo="5080309517";
	int type=3;
	static Connection conn;
	MainJFrame window;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				InfoManageJFrame inst = new InfoManageJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public InfoManageJFrame() {
		super();
		initGUI();
	}
	public InfoManageJFrame(String id,MainJFrame w) {
		super();
		stuNo=id;
		window=w;
		type=new UserInfoList().getUserType(id);
		initGUI();
	}
	
	private void initGUI() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 连接数据库
			// 其中localized为主机名，test是数据库名，root为用户名
			conn =DriverManager.getConnection("jdbc:mysql://localhost/database?user=root");
			this.setTitle("信息管理");
			UserInfoList userL=new UserInfoList();
			BorderLayout thisLayout = new BorderLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(thisLayout);
			{
				InfojPanel = new JPanel();
				getContentPane().add(InfojPanel, BorderLayout.CENTER);
				{
					
					GridBagLayout InfojPanelLayout = new GridBagLayout();
					InfojPanelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
					InfojPanelLayout.rowHeights = new int[] {7,7,7,7};
					InfojPanelLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
					InfojPanelLayout.columnWidths = new int[] {7, 7, 7, 7};
					InfojPanel.setLayout(InfojPanelLayout);
					{
						addNewUserjButton = new JButton();
						InfojPanel.add(addNewUserjButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						addNewUserjButton.setText("\u65b0\u589e\u7528\u6237");
						if(type==3) addNewUserjButton.setEnabled(true);
						else addNewUserjButton.setEnabled(false);
						addNewUserjButton.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								SetNewUserFrm userFrm=new SetNewUserFrm(InfoManageJFrame.this); 
								userFrm.setVisible(true);
								InfoManageJFrame.this.setVisible(false);
							}
						});
					}
					{
						deleteUserjButton = new JButton();
						InfojPanel.add(deleteUserjButton, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						deleteUserjButton.setText("\u6ce8\u9500\u7528\u6237");
						if(type==3) deleteUserjButton.setEnabled(true);
						else deleteUserjButton.setEnabled(false);
						deleteUserjButton.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								DeleteUserJFrm delFrm=new DeleteUserJFrm(InfoManageJFrame.this); 
								delFrm.setVisible(true);
								InfoManageJFrame.this.setVisible(false);
							}
						});
					}
				
					{
						setBasicInfojButton1 = new JButton();
						InfojPanel.add(setBasicInfojButton1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						setBasicInfojButton1.setText("\u4e2a\u4eba\u8bbe\u5b9a");
						setBasicInfojButton1.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								MyInfoSetJFrame infoFrm=new MyInfoSetJFrame(stuNo,InfoManageJFrame.this); 
								infoFrm.setVisible(true);
								InfoManageJFrame.this.setVisible(false);
							}
						});
					}
					{
						ChanggePassjButton2 = new JButton();
						InfojPanel.add(ChanggePassjButton2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						ChanggePassjButton2.setText("\u4fee\u6539\u5bc6\u7801");
						ChanggePassjButton2.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								EditPasswordJFrame passFrm=new EditPasswordJFrame(stuNo,InfoManageJFrame.this); 
								passFrm.setVisible(true);
								InfoManageJFrame.this.setVisible(false);
							}
						});
					}
					{
						jLabel1 = new JLabel();
						InfojPanel.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel1.setText("\u666e\u901a\u7528\u6237\u533a");
					}
					{
						SuperAdmjButton1 = new JButton();
						InfojPanel.add(SuperAdmjButton1, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						SuperAdmjButton1.setText("\u7528\u6237\u4fe1\u606f\u8bbe\u5b9a");
						if(type==3) SuperAdmjButton1.setEnabled(true);
						else SuperAdmjButton1.setEnabled(false);
						SuperAdmjButton1.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								GetAndEditInfoJFrm infoFrm1=new GetAndEditInfoJFrm(InfoManageJFrame.this); 
								infoFrm1.setVisible(true);
								InfoManageJFrame.this.setVisible(false);
							}
						});
					}
					{
						jLabel2 = new JLabel();
						InfojPanel.add(jLabel2, new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel2.setText("\u8d85\u7ea7\u7ba1\u7406\u5458\u4e13\u7528\u533a");
						if(type==3) jLabel2.setEnabled(true);
						else jLabel2.setEnabled(false);
					}
					{
						telejButton1 = new JButton();
						InfojPanel.add(telejButton1, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						telejButton1.setText("\u8054\u7cfb\u65b9\u5f0f\u67e5\u8be2");
						telejButton1.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								GetTeleJFrame teleFrm1=new GetTeleJFrame(InfoManageJFrame.this); 
								teleFrm1.setVisible(true);
								InfoManageJFrame.this.setVisible(false);
							}
						});
					}
					{
						exitjButton1 = new JButton();
						InfojPanel.add(exitjButton1, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						exitjButton1.setText("\u9000\u51fa");
						exitjButton1.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								window.setVisible(true);
								InfoManageJFrame.this.dispose();
							}
						});
					
					}

				}
			}
			pack();
			this.setSize(411, 381);
		} catch (ClassNotFoundException e) {
			System.err.println("database driver not found");
		}

		catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
		} 
		catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
