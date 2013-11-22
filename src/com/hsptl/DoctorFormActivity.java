package com.hsptl;

import java.util.List;
import DB.DoctorMethods;
import DB.SpecialtyMethods;
import Models.Doctor;
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
import android.widget.EditText;
import android.widget.Spinner;

public class DoctorFormActivity extends Activity implements OnItemSelectedListener {

	EditText txtName;
	EditText txtArriveTime;
	EditText txtLeavingTime;
	EditText txtLogin;
	
	Spinner cmbSpecialties;
	Doctor personal;
	DoctorMethods methods;
	String selectedSpecialty;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_form);
		bindingData();
		methods=new DoctorMethods(this);
		if(CurrentUser._userMode.equals(Constants.EDIT_MODE))
			personal=methods.getDoctorByID(getIntent().getIntExtra("PERSONALID", 0));
		loadToView();
	}

	private void loadToView() 
	{
		if(personal==null)
			personal=new Doctor();
		txtName.setText(personal.getName());
		txtArriveTime.setText(personal.getArriveTime());
		txtLeavingTime.setText(personal.getLeavingTime());
		txtLogin.setText(personal.getLogin());
		if(CurrentUser._userMode.equals(Constants.EDIT_MODE))
		{	
			cmbSpecialties.setEnabled(false);
			txtLogin.setEnabled(false);
		}
		loadDataToCmb();
	}
	SpecialtyMethods specialtyMethods;
	private void loadDataToCmb() 
	{
		specialtyMethods = new SpecialtyMethods(this);
		List<String> tables=specialtyMethods.selectNames();
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.tablesadapter, tables);
		cmbSpecialties.setAdapter(adapter);
		cmbSpecialties.setOnItemSelectedListener(this);
	}
	public void save(View view)
	{
		loadFromView();
		if(CurrentUser._userMode.equals(Constants.EDIT_MODE))
			saveChanges(methods.update(personal)-1);
		if(CurrentUser._userMode.equals(Constants.CREATE_MODE))
			saveChanges(methods.insert(personal));
	}
	private void saveChanges(int resultCode) 
	{
		resultCode++;
		Intent intent=new Intent();
		setResult(resultCode,intent);
		finish();
	}

	private void loadFromView() 
	{
		if(personal==null)
			personal=new Doctor();
		personal.setName(txtName.getText().toString());
		personal.setArriveTime(txtArriveTime.getText().toString());
		personal.setLeavingTime(txtLeavingTime.getText().toString());
		personal.setSpecialty(specialtyMethods.getSpecialty(selectedSpecialty));
		personal.setLogin(txtLogin.getText().toString());
	}

	private void bindingData() 
	{
		txtName=(EditText)findViewById(R.id.txtPersonalName);
		txtArriveTime=(EditText)findViewById(R.id.txtPersonalArriveTie);
		txtLeavingTime=(EditText)findViewById(R.id.txtPersonalLeavingTime);
		txtLogin=(EditText)findViewById(R.id.txtPersonalLogin);
		cmbSpecialties=(Spinner)findViewById(R.id.cbxDoctorSpecialty);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_form, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) 
	{
		selectedSpecialty=cmbSpecialties.getItemAtPosition(arg2).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}