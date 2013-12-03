package DB;

import java.util.ArrayList;

import Models.Bed;
import Models.Hall;
import Utils.Strings;
import android.content.Context;
import android.database.Cursor;

public class HallMethods extends DBHelper {
	Context context;
	String table=Strings._TABLEHALL;
	String[] columns={"HALLID","NAME","PAVILIONID"};
	BedMethods bedMethods;
	public HallMethods(Context context) 
	{
		super(context);
		this.context=context;
		bedMethods=new BedMethods(context);
	}
	public ArrayList<Hall> GetByPavilionID(int pavilionID)
	{
		Cursor cursor = db.query(table, columns, "PAVILIONID LIKE '"+pavilionID+"'", null, null, null,null);
		return toArray(cursor);
	}
	private ArrayList<Hall> toArray(Cursor cursor) 
	{
		ArrayList<Hall> halls = new ArrayList<Hall>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			halls.add(toHall(cursor));
		}
		return halls;
	}
	private Hall toHall(Cursor cursor) 
	{
		Hall hall=new Hall();
		hall.sethallID(cursor.getInt(0));
		hall.setname(cursor.getString(1));
		hall.setpavilionID(cursor.getShort(2));
		hall.setBeds(bedMethods.GetByHallID(hall.gethallID()));
		return hall;
	}
}
