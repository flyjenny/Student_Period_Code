package _gui_test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


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
public class NewJFrame{

	public static void main(String[ ] args) {	 //ͼ�ν������Ļ��ʾ����
        JFrame frame = new JFrame("Calendar");
        JTabbedPane pane=new JTabbedPane();
        final JTextField Text= new JTextField("    ѧ �� Java �� �� �� ��    ");
        JButton Button = new JButton("��ť");
        frame.setLayout(new FlowLayout());//���ò��ֹ�����
        frame.add(Text);
        frame.add(Button);
        frame.pack( );
        frame.setVisible(true);//ͼ�ν�����¼�������     
        Button.addActionListener(new ActionListener( ){
   	public void actionPerformed(ActionEvent e){
	  	Text.setText("����JTextField��JButton��һ��ʾ��");
		   }
	   });
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         }
}
