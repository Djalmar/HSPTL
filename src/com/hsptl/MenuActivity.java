package com.hsptl;

import DB.UserPermitionsMethods;
import Models.UserPermitions;
import Utils.CurrentUser;
import Utils.Strings;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity 
{
	Button btnPersonal;
	Button btnUsers;
	Button btnConsult;
	Button btnPatients;
	Button btnPeople;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		loadPermitions();
		bindingData();
	}
	private void bindingData() {
		btnPersonal=(Button)findViewById(R.id.btnPersonal);
		btnUsers=(Button)findViewById(R.id.btnUsers);
		btnConsult=(Button)findViewById(R.id.btnConsult);
		btnPatients=(Button)findViewById(R.id.btnPatients);
		btnPeople=(Button)findViewById(R.id.btnPeople);
		if(!hasPermition(Strings._TABLEPERSONAL))
			btnPersonal.setVisibility(8);
		if(!hasPermition(Strings._TABLEUSER))
			btnUsers.setVisibility(8);
		if(!hasPermition(Strings._TABLECONSULT))
			btnConsult.setVisibility(8);
		if(!hasPermition(Strings._TABLEPATIENT))
			btnPatients.setVisibility(8);
		if(!hasPermition(Strings._TABLEPERSON))
			btnPeople.setVisibility(8);
	}
	private boolean hasPermition(String _TABLEPERSONAL) 
	{
		for (UserPermitions item : CurrentUser._USERPERMITIONS)
			if(item.getTableName().equals(_TABLEPERSONAL))
				return true;
		return false;
	}
	private void loadPermitions() 
	{
		UserPermitionsMethods methods=new UserPermitionsMethods(this);
		CurrentUser._USERPERMITIONS=methods.getPermitions(CurrentUser._USER);
	}

	public void pacientView(View view)
	{
		Intent intent = new Intent (this,BETAPatientsActivity.class); 
		startActivity(intent);
	}
	public void personalView(View view)
	{
		Intent intent=new Intent(this,DoctorsActivity.class);
		startActivity(intent);
	}
	public void consultView(View view)
	{
		Intent intent = new Intent (this,ConsultsActivity.class); 
		startActivity(intent);
	}
	public void peopleView(View view)
	{
		Intent intent=new Intent(this,PatientsActivity.class);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
