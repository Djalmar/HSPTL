package com.hsptl;

import java.util.List;
import DB.PatientMethods;
import DataTemplates.PersonItemTemplate;
import Models.Patient;
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
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

public class PatientsActivity extends Activity {

	ListView lstPeople;
	List<Patient> peopleList;
	PatientMethods methods;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_people);
		methods=new PatientMethods(this);
		peopleList=methods.selectAll();
		loadToView();
	}
	private void loadToView() {
		lstPeople=(ListView)findViewById(R.id.lstPeople);
		setViewAdapter(peopleList);
		registerForContextMenu(lstPeople);
	}
	private void setViewAdapter(List<Patient> data) {
		PersonItemTemplate template=new PersonItemTemplate(this, R.layout.persontemplate, data);
		lstPeople.setAdapter(template);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.peopleitemmenu, menu);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.people, menu);
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		Patient person;
		AdapterContextMenuInfo info;
		info = (AdapterContextMenuInfo) item.getMenuInfo();
		person= (Patient) lstPeople.getAdapter().getItem(info.position);
		switch (item.getItemId()) 
		{
		case R.id.item1:
			if(hasPermitions(Constants.EDIT_MODE))
				editPerson(person);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item12:
			if(hasPermitions(Constants.DELETE_MODE))
				confirmDeletePerson(person);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item13:
			if(hasPermitions(Strings._TABLECONSULT,Constants.CREATE_MODE))
				consult(person);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item14:
			if(hasPermitions(Strings._TABLECONSULT,Constants.CREATE_MODE))
				consultList(person);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item15:
			if(hasPermitions(Strings._TABLEHOSPITALIZE,Constants.CREATE_MODE)||true)
				hospitalize(person);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item16:
			if(hasPermitions(Strings._TABLEHOSPITALIZE,Constants.CREATE_MODE) || true)
				hospitalizeList(person);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void consultList(Patient person) 
	{
		Intent intent =new Intent(this, ConsultsActivity.class);
		intent.putExtra("PERSONID", person.getPatientID());
		startActivityForResult(intent,1);
	}

	private void consult(Patient person) {
		Intent intent =new Intent(this, ConsultFormActivity.class);
		intent.putExtra("PERSONID", person.getPatientID());
		startActivityForResult(intent,1);
	}
	private void hospitalizeList(Patient person) 
	{
		Intent intent =new Intent(this, HospitalizeActivity.class);
		intent.putExtra("PERSONID", person.getPatientID());
		startActivityForResult(intent,1);
	}

	private void hospitalize(Patient person) {
		Intent intent =new Intent(this, HospitalizeFormActivity.class);
		intent.putExtra("PERSONID", person.getPatientID());
		startActivityForResult(intent,1);
	}
	private boolean hasPermitions(String table,String permition) 
	{
		for (UserPermitions item : CurrentUser._USERPERMITIONS)
			if(item.getTableName().equals(table))
				if(item.hasPermitions(permition))
					return true;
		return false;
	}

	private void confirmDeletePerson(Patient person) {
		//crear un dialogo de confirmacion
		String message="Data don't saved";
		if(methods.delete(person)!=-1)
		{
			message="Data saved";
			peopleList=methods.selectAll();
			loadToView();
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private void editPerson(Patient person) 
	{
		Intent intent=new Intent(this, PatientFormActivity.class);
		intent.putExtra("PERSONID", person.getPatientID());
		CurrentUser._userMode=Constants.EDIT_MODE;
		startActivityForResult(intent, 1);
	}

	private boolean hasPermitions(String edit) 
	{
		for (UserPermitions item : CurrentUser._USERPERMITIONS) 
			if(item.getTableName().equals(Strings._TABLEPERSON))
				return item.hasPermitions(edit);
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		String message="Data don't saved";
		if(resultCode!=Constants.RESULT_NOT_OK&&resultCode>0)
		{
			message="Data saved";
			peopleList=methods.selectAll();
			loadToView();
		}
		if(resultCode==-2)
			message="Current user is not a personal registered";
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_newPerson:
			// Go to edit activity
			Intent intent = new Intent(this, PatientFormActivity.class);
			CurrentUser._userMode=Constants.CREATE_MODE;
			startActivityForResult(intent, 1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
