package DataTemplates;

import java.util.List;
import com.hsptl.R;
import DB.PatientMethods;
import DB.DoctorMethods;
//import DB.SpecialtyMethods;
//import Models.Consult;
import Models.Hospitalize;
import Models.Patient;
import Models.Doctor;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HospitalizeItemTemplate extends ArrayAdapter<Hospitalize> 
{
	private Context context;
	private int layoutResourceId;
	private List<Hospitalize> data;

	DoctorMethods doctorMethods=new DoctorMethods(context);
	PatientMethods patientMethods=new PatientMethods(context);
	//SpecialtyMethods specialtyMethods=new SpecialtyMethods(context);
	
	public HospitalizeItemTemplate(Context context, 
			int layoutResourceId, 
			List<Hospitalize> data) 
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
			template.lblPersonName=(TextView) row.findViewById(R.id.lblHospitalizePersonName);
			template.lblPersonalName=(TextView) row.findViewById(R.id.lblHospitalizePersonalName);
			template.lblSpeciality=(TextView) row.findViewById(R.id.lblHospitalizeSpeciality);
			template.lblInchargeDate=(TextView) row.findViewById(R.id.lblHospitalizeInchargeDate);
			template.lblDischargeDate=(TextView) row.findViewById(R.id.lblHospitalizeDischargeDate);
			row.setTag(template);
		}
		else
			template = (DataTemplate) row.getTag();
		Hospitalize hospitalize = data.get(position);
		Patient patient = patientMethods.getPatientByID(hospitalize.getpatientID());
		if(patient != null)
		{
			template.lblPersonName.setText(patient.getName());
			Doctor doctor = doctorMethods.getDoctorByID(hospitalize.getdoctorID());
			if(doctor!=null)
			{
				template.lblPersonalName.setText(doctor.getName());
				template.lblInchargeDate.setText(hospitalize.getinchargeDate());
				template.lblDischargeDate.setText(hospitalize.getdischargeDate());
				template.lblSpeciality.setText(doctor.getSpecialty().getName());
			}
		}
		return row;
	}
	
	static class DataTemplate
	{
		TextView lblPersonName;
		TextView lblPersonalName;
		TextView lblInchargeDate;
		TextView lblDischargeDate;
		TextView lblSpeciality;
	}
}
