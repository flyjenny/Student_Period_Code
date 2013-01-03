package _searchInfo;

public class PoliceClass
{
	private int age;
	private String name;
	public String gender; 
	public PoliceClass(int age, String name, String gender)
	{
		this.setInfo(age, name, gender);
	}
 
	private void setInfo(int age, String name, String gender)
	{
		this.age = age;
		this.name = name;
		this.gender = gender;
    }
 
	public String askForHelp(String HelpContent)
	{
		return "I can help you with "+HelpContent;
	}
 
	
}
