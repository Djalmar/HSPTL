package com.hsptl;

import DB.ConsultMethods;
import DB.PatientMethods;
import DB.DoctorMethods;
import Models.Consult;
import Models.Patient;
import Models.Doctor;
import Utils.Constants;
import Utils.CurrentUser;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ConsultFormActivity extends Activity{

	Consult consult;
	Doctor doctor;
	Patient patient;

	EditText txtDoctorName;
	EditText txtUserName;
	EditText txtObservation;
	EditText txtDiagnostic;

	PatientMethods patientMethods;
	DoctorMethods doctorMethods;
	ConsultMethods consultMethods;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consult_form);
		BindingData();
		loadToView();
	}
	public void save(View view)
	{
		loadFromView();
		saveChanges(consultMethods.insertConsult(consult));
	}

	private void saveChanges(int resultCode) {
		Intent intent=new Intent();
		setResult(resultCode, intent);
		finish();
	}

	private void loadFromView() {
		if(consult==null)
			consult=new Consult();
		consult.setDoctorID(doctor.getDoctorID());
		consult.setPatientID(patient.getPatientID());
		consult.setDiagnostic(txtDiagnostic.getText().toString());
		consult.setObservation(txtObservation.getText().toString());
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
		txtObservation=(EditText)findViewById(R.id.txtConsultObservation);
		txtDiagnostic=(EditText)findViewById(R.id.txtConsultDiagnostic);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consult, menu);
		return true;
	}

}
