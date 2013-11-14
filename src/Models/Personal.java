package Models;

import DB.PersonalChargeMethods;
import DB.UserMethods;

public class Personal {
	private int personalID;
	private String name;
	private double salary;
	private String arriveTime;
	private String leavingTime;
	private int personalChargeID=-1;
	private int userID;
	private String charge="";
	private String login="";

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
	public String getCharge() {
		if(charge.equals("")&&personalChargeID!=-1)
		{
			PersonalChargeMethods methods=new PersonalChargeMethods(null);
			charge=methods.getCharge(personalChargeID);
		}
		return charge;
	}
	public void setCharge(String charge) 
	{
		//no creo usar a menos q sea un update recien llamar a la base pero aqui no creo
		//se debe usar al crear en la base de datos
		if(personalChargeID==-1)
		{
			PersonalChargeMethods methods=new PersonalChargeMethods(null);
			personalChargeID=methods.getChargeID(charge);
		}
		this.charge = charge;
	}
	public int getPersonalID() {
		return personalID;
	}
	public void setPersonalID(int personalID) {
		this.personalID = personalID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
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
	public int getPersonalChargeID() {
		return personalChargeID;
	}
	public void setPersonalChargeID(int personalChargeID) {
		this.personalChargeID = personalChargeID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}

}
