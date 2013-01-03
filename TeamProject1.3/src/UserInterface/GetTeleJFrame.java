package UserInterface;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
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
public class GetTeleJFrame extends javax.swing.JFrame {
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private JTable jTable1;
	private JButton retjButton1;
	private JButton searchjButton1;
	private JTextField telejTextField1;
	private JLabel telejLabel4;
	private JTextField namejTextField1;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel1;
    private static Connection conn;
    InfoManageJFrame window;
	/**
	* Auto-generated main method to display this JFrame
	*/
/*	public static void main(String[] args) {
		
			
			
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GetTeleJFrame inst = new GetTeleJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}*/
	
	public GetTeleJFrame(InfoManageJFrame w) {
		super();
		window=w;
		initGUI();
	}
	
	private void initGUI() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 连接数据库
			// 其中localized为主机名，test是数据库名，root为用户名
			conn =DriverManager.getConnection("jdbc:mysql://localhost/database?user=root");
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				GridBagLayout jPanel1Layout = new GridBagLayout();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1Layout.rowWeights = new double[] {0.0, 0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.rowHeights = new int[] {59, 7, 7, 7, 7};
				jPanel1Layout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				jPanel1Layout.columnWidths = new int[] {7, 7, 7, 7};
				jPanel1.setLayout(jPanel1Layout);
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText("\u6240\u6709\u4eba\u8054\u7cfb\u65b9\u5f0f\uff1a");
				}
				{
					jScrollPane1 = new JScrollPane();
					jPanel1.add(jScrollPane1, new GridBagConstraints(0, 1, 2, 3, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 0), 0, 0));
					{
						TableModel jTable1Model = 
							new DefaultTableModel(
									new String[][] { { "One", "Two" }, { "Three", "Four" } },
									new String[] { "Column 1", "Column 2" });
						jTable1 = new JTable();
						jScrollPane1.setViewportView(jTable1);
						jTable1.setModel(jTable1Model);
						jTable1.setPreferredSize(new java.awt.Dimension(225, 205));
						Vector vect=this.getAllData(conn,"name,phone_num","userinfo");
						// 表列标题
						String[] titleStr1 = { "姓名 ", "手机号码" };// 表列标题设定
						Vector title1 = new Vector();// 用于存放表列标题
						title1.removeAllElements();//初始化
						for (int i = 0; i < titleStr1.length; i++) {
							title1.addElement(titleStr1[i]);
						}//添加元素
						DefaultTableModel model1=(DefaultTableModel)jTable1.getModel();  
						 model1.setDataVector(vect, title1);//设置表格元素
					}
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2, new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel2.setText("\u6839\u636e\u59d3\u540d\u67e5\u8be2\u8054\u7cfb\u65b9\u5f0f\uff1a");
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel3.setText("\u59d3\u540d\uff1a");
				}
				{
					namejTextField1 = new JTextField();
					jPanel1.add(namejTextField1, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					telejLabel4 = new JLabel();
					jPanel1.add(telejLabel4, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					telejLabel4.setText("\u53f7\u7801\uff1a");
				}
				{
					telejTextField1 = new JTextField();
					jPanel1.add(telejTextField1, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
				{
					searchjButton1 = new JButton();
					jPanel1.add(searchjButton1, new GridBagConstraints(2, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					searchjButton1.setText("\u67e5\u8be2");
					searchjButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							String name=namejTextField1.getText();
							try {
								Statement st=conn.createStatement();
							    ResultSet rs=st.executeQuery("select phone_num from userinfo where name='"+name+"'");
							    rs.first();
							    int row=rs.getRow();
							    if(row==0) telejTextField1.setText("没有该姓名对应号码");
							    else telejTextField1.setText(rs.getString(1));
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
				{
					retjButton1 = new JButton();
					jPanel1.add(retjButton1, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					retjButton1.setText("\u8fd4\u56de");
					retjButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							window.setVisible(true);
							GetTeleJFrame.this.dispose();
						}
					});
					
				}
			}
			pack();
			setSize(500, 400);
		} 
		catch (ClassNotFoundException e) {
			System.err.println("database driver not found");
		}

		catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.err.println("SQLState: " + e.getSQLState());
			System.err.println("VendorError: " + e.getErrorCode());
		}
		catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
		
	}
	static public Vector getAllData(Connection con,String choice ,String table)
	{
		Vector   vect   =   new   Vector();//用于存放数据记录 
		try
		{
			Statement   stmt   =   con.createStatement(); 
            String   sqlCode   =   "SELECT "+choice+   "  from " +table;//
            ResultSet   rs   =   stmt.executeQuery(sqlCode);
            ResultSetMetaData md = rs.getMetaData();
            int col=md.getColumnCount();
            
            vect.removeAllElements();//初始化向量对象 
            
            while(rs.next())   {//依次读取数据结果集 
                Vector   rec_vector=new   Vector();//从结果集中取数据放入向量rec_vector中 
                for(int i=1;i<=col;i++)
                rec_vector.addElement(rs.getString(i));  
                vect.addElement(rec_vector);//向量rec_vector加入向量vect中 
            } 
 
            //释放资源 
            rs.close(); 
            stmt.close();  
		}
		catch(Exception   e)   { 
            System.out.println(   e); 
		
	}
		return vect;
	}

}
