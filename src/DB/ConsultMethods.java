package DB;

import java.util.ArrayList;
import java.util.List;

import Models.Consult;
import Utils.Strings;
import android.content.Context;
import android.database.Cursor;

public class ConsultMethods extends DBHelper 
{
	private String table=Strings._TABLECONSULT;
	private String[] columns={"CONSULTID","DATE","CLINICHISTORYID","PERSONALID","SPECIALITYID","OBSERVATION"};
	public ConsultMethods(Context context) {
		super(context);
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
		consult.setClinicHistoryID((int)cursor.getShort(2));
		consult.setPersonalID((int)cursor.getShort(3));
		consult.setSpecialtyID((int)cursor.getShort(4));
		consult.setObservation(cursor.getString(5));
		return consult;
	}
	public List<Consult> getConsulstByUserID(int personID) {
		PersonMethods methods=new PersonMethods(context);
		int historyID = methods.getClinicHistory(personID);
		return toArray(db.query(table, columns, "CLINICHISTORYID LIKE '"+historyID+"'" , null, null, null, null));
	}
	public List<Consult> getConsulstByPersonalID(int personalID) {
		return toArray(db.query(table, columns, "PERSONALID LIKE '"+personalID+"'" , null, null, null, null));
	}
}
