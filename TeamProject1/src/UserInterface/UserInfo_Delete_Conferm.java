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
public class UserInfo_Delete_Conferm extends javax.swing.JFrame {
	DeleteUserJFrm Window;
	UserInfoList List;
	int ID;
	private JPanel jPanel1;
	private JButton jButton1;
	private JLabel jLabel2;
	private JButton jButton2;
	private JLabel jLabel1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	
	public UserInfo_Delete_Conferm(UserInfoList list, int id, DeleteUserJFrm window) {
		super();
		List = list;
		ID = id;
		Window = window;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				GridBagLayout jPanel1Layout = new GridBagLayout();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setPreferredSize(new java.awt.Dimension(355, 153));
				jPanel1Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.rowHeights = new int[] {7, 7, 7, 7};
				jPanel1Layout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.columnWidths = new int[] {7, 7, 7, 7};
				jPanel1.setLayout(jPanel1Layout);
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel2.setText("\u59d3\u540d+\u5b66\u53f7");
					jLabel2.setText(List.getUserName(ID)+" "+List.getUserID(ID));
				}
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText("\u786e\u5b9a\u6ce8\u9500\u8be5\u7528\u6237?");
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jButton1.setText("\u786e\u5b9a");
					jButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("jButton1.actionPerformed, event="+evt);
							//TODO add your code for jButton1.actionPerformed
							List.deleteUser(ID);
							jLabel1.setText("É¾³ý³É¹¦");
							jButton1.setEnabled(false);
						}
					});
				}
				{
					jButton2 = new JButton();
					jPanel1.add(jButton2, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jButton2.setText("\u8fd4\u56de");
					jButton2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("jButton2.actionPerformed, event="+evt);
							//TODO add your code for jButton2.actionPerformed
							List.setUserList();
							Window.setEnabled(true);
							UserInfo_Delete_Conferm.this.dispose();
						}
					});
				}
			}
			pack();
			this.setSize(335, 167);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
}
