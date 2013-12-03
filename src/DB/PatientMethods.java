package DB;

import java.util.ArrayList;
import java.util.List;

import Models.Patient;
import Utils.Strings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class PatientMethods extends DBHelper 
{
	private Patient person;
	private String table=Strings._TABLEPATIENT;
	private String[] columns={"PATIENTID","NAME","WEIGHT","SIZE","GENDER","BIRTHDAY"};
	public PatientMethods(Context context) {
		super(context);
	}
	public PatientMethods(Context context,Patient person) {
		super(context);
		this.person=person;
	}
	public long insert()
	{
		ContentValues values=new ContentValues();
		values.put("NAME", person.getName());
		values.put("WEIGHT", person.getWeight());
		values.put("SIZE",person.getSize());
		values.put("GENDER",person.isGender());
		values.put("BIRTHDAY", person.getBirthday().toString());
		return db.insert(table, null, values);
	}
	public long insert(Patient person)
	{
		ContentValues values=new ContentValues();
		values.put("NAME", person.getName());
		values.put("WEIGHT", person.getWeight());
		values.put("SIZE",person.getSize());
		values.put("GENDER",person.isGender());
		values.put("BIRTHDAY", person.getBirthday().toString());
		return db.insert(table, null, values);
	}
	public Patient getPatientByID(int patientID)
	{
		List<Patient> personList=new ArrayList<Patient>();
		personList=toArray(db.query(table, columns, "PATIENTID LIKE '"+patientID+"'", null, null, null, null));
		if (personList.size()==1)
			return personList.get(0);
		return null;
	}
	public List<Patient> selectAll()
	{
		Cursor cursor=db.query(table, columns, null, null, null, null, null);
		return toArray(cursor);
	}
	private List<Patient> toArray(Cursor cursor) {
		List<Patient> personList=new ArrayList<Patient>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			Patient person=new Patient();
			person.setPatientID(cursor.getInt(0));
			person.setName(cursor.getString(1));
			person.setWeight(cursor.getDouble(2));
			person.setSize(cursor.getDouble(3));
			person.setGender(cursor.getString(4));
			person.setBirthday(cursor.getString(5));
			personList.add(person);
		}
		return personList;
	}
	public int update(Patient patient) 
	{
		ContentValues values=new ContentValues();
		values.put("NAME", patient.getName());
		values.put("WEIGHT", patient.getWeight());
		values.put("SIZE", patient.getSize());
		values.put("GENDER", patient.isGender());
		values.put("BIRTHDAY", patient.getBirthday());
		return db.update(table, values, "PATIENTID LIKE '"+patient.getPatientID()+"'", null);
	}
	public int delete(Patient person) 
	{
		return db.delete(table, "PATIENTID LIKE '"+person.getPatientID()+"'", null);
	}
}
