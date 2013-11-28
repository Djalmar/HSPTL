package Models;

public class Pavilion {
	private int pavilionID;
	private String name;
	private String description;
	
	public int getpavilionID(){
		return pavilionID;
	}
	public String getname(){
		return name;
	}
	public String getDescription(){
		return description;
	}
	public void setpavilionID(int pavilionID){
		this.pavilionID = pavilionID;
	}
	public void setname(String name){
		this.name = name;
	}
	public void setdescription(String description){
		this.description = description;
	}
}
