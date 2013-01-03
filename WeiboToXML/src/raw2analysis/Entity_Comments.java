package raw2analysis;

import java.util.HashMap;

public class Entity_Comments {
	public String entity;	//评论对象
	public int type_ent;	//实体类型（0--人名；1--地名；2--组织名；3--其他专名；-1--非法）
	public int count;	//原文中出现频次，用于转换字号
	public HashMap<String, Integer> comments;	//评论词（包括评论词和出现频次）

	
	public void setEntity(String str)
	{
		entity = str;
	}
	public void setType_ent(int t)
	{
		type_ent = t;
	}
	public void setCount(int c)
	{
		count = c;
	}
}
