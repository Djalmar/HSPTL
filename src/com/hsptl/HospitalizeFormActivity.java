package com.hsptl;

import DB.DoctorMethods;
import DB.HospitalizeMethods;
import DB.PatientMethods;
import Models.Doctor;
import Models.Hospitalize;
import Models.Patient;
import Utils.Constants;
import Utils.CurrentUser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class HospitalizeFormActivity extends Activity{

	Hospitalize hospitalize;
	Doctor doctor;
	Patient patient;

	EditText txtDoctorName;
	EditText txtUserName;
	EditText txtDiagnostic;
	EditText txtInchargeDate;
	EditText txtDischargeDate;
	Spinner pavilion;
	Spinner hall;
	Spinner bed;

	PatientMethods patientMethods;
	DoctorMethods doctorMethods;
	HospitalizeMethods hospitalizeMethods;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospitalize_form);
		BindingData();
		loadToView();
	}
	public void save(View view)
	{
		loadFromView();
		saveChanges(hospitalizeMethods.insertHospitalize(hospitalize));
	}

	private void saveChanges(int resultCode) {
		Intent intent=new Intent();
		setResult(resultCode, intent);
		finish();
	}

	private void loadFromView() {
		if(hospitalize==null)
			hospitalize=new Hospitalize();
		hospitalize.setpatientID(patient.getPatientID());
		hospitalize.setdoctorID(doctor.getDoctorID());
		hospitalize.setinchargeDate(txtInchargeDate.getText().toString());
		hospitalize.setdischargeDate(txtDischargeDate.getText().toString());
		hospitalize.setdiagnostic(txtDiagnostic.getText().toString());
		//hospitalize.setpavilionID(pavilion.)
	}

	private void loadToView() {
		patientMethods=new PatientMethods(this);
		doctorMethods=new DoctorMethods(this);
		patient=patientMethods.getPatientByID(getIntent().getIntExtra("PERSONID", 0));
		doctor=doctorMethods.getDoctorByUserID(CurrentUser._USER.getUserID());
		if(doctor==null)
			saveChanges(Constants.RESULT_NOT_OK-1);
		else
			txtDoctorName.setText(doctor.getName());
		txtUserName.setText(patient.getName());
	}

	private void BindingData() {
		txtDoctorName=(EditText)findViewById(R.id.txtDoctorName);
		txtUserName=(EditText)findViewById(R.id.txtPatientName);
		txtDiagnostic=(EditText)findViewById(R.id.txtConsultDiagnostic);
		txtInchargeDate=(EditText)findViewById(R.id.txtHospitalizeInchargeDate);
		txtDischargeDate=(EditText)findViewById(R.id.txtHospitalizeDischargeDate);
		pavilion=(Spinner)findViewById(R.id.sp_pavilion);
		hall=(Spinner)findViewById(R.id.sp_hall);
		bed=(Spinner)findViewById(R.id.sp_bed);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hospitalize, menu);
		return true;
	}

}
