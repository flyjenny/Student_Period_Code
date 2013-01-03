package UserInterface;
import infoManage.*;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	int stuNo=0;
	int type=0;

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
	public InfoManageJFrame(int id) {
		super();
		stuNo=id;
		initGUI();
	}
	
	private void initGUI() {
		try {
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
					InfojPanelLayout.rowHeights = new int[] {7, 7, 7, 7};
					InfojPanelLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
					InfojPanelLayout.columnWidths = new int[] {7, 7, 7, 7};
					InfojPanel.setLayout(InfojPanelLayout);
					{
						addNewUserjButton = new JButton();
						InfojPanel.add(addNewUserjButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						addNewUserjButton.setText("\u65b0\u589e\u7528\u6237");
						addNewUserjButton.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								SetNewUserFrm userFrm=new SetNewUserFrm(); 
								userFrm.setVisible(true);
							}
						});
					}
					{
						deleteUserjButton = new JButton();
						InfojPanel.add(deleteUserjButton, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						deleteUserjButton.setText("\u6ce8\u9500\u7528\u6237");
						deleteUserjButton.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								DeleteUserJFrm delFrm=new DeleteUserJFrm(); 
								delFrm.setVisible(true);
							}
						});
					}
					{
						mySetjButton1 = new JButton();
						InfojPanel.add(mySetjButton1, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						mySetjButton1.setText("\u6211\u7684\u4e2a\u4eba\u8bbe\u5b9a");
						mySetjButton1.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								setBasicInfojButton1.setVisible(true);
								ChanggePassjButton2.setVisible(true);
							}
						});
					}
					{
						setBasicInfojButton1 = new JButton();
						InfojPanel.add(setBasicInfojButton1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						setBasicInfojButton1.setText("\u57fa\u672c\u4fe1\u606f\u8bbe\u5b9a");
						setBasicInfojButton1.setVisible(false);
						setBasicInfojButton1.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								GetAndEditInfoJFrm infoFrm=new GetAndEditInfoJFrm(); 
								infoFrm.setVisible(true);
							}
						});
					}
					{
						ChanggePassjButton2 = new JButton();
						InfojPanel.add(ChanggePassjButton2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						ChanggePassjButton2.setText("\u4fee\u6539\u5bc6\u7801");
						ChanggePassjButton2.setVisible(false);
						ChanggePassjButton2.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								EditPasswordJFrame passFrm=new EditPasswordJFrame(); 
								passFrm.setVisible(true);
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
						SuperAdmjButton1.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e)
							{
								GetAndEditInfoJFrm infoFrm1=new GetAndEditInfoJFrm(); 
								infoFrm1.setVisible(true);
							}
						});
					}
					{
						jLabel2 = new JLabel();
						InfojPanel.add(jLabel2, new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jLabel2.setText("\u8d85\u7ea7\u7ba1\u7406\u5458\u4e13\u7528\u533a");
					}
				
				
				
				}
			}
			pack();
			this.setSize(411, 399);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
