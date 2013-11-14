package DB;

import java.util.ArrayList;

import Models.Personal;
import Utils.Strings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class PersonalMethods extends DBHelper 
{
	private Personal personal;
	private String table=Strings._TABLEPERSONAL;
	private String[] columns={"PERSONALID","NAME","SALARY","ARRIVETIME","LEAVINGTIME","PERSONALCHARGEID","USERID"};
	public PersonalMethods(Context context,Personal personal) 
	{
		super(context);
		this.personal=personal;
	}
	public PersonalMethods(Context context) 
	{
		super(context);
	}
	public void insert()
	{
		ContentValues values=new ContentValues();
		values.put("NAME",personal.getName());
		values.put("SALARY", personal.getSalary());
		values.put("ARRIVETIME",personal.getArriveTime());
		values.put("LEAVINGTIME", personal.getLeavingTime());
		values.put("PERSONALCHARGEID", personal.getPersonalChargeID());
		values.put("USERID",personal.getUserID());
		try
		{
			if(db.insertOrThrow(table, null, values)!=-1)
				return;
			//manage a message
		}
		catch (Exception e) {
			//manage a message
		}
	}
	public int insert(Personal personal)
	{
		ContentValues values=new ContentValues();
		values.put("NAME",personal.getName());
		values.put("SALARY", personal.getSalary());
		values.put("ARRIVETIME",personal.getArriveTime());
		values.put("LEAVINGTIME", personal.getLeavingTime());
		values.put("PERSONALCHARGEID", personal.getPersonalChargeID());
		values.put("USERID",personal.getUserID());
		return (int)db.insert(table, null, values);
	}
	public ArrayList<Personal> selectAll()
	{
		Cursor cursor=db.query(table, columns, null, null, null, null, null);
		return toArray(cursor);
	}
	public Personal getPersonalByUserID(int userID)
	{
		Cursor cursor=db.query(table, columns, "USERID LIKE "+"'"+userID+"'", null, null, null, null);
		ArrayList<Personal> personalList=toArray(cursor);
		if(personalList.size()>0)
			return personalList.get(0);
		else
			return null;
	}
	public Personal getPersonalByID(int personalID)
	{
		Cursor cursor=db.query(table, columns, "PERSONALID LIKE '"+personalID+"'", null, null, null, null);
		ArrayList<Personal> personalList=toArray(cursor);
		if(personalList.size()==1)
			return personalList.get(0);
		else
			return null;
	}
	
	private ArrayList<Personal> toArray(Cursor cursor) {
		ArrayList<Personal> personalList=new ArrayList<Personal>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			Personal personal=new Personal();
			personal.setPersonalID(Integer.parseInt( cursor.getString(0)));
			personal.setName(cursor.getString(1));
			personal.setSalary(Double.parseDouble(cursor.getString(2)));
			personal.setArriveTime(cursor.getString(3));
			personal.setLeavingTime(cursor.getString(4));
			personal.setPersonalChargeID((int)cursor.getShort(5));
			personal.setUserID((int)cursor.getShort(6));
			personal.setLogin((int)cursor.getShort(6));
			personalList.add(personal);
		}
		return personalList;
	}
	public int delete(Personal personal) {
		//informar de los riesgos
		//hacer update en la tabla de permisos
		db.delete(Strings._TABLEUSERPERMITIONS, "USERID LIKE '"+personal.getPersonalID()+"'", null);
		return db.delete(table, "USERID LIKE '"+personal.getPersonalID()+"'", null);
	}
	public int update(Personal personal)
	{	
		ContentValues values=new ContentValues();
		values.put("NAME",personal.getName());
		values.put("SALARY", personal.getSalary());
		values.put("ARRIVETIME",personal.getArriveTime());
		values.put("LEAVINGTIME", personal.getLeavingTime());
		values.put("PERSONALCHARGEID", personal.getPersonalChargeID());
		values.put("USERID",personal.getUserID());
		return (int)db.update(table, values, "PERSONALID LIKE '"+personal.getPersonalID()+"'", null);
	}
}
