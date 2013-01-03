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

	public static void main(String[ ] args) {	 //图形界面的屏幕显示部分
        JFrame frame = new JFrame("Calendar");
        JTabbedPane pane=new JTabbedPane();
        final JTextField Text= new JTextField("    学 好 Java 用 处 大 ！    ");
        JButton Button = new JButton("按钮");
        frame.setLayout(new FlowLayout());//设置布局管理器
        frame.add(Text);
        frame.add(Button);
        frame.pack( );
        frame.setVisible(true);//图形界面的事件处理部分     
        Button.addActionListener(new ActionListener( ){
   	public void actionPerformed(ActionEvent e){
	  	Text.setText("这是JTextField和JButton的一个示例");
		   }
	   });
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         }
}
