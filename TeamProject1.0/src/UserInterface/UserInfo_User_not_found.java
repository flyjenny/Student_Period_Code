package UserInterface;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

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
public class UserInfo_User_not_found extends javax.swing.JFrame {
	private DeleteUserJFrm Window1;
	private GetAndEditInfoJFrm Window2;
	private JPanel jPanel1;
	private JButton jButton1;
	private JLabel jLabel1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	
	public UserInfo_User_not_found(DeleteUserJFrm window) {
		super();
		Window1 = window;
		initGUI();
	}
	
	public UserInfo_User_not_found(GetAndEditInfoJFrm window) {
		super();
		Window2 = window;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				GridBagLayout jPanel1Layout = new GridBagLayout();
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText("\u8be5\u7528\u6237\u4e0d\u5b58\u5728");
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jButton1.setText("\u786e\u5b9a");
					jButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("jButton1.actionPerformed, event="+evt);
							//TODO add your code for jButton1.actionPerformed
							if (Window1!=null)
								Window1.setEnabled(true);
							if (Window2!=null)
								Window2.setEnabled(true);
							UserInfo_User_not_found.this.dispose();
						}
					});
				}
					jPanel1Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
					jPanel1Layout.rowHeights = new int[] {7, 7, 7, 7};
					jPanel1Layout.columnWeights = new double[] {0.1, 0.1, 0.1};
					jPanel1Layout.columnWidths = new int[] {7, 7, 7};
			}
			pack();
			this.setSize(367, 176);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
