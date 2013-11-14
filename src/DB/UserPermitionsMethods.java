package DB;

import java.util.ArrayList;
import java.util.HashMap;

import Models.User;
import Models.UserPermitions;
import Utils.Strings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.widget.Toast;

public class UserPermitionsMethods extends DBHelper 
{
	private String table=Strings._TABLEUSERPERMITIONS;
	private String[] columns={"PERMITIONSID","USERID","TABLENAME","ATTRIB"};
	public UserPermitionsMethods(Context context) 
	{
		super(context);
	}
	public ArrayList<UserPermitions> getPermitions(User user)
	{
		return toArray(db.query(table, columns, "USERID LIKE "+"'"+user.getUserID()+"'", null, null, null, null));
	}
	private ArrayList<UserPermitions> toArray(Cursor cursor) 
	{
		ArrayList<UserPermitions> permitionsList=new ArrayList<UserPermitions>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			UserPermitions permitions=new UserPermitions();
			permitions.setPermitionsID(cursor.getInt(0));
			permitions.setUserID(cursor.getInt(1));
			permitions.setTableName(cursor.getString(2));
			permitions.setPermitions(cursor.getString(3));
			permitionsList.add(permitions);
		}
		return permitionsList;
	}
	public void insert(HashMap<String, String> permitions,User user)
	{
		for (String item : permitions.keySet()) {
			UserPermitions permition=new UserPermitions();
			permition.setTableName(item);
			permition.setPermitions(permitions.get(item));
			permition.setUserID(user.getUserID());
			if(!permition.getPermitions().equals("000"))
			{
				Toast.makeText(context, insert(permition)+" "+permition.getUserID()+" "+permition.getTableName()+" "+permition.getPermitions(), Toast.LENGTH_SHORT).show();
			}
		}
	}
	public int insert(UserPermitions permition)
	{
		ContentValues values=new ContentValues();
		values.put(columns[1], (Integer)permition.getUserID());
		values.put(columns[2], permition.getTableName());
		values.put(columns[3], permition.getPermitions());
		try
		{
			int x=(int) db.insertOrThrow(table, null, values);
			return x;
		}
		catch (SQLException e) {
			Toast.makeText(context,e.toString()+"Error adding permitions in table : "+permition.getTableName(), Toast.LENGTH_SHORT).show();
			return -1;
		}
	}
	public void insert(ArrayList<UserPermitions> permitions)
	{
		for (UserPermitions userPermitions : permitions)
			insert(userPermitions);
	}
}
