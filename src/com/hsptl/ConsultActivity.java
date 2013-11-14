package com.hsptl;

import java.util.List;

import DB.ConsultMethods;
import DataTemplates.ConsultItemTemplate;
import Models.Consult;
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

public class ConsultActivity extends Activity {

	ListView lstConsult;
	ConsultMethods methods;
	List<Consult> consultList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consult);
		bindingData();
		loadDataToView();
	}

	private void loadDataToView() {
		methods=new ConsultMethods(this);
		int personID=getIntent().getIntExtra("PERSONID", -1);
		if(personID != -1)
			consultList=methods.getConsulstByUserID(personID);
		else 
		{
			int personalID=getIntent().getIntExtra("PERSONALID", -1);
			if(personalID != -1)
				consultList=methods.getConsulstByPersonalID(personalID);
			else
				consultList=methods.selecctAll();
		}
		
		for (Consult item : consultList) {
			if(item.getPersonalID()==0)
				consultList.remove(item);
		}
		setViewAdapter(consultList);
		//registerForContextMenu(lstConsult);
	}

	private void setViewAdapter(List<Consult> consultList2) {
		ConsultItemTemplate adapter=new ConsultItemTemplate(this, R.layout.consulttemplate, consultList);
		lstConsult.setAdapter(adapter);
	}

	private void bindingData() {
		lstConsult=(ListView)findViewById(R.id.lstConsults);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.consulitemmenu, menu);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		Consult consult;
		AdapterContextMenuInfo info;
		info = (AdapterContextMenuInfo) item.getMenuInfo();
		consult= (Consult) lstConsult.getAdapter().getItem(info.position);
		switch (item.getItemId()) 
		{
		case R.id.itemDetails:
			if(hasPermitions(Constants.EDIT_MODE))
				editConsult(consult);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item12:
			if(hasPermitions(Constants.DELETE_MODE))
				confirmDeleteConsult(consult);
			else
				Toast.makeText(this, "You don't have permitions", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void confirmDeleteConsult(Consult consult) {
		//crear un dialogo de confirmacion
		String message="Data don't saved";
		//if(methods.delete(consult)!=-1)
		{
			message="Data saved";
			consultList=methods.selecctAll();
			//loadToView();
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private void editConsult(Consult consult) 
	{
		Intent intent=new Intent(this, ConsultFormActivity.class);
		intent.putExtra("CONSULTID", consult.getConsultID());
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
		getMenuInflater().inflate(R.menu.consult, menu);
		return true;
	}
}
