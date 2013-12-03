package Models;

import java.util.ArrayList;

public class Hall {
	private int hallID;
	private String name;
	private int pavilionID;
	ArrayList<Bed> beds;
	
	public ArrayList<Bed> getBeds() 
	{
		return beds;
	}
	public void setBeds(ArrayList<Bed> beds) 
	{
		this.beds = beds;
	}
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
