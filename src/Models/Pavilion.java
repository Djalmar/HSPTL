package Models;

import java.util.ArrayList;

public class Pavilion {
	private int pavilionID;
	private String name;
	private String description;
	ArrayList<Hall> halls;
	
	public ArrayList<Hall> getHalls() {
		return halls;
	}
	public void setHalls(ArrayList<Hall> pavilions) {
		this.halls = pavilions;
	}
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
