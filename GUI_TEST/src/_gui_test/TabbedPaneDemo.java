package _gui_test;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
/**
 * <p>Title: ѡ���ʾ</p>
 * <p>Description: ������һ��ѡ���ʾ�������ͬ�Ŀ�Ƭ����ʾ�����ݲ�ͬ</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Filename: TabbedPaneDemo.java</p>
 * @version 1.0
 */
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
public class TabbedPaneDemo extends JPanel {
    public TabbedPaneDemo() {
    	{
    		JTabbedPane tabbedPane = new JTabbedPane();
    		this.add(tabbedPane);
    		tabbedPane.setSelectedIndex(0);
    		tabbedPane.setPreferredSize(new java.awt.Dimension(487, 357));
    		{
    			Component panel1 = makeTextPanel("#��һ����Ƭ#");
    			tabbedPane.addTab("One", icon, panel1,
    					"��һ����Ƭ��ʾ��Ϣ��");
    			{
    				JLabel filler = new JLabel(text);
    				panel.add(filler);
    				filler.setHorizontalAlignment(JLabel.CENTER);
    			}
    		}
    		{
    			Component panel2 = makeTextPanel("##�ڶ�����Ƭ##");
    			tabbedPane.addTab("Two", icon, panel2,
    					"�ڶ�����Ƭ��ʾ��Ϣ��");
    			panel.setPreferredSize(new java.awt.Dimension(491, 346));
    		}
    		{
    			Component panel3 = makeTextPanel("###��������Ƭ###");
    			tabbedPane.addTab("Three", icon, panel3,
    					"��������Ƭ��ʾ��Ϣ��");
    		}
    	}
        super(new GridLayout(1, 1));

        ImageIcon icon = createImageIcon("images/middle.gif");

        //��ѡ���ӵ�panl��
    }
/**
 *<br>����˵���������Ϣ��ѡ���
 *<br>���������String text ��ʾ����Ϣ����
 *<br>�������ͣ�Component ��Ա����
 */
    protected Component makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        return panel;
    }
/**
 *<br>����˵�������ͼƬ
 *<br>���������String path ͼƬ��·��
 *<br>�������ͣ�ImageIcon ͼƬ����
 */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TabbedPaneDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        //ʹ��Swing��������
        JFrame.setDefaultLookAndFeelDecorated(true);

        //��������
        JFrame frame = new JFrame("TabbedPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TabbedPaneDemo(),
                                 BorderLayout.CENTER);

        //��ʾ����
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}