package raw2analysis;

import java.util.HashMap;

public class Entity_Comments {
	public String entity;	//���۶���
	public int type_ent;	//ʵ�����ͣ�0--������1--������2--��֯����3--����ר����-1--�Ƿ���
	public int count;	//ԭ���г���Ƶ�Σ�����ת���ֺ�
	public HashMap<String, Integer> comments;	//���۴ʣ��������۴ʺͳ���Ƶ�Σ�

	
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
