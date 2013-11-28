package Models;

public class Hall {
	private int hallID;
	private String name;
	private int pavilionID;
	
	public int gethallID(){
		return hallID;
	}
	public String getname(){
		return name;
	}
	public int getpailionID(){
		return pavilionID;
	}
	public void sethallID(int hallID){
		this.hallID = hallID;
	}
	public void setname(String name){
		this.name = name;
	}
	public void setpavilionID(int pavilionID){
		this.pavilionID = pavilionID;
	}
}
