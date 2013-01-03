package Plans;

import java.util.ArrayList;

public class PreTreeToPlan {
	public Parse.Parse parser;
	public Absyn.Absyn result;
	public Absyn.SqlExp sqlExp;
	public Absyn.SelectOp selectOp;
	public Absyn.WhereExp whereExp;
	public Absyn.WhereCondExp whereCondExp;
	public ArrayList<Absyn.WhereCondBinExp> whereCondBinExpList =new ArrayList<Absyn.WhereCondBinExp>();
	public int selectBinExpCount;//有多少union和union all
	public int whereExpCount;//是否有Where语句
	public int whereCondBinExpCount;//Where中有多少个and和or
	public int subQueriesCount[];//有多少个子查询
	/**
	 * @param filename
	 * 暂时只适用于没有union
	 */
	public PreTreeToPlan(String filename){
		parser=new Parse.Parse(filename);
		result=parser.result;
		sqlExp=((Absyn.SqlExpList)((Absyn.List)result)).head;
		selectBinExpCount=0;
		SelectBinExpCount();
		
		selectOp=null;
		whereCondExp=null;
		whereExp=null;
		whereCondBinExpCount=0;
		whereExpCount=1;
		if(this.SqlExpType()==Marks.SELECTEXP){
			if(selectBinExpCount==0){//没有union
				selectOp=(Absyn.SelectOp)sqlExp;
				if(selectOp.whereExp==null){
					whereExp=null;
				}
				else{
					whereExp=selectOp.whereExp;
					if(whereExp.cond==null){
						whereExpCount=0;
					}
					else{
						whereCondExp=whereExp.cond;
						if(whereCondExp instanceof Absyn.WhereCondBinExp){
							whereCondBinExpCount++;
							
							Absyn.WhereCondBinExp whereCondBinExpTmp =(Absyn.WhereCondBinExp)whereCondExp;
							WhereCondBinExpCount(whereCondBinExpTmp,1);
						}
					}
				}
			}
		}
		subQueriesCount=new int[whereCondBinExpCount+1];
	}
	public PreTreeToPlan(PreTreeToPlan pttp){
		this.parser=pttp.parser;
		this.result=pttp.result;
		this.sqlExp=pttp.sqlExp;
		
		this.selectOp=pttp.selectOp;
		this.whereExp=pttp.whereExp;
		this.whereCondExp=pttp.whereCondExp;
		this.whereCondBinExpList=pttp.whereCondBinExpList;
		this.selectBinExpCount=pttp.selectBinExpCount;
		this.whereExpCount=pttp.selectBinExpCount;
		this.whereCondBinExpCount=pttp.selectBinExpCount;
		this.subQueriesCount=pttp.subQueriesCount;
		
	}
	public int SqlExpType(){
		if(sqlExp instanceof Absyn.SelectExp){
			System.out.println("Absyn.SelectExp");
			return Marks.SELECTEXP;
		}
		else if(sqlExp instanceof Absyn.CreateExp){
			System.out.println("Absyn.CreateExp");
			return Marks.CREATEEXP;
		}
		else if(sqlExp instanceof Absyn.InsertExp){
			System.out.println("Absyn.InsertExp");
			return Marks.INSERTEXP;
		}
		else if(sqlExp instanceof Absyn.DropExp){
			System.out.println("Absyn.DropExp");
			return Marks.DROPEXP;
		}
		else if(sqlExp instanceof Absyn.DeleteExp){
			System.out.println("Absyn.DeleteExp");
			return Marks.DELETEEXP;
		}
		else if(sqlExp instanceof Absyn.UpdateExp){
			System.out.println("Absyn.UPdateExp");
			return Marks.UPDATEEXP;
		}
		else if(sqlExp instanceof Absyn.AlterExp){
			System.out.println("Absyn.AlterExp");
			return Marks.ALTEREXP;
		}
		return 0;
	}

	public int SelectBinExpCount(){
		if(sqlExp instanceof Absyn.SelectBinExp){
			selectBinExpCount++;
			Absyn.SelectBinExp selectBinExpTmp=(Absyn.SelectBinExp)sqlExp;
			while(selectBinExpTmp.left instanceof Absyn.SelectBinExp){
				selectBinExpCount++;
				selectBinExpTmp=(Absyn.SelectBinExp)selectBinExpTmp.left;
			}
		}
		return selectBinExpCount;
	}
	
	public int WhereCondBinExpCount(Absyn.WhereCondBinExp whereCondBinExp,int set){
		int i=0;
		if((whereCondBinExp.left instanceof Absyn.WhereCondBinExp)||(whereCondBinExp.right instanceof Absyn.WhereCondBinExp)){
			if(whereCondBinExp.left instanceof Absyn.WhereCondBinExp){
				if(set==1){
					whereCondBinExpCount++;
				}
				i++;
				i+=WhereCondBinExpCount(((Absyn.WhereCondBinExp)whereCondBinExp.left),set);
			}
			if(whereCondBinExp.right instanceof Absyn.WhereCondBinExp){
				if(set==1){
					whereCondBinExpCount++;
				}
				i++;
				i+=WhereCondBinExpCount(((Absyn.WhereCondBinExp)whereCondBinExp.right),set);
			}
			if(set==1){
				whereCondBinExpList.add(whereCondBinExp);
			}
			return i;
		}
		else{
			if(set==1){
				whereCondBinExpList.add(whereCondBinExp);
			}
			return i;
		}
	}
	/**
	 * @return the subqueries count
	 * 
	 */
	public int SubQueries(){
		if(selectBinExpCount>0){
			return -1;
		}
		
		int i=0;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
		
		return i;
	}
	public void print(){
		System.out.println(result.print(""));
	}
	

}
