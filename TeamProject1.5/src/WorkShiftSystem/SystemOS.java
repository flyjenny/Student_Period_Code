package WorkShiftSystem;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.font.TextLayout;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

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
public class SystemOS extends JFrame implements ActionListener{
  JPanel pnlMain;
  JTextField txtfile;
  private JButton jButton1;
  JButton btnSelect;
  JFileChooser fc = new JFileChooser();
  String path=null;
  static JTable table=null;
  static String filename=null;
  
  public SystemOS() {
    pnlMain=new JPanel();
    GridBagLayout thisLayout = new GridBagLayout();
    getContentPane().setLayout(thisLayout);
    getContentPane().add(pnlMain, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    {
    	jButton1 = new JButton();
    	getContentPane().add(jButton1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    	jButton1.setText("\u786e\u5b9a");
    	jButton1.setHorizontalAlignment(SwingConstants.TRAILING);
    	jButton1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {jButton1ActionPerformed(evt); }});
    }
    txtfile=new JTextField(10);
    btnSelect =new JButton("选择");
    btnSelect.addActionListener(this);
    pnlMain.add(txtfile);
    pnlMain.add(btnSelect);
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource()==btnSelect){
/*
    这是尤为重要的。因为JFileChooser默认的是选择文件，而需要选目录。
    故要将DIRECTORIES_ONLY装入模型
另外，若选择文件，则无需此句
 */
      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int intRetVal = fc.showOpenDialog(this);
      if( intRetVal == JFileChooser.APPROVE_OPTION){
        txtfile.setText(fc.getSelectedFile().getPath());
      }
    }
  }
  private void jButton1ActionPerformed(ActionEvent evt)//确定
  {
	  path=fc.getSelectedFile().getPath();
	  ExportToExcel(table, "空闲时间表");
	  this.dispose();
	    
  }
  /*首先要导入jxl的jar包*/
  /*Title是保存出来的文件名，gbl_LastOpenPath用于记录上次打开的路径*/
  public void ExportToExcel(JTable table, String Title){
          File file = null;
          if(path!=null){
              file = new File(path+"/"+Title+".xls");
              if(file.exists()){
                  fc.setSelectedFile(file);
              }
          }
          if(path==null){ file=new File("c:/"+filename+".xls");
        	  fc.setSelectedFile(file); }
              /*开始导出数据*/
          try {
                  WritableWorkbook book = Workbook.createWorkbook(file);
                  WritableSheet sheet=book.createSheet(Title,0); //工作表名称
                  sheet.mergeCells(0,0,(table.getColumnCount()-1),0); //合并第一行
                  /*表头：加粗*/
                  WritableFont CaptionFont = new WritableFont(WritableFont.ARIAL, 14,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
                  /*表头：居中*/
                  WritableCellFormat CatpionStyle = new WritableCellFormat(CaptionFont);
                  CatpionStyle.setAlignment(Alignment.CENTRE);
                  CatpionStyle.setVerticalAlignment(VerticalAlignment.CENTRE);
                  sheet.addCell(new Label(0,0,Title,CatpionStyle));
                  /*写表头*/
                  WritableFont TitleFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
                  /*表头：居中*/
                  WritableCellFormat TitleStyle = new WritableCellFormat(TitleFont);
                  TitleStyle.setAlignment(Alignment.CENTRE);
                  TitleStyle.setVerticalAlignment(VerticalAlignment.CENTRE);
                  for(int i = 0;i<table.getColumnCount();i++){
                      sheet.addCell(new Label(i,1,table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString(),TitleStyle));
                  }
                  for(int i = 0;i<table.getRowCount();i++){
                      for(int j = 0;j<table.getColumnCount();j++){
                          if(table.getValueAt(i, j)!=null){
                              Label label = new Label(j,i+2,table.getValueAt(i, j).toString());
                              sheet.addCell(label);
                          }
                      }
                  }
                  book.write();
                  book.close();
                  JOptionPane.showMessageDialog(null, "已成功导出到"+file.getPath());
              } catch (WriteException ex) {
                  //Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                //  Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IOException ex) {
                 // Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                  //Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
              }

          }
      
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}