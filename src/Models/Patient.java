package Models;
public class Patient 
{
	public int patientID;
	public String name;
	public double weight;
	public double size;
	public boolean gender;
	public String birthday;
	public int getPatientID() {
		return patientID;
	}
	public void setPatientID(int personID) {
		this.patientID = personID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public boolean setGender(String string) {
		if(string.toLowerCase().equals("male")||string.toLowerCase().equals("1"))
		{
			gender=true;
			return true;
		}
		if(string.toLowerCase().equals("female")||string.toLowerCase().equals("0"))
		{
			gender=false;
			return true;
		}
		return false;
	}
	public String getGender()
	{
		if(gender)
			return "MALE";
		return "FEMALE";
	}
}
