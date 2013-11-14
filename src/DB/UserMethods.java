package DB;

import java.util.ArrayList;

import Models.User;
import Utils.Strings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

public class UserMethods extends DBHelper 
{
	User user;
	String table=Strings._TABLEUSER;
	String[] columns={"USERID","LOGIN","PASSWORD"};
	public UserMethods(Context context) {
		super(context);
	}
	public UserMethods(Context context,User user) {
		super(context);
		this.user=user;
	}
	public int insert()
	{
		if(!userExists())
		{
			ContentValues values=new ContentValues();
			values.put("LOGIN", user.getLogin());
			values.put("PASSWORD",user.getPassword());
			try
			{
				return (int)db.insertOrThrow(table, null, values);
			}
			catch (Exception e) {
				Toast.makeText(context, "There was a problemo jefe", Toast.LENGTH_SHORT).show();
			}
			return -1;
		}
		return -1;
	}
	public boolean userExists() 
	{
		if(getUser()==null)
			return false;
		return true;
	}
	public User getUser(String login,String password)
	{
		ArrayList<User> userList=new ArrayList<User>();
		userList=toArray(db.query(table, columns, "LOGIN LIKE "+"'"+login+"' AND PASSWORD LIKE "+"'"+password+"'", null, null, null, null));
		if(userList.size()!=0)
			return userList.get(0);
		return null;
	}
	public User getUser()
	{
		ArrayList<User> userList=new ArrayList<User>();
		userList=toArray(db.query(table, columns, "LOGIN LIKE "+"'"+user.getLogin()+"'", null, null, null, null));
		if(userList.size()!=0)
			return userList.get(0);
		return null;
	}
	private ArrayList<User> toArray(Cursor cursor) {
		ArrayList<User> userList=new ArrayList<User>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			User user=new User();
			user.setUserID(cursor.getInt(0));
			user.setLogin(cursor.getString(1));
			user.setPassword(cursor.getString(2));
			userList.add(user);
		}
		return userList;
	}
	public User getUserByID(int userID) 
	{
		ArrayList<User> userList=new ArrayList<User>();
		userList=toArray(db.query(table, columns, "USERID LIKE '"+userID+"'", null, null, null, null));
		if(userList.size()!=0)
			return userList.get(0);
		return null;
	}
	public User getUser(String login) {
		ArrayList<User> userList=new ArrayList<User>();
		userList=toArray(db.query(table, columns, "LOGIN LIKE '"+login+"'", null, null, null, null));
		if(userList.size()!=0)
			return userList.get(0);
		return null;
	}
}
