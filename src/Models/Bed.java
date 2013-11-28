package Models;

public class Bed {

	private int bedID;
	private int hallID;
	private String availability;
	
	public int getbedID(){
		return bedID;
	}
	public int gethallID(){
		return hallID;
	}
	public String getavailability(){
		return availability;
	}
	public void setbedID(int bedID){
		this.bedID = bedID;
	}
	public void sethallID(int hallID){
		this.hallID = hallID;
	}
	public void setavailability(String availability){
		this.availability = availability;
	}
}
