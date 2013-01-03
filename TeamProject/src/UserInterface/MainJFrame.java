

package UserInterface;

import infoManage.*;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

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
public class MainJFrame extends javax.swing.JFrame {
	private JPanel jPanel1;
	private JButton ExitjButton1;
	private JButton WorkjButton1;
	private JButton WagesjButton1;
	private JButton InfojButton1;
	infoManage.UserInfoList userList=new infoManage.UserInfoList(508030910,new infoManage.UserInfo(508030910, "陈斌", "13564900824", "上海市东川路800号",
			true, 2, 123456));

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainJFrame inst = new MainJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public MainJFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setTitle("***，欢迎你来到勤工助学系统");
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
					InfojButton1 = new JButton();
					jPanel1.add(InfojButton1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					InfojButton1.setText("\u4fe1\u606f\u7ba1\u7406");
					InfojButton1.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							InfoManageJFrame infoFrm=new InfoManageJFrame(); 
							infoFrm.setVisible(true);
						}
					});
				}
				/*{
					WagesjButton1 = new JButton();
					jPanel1.add(WagesjButton1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					WagesjButton1.setText("\u5de5\u8d44\u7ba1\u7406");
					WagesjButton1.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							WageDemo wagesFrm=new WageDemo(); 
							wagesFrm.setVisible(true);
						}
					});
				}*/
				{
					WorkjButton1 = new JButton();
					jPanel1.add(WorkjButton1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					WorkjButton1.setText("\u6392\u73ed\u7ba1\u7406");
					WorkjButton1.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							NewJFrame workFrm=new NewJFrame(); 
							workFrm.setVisible(true);
						}
					});
				}
				{
					ExitjButton1 = new JButton();
					jPanel1.add(ExitjButton1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					ExitjButton1.setText("\u9000\u51fa\u7cfb\u7edf");
					ExitjButton1.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							MainJFrame.this.dispose();
						}
					});
					
				}
			}
			pack();
			this.setSize(419, 311);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
