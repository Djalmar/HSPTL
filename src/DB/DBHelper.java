package DB;

import java.util.ArrayList;
import Utils.Strings;
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
}
