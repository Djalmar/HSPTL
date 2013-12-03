package com.hsptl;

import java.util.List;

import DB.DoctorMethods;
import DataTemplates.DoctorItemTemplate;
import Models.Doctor;
import Models.UserPermitions;
import Utils.Constants;
import Utils.CurrentUser;
import Utils.Strings;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DoctorsActivity extends Activity {

	ListView lstPersonal;
	List<Doctor> personalList;
	DoctorMethods methods;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		methods=new DoctorMethods(this);
		personalList=methods.selectAll();
		loadToView();
	}

	private void loadToView() {
		lstPersonal=(ListView)findViewById(R.id.lstPersonal);
		setViewAdapter(personalList);
		registerForContextMenu(lstPersonal);
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.personalitemenu, menu);
	}

	private void setViewAdapter(List<Doctor> personalList) 
	{
		DoctorItemTemplate template=new DoctorItemTemplate(this, R.layout.personaltemplate, personalList);
		lstPersonal.setAdapter(template);
		
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		Doctor personal;
		AdapterContextMenuInfo info;
		info = (AdapterContextMenuInfo) item.getMenuInfo();
		personal= (Doctor) lstPersonal.getAdapter().getItem(info.position);
		switch (item.getItemId()) 
		{
		case R.id.item1:
			if(hasPermitions(Constants.EDIT_MODE))
				editPerson(personal);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item12:
			if(hasPermitions(Constants.DELETE_MODE))
				confirmDeletePerson(personal);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item13:
			if(hasPermitions(Strings._TABLECONSULT, Constants.CREATE_MODE))
				consultList(personal);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	private void consultList(Doctor personal) 
	{
		Intent intent =new Intent(this, ConsultsActivity.class);
		intent.putExtra("PERSONALID", personal.getDoctorID());
		startActivity(intent);
	}

	private boolean hasPermitions(String table,String permition) 
	{
		for (UserPermitions item : CurrentUser._USERPERMITIONS)
			if(item.getTableName().equals(table))
				if(item.hasPermitions(permition))
					return true;
		return false;
	}
	private void confirmDeletePerson(Doctor personal) {
		//crear un dialogo de confirmacion
		String message="Data don't saved";
		if(methods.delete(personal)!=-1)
		{
			message="Data saved";
			personalList=methods.selectAll();
			loadToView();
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private void editPerson(Doctor personal) 
	{
		Intent intent=new Intent(this, DoctorFormActivity.class);
		intent.putExtra("PERSONALID", personal.getDoctorID());
		CurrentUser._userMode=Constants.EDIT_MODE;
		startActivityForResult(intent, 1);
	}

	private boolean hasPermitions(String edit) 
	{
		for (UserPermitions item : CurrentUser._USERPERMITIONS) 
			if(item.getTableName().equals(Strings._TABLEDOCTOR))
				return item.hasPermitions(edit);
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		String message="Data don't saved";
		if(resultCode>Constants.RESULT_NOT_OK && resultCode>0)
		{
			message="Data saved";
			personalList=methods.selectAll();
			loadToView();
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_newPersonal:
			// Go to edit activity
			Intent intent = new Intent(this, DoctorFormActivity.class);
			CurrentUser._userMode=Constants.CREATE_MODE;
			startActivityForResult(intent, 1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal, menu);
		return true;
	}

}
