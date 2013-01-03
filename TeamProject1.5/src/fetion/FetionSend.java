package fetion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


public class FetionSend extends TimerTask {
     private ArrayList<String> phoneList;
      private Date taskDate;
      private String[][] list;
      public FetionSend(ArrayList<String> phone){
    	  phoneList=phone;
    	  list=new String[14][3];
      }
      
      public void run() {
    	  for(String c:phoneList){
    	  Date date = new Date(); 
          SimpleDateFormat bartDateFormat =new SimpleDateFormat("yyyy-MM-dd EEEE-HH-mm-ss"); 
          System.out.println(bartDateFormat.format(date));
          String PostURL=("http://sms.api.bz/fetion.php?username=&password=&sendto=&message=");
          HttpClient client = new HttpClient();
          PostMethod postMethod =  new PostMethod(PostURL);
         
          postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");//发送中文，避免乱码
          postMethod.addParameter("username","15121025925");
          postMethod.addParameter("password","594chenbin");
          System.out.println(c);
       	  postMethod.addParameter("sendto",c);//接收人号码
       	  postMethod.addParameter("message","王晓东是猪");
       	  System.out.println(postMethod.getName());
       	  client.setConnectionTimeout(5000);
       	  try {
       		  Thread.sleep(5000);
       		  client.executeMethod(postMethod);
       	  } catch (HttpException e) {
        	  e.printStackTrace();
          } catch (IOException e) {
        	  e.printStackTrace();
       	  }catch (InterruptedException e) {
      		  e.printStackTrace();
      	  }
    	  }
       }
      
      public void sendTo(){
   // 	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd EEEE-HH-mm-ss");
   /* 	  Date date=new Date();
    	  Date taskdate=new Date();
    	  Date taskdate1=new Date();
    	  int currentDay=date.getDay();
    	  long secondsOfDate=4000L;			// 一天的毫秒数 86400000ms
    	  long temp=date.getTime()+secondsOfDate*i;
    	  
    	  try {
  			taskdate=format.parse(format.format(temp));
  		  } catch (ParseException e) {
  			e.printStackTrace();
  		  }
  */		  
  		  Timer timer = new Timer();
  		  timer.schedule(this,taskDate,30000L); 
  		   
      }
      
