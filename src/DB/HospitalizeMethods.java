package DB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import Models.Hospitalize;
import Utils.Strings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

public class HospitalizeMethods extends DBHelper 
{
	private String table=Strings._TABLEHOSPITALIZE;
	private String[] columns={"HOSPITALIZEID","DOCTORID","PACIENTID","BEDID","HALLID","INCHARGEDATEE","DISCHARGEDATE","DIAGNOSTIC","PAVILIONID"};
	public HospitalizeMethods(Context context) {
		super(context);
	}
	public int insertHospitalize(Hospitalize hospitalize) {
		ContentValues values=new ContentValues();
		values.put(columns[1],hospitalize.getdoctorID());
		values.put(columns[2],hospitalize.getpatientID());
		values.put(columns[3],hospitalize.getbedID());
		values.put(columns[4],hospitalize.gethallID());
		values.put(columns[5],getDatePhone());
		values.put(columns[6],hospitalize.getdischargeDate());
		values.put(columns[7],hospitalize.getdiagnostic());
		values.put(columns[8],hospitalize.getpavilionID());
		
		try
		{
			return (int)db.insertOrThrow("HOSPITALIZE", null, values);
		}
		catch (Exception e) {
			Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
		}
		return -1;
	}
	public List<Hospitalize>  selecctAll()
	{
		return toArray(db.query(table, columns, null, null, null, null, null, null));			
	}
	private List<Hospitalize> toArray(Cursor cursor) 
	{
		List<Hospitalize> hospitalizeList=new ArrayList<Hospitalize>();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
			hospitalizeList.add(toHospitalize(cursor));
		return hospitalizeList;
	}
	private Hospitalize toHospitalize(Cursor cursor) {
		Hospitalize hospitalize=new Hospitalize();
		hospitalize.sethospiltalizeID((int)cursor.getShort(0));
		hospitalize.setpatientID((int)cursor.getShort(1));
		hospitalize.setdoctorID((int)cursor.getShort(2));
		hospitalize.setbedID((int)cursor.getShort(3));
		hospitalize.sethallID((int)cursor.getShort(4));
		hospitalize.setinchargeDate(cursor.getString(5));
		hospitalize.setdischargeDate(cursor.getString(6));
		hospitalize.setdiagnostic(cursor.getString(7));
		hospitalize.setpavilionID((int)cursor.getShort(8));
		return hospitalize;
	}
	public List<Hospitalize> getHospitalizeByUserID(int personID) {
		return toArray(db.query(table, columns, "PATIENTID LIKE '"+personID+"'" , null, null, null, null));
	}
	public List<Hospitalize> getHospitalizeByDoctorID(int doctorID) {
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
