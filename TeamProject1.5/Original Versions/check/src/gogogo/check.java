package gogogo;
import java.applet.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class check extends JApplet implements ActionListener
{
	 Panel pane1=new Panel();
	 Panel pane2=new Panel();
	 Panel pane3=new Panel();
	 Panel pane4=new Panel();
	 TextField text1=new TextField(8);
	 TextField text2=new TextField(8);
	 Label label1=new Label("学   号");
	 Label label2=new Label("密   码");
	 JOptionPane opt=new JOptionPane("",JOptionPane.PLAIN_MESSAGE,JOptionPane.DEFAULT_OPTION);
	 JRadioButton bu1=new JRadioButton("User",true);
	JRadioButton bu2=new JRadioButton("Adminis",false);
	JRadioButton bu3=new JRadioButton("Super",false);
	
	int i=0;
	 ButtonGroup bu=new ButtonGroup();
	 
	 Button b1=new Button("登陆");
	 public int getID()//接口，告诉身份是普通用户，管理员，还是超级管理员
	 {
		 return i;
	 }


	  public void init ( )
	  {
		
		  pane1.add(label1);
		  pane1.add(text1);
		  pane2.add(label2);
		  text2.setEchoChar('*');
		  pane2.add(text2);
	  
		ActionListener a=new ActionListener()
		  {

			
			public void actionPerformed(ActionEvent e) 
			{				 
					  if(e.getActionCommand().equals("User"))
					  {						  
							i=0;
							return;
					  }
					  if(e.getActionCommand().equals("Adminis"))
					  {						
							i=1;
							return;
					  }
					  if(e.getActionCommand().equals("Super"))
					  {						  
							i=2;
							return;
					  }			
			}
			  
		  };
		bu1.addActionListener(a);
		bu2.addActionListener(a);
		bu3.addActionListener(a);
		  bu.add(bu1);
		  bu.add(bu2);
		  bu.add(bu3);
		  pane3.add(bu1);
		pane3.add(bu2);
		pane3.add(bu3);
		pane3.setLayout(new GridLayout(1,3));
		  b1.addActionListener(this);
		  b1.setSize(16, 3);
		  pane4.add(b1);
		  Container contentPane = getContentPane( );
		  contentPane.setLayout(new GridLayout(4,1));
    	add(pane1);
    	add(pane2);
    	add(pane3);
    	add(pane4);
    	contentPane.setVisible(true);
    	contentPane.setBounds(0, 0, 8, 20);
	  }


	public void actionPerformed(ActionEvent e) 
	{			
		try{
			FileReader in=new FileReader("text1.txt");;
			switch(i)
			{
			case 0:
			  {
				   in=new FileReader("text1.txt");
				   break;
					
					
			  }
			case 1:
			 
			  {
				   in=new FileReader("text2.txt");
				   break;
					
					
			  }
			case 2:
			  
			  {
				  in=new FileReader("text3.txt");
				  break;
					
				
			  }
			}
			  
		 
				BufferedReader read1=new BufferedReader(in);
				String account="abc",password="def";
				while(true)
				{
					
					
						
						account=read1.readLine();						
						password=read1.readLine();
					
					if(text1.getText().equals(""))
					{
						opt.showMessageDialog(null, "请输入用户名");break;
					}
					if(text2.getText().equals(""))
					{
						opt.showMessageDialog(null, "请输入密码");break;
					}
					if(account!=null&&account.equals(text1.getText()))
					{
						if(password!=null&&password.equals(text2.getText()))
						{
							System.out.print("Load Successfully");break;//登陆成功

						}
						else
						{
							opt.showMessageDialog(null, "用户名1或密码输入有误");break;
						}
					}
					if(account==null)
					{
						opt.showMessageDialog(null, "用户名2或密码输入有误");break;
						//不能告诉用户是用户名错了还是密码错了
					}
				}				
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}		
	}

	  
}


