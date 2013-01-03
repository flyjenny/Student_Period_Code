package WageSystem;

/**
 * Copyright ® 2010 Shanghai.SJTU.BennyChan
 */
import FileSystem.FileSystem;
import infoManage.UserInfoList;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.SwingUtilities;

import UserInterface.MainJFrame;
import java.awt.Component; 

import javax.swing.*;


import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.text.DecimalFormat;

import UserInterface.MainJFrame;
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
public class WageDemo extends javax.swing.JFrame {
	private JTabbedPane jTabbedPane;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private JButton jButton1;
	private JScrollPane jScrollPane2;
	private JButton jButton6;
	private JButton jButton5;
	private JButton jButton4;
	private JButton jButton3;
	private JButton jButton2;
	private JTable jTable1;
	private JTable jTable2;
	private JTextArea jTextArea1;
	private JPanel jPanel2;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WageDemo inst = new WageDemo();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setEnabled(true);
			}
		});
	}
	
	public WageDemo() {
		super();
		initGUI();
		
	}
	
	public void jButton1ActionPerform(ActionEvent evt)
	{
		FileSystem salary=new FileSystem();
		ListOfWage saralylist=salary.getSalary();
		jButton4.setVisible(false);
		String[] Names={"学号","姓名","工作时间（小时）","奖金（元）","罚金（元）","总计（元）","备注"};
		Object[][] Info2=new Object[1][7];		
		PersonalWageInfo temp=saralylist.getWageList().get(MainJFrame.stuNo+"");
		Object[][] Info=new Object[1][7];
		Info2[0][0]=temp.getId();
		Info2[0][1]=temp.getName();
		Info2[0][2]=temp.getHours();
		Info2[0][3]=temp.getBonus();
		Info2[0][4]=temp.getFine();
		Info2[0][5]=temp.getTotal();
		Info2[0][6]=temp.getRemark();
		TableModel Model2 = new DefaultTableModel(Info2,Names){
			public boolean isCellEditable(int row, int column) {
				return false;
				}
			};
		jTable1.setModel(Model2);
		FitTableColumns(jTable1);
	}
	
	public void jButton2ActionPerform(ActionEvent evt)
	{
		String[] Names={"学号","姓名","工作时间（小时）","奖金（元）","罚金（元）","总计（元）","备注"};
		FileSystem salary=new FileSystem();
		ListOfWage saralylist=salary.getSalary();
		int size=saralylist.getWageList().size();
  		Object[][] Info=new Object[size][7];
		int i=0;
		DecimalFormat format=new DecimalFormat("#0.00");
		Iterator iter=saralylist.search().entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry=(Map.Entry) iter.next();
			Info[i][0]=((PersonalWageInfo) entry.getValue()).getId();
			Info[i][1]=((PersonalWageInfo) entry.getValue()).getName();
			Info[i][2]=((PersonalWageInfo) entry.getValue()).getHours();
			Info[i][3]=format.format(((PersonalWageInfo) entry.getValue()).getBonus());
			Info[i][4]=format.format(((PersonalWageInfo) entry.getValue()).getFine());
			Info[i][5]=format.format(((PersonalWageInfo) entry.getValue()).getTotal());
			Info[i][6]=((PersonalWageInfo) entry.getValue()).getRemark();
			i++;
			}
			TableModel Model = new DefaultTableModel(Info,Names){
				public boolean isCellEditable(int row, int column) {
					return false;
					}
				};
			jTable1.setModel(Model);
			FitTableColumns(jTable1);
			jButton4.setVisible(true);
			
	}
	
	public void jButton3ActionPerform(ActionEvent evt) throws RowsExceededException, WriteException, IOException
	{
		WageSystem system=new WageSystem(8,10);
		ListOfWage List =new ListOfWage();
		int rows=jTable2.getRowCount();
		UserInfoList InfoList=new UserInfoList();
		for(int i=0;i<rows;i++)
		{
			PersonalWageInfo info=new PersonalWageInfo(InfoList.getUserType(Integer.parseInt(jTable2.getValueAt(i, 0).toString())),jTable2.getValueAt(i, 0).toString(),
													  jTable2.getValueAt(i,1)==null?"":jTable2.getValueAt(i, 1).toString(),
													  jTable2.getValueAt(i, 2)==null?0.0:Double.parseDouble(jTable2.getValueAt(i, 2).toString()),
												      jTable2.getValueAt(i, 3)==null?0.0:Double.parseDouble(jTable2.getValueAt(i, 3).toString()),
												      jTable2.getValueAt(i, 4)==null?0.0:Double.parseDouble(jTable2.getValueAt(i, 4).toString()),
												      jTable2.getValueAt(i, 2)==null?"":(String)jTable2.getValueAt(i, 5));
			List.makeList(info.getId(), info);
		}
		FileSystem file = new FileSystem();
		file.setSalary(List);
	
	}
	
	public void jButton4ActionPerform(ActionEvent evt) throws RowsExceededException, WriteException, IOException
	{
		FileChooser cho = new FileChooser(); 
		JFileChooser chooser = new JFileChooser("");
		chooser.setApproveButtonText("保存");
		chooser.addActionListener(new AbstractAction() {
		      public void actionPerformed(ActionEvent evt) {
		        JFileChooser chooser = (JFileChooser) evt.getSource();
		        if (JFileChooser.CANCEL_SELECTION.equals(evt.getActionCommand())) {
		          chooser.setVisible(false);
		        }
		      }
		    });
        Component comp =cho.getLabelForInChooser(chooser, "FileChooser.fileNameLabelText"); 
        if (comp instanceof JTextField) { 
            JTextField field = ((JTextField)comp); 
            field.setText("WageList.xls");
            field.setEditable(true); 

        } 
        chooser.showOpenDialog(null);
		FileSystem salary=new FileSystem();
		ListOfWage saralylist=salary.getSalary();
		try{
			saralylist.output(chooser.getSelectedFile().getPath());
		}catch(NullPointerException e){
		}
	}
	
	
	
	
	
	/*
	* 设置table列宽随内容调整
	*/
	public void FitTableColumns(JTable myTable) { 
		  JTableHeader header = myTable.getTableHeader();
		  int rowCount = myTable.getRowCount();
		  Enumeration columns = myTable.getColumnModel().getColumns();
		  while (columns.hasMoreElements()) {
		  TableColumn column = (TableColumn) columns.nextElement();
		  int col = header.getColumnModel().getColumnIndex(
		  column.getIdentifier());
		  int width = (int) myTable.getTableHeader().getDefaultRenderer()
		  .getTableCellRendererComponent(myTable,column.getIdentifier(), false, false, -1, col)
		  .getPreferredSize().getWidth();
		  for (int row = 0; row < rowCount; row++) {
		  int preferedWidth = (int) myTable.getCellRenderer(row, col)
		  .getTableCellRendererComponent(myTable,
		  myTable.getValueAt(row, col), false, false,
		  row, col).getPreferredSize().getWidth();
		  width = Math.max(width, preferedWidth);
		  }
		  header.setResizingColumn(column);
		  column.setWidth(width + myTable.getIntercellSpacing().width+5);
		  }
		}
	
	/*****************************************************************************/
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(thisLayout);
			{
				jTabbedPane = new JTabbedPane();
				getContentPane().add(jTabbedPane, BorderLayout.CENTER);
				{
					jPanel1 = new JPanel();
					GridBagLayout jPanel1Layout = new GridBagLayout();
					jTabbedPane.addTab("工资查询", null, jPanel1, null);
					jPanel1Layout.rowWeights = new double[] {0.0, 0.1, 0.0, 0.1};
					jPanel1Layout.rowHeights = new int[] {77, 7, 287, 7};
					jPanel1Layout.columnWeights = new double[] {0.1, 0.1, 0.1};
					jPanel1Layout.columnWidths = new int[] {7, 7, 7};
					jPanel1.setLayout(jPanel1Layout);
					{
						UserInfoList InfoList=new UserInfoList();
						String name=InfoList.getUserName(MainJFrame.stuNo);
						jTextArea1 = new JTextArea();
						jTextArea1.setEnabled(false);
						jPanel1.add(jTextArea1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						jTextArea1.setText("姓名："+name+"\n"+"学号："+MainJFrame.stuNo);
					}
					{
						jScrollPane1 = new JScrollPane(jTable1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	                               JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						jPanel1.add(jScrollPane1, new GridBagConstraints(0, 1, 3, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						jTable1 = new JTable();
						jScrollPane1.setViewportView(jTable1);
		//				FitTableColumns(jTable1);			
						
					}
					{
						jButton1 = new JButton();
						jPanel1.add(jButton1, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jButton1.setText("\u4e2a\u4eba\u5de5\u8d44\u67e5\u8be2");
						jButton1.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent evt){
								jButton1ActionPerform(evt);
								
							}
						});
					}
					{
						jButton2 = new JButton();
						jPanel1.add(jButton2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jButton2.setText("\u6240\u6709\u5458\u5de5\u5de5\u8d44\u67e5\u8be2");
						UserInfoList InfoList=new UserInfoList();
						if(InfoList.getUserType(MainJFrame.stuNo)==0) jButton2.setEnabled(false);
						jButton2.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent evt){
								jButton2ActionPerform(evt);
								
							}
						});
						
					}
					{
						jButton4 = new JButton();
						jPanel1.add(jButton4, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jButton4.setText("\u5bfc\u51fa\u5230excel\u8868\u683c");
						jButton4.setVisible(false);
						jButton4.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent evt){
								try {
									jButton4ActionPerform(evt);
								} catch (RowsExceededException e) {
									e.printStackTrace();
								} catch (WriteException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
								
							}
						});
					}
					{
						jButton5 = new JButton();
						jPanel1.add(jButton5, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 300, 0, 0), 0, 0));
						jButton5.setText("\u8fd4\u56de");
						jButton5.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent evt){
								WageDemo.this.dispose();
								
							}
						});
					}
				}
				{
					jPanel2 = new JPanel();
					GridBagLayout jPanel2Layout = new GridBagLayout();
					jTabbedPane.addTab("工资录入", null, jPanel2, null);
					jPanel2Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
					jPanel2Layout.rowHeights = new int[] {7, 7, 7, 7};
					jPanel2Layout.columnWeights = new double[] {0.1, 0.1, 0.0, 0.1};
					jPanel2Layout.columnWidths = new int[] {7, 7, 455, 7};
					jPanel2.setLayout(jPanel2Layout);
					
					{
						FileSystem file=new FileSystem();
						ListOfWage saralylist=file.getSalary();
						int length=file.getWorkshift().getLaborID().length;
						jScrollPane2 = new JScrollPane(jTable2,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	                               JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						jPanel2.add(jScrollPane2, new GridBagConstraints(0, 0, 3, 4, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
						{
							
							String[] head={"学号","姓名","工作时间(小时)","奖金(元)","罚金(元)","备注"};
							Object[][] info=new Object[length][6];
							for(int j=0;j<length;j++)
							{
								info[j][0]=file.getWorkshift().getLaborID()[j];
								info[j][1]=file.getWorkshift().getLaborName()[j];
								info[j][2]=file.getWorkshift().getLabor()[j];
							}
							if (saralylist!=null){
								for (int i=0;i<length;i++){
									info[i][3]=saralylist.getWageList().get(info[i][0]+"").getBonus();
									info[i][4]=saralylist.getWageList().get(info[i][0]+"").getFine();
									info[i][5]=saralylist.getWageList().get(info[i][0]+"").getRemark();
								}
							}
							TableModel jTable1Model =new DefaultTableModel(info,head){
											public boolean isCellEditable(int row, int column) {
													if (column == 0||column==1) { return false; }
													 return true;
												}
										};
							
							jTable2 = new JTable();
							
							jScrollPane2.setViewportView(jTable2);
							jTable2.setModel(jTable1Model);
							FitTableColumns(jTable2);
												
						}
					}
					{
						jButton3 = new JButton();
						jPanel2.add(jButton3, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jButton3.setText("\u4fdd\u5b58");
						jButton3.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent evt){
								try {
									jButton3ActionPerform(evt);
								} catch (RowsExceededException e) {
									e.printStackTrace();
								} catch (WriteException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
								
							}
						});
						FileSystem file=new FileSystem();
						ListOfWage saralylist=file.getSalary();
						if (saralylist==null){
						jButton3.doClick();
						}
					}
					
					{
						jButton6 = new JButton();
						jPanel2.add(jButton6, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						jButton6.setText("\u8fd4\u56de");
						jButton6.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent evt){
								WageDemo.this.dispose();
								
							}
						});
					}
				}
			}
			pack();
			setSize(1000, 600);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

}
