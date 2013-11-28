package com.hsptl;

import java.util.List;

import DB.HospitalizeMethods;
import DataTemplates.HospitalizeItemTemplate;
import Models.Hospitalize;
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

public class HospitalizeActivity extends Activity {

	ListView lstHospitalize;
	HospitalizeMethods methods;
	List<Hospitalize> hospitalizeList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospitalize);
		bindingData();
		loadDataToView();
	}

	private void loadDataToView() {
		methods=new HospitalizeMethods(this);
		int personID=getIntent().getIntExtra("PERSONID", -1);
		if(personID != -1)
			hospitalizeList=methods.getHospitalizeByUserID(personID);
		else 
		{
			int personalID=getIntent().getIntExtra("PERSONALID", -1);
			if(personalID != -1)
				hospitalizeList=methods.getHospitalizeByDoctorID(personalID);
			else
				hospitalizeList=methods.selecctAll();
		}
		for (Hospitalize item : hospitalizeList) {
			if(item.getdoctorID()==0)
				hospitalizeList.remove(item);
		}
		setViewAdapter(hospitalizeList);
	}

	private void setViewAdapter(List<Hospitalize> hospitalizeList2) {
		HospitalizeItemTemplate adapter=new HospitalizeItemTemplate(this, R.layout.hospitalizetemplate, hospitalizeList);
		lstHospitalize.setAdapter(adapter);
	}

	private void bindingData() {
		lstHospitalize=(ListView)findViewById(R.id.lstHospitalize);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.hospitalizeitemmenu, menu);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		Hospitalize hospitalize;
		AdapterContextMenuInfo info;
		info = (AdapterContextMenuInfo) item.getMenuInfo();
		hospitalize= (Hospitalize) lstHospitalize.getAdapter().getItem(info.position);
		switch (item.getItemId()) 
		{
		case R.id.itemDetails:
			if(hasPermitions(Constants.EDIT_MODE))
				editHospitalize(hospitalize);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item12:
			if(hasPermitions(Constants.DELETE_MODE))
				confirmDeleteHospitalize(hospitalize);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void confirmDeleteHospitalize(Hospitalize hospitalize) {
		//crear un dialogo de confirmacion
		String message="Data don't saved";
		//if(methods.delete(consult)!=-1)
		{
			message="Data saved";
			hospitalizeList=methods.selecctAll();
			//loadToView();
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private void editHospitalize(Hospitalize hospitalize) 
	{
		Intent intent=new Intent(this, HospitalizeFormActivity.class);
		intent.putExtra("HOSPITALIZEID", hospitalize.gethospitalizeId());
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hospitalize, menu);
		return true;
	}
}
