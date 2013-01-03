package UserInterface;

import info.clearthought.layout.TableLayout;
import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

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
public class FileChooser extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public File file;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel jPanel1;
	private JFileChooser jFileChooser1;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FileChooser inst = new FileChooser();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public FileChooser() {
		super();
		file = null;
		initGUI();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				TableLayout jPanel1Layout = new TableLayout(new double[][] {
						{ TableLayout.FILL, TableLayout.FILL, TableLayout.FILL,
								TableLayout.FILL },
						{ TableLayout.FILL, TableLayout.FILL, TableLayout.FILL,
								TableLayout.FILL } });
				jPanel1Layout.setHGap(5);
				jPanel1Layout.setVGap(5);
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				{
					jFileChooser1 = new JFileChooser();
					jPanel1.add(jFileChooser1, "0, 0, 3, 3");
					jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int option = jFileChooser1.showOpenDialog(FileChooser.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						file = jFileChooser1.getSelectedFile();
						// String path = file.getAbsolutePath();
						// System.out.println(path);
						this.setVisible(false);
						dispose();
					}
					if (option == JFileChooser.CANCEL_OPTION) {
						dispose();
					}
				}
			}
			pack();
			this.setSize(624, 416);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

}
