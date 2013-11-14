package DataTemplates;

import java.util.List;
import com.hsptl.R;
import DB.DBHelper;
import DB.PersonMethods;
import DB.PersonalMethods;
import Models.Consult;
import Models.Person;
import Models.Personal;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ConsultItemTemplate extends ArrayAdapter<Consult> 
{
	private Context context;
	private int layoutResourceId;
	private List<Consult> data;

	public ConsultItemTemplate(Context context, 
			int layoutResourceId, 
			List<Consult> data) 
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
			template.lblPersonName=(TextView) row.findViewById(R.id.lblConsultPersonName);
			template.lblPersonalName=(TextView)row.findViewById(R.id.lblConsultPersonalName);
			template.lblSpecialty=(TextView)row.findViewById(R.id.lblConsultSpecialty);
			template.lblDate=(TextView)row.findViewById(R.id.lblConsultDate);
			row.setTag(template);
		}
		else
			template = (DataTemplate) row.getTag();
		Consult consult=data.get(position);
		Person person=personMethods.getPeronByHistoryID(consult.getClinicHistoryID());
		if(person != null)
		{
			template.lblPersonName.setText(person.getName());
			Personal personal=personalMethods.getPersonalByID(consult.getPersonalID());
			if(personal!=null)
			{
				template.lblPersonalName.setText(personal.getName());
				template.lblDate.setText(consult.getDate());
			}
			template.lblSpecialty.setText(methods.getSpecialtyByID(consult.getSpecialtyID()));
		}
		return row;
	}
	DBHelper methods=new DBHelper(context);
	PersonalMethods personalMethods=new PersonalMethods(context);
	PersonMethods personMethods=new PersonMethods(context);
	static class DataTemplate
	{
		TextView lblPersonName;
		TextView lblPersonalName;
		TextView lblSpecialty;
		TextView lblDate;
	}
}
