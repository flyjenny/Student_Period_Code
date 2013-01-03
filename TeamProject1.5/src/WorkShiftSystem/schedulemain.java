package WorkShiftSystem;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

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
public class schedulemain extends javax.swing.JFrame {
	private JButton jButton1;
	private JButton jButton3;
	private JButton jButton2;
	private WorkShiftList workList;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				schedulemain inst = new schedulemain();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public schedulemain() {
		super();
		workList = new WorkShiftList();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 7, 7, 20};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {7, 7, 7, 7};
			getContentPane().setLayout(thisLayout);
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("\u7a7a\u95f2\u65f6\u95f4\u8f93\u5165");
				jButton1.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton1ActionPerformed(evt); }});
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton2.setText("\u6392\u73ed\u8868");
				jButton2.setPreferredSize(new java.awt.Dimension(91, 22));
				jButton2.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton2ActionPerformed(evt); }});
			}
			{
				jButton3 = new JButton();
				getContentPane().add(jButton3, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton3.setText("\u67e5\u8be2\u7a7a\u95f2\u65f6\u95f4");
				jButton3.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {jButton3ActionPerformed(evt); }});
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	private void jButton1ActionPerformed(ActionEvent evt)//空闲时间录入
    {
		freeTimeInput inst = new freeTimeInput(schedulemain.this.workList);
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
    }
	private void jButton2ActionPerformed(ActionEvent evt)//排班表
    {
		scheduledTable inst = new scheduledTable(schedulemain.this.workList);
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
    }
	private void jButton3ActionPerformed(ActionEvent evt)//查询空闲时间
    {
		inquireFreetime inst = new inquireFreetime(schedulemain.this.workList);
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
    }
}
