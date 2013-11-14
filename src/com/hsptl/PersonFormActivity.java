package com.hsptl;

import DB.PersonMethods;
import Models.Person;
import Utils.Constants;
import Utils.CurrentUser;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PersonFormActivity extends Activity {

	Person person;
	EditText txtName;
	EditText txtWeight;
	EditText txtSize;
	EditText txtGender;
	EditText txtBirthday;
	PersonMethods methods;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_form);
		bindingData();
		methods=new PersonMethods(this);
		if(CurrentUser._userMode.equals(Constants.EDIT_MODE))
		{
			person=methods.getPersonByID(getIntent().getIntExtra("PERSONID", 0));
			loadToView();
		}
	}

	private void loadToView() {
		txtName.setText(person.getName());
		txtBirthday.setText(person.getBirthday());
		if(person.isGender())
			txtGender.setText("MALE");
		else
			txtGender.setText("FEMALE");
		txtSize.setText(person.getSize()+"");
		txtWeight.setText(person.getWeight()+"");
	}
	public void save(View view)
	{
		loadFromView();
		if(CurrentUser._userMode.equals(Constants.EDIT_MODE))
			saveChanges(methods.update(person)-1);
		if(CurrentUser._userMode.equals(Constants.CREATE_MODE))
			saveChanges((int)methods.insert(person));
	}
	private void saveChanges(int resultCode) 
	{
		resultCode++;
		Intent intent =new Intent();
		setResult(resultCode,intent);
		finish();
	}

	private void loadFromView() 
	{
		if(person==null)
			person=new Person();
		person.setName(txtName.getText().toString().trim());
		person.setBirthday(txtBirthday.getText().toString());
		person.setSize(Double.parseDouble(txtSize.getText().toString()));
		person.setWeight(Double.parseDouble(txtWeight.getText().toString()));
		if(!person.setGender(txtGender.getText().toString()))
		{
			Toast.makeText(this, "Gender not valid", Toast.LENGTH_SHORT).show();
			return;
		}
		
	}

	private void bindingData() {
		txtName=(EditText)findViewById(R.id.txtPersonName);
		txtBirthday=(EditText)findViewById(R.id.txtPersonBirthday);
		txtGender=(EditText)findViewById(R.id.txtpersonGender);
		txtSize=(EditText)findViewById(R.id.txtPersonSize);
		txtWeight=(EditText)findViewById(R.id.txtPersonWeight);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person_form, menu);
		return true;
	}

}
