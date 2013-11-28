package Models;

public class Hospitalize {
	private int hospitalizeID;
	private int patientID;
	private int doctorID;
	private int bedID;
	private int hallID;
	private String inchargeDate;
	private String dischargeDate;
	private String diagnostic;
	private int pavilionID;
	
	public int gethospitalizeId(){
		return hospitalizeID;	
	}
	public int getpatientID(){
		return patientID;
	}
	public int getdoctorID(){
		return doctorID;
	}
	public int getbedID(){
		return bedID;
	}
	public int gethallID(){
		return hallID;
	}
	public String getinchargeDate(){
		return inchargeDate;
	}
	public String getdischargeDate(){
		return dischargeDate;
	}
	public String getdiagnostic(){
		return diagnostic;
	}
	public int getpavilionID(){
		return pavilionID;
	}
	public void sethospiltalizeID(int hospitalizeID){
		this.hospitalizeID = hospitalizeID;
	}
	public void setpatientID(int patientID){
		this.patientID = patientID;
	}
	public void setdoctorID(int doctorID){
		this.doctorID = doctorID;
	}
	public void setbedID(int bedID){
		this.bedID = bedID;
	}
	public void sethallID(int hallID){
		this.hallID = hallID;
	}
	public void setinchargeDate(String inchargeDate){
		this.inchargeDate = inchargeDate;
	}
	public void setdischargeDate(String dischargeDate){
		this.dischargeDate = dischargeDate;
	}
	public void setdiagnostic(String diagnostic){
		this.diagnostic = diagnostic;
	}
	public void setpavilionID(int pavilionID){
		this.pavilionID = pavilionID;
	}
}
