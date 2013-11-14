package Models;

import Utils.Constants;

public class UserPermitions 
{
	private int permitionsID;
	private int userID;
	private String tableName;
	private String permitions;
	public int getPermitionsID() {
		return permitionsID;
	}
	public void setPermitionsID(int permitionsID) {
		this.permitionsID = permitionsID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getPermitions() {
		return permitions;
	}
	public void setPermitions(String permitions) {
		this.permitions = permitions;
	}
	public boolean hasPermitions(String permition)
	{
		if(permition.equals(Constants.EDIT_MODE))
		{
			if(permitions.charAt(1)=='1')
				return true;
			else return false;
		}
		if(permition.equals(Constants.DELETE_MODE))
		{
			if(permitions.charAt(2)=='1')
				return true;
			else return false;
		}	
		if(permition.equals(Constants.CREATE_MODE))
		{
			if(permitions.charAt(0)=='1')
				return true;
			else return false;
		}	
		return false;
	}
}
