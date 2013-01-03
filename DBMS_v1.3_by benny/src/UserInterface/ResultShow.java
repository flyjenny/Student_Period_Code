package UserInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import info.clearthought.layout.TableLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

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
public class ResultShow extends javax.swing.JFrame {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JScrollPane jScrollPane1;
	private JButton jButton1;
	private JTextPane jTextPane1;
	private String result;
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ResultShow inst = new ResultShow("abcd\nhello");
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public ResultShow(String text) {
		super();
		result=text;
		initGUI();
	}
	
	private void initGUI() {
		try {
			TableLayout thisLayout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}});
			thisLayout.setHGap(5);
			thisLayout.setVGap(5);
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1, "0, 0, 2, 3");
				{
					jTextPane1 = new JTextPane();
					jScrollPane1.setViewportView(jTextPane1);
					jTextPane1.setText(result);
				}
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, "3, 3");
				jButton1.setText("\u786e\u5b9a");
				jButton1.addActionListener(new ButtonAct());
			}
			pack();
			this.setSize(462, 376);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	class ButtonAct implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}

}
