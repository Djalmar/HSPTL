package DB;

import java.util.ArrayList;

import Models.Bed;
import Utils.Strings;
import android.content.Context;
import android.database.Cursor;

public class BedMethods extends DBHelper 
{
	Context context;
	String table=Strings._TABLEBED;
	String[] columns={"BEDID","HALLID","AVAILABILITY"};
	public BedMethods(Context context) 
	{
		super(context);
		this.context=context;
	}
	public ArrayList<Bed> GetByHallID(int hallID)
	{
		Cursor cursor = db.query(table, columns, "HALLID LIKE '"+hallID+"'", null, null, null,null);
		return toArray(cursor);
	}
	private ArrayList<Bed> toArray(Cursor cursor) 
	{
		ArrayList<Bed> beds = new ArrayList<Bed>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			beds.add(toBed(cursor));
		}
		return beds;
	}
	private Bed toBed(Cursor cursor) 
	{
		Bed bed=new Bed();
		bed.setbedID(cursor.getInt(0));
		bed.sethallID(cursor.getInt(1));
		bed.setavailability(cursor.getString(2));
		return bed;
	}
}
