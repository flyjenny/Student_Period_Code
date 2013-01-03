package _searchInfo;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

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
public class Interface extends javax.swing.JFrame {
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JButton jButton1;
	private JButton jButton4;
	private JButton jButton3;
	private JButton jButton2;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Interface inst = new Interface();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public Interface() {
		super();
		initGUI();
	}
	
	
	
	//选择查询Volunteer信息
	public void jButton1ActionPerform(ActionEvent evt)
	{
		Volunteer volunteer=new Volunteer();
		volunteer.setLocationRelativeTo(null);
		volunteer.setVisible(true);
		this.setVisible(false);
	}
	//选择查询武警信息
	public void jButton2ActionPerform(ActionEvent evt)
	{
		Police police=new Police();
		police.setLocationRelativeTo(null);
		police.setVisible(true);
		this.setVisible(false);
	}
	//选择查询广播人员信息
	public void jButton3ActionPerform(ActionEvent evt)
	{
		BroadCaster broadcaster=new BroadCaster();
		broadcaster.setLocationRelativeTo(null);
		broadcaster.setVisible(true);
		this.setVisible(false);
	}
	//选择查询医护人员信息
	public void jButton4ActionPerform(ActionEvent evt)
	{
		HealthCare healthcare=new HealthCare();
		healthcare.setLocationRelativeTo(null);
		healthcare.setVisible(true);
		this.setVisible(false);
	}
	//可视化窗口
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.0, 0.0, 0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {49, 67, 7, 20, 20, 7};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {7, 7, 7, 7};
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(thisLayout);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("\u6b22\u8fce\u767b\u9646\u4e16\u535a\u4fe1\u606f\u67e5\u8be2\u7cfb\u7edf");
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText("\u8bf7\u9009\u62e9\u60a8\u9700\u8981\u67e5\u8be2\u7684\u4fe1\u606f");
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("1\u3001\u5fd7\u613f\u8005\u4fe1\u606f\u67e5\u8be2");
				jButton1.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton1ActionPerform(evt);
						
					}
				});
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jButton2.setText("2\u3001\u6b66\u8b66\u4fe1\u606f\u67e5\u8be2");
				jButton2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton2ActionPerform(evt);
						
					}
				});
			}
			{
				jButton3 = new JButton();
				getContentPane().add(jButton3, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jButton3.setText("3\u3001\u5e7f\u64ad\u4eba\u5458\u4fe1\u606f\u67e5\u8be2");
				jButton3.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton3ActionPerform(evt);
						
					}
				});
			}
			{
				jButton4 = new JButton();
				getContentPane().add(jButton4, new GridBagConstraints(1, 5, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				jButton4.setText("4\u3001\u533b\u52a1\u4eba\u5458\u4fe1\u606f\u67e5\u8be2");
				jButton4.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton4ActionPerform(evt);
						
					}
				});
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
