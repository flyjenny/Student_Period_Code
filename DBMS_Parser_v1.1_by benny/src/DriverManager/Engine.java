package DriverManager;

import java.io.UnsupportedEncodingException;

import QueryManager.Plans.*;
import Tmp.*;

public class Engine {
	public LogicalQueryPlan plan;
	public RecordManager manager;
	public Engine(String filename1,String filename2) throws UnsupportedEncodingException{
		PreTreeToPlan pttp=new PreTreeToPlan(filename1);
		plan=new LogicalQueryPlan(pttp);
		manager=new RecordManager(filename2);
	}
	
	public boolean CreateTable(){
		boolean mark=false;
		
		return mark;
	}
}
