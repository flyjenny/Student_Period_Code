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
	static Add add;					  //Add�������ݳ�Ա����סadd���󴫻����Ĳ���
	static ArrayList<String> command;//��ס �û������������������ύ����������
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
	 try{ //װ������
	  Class.forName("com.mysql.jdbc.Driver");
	  //�������ݿ�
	  //����localizedΪ��������test�����ݿ�����rootΪ�û���
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
					ResultSet rs = stat.executeQuery("select name,birthday,homeAddress,classNumber from student");//ִ�в�ѯ����
					ResultSetMetaData md = rs.getMetaData();
					int columnCount = md.getColumnCount();
					stat.getMaxRows();
					rs.last();
					int rowCount=rs.getRow();
					Object[][] info=new Object[rowCount][columnCount];
					int m=0;
					if(rs.first())//����
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
	 * ˢ��table��ʾ
	 * @throws SQLException
	 */
	public void reflash() throws SQLException{
		String [] head={"name","birthday","homeAddress","classNumber"};
		
		Statement stat=this.connect().createStatement();
		ResultSet rs = stat.executeQuery("select name,birthday,homeAddress,classNumber from student");//ִ�в�ѯ����
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		stat.getMaxRows();
		rs.last();
		int rowCount=rs.getRow();
		Object[][] info=new Object[rowCount][columnCount];
		int m=0;
		if(rs.first())//����
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
	 * ��Ӻ���ʾ��table��
	 */
	public static void addShow(){
		DefaultTableModel   dtm=(DefaultTableModel)table.getModel();
		Object[] in=add.in;
		dtm.addRow(in);
		addDatabase();
	}
	/**
	 * ��ס���ӵ�������ӵ��ĸ�������Add����
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
	 * ��������Table�����ݸı�ļ������Ѹı�������ס����ArrayList<String>
	 * 
	 * 
	 */
	public void tableChagedAction(TableModelEvent e){
		int rowcount=table.getSelectedRow();		//��ס�ı����
		int columncount=e.getColumn();				//��ס�ı����
		if (columncount!=-1){						//��ɾ��������Ӳ���
	
		String change ="update stu.student set "+table.getColumnName(columncount)+
						"='"+table.getValueAt(rowcount, columncount)+"' where name='"+
						table.getValueAt(rowcount,0)+"'"; 
		command.add(change);						//��������ӵ�command��
		}
	}
	/**
	 * ����ɾ���ļ����������ݿ��ж�Ӧ�������������ס����������ͬʱɾ��
	 * @param evt
	 */
	public void deleteButtonActionPerform(ActionEvent evt){
		int[] selections=table.getSelectedRows();
		
		for (int i = 0; i < selections.length; i++) {  
		    selections[i] = table.convertRowIndexToModel(selections[i]);  
		}
		for (int i : selections) {  
		    //��ȡѡ���е�����  
		    String delete="delete from stu.student where name='"+table.getValueAt(i, 0)+"'";
		    command.add(delete);
	//	    state.executeUpdate(delete);
		}  
		DefaultTableModel df = (DefaultTableModel) table.getModel();  
		//����ɾ��  
		for (int i = selections.length; i > 0; i--) {  
		    df.removeRow(table.getSelectedRow());  
		}
	}
	
	/**
	 * �ύ��ť�ļ��������쳣�׳�rollback���ɹ�commit
	 * @param evt
	 */
	public void commitActionPerform(ActionEvent evt){
		 Connection conn=null;
		 try{ //װ������
		  Class.forName("com.mysql.jdbc.Driver");
		  //�������ݿ�
		  //����localizedΪ��������test�����ݿ�����rootΪ�û���
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
			conn.setAutoCommit(false);	//���óɲ����Զ��ύ�����ݿ�
			stat = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		  
		
		  try{
		  for(String c:command){
			  System.out.println(c);
			  stat.executeUpdate(c);	//��֮ǰ��סÿһ�������ύ
		  }
		  conn.commit();				//�ύ�����ݿ�
		  JOptionPane.showMessageDialog(null, "�ύ�ɹ�");

		  this.reflash();			//���´����ݿ��б���ˢ����ʾ
		  }
		  catch(SQLException e){
			  System.out.println("Commit Err");
			  JOptionPane.showMessageDialog(null, "�ύʧ��");
			  try {
				 
				conn.rollback();		//�쳣�׳���rollback
				this.reflash();			//���´����ݿ��б���ˢ����ʾ
				conn.close();			//�ر�����
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		  }
		  command=new ArrayList<String>();		//��ռ�ס�����ArrayList
		  	
	}
	
	/**
	 * �˳�ϵͳ
	 * 
	 */
	public void exitButtonActionPerform(ActionEvent evt){
		System.exit(0);
	}
		
	
	

}
