package DB;

import java.util.ArrayList;


import Models.Pavilion;
import Utils.Strings;
import android.content.Context;
import android.database.Cursor;

public class PavilionMethods extends DBHelper 
{
	Context context;
	String table=Strings._TABLEPAVILION;
	String[] columns={"PAVILIONID","NAME","DESCRIPTION"};
	public PavilionMethods(Context context) 
	{
		super(context);
		this.context=context;
		hallMethods=new HallMethods(context);
	}
	public ArrayList<Pavilion> GetAvailable()
	{
		ArrayList<Pavilion> pavilions=new ArrayList<Pavilion>();
		pavilions=GetAll();
		int pavilionsNumber=pavilions.size();
		for (int i = 0; i < pavilionsNumber; i++) {
			int hallsNumber=pavilions.get(i).getHalls().size();
			for (int j = 0; j < hallsNumber; j++) {
				int bedsNumber=pavilions.get(i).getHalls().get(j).getBeds().size();
				for (int k = 0; k < bedsNumber; k++) {
					if(pavilions.get(i).getHalls().get(j).getBeds().get(k).getavailability().equals("false"))
						pavilions.get(i).getHalls().get(j).getBeds().remove(k);
				}
			}
		}
		return pavilions;
	}
	public ArrayList<Pavilion> GetAll()
	{
		Cursor cursor = db.query(table, columns,null, null, null, null,null);
		return toArray(cursor);
	}
	private ArrayList<Pavilion> toArray(Cursor cursor) 
	{
		ArrayList<Pavilion> pavilions = new ArrayList<Pavilion>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			pavilions.add(toPavilion(cursor));
		}
		return pavilions;
	}
	HallMethods hallMethods;
	private Pavilion toPavilion(Cursor cursor) 
	{
		Pavilion pavilion=new Pavilion();
		pavilion.setpavilionID(cursor.getInt(0));
		pavilion.setname(cursor.getString(1));
		pavilion.setdescription(cursor.getString(2));
		pavilion.setHalls(hallMethods.GetByPavilionID(pavilion.getpavilionID()));
		return pavilion;
	}
}
