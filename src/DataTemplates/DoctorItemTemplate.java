package DataTemplates;

import java.util.List;
import Models.Doctor;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hsptl.R;

public class DoctorItemTemplate extends ArrayAdapter<Doctor> {
	private Context context;
	private int layoutResourceId;
	private List<Doctor> data;

	public DoctorItemTemplate(Context context, 
			int layoutResourceId, 
			List<Doctor> data) 
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
			template.lblName=(TextView) row.findViewById(R.id.lblPersonalName);
			template.lblSalary=(TextView)row.findViewById(R.id.lblPersonalSalary);
			template.lblCharge=(TextView)row.findViewById(R.id.lblPersonalCharge);
			row.setTag(template);
		}
		else
			template = (DataTemplate) row.getTag();
		Doctor personal=data.get(position);
		template.lblName.setText(personal.getName());
		return row;
	}
	static class DataTemplate
	{
		TextView lblName;
		TextView lblSalary;
		TextView lblCharge;
	}
}