      public void getTaskDate(int i){
    	  int number=i/2+1;
    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEEE HH:mm:ss");
    	  SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd EEEE ");
     	  Date date=new Date();
     	  Date taskdate=new Date();
   //  	  System.out.println(sdf.format(date));
     	  long secondsOfDate= 86400000L;	// 一天的毫秒数 86400000ms
     	  if(number>date.getDay()){
    		  long temp=date.getTime()+secondsOfDate*(1+number-date.getDate());
    		  s.format(temp);
    		  if (i%2==0){
    			  try {
    	   				taskdate=sdf.parse(s.format(temp)+"15:00:00");  //返回Date类型
    			  	  } catch (ParseException e) {
    			  		  e.printStackTrace();
    			  	  }
    		  }
    		  if (i%2==1){
    			  try {
  	   				taskdate=sdf.parse(s.format(temp)+"18:00:00");  //返回Date类型
  			  	  } catch (ParseException e) {
  			  		  e.printStackTrace();
  			  	  }
    		  }
    	  }
    	  if(number<date.getDay()){
    		  long temp=date.getTime()+secondsOfDate*(1+number-date.getDate()+7);
    		  s.format(temp);
    		  if (i%2==0){
    			  try {
    	   				taskdate=sdf.parse(s.format(temp)+"15:00:00");  //返回Date类型
    	   		  	  } catch (ParseException e) {
    	   		  		e.printStackTrace();
    	   		  	  }
    		  }
    		  if(i%2==1){
    			  try {
  	   				taskdate=sdf.parse(s.format(temp)+"18:00:00");  //返回Date类型
  	   		  	  } catch (ParseException e) {
  	   		  		e.printStackTrace();
  	   		  	  }
    		  }
    	   	 
    	  }
    	  if(number==date.getDay()){
    		  if(i%2==0){
    		  if(date.getHours()>=15){
    			 long temp=date.getTime()+secondsOfDate*7;  
    			 s.format(temp);
       		  	 try {
       	   			taskdate=sdf.parse(s.format(temp)+"15:00:00");  //返回Date类型
       		  	 } catch (ParseException e) {
       	   			e.printStackTrace();
       	   		 }
    		  }
    		  if(date.getHours()<15){
    			  long temp=date.getTime();
    			  s.format(temp);
        		  try {
        	   		taskdate=sdf.parse(s.format(temp)+"15:00:00");  //返回Date类型
        		  } catch (ParseException e) {
        	   		e.printStackTrace();
        	   	  }
    		  }
    		  }
    		  if(i%2==1){
    			  if(date.getHours()>=18){
    	    			 long temp=date.getTime()+secondsOfDate*7;  
    	    			 s.format(temp);
    	       		  	 try {
    	       	   			taskdate=sdf.parse(s.format(temp)+"18:00:00");  //返回Date类型
    	       		  	 } catch (ParseException e) {
    	       	   			e.printStackTrace();
    	       	   		 }
    	    		  }
    	    		  if(date.getHours()<18){
    	    			  long temp=date.getTime();
    	    			  s.format(temp);
    	        		  try {
    	        	   		taskdate=sdf.parse(s.format(temp)+"18:00:00");  //返回Date类型
    	        		  } catch (ParseException e) {
    	        	   		e.printStackTrace();
    	        	   	  }
    	    		  }
    		  }
    		  
    	  }
    	  taskDate=taskdate;
    	  
    	  
      }
      public void send(){
    //	  ArrayList<String> b=new ArrayList<String>(); 
    //	  FetionSend[] task=new FetionSend(b);
    	  ArrayList<String> phonelist=new ArrayList<String>();
    	  for(int i=0;i<14;i++){
    		   for(int j=0;j<3;j++){
    			   if(list[i][j]!=null){
    				   phonelist.add(list[i][j]);  
    			   }
    		   }
    		   FetionSend task=new FetionSend(phonelist);
    		   task.getTaskDate(i);
    		   task.sendTo();
    		   phonelist.clear();
    	  }
      }
      public void inputList() throws SQLException{
    	  	 
    	  Connection conn=null;
    		 try{ //装载驱动
    		  Class.forName("com.mysql.jdbc.Driver");
    		  //连接数据库
    		  //其中localized为主机名，test是数据库名，root为用户名
    		  conn = DriverManager.getConnection("jdbc:mysql://localhost/info?user=root&&password=");
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
    		  Statement stat=conn.createStatement();
    		  String[][] temp=new String[14][3];
    		  
    		  for(int i=0;i<14;i++){
    	  			ResultSet rs = stat.executeQuery("select manager,clerk0,clerk1 from shiftlist where banci="+(i+1));//执行查询操作
    	  			rs.first();  
    	  			for(int j=0;j<3;j++){
    	  				  temp[i][j]=rs.getString(j+1);
    	  			  }
    	  		  }
    	  			String[][] tellist=new String[14][3];
    		  for(int i=0;i<14;i++){
    				  for(int j=0;j<3;j++){
    					  if(!temp[i][j].equals("")){
    					  ResultSet result = stat.executeQuery("select tel from info where name='"+temp[i][j]+"'");//执行查询操作
    					  result.first();
    					  tellist[i][j]=result.getString(1);
    					  }
    					  
    				  }
    			  }
    		  list=tellist;
    		  conn.close();
      }
      public static void main(String[] args) throws SQLException {
    	  ArrayList<String> b=new ArrayList<String>();  
    	  FetionSend a=new FetionSend(b);
    	  a.inputList();
    	  a.send();
      }
}