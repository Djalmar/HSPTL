package Models;

public class Consult {

	private int consultID;
	private String date;
	private String observation;
	private int clinicHistoryID;
	private int specialtyID;
	private int personalID;
	
	
	
	
	public int getClinicHistoryID() {
		return clinicHistoryID;
	}
	public void setClinicHistoryID(int clinicHistoryID) {
		this.clinicHistoryID = clinicHistoryID;
	}
	public int getSpecialtyID() {
		return specialtyID;
	}
	public void setSpecialtyID(int specialtyID) {
		this.specialtyID = specialtyID;
	}
	public int getPersonalID() {
		return personalID;
	}
	public void setPersonalID(int personalID) {
		this.personalID = personalID;
	}
	public int getConsultID() {
		return consultID;
	}
	public void setConsultID(int consultID) {
		this.consultID = consultID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;	
	}
}
