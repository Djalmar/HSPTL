package DB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import Models.Consult;
import Utils.Strings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

public class ConsultMethods extends DBHelper 
{
	private String table=Strings._TABLECONSULT;
	private String[] columns={"CONSULTID","DATE","OBSERVATION","DIAGNOSTIC","DOCTORID","PATIENTID"};
	public ConsultMethods(Context context) {
		super(context);
	}
	public int insertConsult(Consult consult) {
		ContentValues values=new ContentValues();
		values.put(columns[1],getDatePhone());
		values.put(columns[2],consult.getObservation());
		values.put(columns[3],consult.getDiagnostic());
		values.put(columns[4], consult.getDoctorID());
		values.put(columns[5], consult.getPatientID());
		
		try
		{
			return (int)db.insertOrThrow("CONSULT", null, values);
		}
		catch (Exception e) {
			Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
		}
		return -1;
	}
	public List<Consult>  selecctAll()
	{
		return toArray(db.query(table, columns, null, null, null, null, null));			
	}
	private List<Consult> toArray(Cursor cursor) 
	{
		List<Consult> consultList=new ArrayList<Consult>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			consultList.add(toConsult(cursor));
		return consultList;
	}
	private Consult toConsult(Cursor cursor) {
		Consult consult=new Consult();
		consult.setConsultID((int)cursor.getShort(0));
		consult.setDate(cursor.getString(1));
		consult.setObservation(cursor.getString(2));
		consult.setDiagnostic(cursor.getString(3));
		consult.setDoctorID(cursor.getInt(4));
		consult.setPatientID(cursor.getInt(5));
		return consult;
	}
	public List<Consult> getConsulstByUserID(int personID) {
		return toArray(db.query(table, columns, "PATIENTID LIKE '"+personID+"'" , null, null, null, null));
	}
	public List<Consult> getConsulstByDoctorID(int doctorID) {
		return toArray(db.query(table, columns, "DOCTORID LIKE '"+doctorID+"'" , null, null, null, null));
	}
	private String getDatePhone()
	{
	    Calendar cal = new GregorianCalendar();
	    Date date = cal.getTime();
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    String formatteDate = df.format(date);
	    return formatteDate;
	}
}
