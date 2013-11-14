package DB;

import java.util.ArrayList;
import java.util.List;

import Models.Person;
import Utils.Strings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class PersonMethods extends DBHelper 
{
	private Person person;
	private String table=Strings._TABLEPERSON;
	private String[] columns={"PERSONID","NAME","WEIGHT","SIZE","GENDER","BIRTHDAY"};
	public PersonMethods(Context context) {
		super(context);
	}
	public PersonMethods(Context context,Person person) {
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
	public long insert(Person person)
	{
		ContentValues values=new ContentValues();
		values.put("NAME", person.getName());
		values.put("WEIGHT", person.getWeight());
		values.put("SIZE",person.getSize());
		values.put("GENDER",person.isGender());
		values.put("BIRTHDAY", person.getBirthday().toString());
		return db.insert(table, null, values);
	}
	public Person getPersonByID(int personID)
	{
		List<Person> personList=new ArrayList<Person>();
		personList=toArray(db.query(table, columns, "PERSONID LIKE '"+personID+"'", null, null, null, null));
		if (personList.size()==1)
			return personList.get(0);
		return null;
	}
	public List<Person> selectAll()
	{
		Cursor cursor=db.query(table, columns, null, null, null, null, null);
		return toArray(cursor);
	}
	private List<Person> toArray(Cursor cursor) {
		List<Person> personList=new ArrayList<Person>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			Person person=new Person();
			person.setPersonID(cursor.getInt(0));
			person.setName(cursor.getString(1));
			person.setWeight(cursor.getDouble(2));
			person.setSize(cursor.getDouble(3));
			person.setGender(cursor.getString(4));
			person.setBirthday(cursor.getString(5));
			personList.add(person);
		}
		return personList;
	}
	public int update(Person person) 
	{
		ContentValues values=new ContentValues();
		values.put("NAME", person.getName());
		values.put("WEIGHT", person.getWeight());
		values.put("SIZE", person.getSize());
		values.put("GENDER", person.isGender());
		values.put("BIRTHDAY", person.getBirthday());
		return db.update(table, values, "PERSONID LIKE '"+person.personID+"'", null);
	}
	public int delete(Person person) 
	{
		return db.delete(table, "PERSONID LIKE '"+person.personID+"'", null);
	}
	public int getClinicHistory(int personID) 
	{
		String[] columns={"CLINICHISTORYID"};
		Cursor cursor=db.query("CLINICHISTORY", columns, "PERSONID LIKE '"+personID+"'", null, null, null, null);
		int historyID=-1;
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			historyID=(int)cursor.getShort(0);
		if(historyID==-1)
			return createHistory(personID);
		return historyID;
	}
	public Person getPeronByHistoryID(int historyID)
	{
		String[] columns={"PERSONID"};
		Cursor cursorHistory=db.query("CLINICHISTORY", columns, "CLINICHISTORYID LIKE '"+historyID+"'", null, null, null, null);
		int personID=-1;
		for(cursorHistory.moveToFirst();!cursorHistory.isAfterLast();cursorHistory.moveToNext())
			personID=(int)cursorHistory.getShort(0);
		if(personID!=-1)
			return getPersonByID(personID);
		return null;
	}
	public int createHistory(int personID)
	{
		ContentValues values=new ContentValues();
		values.put("CREATIONDATE", "12/03/2012");
		values.put("PERSONID",personID);
		return (int) db.insert("CLINICHISTORY", null, values);
	}
}
