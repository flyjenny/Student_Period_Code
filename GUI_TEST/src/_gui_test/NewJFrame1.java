package _gui_test;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


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
public class NewJFrame1 extends javax.swing.JFrame {
	
	public static void main(String[] args){
		
		JFrame frame=new JFrame();
		JLabel Label = new JLabel("Hello Everyone!");
		frame.getContentPane().add(Label, BorderLayout.CENTER);
	//	Label.setText("jLabel1");
	//	WindowAdapter window=new WindowAdapter();
	//	frame.addWindowListener(window)
        frame.addWindowListener(new WindowAdapter( ){
        	public void windowClosing(WindowEvent e) {
			System.exit(0); }
        });
		frame.pack();
		frame.setVisible(true);
		
		
	}
}	
