package com.hsptl;
import java.util.ArrayList;
import java.util.List;

import DB.DBHelper;
import DB.PersonMethods;
import DB.PersonalMethods;
import Models.Consult;
import Models.Person;
import Models.Personal;
import Utils.Constants;
import Utils.CurrentUser;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ConsultFormActivity extends Activity implements OnItemSelectedListener{

	Consult consult;
	Personal personal;
	Person person;
	EditText txtPersonalName;
	EditText txtUserName;
	EditText txtObservation;
	EditText txtDate;
	Spinner cmbSpecialties;
	PersonMethods personMethods;
	PersonalMethods personalMethods;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consult_form);
		BindingData();
		loadToView();
	}
	public void save(View view)
	{
		loadFromVIew();
		saveChanges(methods.insertConsult(consult));
	}
	
	private void saveChanges(int resultCode) {
		Intent intent=new Intent();
		setResult(resultCode, intent);
		finish();
	}

	private void loadFromVIew() {
		int clinicHistoryID=personMethods.getClinicHistory(person.getPersonID());
		if(consult==null)
			consult=new Consult();
		consult.setClinicHistoryID(clinicHistoryID);
		consult.setPersonalID(personal.getPersonalID());
		consult.setSpecialtyID(methods.getSpecialtyID(currentSpecialty));
		consult.setDate(txtDate.getText().toString());
		consult.setObservation(txtObservation.getText().toString());
	}

	private void loadToView() {
		personMethods=new PersonMethods(this);
		personalMethods=new PersonalMethods(this);
		person=personMethods.getPersonByID(getIntent().getIntExtra("PERSONID", 0));
		personal=personalMethods.getPersonalByUserID(CurrentUser._USER.getUserID());
		if(personal==null)
			saveChanges(Constants.RESULT_NOT_OK-1);
		else
			txtPersonalName.setText(personal.getName());
		txtUserName.setText(person.getName());
		loadDataToCmb();
	}
private DBHelper methods;
	private void loadDataToCmb() {
		List<String> data =new ArrayList<String>();
		methods=new PersonMethods(this);
		data=methods.getSpecialtys();
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.tablesadapter, data);
		cmbSpecialties.setAdapter(adapter);
		cmbSpecialties.setOnItemSelectedListener(this);
	}

	private void BindingData() {
		txtPersonalName=(EditText)findViewById(R.id.txtDoctorName);
		txtUserName=(EditText)findViewById(R.id.txtPatientName);
		txtObservation=(EditText)findViewById(R.id.txtConsultObservation);
		txtDate=(EditText)findViewById(R.id.txtConsultDate);
		cmbSpecialties=(Spinner)findViewById(R.id.cmbSpeciality);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consult, menu);
		return true;
	}

	String currentSpecialty;
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		currentSpecialty=cmbSpecialties.getItemAtPosition(arg2).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
