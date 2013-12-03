package DB;

import java.util.ArrayList;

import Models.Doctor;
import Utils.Strings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DoctorMethods extends DBHelper 
{
	private SpecialtyMethods specialtyMethods;
	private String table=Strings._TABLEDOCTOR;
	private String[] columns={"DOCTORID","NAME","SPECIALTYID","ARRIVETIME","LEAVINGTIME","USERID"};
	public DoctorMethods(Context context) 
	{
		super(context);
		specialtyMethods=new SpecialtyMethods(context);
	}
	public int insert(Doctor personal)
	{
		ContentValues values=new ContentValues();
		values.put("NAME",personal.getName());
		values.put("ARRIVETIME",personal.getArriveTime());
		values.put("LEAVINGTIME", personal.getLeavingTime());
		values.put("USERID",personal.getUserID());
		return (int)db.insert(table, null, values);
	}
	public ArrayList<Doctor> selectAll()
	{
		Cursor cursor=db.query(table, columns, null, null, null, null, null);
		return toArray(cursor);
	}
	public Doctor getDoctorByUserID(int userID)
	{
		Cursor cursor=db.query(table, columns, "USERID LIKE "+"'"+userID+"'", null, null, null, null);
		ArrayList<Doctor> personalList=toArray(cursor);
		if(personalList.size()>0)
			return personalList.get(0);
		else
			return null;
	}
	public Doctor getDoctorByID(int doctorID)
	{
		Cursor cursor=db.query(table, columns, "DOCTORID LIKE '"+doctorID+"'", null, null, null, null);
		ArrayList<Doctor> personalList=toArray(cursor);
		if(personalList.size()==1)
			return personalList.get(0);
		else
			return null;
	}
	private ArrayList<Doctor> toArray(Cursor cursor) {
		ArrayList<Doctor> personalList=new ArrayList<Doctor>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			Doctor personal=new Doctor();
			personal.setDoctorID(Integer.parseInt( cursor.getString(0)));
			personal.setName(cursor.getString(1));
			personal.setSpecialty(specialtyMethods.getSpecialtyByID(cursor.getInt(2)));
			personal.setArriveTime(cursor.getString(3));
			personal.setLeavingTime(cursor.getString(4));
			personal.setUserID((int)cursor.getShort(5));
			personal.setLogin((int)cursor.getShort(5));
			personalList.add(personal);
		}
		return personalList;
	}
	public int delete(Doctor doctor) {
		//informar de los riesgos
		//hacer update en la tabla de permisos
		db.delete(Strings._TABLEUSERPERMITIONS, "USERID LIKE '"+doctor.getDoctorID()+"'", null);
		return db.delete(table, "USERID LIKE '"+doctor.getDoctorID()+"'", null);
	}
	public int update(Doctor doctor)
	{	
		ContentValues values=new ContentValues();
		values.put("NAME",doctor.getName());
		values.put("ARRIVETIME",doctor.getArriveTime());
		values.put("LEAVINGTIME", doctor.getLeavingTime());
		values.put("USERID",doctor.getUserID());
		values.put("SPECIALTYID", doctor.getSpecialty().getSpecialtyID());
		return (int)db.update(table, values, "PERSONALID LIKE '"+doctor.getDoctorID()+"'", null);
	}
}
