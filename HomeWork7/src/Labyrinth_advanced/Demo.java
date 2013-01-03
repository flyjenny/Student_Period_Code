package Labyrinth_advanced;
import info.clearthought.layout.TableLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.WindowConstants;


/**
* @author 陈斌 5080309505 
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


//二维按键数组监听
class VertexSelectListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		System.out.println("VertexSelectListener called");
		String temp = e.getActionCommand();
		Demo.Vertex[0] = temp.charAt(0) - 48;
		Demo.Vertex[1] = temp.charAt(1) - 48;
//		Demo.jButton[Demo.Vertex[0]][Demo.Vertex[1]].setForeground(Color.RED);
		System.out.println("Vtx: ("+Demo.Vertex[0]+","+Demo.Vertex[1]+")");
		Demo.VertexOK++;
		if ((Demo.MazeOK)&&(Demo.CashOK)&&(Demo.VertexOK == 4)) {//当迷宫、坐标、现金设置好后ready
			Demo.Ready = true;
//			Demo.Execute_Button.setEnabled(true);
		}
	}
}
public class Demo extends javax.swing.JFrame {
	static int[] Vertex = new int[2];
	static int[][]Maze = new int[4][5];
	static int[] Cash = new int[3];
	static boolean MazeOK, CashOK;
	static int VertexOK;
	static boolean Ready;
	
	private JPanel jPanel1;
	private JButton CreateMaze_random;
	private JPanel jPanel2;
	private JButton Cash_conferm;
	private JLabel Cash_set_lable;
	private JTextField Cash2_text;
	private JLabel Cash2_lable;
	private JTextField Cash0_text;
	private JLabel Cash1_lable;
	private JTextField Cash1_text;
	private JLabel Cash0_lable;
	private JPanel jPanel3;
	private VertexSelectListener Press_Button;
	private JButton jButton[][];
	private JTextField jText[][];

	/**
	* Auto-generated main method to display this JFrame
	*/
	
	public Demo() {
		super();
		Vertex[0] = -1;
		Vertex[1] = -1;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				TableLayout jPanel1Layout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}, {TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}});
				jPanel1Layout.setHGap(5);
				jPanel1Layout.setVGap(5);
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setPreferredSize(new java.awt.Dimension(293, 234));
				{
					Press_Button = new VertexSelectListener();
				}
				{
					jButton = new JButton[4][5];		//4*5的迷宫按钮二维数组
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 5; j++){
							jButton[i][j] = new JButton();
							jPanel1.add(jButton[i][j], j+", "+i);
							jButton[i][j].setActionCommand(i+""+j);
							jButton[i][j].addActionListener(Press_Button);
							jButton[i][j].setVisible(true);
						}
					}
				}
				{
					jText = new JTextField[4][5];		//提供用户手动输入迷宫
					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 5; j++){
							jText[i][j] = new JTextField();
							jPanel1.add(jText[i][j], j+", "+i);
							jText[i][j].setActionCommand(i+""+j);
						}
					}
				}
			}
			{
				jPanel2 = new JPanel();
				getContentPane().add(jPanel2, BorderLayout.SOUTH);
				{
					CreateMaze_random = new JButton();
					jPanel2.add(CreateMaze_random);
					CreateMaze_random.setText("自动生成迷宫");
					CreateMaze_random.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("CreateMaze_random.actionPerformed, event="+evt);
							//TODO add your code for CreateMaze_random.actionPerformed
							Random seed = new Random();
							for (int x = 0; x < 4; x++) {			//自动生成的迷宫格子要在-14~10的范围内
								for (int y = 0; y < 5; y++){
									if (!jText[x][y].getText().isEmpty()&&(Integer.parseInt(jText[x][y].getText())>-14)&&(Integer.parseInt(jText[x][y].getText())<10))
										Maze[x][y] = Integer.parseInt(jText[x][y].getText());
									else Maze[x][y] = seed.nextInt(23) - 13;
									
									if (Maze[x][y] > -10)
										jButton[x][y].setText(Maze[x][y]+"");
									else if (Maze[x][y] == -10)		//Jefferson专属格子
										jButton[x][y].setText("J");
									else if (Maze[x][y] == -11)		//Bob专属格子
										jButton[x][y].setText("B");
									else if (Maze[x][y] == -12)		//Alice专属格子
										jButton[x][y].setText("A");
									else jButton[x][y].setText("*");//墙壁，任何人无法进入
									jText[x][y].setVisible(false);
									jButton[x][y].setVisible(true);
								}
							}
							MazeOK = true;
							if (MazeOK&&CashOK&&(VertexOK == 4)) {
								Ready = true;
//								Execute_Button.setEnabled(true);
							}
						}
					});
				}
			}
			{
				jPanel3 = new JPanel();
				GridBagLayout jPanel3Layout = new GridBagLayout();
				jPanel3Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1};
				jPanel3Layout.rowHeights = new int[] {20, 7, 7, 7, 7};
				jPanel3Layout.columnWeights = new double[] {0.1, 0.1};
				jPanel3Layout.columnWidths = new int[] {7, 7};
				jPanel3.setLayout(jPanel3Layout);
				getContentPane().add(jPanel3, BorderLayout.EAST);
				{
					Cash0_lable = new JLabel();
					jPanel3.add(Cash0_lable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					Cash0_lable.setText("Jefferson");
				}
				{
					Cash0_text = new JTextField();
					jPanel3.add(Cash0_text, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					Cash0_text.setPreferredSize(new java.awt.Dimension(47, 24));
				}
				{
					Cash1_lable = new JLabel();
					jPanel3.add(Cash1_lable, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					Cash1_lable.setText("Bob");
				}
				{
					Cash1_text = new JTextField();
					jPanel3.add(Cash1_text, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					Cash1_text.setPreferredSize(new java.awt.Dimension(49, 24));
				}
				{
					Cash2_lable = new JLabel();
					jPanel3.add(Cash2_lable, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					Cash2_lable.setText("Alice");
				}
				{
					Cash2_text = new JTextField();
					jPanel3.add(Cash2_text, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					Cash2_text.setPreferredSize(new java.awt.Dimension(44, 24));
				}
				{
					Cash_set_lable = new JLabel();
					jPanel3.add(Cash_set_lable, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					Cash_set_lable.setText("\u521d\u59cb\u73b0\u91d1\u8bbe\u5b9a");
				}
				{
					Cash_conferm = new JButton();
					jPanel3.add(Cash_conferm, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					Cash_conferm.setText(" 设定现金");
					Cash_conferm.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							System.out.println("Cash_conferm.actionPerformed, event="+evt);
							Cash[0] = Integer.parseInt(Cash0_text.getText());
							Cash[1] = Integer.parseInt(Cash1_text.getText());
							Cash[2] = Integer.parseInt(Cash2_text.getText());
							CashOK = true;
							if (MazeOK&&CashOK&&(VertexOK == 4)) {
								Ready = true;
	//							Execute_Button.setEnabled(true);
							}
						}
					});
				}
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
