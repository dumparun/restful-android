package mn.aug.restfulandroid.provider;

import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This creates, updates, and opens the database.  Opening is handled by the superclass, we handle 
 * the create & upgrade steps
 */
public class ProviderDbHelper extends SQLiteOpenHelper {

	public final String TAG = getClass().getSimpleName();

	//Name of the database file
	private static final String DATABASE_NAME = "restdroid.db";
	private static final int DATABASE_VERSION = 1;

	public ProviderDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		/* Create cat pictures table */
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("CREATE TABLE " + CatPicturesTable.TABLE_NAME + " (");
		sqlBuilder.append(CatPicturesTable._ID + " INTEGER, ");
		sqlBuilder.append(CatPicturesTable._STATUS + " TEXT, ");
		sqlBuilder.append(CatPicturesTable._RESULT + " INTEGER, ");
		sqlBuilder.append(CatPicturesTable.AUTHOR + " TEXT, ");
		sqlBuilder.append(CatPicturesTable.TITLE + " TEXT, ");
		sqlBuilder.append(CatPicturesTable.CREATED + " INTEGER, ");
		sqlBuilder.append(");");
		String sql = sqlBuilder.toString();
		Log.i(TAG, "Creating DB table with string: '" + sql + "'");
		db.execSQL(sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Gets called when the database is upgraded, i.e. the version number changes
	}

}
