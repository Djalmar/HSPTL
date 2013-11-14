package com.hsptl;

import java.util.List;
import DB.DBHelper;
import DB.PersonalMethods;
import Models.Personal;
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

public class PersonalFormActivity extends Activity implements OnItemSelectedListener {

	EditText txtName;
	EditText txtSalary;
	EditText txtArriveTime;
	EditText txtLeavingTime;
	EditText txtLogin;
	Spinner cmbCharges;
	Personal personal;
	PersonalMethods methods;
	String selectedCharge;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_form);
		bindingData();
		methods=new PersonalMethods(this);
		if(CurrentUser._userMode.equals(Constants.EDIT_MODE))
			personal=methods.getPersonalByID(getIntent().getIntExtra("PERSONALID", 0));
		loadToView();
	}

	private void loadToView() 
	{
		if(personal==null)
			personal=new Personal();
		txtName.setText(personal.getName());
		txtArriveTime.setText(personal.getArriveTime());
		txtLeavingTime.setText(personal.getLeavingTime());
		txtSalary.setText(personal.getSalary()+"");
		txtLogin.setText(personal.getLogin());
		if(CurrentUser._userMode.equals(Constants.EDIT_MODE))
		{	
			selectedCharge=personal.getCharge();
			cmbCharges.setEnabled(false);
			txtLogin.setEnabled(false);
		}
		loadDataToCmb();
	}

	private void loadDataToCmb() 
	{
		DBHelper helper=new DBHelper(this);
		List<String> tables=helper.getCharges();
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.tablesadapter, tables);
		cmbCharges.setAdapter(adapter);
		cmbCharges.setOnItemSelectedListener(this);
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
			personal=new Personal();
		personal.setName(txtName.getText().toString());
		personal.setArriveTime(txtArriveTime.getText().toString());
		personal.setLeavingTime(txtLeavingTime.getText().toString());
		personal.setSalary(Double.parseDouble(txtSalary.getText().toString()));
		personal.setCharge(selectedCharge);
		personal.setLogin(txtLogin.getText().toString());
	}

	private void bindingData() 
	{
		txtName=(EditText)findViewById(R.id.txtPersonalName);
		txtArriveTime=(EditText)findViewById(R.id.txtPersonalArriveTie);
		txtLeavingTime=(EditText)findViewById(R.id.txtPersonalLeavingTime);
		txtSalary=(EditText)findViewById(R.id.txtPersonalSalary);
		txtLogin=(EditText)findViewById(R.id.txtPersonalLogin);
		cmbCharges=(Spinner)findViewById(R.id.cbxPersonalCharge);
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
		selectedCharge=cmbCharges.getItemAtPosition(arg2).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
