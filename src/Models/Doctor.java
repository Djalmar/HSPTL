package Models;

import DB.UserMethods;

public class Doctor {
	private int doctorID;
	private String name;
	private String arriveTime;
	private String leavingTime;
	private int userID;
	private String login="";
	public Specialty specialty;
	
	public int getDoctorID() {
		return doctorID;
	}
	public void setDoctorID(int doctorlID) {
		this.doctorID = doctorlID;
	}
	public Specialty getSpecialty() {
		return specialty;
	}
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}
	public String getLogin() {
		if(login.equals(""))
		{
			login="";
		}
		return login;
	}
	public int setLogin(String login) 
	{
		if(this.login.equals(""))
		{
			UserMethods methods=new UserMethods(null);
			User user=methods.getUser(login);
			if(user==null)
				return -1;
			this.userID=user.getUserID();
			this.login = login;
			return 1;
		}
		return 1;
	}
	public int setLogin(int userID) 
	{
		if(this.login.equals(""))
		{
			UserMethods methods=new UserMethods(null);
			User user=methods.getUserByID(userID);
			if(user==null)
				return -1;
			login=user.getLogin();
			return 1;
		}
		return 1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getLeavingTime() {
		return leavingTime;
	}
	public void setLeavingTime(String leavingTime) {
		this.leavingTime = leavingTime;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}

}
