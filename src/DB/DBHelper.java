package DB;

import java.util.ArrayList;
import java.util.List;
import Models.Consult;
import Utils.CurrentUser;
import Utils.Strings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DBHelper {

	SQLiteDatabase db;
	Context context;
	public DBHelper(Context context) {
		try {
			this.context=context;
			db=SQLiteDatabase.openDatabase("/mnt/sdcard/HSPTLDB",
					null,SQLiteDatabase.CREATE_IF_NECESSARY);
		}
		catch (Exception e) {
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	public ArrayList<String> getTables()
	{
		ArrayList<String> tableList=new ArrayList<String>();
		String[] columns={"TABLENAME"};
		Cursor cursor=db.query(Strings._TABLEUSERPERMITIONS, columns,null , null, null, null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			if(!tableList.contains(cursor.getString(0)))
				tableList.add(cursor.getString(0));	
		return tableList;
	}
	public long insertConsult(Consult consult,int clinicHistoryID)
	{
		String[] columns={"PERSONALID"};
		int personalID=0;
		Cursor cursor=db.query("PERSONAL", columns, "USERID LIKE"+"'"+CurrentUser._USER.getUserID()+"'", null, null, null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			personalID=Integer.parseInt(cursor.getString(0));
		ContentValues values=new ContentValues();
		//values.put("USERID",);
		values.put("DATE",consult.getDate());
		values.put("OBSERVATION",consult.getObservation());
		values.put("CLINICHISTORYID", clinicHistoryID);
		values.put("PERSONALID", personalID);
		try
		{
			return db.insertOrThrow("CONSULT", null, values);
		}
		catch (Exception e) {
			Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
		}
		return -1;
	}
/*	public long insertUser(User user)
	{
		if(validateUser(user))
		{
			ContentValues values=new ContentValues();
			//values.put("USERID",);
			values.put("LOGIN",user.getLogin());
			values.put("PASSWORD",user.getPassword());
			try
			{
				return db.insertOrThrow("USER", null, values);
			}
			catch (Exception e) {
				Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
		else
			Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show();
		return -1;
	}*/
	/*
	private boolean validateUser(User user) 
	{
		String[] columns={"USERID","LOGIN","PASSWORD"};
		Cursor users = selectAll("USER",columns);
		for(users.moveToFirst();!users.isAfterLast();users.moveToNext())
		{
			Toast.makeText(context, users.getString(0).toString(), Toast.LENGTH_SHORT).show();
			if(users.getString(1).toString().equals(user.getLogin()))
				return false;
		}
		return true;
	}
	public Cursor selectAll(String table,String[] columns)
	{
		return db.query(table, columns, null, null, null, null, null);
	}

	public Cursor cursorFromQuery(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy)
	{
		return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	public int insertPermitions(HashMap<String, String> tablesAccess, int userid) 
	{
		for (String item : tablesAccess.keySet()) {
			if(!tablesAccess.get(item).equals("000"))
			{
				ContentValues values=new ContentValues();
				values.put("USERID",userid);
				values.put("TABLENAME",item);
				values.put("ATTRIB",tablesAccess.get(item));
				try
				{
					db.insertOrThrow("USERPERMITIONS", null, values);
				}
				catch (Exception e) {
					Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
					return -1;
				}
			}
		}
		return 1;
	}
	public HashMap<String, String> getUserPermitions(int _userID) {
		HashMap<String, String> permitions=new HashMap<String, String>();
		String[] columns={"TABLENAME","ATTRIB"};
		Cursor cursor=db.query("USERPERMITIONS", columns, "USERID LIKE"+"'"+_userID+"'", null, null, null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			permitions.put(cursor.getString(0),cursor.getString(1));
		return permitions;
	}*/
	public List<String> getCharges() {
		List<String> chargesList=new ArrayList<String>();
		String[] columns={"CHARGE"};
		Cursor cursor=db.query("PERSONALCHARGE", columns, null, null, null, null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			chargesList.add(cursor.getString(0));
		return chargesList;
	}
	public List<String> getSpecialtys() 
	{
		List<String> chargesList=new ArrayList<String>();
		String[] columns={"NAME"};
		Cursor cursor=db.query("SPECIALITY", columns, null, null, null, null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			if(!chargesList.contains(cursor.getString(0)))
				chargesList.add(cursor.getString(0));
		return chargesList;
	}
	public int getSpecialtyID(String currentSpecialty) {
		List<Integer> chargesList=new ArrayList<Integer>();
		String[] columns={"SPECIALITYID"};
		Cursor cursor=db.query("SPECIALITY", columns, "NAME LIKE '"+currentSpecialty+"'", null, null, null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			chargesList.add((int)cursor.getShort(0));
		if(chargesList.size()>0)
			return chargesList.get(0);
		return -1;
	}
	public String getSpecialtyByID(int specialtyID)
	{
		List<String> chargesList=new ArrayList<String>();
		String[] columns={"NAME"};
		Cursor cursor=db.query("SPECIALITY", columns, "SPECIALITYID LIKE '"+specialtyID+"'", null, null, null, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			chargesList.add(cursor.getString(0));
		if(chargesList.size()>0)
			return chargesList.get(0);
		return "";
	}
	public int insertConsult(Consult consult) {
		ContentValues values=new ContentValues();
		values.put("DATE",consult.getDate());
		values.put("OBSERVATION",consult.getObservation());
		values.put("CLINICHISTORYID", consult.getClinicHistoryID());
		values.put("PERSONALID", consult.getPersonalID());
		values.put("SPECIALITYID",consult.getSpecialtyID());
		try
		{
			return (int)db.insertOrThrow("CONSULT", null, values);
		}
		catch (Exception e) {
			Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
		}
		return -1;
	}
}
