package _searchInfo;

public class VolunteerClass
{
	private int age;
	private String name;
	private String schoolName;
	public String gender; 
	public VolunteerClass(int age, String name, String schoolName, String gender)
	{
		this.setInfo(age, name, schoolName, gender);
	}
 
	private void setInfo(int age, String name, String schoolName, String gender)
	{
		this.age = age;
		this.name = name;
		this.schoolName = schoolName;
		this.gender = gender;
    }
 
	public String getAgeBracket(String f)
	{
		if(this.age < 20)
			return "<20"; 
		else if(this.age <= 30)
			return "20~30";
		else if(this.age <= 40)
			return "30~40"; 
		else if(this.age <= 50)
			return "40~50";
		else return ">50";
	}
 
	public String askRoute(String targetAddress)
	{
		return "follow bellowing steps to reach  " + targetAddress;
	}
 
	public String askQueueTime(String venueName)
	{
		return "You have to wait * hour for " + venueName;
	}
	
}
