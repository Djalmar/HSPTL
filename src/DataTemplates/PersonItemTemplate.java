package DataTemplates;

import java.util.List;

import com.hsptl.R;

import Models.Patient;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PersonItemTemplate extends ArrayAdapter<Patient> 
{
	private Context context;
	private int layoutResourceId;
	private List<Patient> data;

	public PersonItemTemplate(Context context, 
			int layoutResourceId, 
			List<Patient> data) 
	{
		super(context, layoutResourceId, data);
		this.context=context;
		this.layoutResourceId=layoutResourceId;
		this.data=data;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//return super.getView(position, convertView, parent);
		//define cada fila
		View row=convertView;
		DataTemplate template=null;
		if(row==null)
		{
			LayoutInflater inflater=((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			template=new DataTemplate();
			
			template.lblName=(TextView) row.findViewById(R.id.lblPersonName);
			template.lblGender=(TextView)row.findViewById(R.id.lblPersonGender);
			template.lblBirthday=(TextView)row.findViewById(R.id.lblPersonBirthday);
			
			row.setTag(template);
		}
		else
			template = (DataTemplate) row.getTag();
		Patient person=data.get(position);
		
		template.lblName.setText(person.getName());
		if(person.isGender()) 
			template.lblGender.setText("MALE");
		else 
			template.lblGender.setText("FEMALE");
		template.lblBirthday.setText(person.getBirthday().toString());
		return row;
	}
	static class DataTemplate
	{
		TextView lblName;
		TextView lblGender;
		TextView lblBirthday;
	}
}
