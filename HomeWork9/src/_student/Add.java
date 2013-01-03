package _student;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import javax.swing.WindowConstants;


/**
* 用户添加时候弹出来的输入窗口，提供用户输入name，birthday，homeAddress，classNumber
* 并将值传回StudentDemo，记住命令的参数
*/
public class Add extends javax.swing.JFrame {
	public  JButton jButton1;
	private JTextField jTextField2;
	private JTextPane jTextPane1;
	private JFormattedTextField jFormattedTextField1;		//Date的格式化输入
	private JTextField jTextField1;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JButton jButton2;
	static Object[] in;										//记住student的4个参数
	/**
	* Auto-generated main method to display this JFrame
	*/
/*	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Add inst = new Add();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
		
	}
*/	
	public Add() {
		super();
		initGUI();
		in =new Object[4];
	}
	
	public void setin(Object[] input){
		in=input;
	}
	
	public void jButton1ActionPerform(ActionEvent evt){
		Object[] input={jTextField1.getText(),jFormattedTextField1.getText(),jTextPane1.getText(),jTextField2.getText()};
		this.setin(input);
		StudentDemo.addShow();
		this.setVisible(false);
		this.dispose();
		
	}
	
	public void jButton2ActionPerform(ActionEvent evt){
		this.dispose();
		
	}
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 20, 20, 7, 7};
			thisLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.1};
			thisLayout.columnWidths = new int[] {125, 122, 111, 7};
			getContentPane().setLayout(thisLayout);
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton1.setText("\u786e\u5b9a");
				jButton1.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){

							jButton1ActionPerform(evt);
						
						
					}
				});
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton2.setText("\u53d6\u6d88");
				jButton2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						jButton2ActionPerform(evt);
						
					}
				});
			}
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("Name:");
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText("Birthday:");
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel3.setText("HomeAddress:");
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel4.setText("ClassNumber:");
			}
			{
				jTextField1 = new JTextField();
				getContentPane().add(jTextField1, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd");//格式化输入Date
				jFormattedTextField1 = new JFormattedTextField(format);
				jFormattedTextField1.setText("yyyy-MM-dd");
				getContentPane().add(jFormattedTextField1, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jTextPane1 = new JTextPane();
				getContentPane().add(jTextPane1, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jTextField2 = new JTextField();
				getContentPane().add(jTextField2, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			pack();
			setSize(500, 400);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
