package com.hsptl;


import DB.UserMethods;
import Models.User;
import Utils.Constants;
import Utils.CurrentUser;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText txtUser;
	EditText txtPassword;
	User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindingData();
		clearFields();
	}

	private void clearFields() {
		txtUser.setText("");
		txtPassword.setText("");
		CurrentUser._USER=null;
	}

	public void bindingData()
	{
		txtUser=(EditText)findViewById(R.id.txtUser);
		txtPassword=(EditText)findViewById(R.id.txtPassword);
	}
	public void loadDataFromView()
	{
		user=new User();
		user.setLogin(txtUser.getText().toString());
		user.setPassword(txtPassword.getText().toString());
	}
	public void logIn(View view)
	{
		loadDataFromView();
		if(validateFields())
		{
			UserMethods methods=new UserMethods(this);
			user= methods.getUser(user.getLogin(), user.getPassword());
			if(user!=null)
			{
				CurrentUser._USER=user;
				Intent intent=new Intent(this,MenuActivity.class);
				startActivity(intent);
			}
			else
				Toast.makeText(this, "User or password not found", Toast.LENGTH_LONG).show();
		}
		else
			Toast.makeText(this, "Both fields must be filled", Toast.LENGTH_LONG).show();

	}
	private boolean validateFields() 
	{
		if(user.getLogin().length()>0 && user.getPassword().length()>0)
			return true;
		return false;
	}

	public void signIn(View view)
	{
		CurrentUser._userMode=Constants.CREATE_MODE;
		Intent intent = new Intent (this,NewUserActivity.class);
		startActivityForResult(intent, 1);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String message = "Changes failed";
		if (resultCode == Constants.RESULT_OK)
			message = "User saved";
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
