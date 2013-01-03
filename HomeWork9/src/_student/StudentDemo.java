package _student;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
public class StudentDemo extends javax.swing.JFrame {
	private JScrollPane jScrollPane;
	private JButton exitButton;
	private JButton commit;
	private JButton deleteButton;
	private JButton addButton;
	private static JTable table;
	static Add add;					  //Add类型数据成员，记住add对象传回来的参数
	static ArrayList<String> command;//记住 用户操作所有命令，在最后提交是逐条运行
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				StudentDemo inst = new StudentDemo();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				add.setVisible(false);
				
			}
		});
	}
	
	public static Connection connect()
	 {
		 Connection conn=null;
	 try{ //装载驱动
	  Class.forName("com.mysql.jdbc.Driver");
	  //连接数据库
	  //其中localized为主机名，test是数据库名，root为用户名
	  conn = DriverManager.getConnection("jdbc:mysql://localhost/stu?user=root&&password=1234");
	  }
	  catch(ClassNotFoundException e)
	  {
	   System.err.println("database driver not found");
	  }
	  
	  catch(SQLException e)
	  {
	   System.err.println("SQLException: " + e.getMessage());
	   System.err.println("SQLState: " + e.getSQLState());
	   System.err.println("VendorError: " + e.getErrorCode());
	  } 
	  return conn;
	  }
	
	
	public StudentDemo() {
		super();
		initGUI();
		add=new Add();
		command=new ArrayList<String>();
	}
	
	public JTable getTable(){
		return table;
	}
	
	public void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {7, 7, 7, 7};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.0, 0.1};
			thisLayout.columnWidths = new int[] {7, 7, 191, 7};
			getContentPane().setLayout(thisLayout);
			{
				jScrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				getContentPane().add(jScrollPane, new GridBagConstraints(0, 0, 3, 4, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				getContentPane().add(getAddButton(), new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				getContentPane().add(getDeleteButton(), new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				getContentPane().add(getCommit(), new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				getContentPane().add(getExitButton(), new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				{
					String [] head={"name","birthday","homeAddress","classNumber"};
					
					Statement stat=this.connect().createStatement();
					ResultSet rs = stat.executeQuery("select name,birthday,homeAddress,classNumber from student");//执行查询操作
					ResultSetMetaData md = rs.getMetaData();
					int columnCount = md.getColumnCount();
					stat.getMaxRows();
					rs.last();
					int rowCount=rs.getRow();
					Object[][] info=new Object[rowCount][columnCount];
					int m=0;
					if(rs.first())//遍历
					  {
					   do
					   {
					    for(int i=0; i<columnCount; i++){
					    	info[m][i]=rs.getString(i+1);
					    }
					    System.out.println();
					    m++;
					   }while(rs.next());
					  }
					  rs.close();
					  this.connect().close();
					  
					TableModel tableModel =new DefaultTableModel(info,head);
					table = new JTable();
					jScrollPane.setViewportView(table);
					table.setModel(tableModel);
					table.getModel().addTableModelListener(new TableModelListener() { 
						 public void tableChanged(TableModelEvent e) {
							 tableChagedAction(e);
						 }
						 });
					this.addWindowListener(new WindowAdapter(){
						public void windowclosing(WindowEvent e){
							add.dispose();
							System.exit(0);
						}
					});
				}
			}
			pack();
			setSize(600, 400);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	private JButton getAddButton() {
		if(addButton == null) {
			addButton = new JButton();
			addButton.setText("\u6dfb\u52a0");
			addButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					addButtonActionPerform(evt);
					
				}
			});
		}
		return addButton;
	}
	
	private JButton getDeleteButton() {
		if(deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("\u5220\u9664");
			deleteButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
						deleteButtonActionPerform(evt);
				}
			});
		}
		return deleteButton;
	}
	
	private JButton getCommit() {
		if(commit == null) {
			commit = new JButton();
			commit.setText("\u63d0\u4ea4");
			commit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					commitActionPerform(evt);
				}
			});
		}
		return commit;
	}
	private JButton getExitButton() {
		if(exitButton == null) {
			exitButton = new JButton();
			exitButton.setText("\u9000\u51fa");
			exitButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					exitButtonActionPerform(evt);
				}
			});
		}
		return exitButton;
	}
	
	/**
	 * 刷新table显示
	 * @throws SQLException
	 */
	public void reflash() throws SQLException{
		String [] head={"name","birthday","homeAddress","classNumber"};
		
		Statement stat=this.connect().createStatement();
		ResultSet rs = stat.executeQuery("select name,birthday,homeAddress,classNumber from student");//执行查询操作
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		stat.getMaxRows();
		rs.last();
		int rowCount=rs.getRow();
		Object[][] info=new Object[rowCount][columnCount];
		int m=0;
		if(rs.first())//遍历
		  {
		   do
		   {
		    for(int i=0; i<columnCount; i++){
		    	info[m][i]=rs.getString(i+1);
		    }
		    System.out.println();
		    m++;
		   }while(rs.next());
		  }
		  rs.close();
		  this.connect().close();
		  TableModel tableModel =new DefaultTableModel(info,head);
		  table.setModel(tableModel);
		  table.getModel().addTableModelListener(new TableModelListener() { 
				 public void tableChanged(TableModelEvent e) {
					 tableChagedAction(e);
				 }
				 });
	}
	/**
	 * 添加后显示在table上
	 */
	public static void addShow(){
		DefaultTableModel   dtm=(DefaultTableModel)table.getModel();
		Object[] in=add.in;
		dtm.addRow(in);
		addDatabase();
	}
	/**
	 * 记住增加的命令，增加的四个参数由Add返回
	 */
	public static void addDatabase(){
		String additem="insert into stu.student " +
		 "(name, birthday,homeAddress,classNumber) " +
		 "values('"+add.in[0]+"','"+
		 add.in[1]+"','"+
		 add.in[2]+"','"+
		 add.in[3]+"')";
		command.add(additem);
	}
	
	public void addButtonActionPerform(ActionEvent evt){
		add.setVisible(true);

	}
	/**
	 * 对于整个Table的内容改变的监听，把改变的情况记住传入ArrayList<String>
	 * 
	 * 
	 */
	public void tableChagedAction(TableModelEvent e){
		int rowcount=table.getSelectedRow();		//记住改变的行
		int columncount=e.getColumn();				//记住改变的列
		if (columncount!=-1){						//非删除或者添加操作
	
		String change ="update stu.student set "+table.getColumnName(columncount)+
						"='"+table.getValueAt(rowcount, columncount)+"' where name='"+
						table.getValueAt(rowcount,0)+"'"; 
		command.add(change);						//将命令添加到command中
		}
	}
	/**
	 * 对于删除的监听，与数据库中对应起来，将命令记住，包括多列同时删除
	 * @param evt
	 */
	public void deleteButtonActionPerform(ActionEvent evt){
		int[] selections=table.getSelectedRows();
		
		for (int i = 0; i < selections.length; i++) {  
		    selections[i] = table.convertRowIndexToModel(selections[i]);  
		}
		for (int i : selections) {  
		    //获取选择行的数据  
		    String delete="delete from stu.student where name='"+table.getValueAt(i, 0)+"'";
		    command.add(delete);
	//	    state.executeUpdate(delete);
		}  
		DefaultTableModel df = (DefaultTableModel) table.getModel();  
		//反向删除  
		for (int i = selections.length; i > 0; i--) {  
		    df.removeRow(table.getSelectedRow());  
		}
	}
	
	/**
	 * 提交按钮的监听，有异常抛出rollback，成功commit
	 * @param evt
	 */
	public void commitActionPerform(ActionEvent evt){
		 Connection conn=null;
		 try{ //装载驱动
		  Class.forName("com.mysql.jdbc.Driver");
		  //连接数据库
		  //其中localized为主机名，test是数据库名，root为用户名
		  conn = DriverManager.getConnection("jdbc:mysql://localhost/stu?user=root&&password=1234");
		  }
		  catch(ClassNotFoundException e)
		  {
		   System.err.println("database driver not found");
		  }
		  
		  catch(SQLException e)
		  {
		   System.err.println("SQLException: " + e.getMessage());
		   System.err.println("SQLState: " + e.getSQLState());
		   System.err.println("VendorError: " + e.getErrorCode());
		  } 
		  Statement stat=null;
		  try {
			conn.setAutoCommit(false);	//设置成不是自动提交到数据库
			stat = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		  
		
		  try{
		  for(String c:command){
			  System.out.println(c);
			  stat.executeUpdate(c);	//将之前记住每一句命令提交
		  }
		  conn.commit();				//提交到数据库
		  JOptionPane.showMessageDialog(null, "提交成功");

		  this.reflash();			//重新从数据库中遍历刷新显示
		  }
		  catch(SQLException e){
			  System.out.println("Commit Err");
			  JOptionPane.showMessageDialog(null, "提交失败");
			  try {
				 
				conn.rollback();		//异常抛出，rollback
				this.reflash();			//重新从数据库中遍历刷新显示
				conn.close();			//关闭连接
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		  }
		  command=new ArrayList<String>();		//清空记住命令的ArrayList
		  	
	}
	
	/**
	 * 退出系统
	 * 
	 */
	public void exitButtonActionPerform(ActionEvent evt){
		System.exit(0);
	}
		
	
	

}
