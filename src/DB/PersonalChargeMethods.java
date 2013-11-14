package DB;

import java.util.ArrayList;
import java.util.List;

import Utils.Strings;
import android.content.Context;
import android.database.Cursor;

public class PersonalChargeMethods extends DBHelper 
{

	private String table=Strings._TABLEPERSONALCHARGE;
	private String[] columns={"PERSONALCHARGEID","CHARGE"};
	public PersonalChargeMethods(Context context) {
		super(context);
	}
	public String getCharge(int personalChargeID)
	{
		List<String> chargesList=new ArrayList<String>();
		chargesList=toArray(db.query(table, columns, "PERSONALCHARGEID LIKE '"+personalChargeID+"'", null, null, null, null));
		if(chargesList.size()!=0)
			return chargesList.get(0);
		return "";
	}
	public List<String> toArray(Cursor cursor)
	{
		List<String> chargesList=new ArrayList<String>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			chargesList.add(cursor.getString(1));
		return chargesList;
	}
	public List<Integer> toIntArray(Cursor cursor)
	{
		List<Integer> chargesList=new ArrayList<Integer>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			chargesList.add(Integer.parseInt(cursor.getString(0)));
		return chargesList;
	}
	public int getChargeID(String charge) 
	{
		List<Integer> idsList=new ArrayList<Integer>();
		idsList=toIntArray(db.query(table, columns, "CHARGE LIKE '"+charge+"'", null, null, null, null));
		if(idsList.size()!=0)
			return idsList.get(0);
		return -1;
	}

}
