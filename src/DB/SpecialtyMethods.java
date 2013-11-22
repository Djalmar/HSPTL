package DB;

import java.util.ArrayList;
import java.util.List;
import Models.Specialty;
import Utils.Strings;
import android.content.Context;
import android.database.Cursor;

public class SpecialtyMethods extends DBHelper 
{
	private String table=Strings._TABLESPECIALTY;
	private String[] columns={"SPECIALTYID","NAME","DESCRIPTION"};
	public SpecialtyMethods(Context context) {
		super(context);
	}

	public Specialty getSpecialtyByID(int specialtyID)
	{
		List<Specialty> specialtyList=new ArrayList<Specialty>();
		specialtyList=toArray(db.query(table, columns, "SPECIALTYID LIKE '"+specialtyID+"'", null, null, null, null));
		if (specialtyList.size()==1)
			return specialtyList.get(0);
		return null;
	}
	public List<String> selectNames()
	{
		List<String> names = new ArrayList<String>();
		List<Specialty> specialties = selectAll();
		for (Specialty specialty : specialties) 
			names.add(specialty.getName());
		return names;
	}
	public List<Specialty> selectAll()
	{
		Cursor cursor=db.query(table, columns, null, null, null, null, null);
		return toArray(cursor);
	}
	private List<Specialty> toArray(Cursor cursor) {
		List<Specialty> specialtyList=new ArrayList<Specialty>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			Specialty specialty = new Specialty();
			specialty.setSpecialtyID(cursor.getInt(0));
			specialty.setName(cursor.getString(1));
			specialty.setDescription(cursor.getString(2));
			specialtyList.add(specialty);
		}
		return specialtyList;
	}
	public int getSpecialtyID(String currentSpecialty) 
	{
		List<Integer> chargesList=new ArrayList<Integer>();
		String[] columns={"SPECIALITYID"};
		Cursor cursor=db.query("SPECIALITY", columns, "NAME LIKE '"+currentSpecialty+"'", null, null, null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			chargesList.add((int)cursor.getShort(0));
		if(chargesList.size()>0)
			return chargesList.get(0);
		return -1;
	}
	public Specialty getSpecialty(String currentSpecialty)
	{
		return getSpecialtyByID(getSpecialtyID(currentSpecialty));
	}
}
