package _searchInfo;



public class BroadCasterClass
{
	private int age;
	private String name;
	public String gender; 
	public BroadCasterClass(int age, String name,String gender)
	{
		this.setInfo(age, name,gender);
	}
 
	private void setInfo(int age, String name, String gender)
	{
		this.age = age;
		this.name = name;
		this.gender = gender;
    }
 
	public String broadCastInfo(String info)
	{
		return "The expo is very clouded now! "+info;
	}
}
