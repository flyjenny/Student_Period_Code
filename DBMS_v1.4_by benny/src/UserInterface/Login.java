package UserInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import info.clearthought.layout.TableLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import UserInterface.UserInterface.Button3Act;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class Login extends javax.swing.JFrame {

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPasswordField jPasswordField1;
	private JButton jButton1;
	private JLabel jLabel3;
	private JPanel jPanel1;
	private JButton jButton2;
	private JTextField jTextField1;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Login inst = new Login();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public Login() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			TableLayout thisLayout = new TableLayout(new double[][] {
					{ TableLayout.FILL, TableLayout.FILL, TableLayout.FILL,
							TableLayout.FILL },
					{ 90.0, 24.0, 25.0, TableLayout.FILL } });
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("数据库登录");
			{
				jPanel1 = new JPanel();

				TableLayout jPanel1Layout = new TableLayout(new double[][] {
						{ TableLayout.FILL, TableLayout.FILL, TableLayout.FILL,
								TableLayout.FILL },
						{ 78.0, 34.0, 32.0, TableLayout.FILL } });
				jPanel1Layout.setHGap(5);
				jPanel1Layout.setVGap(5);
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, "0, 0, 3, 3");
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, "0,1,c,f");
					jLabel1.setText("\u7528\u6237\u540d\uff1a");
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2, "0,2,c,f");
					jLabel2.setText("\u5bc6\u7801\uff1a");
				}
				{
					jTextField1 = new JTextField();
					jPanel1.add(jTextField1, "1, 1, 2, 1");
				}
				{
					jPasswordField1 = new JPasswordField();
					jPanel1.add(jPasswordField1, "1, 2, 2, 2");
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1, "1,3,f,c");
					jButton1.setText("\u786e\u5b9a");
					jButton1.addActionListener(new myConfirm());
				}
				{
					jButton2 = new JButton();
					jPanel1.add(jButton2, "2,3,f,c");
					jButton2.setText("\u53d6\u6d88");
					jButton2.addActionListener(new myExit());
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3, "1, 0, 2, 0");
				}
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}
	class myConfirm implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			jLabel2.setText("");
			String username=jTextField1.getText();
			String password=jPasswordField1.getText();
			System.out.println(username);
			System.out.println(password);
			if(false){
				UserInterface inst = new UserInterface();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				dispose();
			}
			else{
				jLabel3.setText("用户名或密码出错");
			}
		}
	}
}

class myExit implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}
}