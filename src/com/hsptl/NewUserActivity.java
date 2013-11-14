package com.hsptl;

import java.util.ArrayList;
import java.util.HashMap;

import DB.DBHelper;
import DB.UserMethods;
import DB.UserPermitionsMethods;
import Models.User;
import Utils.Constants;
import Utils.CurrentUser;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewUserActivity extends Activity implements OnItemSelectedListener{

	EditText userName;
	EditText userPassword;
	ArrayList<String> tables;
	String selectedTable;
	CheckBox read;
	CheckBox write;
	CheckBox delete;
	HashMap<String, String> permitions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
		DBHelper methods=new DBHelper(this);
		tables = methods.getTables();
		loadTablesToView();
		read = (CheckBox)findViewById(R.id.cbxRead);
		write=(CheckBox)findViewById(R.id.cbxWrite);
		delete=(CheckBox)findViewById(R.id.cbxDelete);
		read.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
					permitions.put(selectedTable, "1"+permitions.get(selectedTable).charAt(1)+""+permitions.get(selectedTable).charAt(2));
				else
					permitions.put(selectedTable, "0"+permitions.get(selectedTable).charAt(1)+""+permitions.get(selectedTable).charAt(2));
				Toast.makeText(buttonView.getContext(), permitions.get(selectedTable)+" read", Toast.LENGTH_SHORT).show();
			}
		});
		write.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) 
			{
				if(isChecked)
					permitions.put(selectedTable,permitions.get(selectedTable).charAt(0)+"1"+permitions.get(selectedTable).charAt(2));
				else
					permitions.put(selectedTable,permitions.get(selectedTable).charAt(0)+"0"+permitions.get(selectedTable).charAt(2));
				Toast.makeText(buttonView.getContext(), permitions.get(selectedTable)+" write", Toast.LENGTH_SHORT).show();
			}
		});
		delete.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
					permitions.put(selectedTable,permitions.get(selectedTable).charAt(0)+""+permitions.get(selectedTable).charAt(1)+"1");
				else
					permitions.put(selectedTable,permitions.get(selectedTable).charAt(0)+""+permitions.get(selectedTable).charAt(1)+"0");
				Toast.makeText(buttonView.getContext(), permitions.get(selectedTable)+" delete", Toast.LENGTH_SHORT).show();
			}
		});
	}
	Spinner spinner;
	private void loadTablesToView() 
	{
		permitions=new HashMap<String, String>();
		for (String item : tables)
			permitions.put(item, "000");
		spinner=(Spinner)findViewById(R.id.cmbTables);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.tablesadapter,tables );
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}	
	public void save(View view)
	{
		loadFromView();
		loadUser();
		UserMethods methods=new UserMethods(this,user);
		if(validateFields())
		{
			if(!methods.userExists())
			{
				int result=Constants.RESULT_NOT_OK;
				if(CurrentUser._userMode.equals(Constants.CREATE_MODE))
				{
					int userID=methods.insert();
					if(userID!=-1)
					{
						user.setUserID(userID);
						UserPermitionsMethods permitionMethods=new UserPermitionsMethods(this);
						permitionMethods.insert(permitions, user);
						result=Constants.RESULT_OK;
					}
				}
				saveChanges(result);
			}
			else
				Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
	}
	private boolean validateFields() {
		if(user.getLogin().length()!=0&&user.getPassword().length()!=0)
			return true;
		Toast.makeText(this, "Both fields must filled", Toast.LENGTH_SHORT).show();
		return false;
	}
	private void saveChanges(int resultCode) {
		Intent intent=new Intent();
		setResult(resultCode,intent);
		finish();
	}
	private User user;

	private void loadUser() {
		user=new User();
		user.setLogin(userName.getText().toString().trim());
		user.setPassword(userPassword.getText().toString());
	}
	private void loadFromView() 
	{
		userName=(EditText)findViewById(R.id.txtUserName);
		userPassword=(EditText)findViewById(R.id.txtUserPassword);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_user, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {		
		selectedTable=spinner.getItemAtPosition(arg2).toString();
		loadToCheckBox();
	}
	private void loadToCheckBox() 
	{
		if(permitions.get(selectedTable).charAt(0)=='1')
			read.setChecked(true);
		else
			read.setChecked(false);
		if(permitions.get(selectedTable).charAt(1)=='1')
			write.setChecked(true);
		else
			write.setChecked(false);
		if(permitions.get(selectedTable).charAt(2)=='1')
			delete.setChecked(true);
		else
			delete.setChecked(false);
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
