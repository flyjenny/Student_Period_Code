package _searchInfo;

public class HealthCareClass
{
	private int age;
	private String name;
	public String gender; 
	public HealthCareClass(int age, String name, String gender)
	{
		this.setInfo(age, name, gender);
	}
 
	private void setInfo(int age, String name, String gender)
	{
		this.age = age;
		this.name = name;
		this.gender = gender;
    }
 
	public String askSymptom(String sypton)
	{
		return "People living with HIV may feel and look " +
			   "completely well but their immune systems " +
			   "may nevertheless be damaged. It is important " +
			   "to remember that once someone is infected with " +
			   "HIV they can pass the virus on immediately, even " +
			   "if they feel healthy. "+sypton;
	}
 
	public String askEmergency(String emergency)
	{
		return "Hurrry to the hospital to treat " + emergency;
	}
 
	
}
