package fetion;

import java.io.IOException;
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
      public FetionSend(ArrayList<String> phone){
    	  phoneList=phone;
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
  /*  	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd EEEE-HH-mm-ss");
    	  Date date=new Date();
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
      
      public Date getTaskDate(int i){
    	  int number=i/2+1;
    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd EEEE HH:mm:ss");
    	  SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd EEEE ");
     	  Date date=new Date();
     	  Date taskdate=new Date();
     	  System.out.println(sdf.format(date));
     	  long secondsOfDate= 86400000L;	// 一天的毫秒数 86400000ms
  //   	  System.out.println(date.getDay());
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
    	  return taskdate;
      }
      public void send(String[][] list){
    	  FetionSend[] task=null;
    	  ArrayList<String> phonelist=new ArrayList<String>();
    	  for(int i=0;i<14;i++){
    		   for(int j=0;j<4;j++){
    			   phonelist.add(list[i][j]);
    		   }
    		   task[i]=new FetionSend(phonelist);
    		   task[i].getTaskDate(i);
    		   task[i].sendTo();
    		   phonelist.clear();
    	  }
      }
       public static void main(String[] args) {
    	   
 //       	  FetionSend task=new FetionSend("",temp);
 //   		  task.getTaskDate(i);
//    		  System.out.println();
    	  
  /*  	  ArrayList<String> temp=new ArrayList<String>();
    	  temp.add("15121025925");
    	  
    	  FetionSend task=new FetionSend("",temp);
    	  task.sendTo(1);
    	  
    	  ArrayList<String> temp1=new ArrayList<String>();
    	  
    	  temp1.add("13761230016");
    	  FetionSend task1=new FetionSend("",temp1);
    	  task1.sendTo(2);
  */  	  
    	  
    	  
    	  
 //   	  TestTimerTask task = new TestTimerTask();
  /*   	  task.sendTo();
     	  SimpleDateFormat   sdt   =   new   SimpleDateFormat( "yyyy-MM-dd EEEE-HH-mm-ss"); 
    	  Date date=new Date();
    	  System.out.println(sdt.format(date));
     	  Date taskdate=new Date();
    	  System.out.println(sdt.format(date));
    	  int currentDay=date.getDay();
    	  long secondsOfDate=5000L;
    	  long temp=date.getTime()+secondsOfDate;
    	  taskdate.parse(temp+"");
    	  System.out.println(sdt.format(taskdate));
    	  System.out.println(sdt.format(temp));
    	  try {
			taskdate=sdt.parse(sdt.format(temp));
		  } catch (ParseException e) {
			e.printStackTrace();
		  }
		  
/*    	  System.out.println(date.getTime());
    	  System.out.println(date.getDay());			//获得星期几 0为星期天
    	  System.out.println(date.getDate());			//获得当前日期
    	  System.out.println(date.getMonth());         //获得月份-1
    	  System.out.println(sdt.format(date));
          Calendar calendar = Calendar.getInstance();  
          calendar.setTime(date);  
          System.out.println(Calendar.DAY_OF_WEEK_IN_MONTH);
          int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
          System.out.println(dayOfWeek);
   */       
  /*       try {
        	  date   = sdt.parse( "2010-08-05 星期四-11-01-30");
			
          } catch (ParseException e) {
        	  e.printStackTrace();
          }
          Timer timer = new Timer();
          TimerTask task = new TestTimerTask();
          System.out.println(date);
          timer.schedule(task,taskdate,3000L);           
 */   }
}