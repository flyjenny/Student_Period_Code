package UserInterface;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UserInterface inst = new UserInterface();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
}
