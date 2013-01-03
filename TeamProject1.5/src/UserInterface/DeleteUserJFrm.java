package UserInterface;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

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
public class DeleteUserJFrm extends javax.swing.JFrame {
	UserInfoList userInfo;
	private JPanel jPanel1;
	private JButton DelUserJButton1;
	private JButton ExitJButton1;
	private JFormattedTextField stuNoJTextField1;
	private JLabel stuNojLabel2;
	private JLabel jLabel1;
	InfoManageJFrame window;

	/**
	* Auto-generated main method to display this JFrame
	*/
	/*public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DeleteUserJFrm inst = new DeleteUserJFrm();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}*/
	
	public DeleteUserJFrm(InfoManageJFrame w) {
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
				jPanel1Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.rowHeights = new int[] {7, 7, 7, 7};
				jPanel1Layout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.columnWidths = new int[] {7, 7, 7, 7};
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setPreferredSize(new java.awt.Dimension(403, 273));
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText("\u6ce8\u9500\u7528\u6237");
				}
				{
					stuNojLabel2 = new JLabel();
					jPanel1.add(stuNojLabel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					stuNojLabel2.setText("\u5b66\u53f7\uff1a");
				}
				{
					stuNoJTextField1 = new JFormattedTextField(new MaskFormatter("##########"));
					jPanel1.add(stuNoJTextField1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					DelUserJButton1 = new JButton();
					jPanel1.add(DelUserJButton1, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					DelUserJButton1.setText("\u6ce8\u9500\u8be5\u7528\u6237");
					DelUserJButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							//TODO add your code for DelUserJButton1.actionPerformed
							if (userInfo.getUserName(stuNoJTextField1.getText()) != null){
								UserInfo_Delete_Conferm newWindow = new UserInfo_Delete_Conferm(userInfo, stuNoJTextField1.getText(), DeleteUserJFrm.this);
								DeleteUserJFrm.this.setEnabled(false);
								newWindow.setVisible(true);
								newWindow.setLocationRelativeTo(null);
								stuNoJTextField1.setText("          ");
							}
							else {
								UserInfo_User_not_found newWindow = new UserInfo_User_not_found(DeleteUserJFrm.this);
								DeleteUserJFrm.this.setEnabled(false);
								newWindow.setVisible(true);
								newWindow.setLocationRelativeTo(null);
								stuNoJTextField1.setText("          ");
							}
						}
					});
				}
				{
					ExitJButton1 = new JButton();
					jPanel1.add(ExitJButton1, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					ExitJButton1.setText("\u8fd4\u56de");
					ExitJButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							window.setVisible(true);				
							DeleteUserJFrm.this.dispose();
						}
					});
				}
			}
			pack();
			this.setSize(373, 319);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
