package mn.aug.restfulandroid.provider;

import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CommentsTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This creates, updates, and opens the database. Opening is handled by the
 * superclass, we handle the create & upgrade steps
 */
public class ProviderDbHelper extends SQLiteOpenHelper {

	public final String TAG = getClass().getSimpleName();

	// Name of the database file
	private static final String DATABASE_NAME = "restdroid.db";
	private static final int DATABASE_VERSION = 1;

	public ProviderDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + CatPicturesTable.TABLE_NAME + ";");
		createTables(db);
	}

	private void createTables(SQLiteDatabase db) {
		/* Create cat pictures table */
		StringBuilder catPicsBuilder = new StringBuilder();
		catPicsBuilder.append("CREATE TABLE " + CatPicturesTable.TABLE_NAME + " (");
		catPicsBuilder.append(CatPicturesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
		catPicsBuilder.append(CatPicturesTable._STATUS + " INTEGER, ");
		catPicsBuilder.append(CatPicturesTable._RESULT + " INTEGER, ");
		catPicsBuilder.append(CatPicturesTable.REF_ID + " TEXT, ");
		catPicsBuilder.append(CatPicturesTable.TITLE + " TEXT, ");
		catPicsBuilder.append(CatPicturesTable.URL + " TEXT, ");
		catPicsBuilder.append(CatPicturesTable.AUTHOR + " TEXT, ");
		catPicsBuilder.append(CatPicturesTable.THUMBNAIL_URL + " TEXT, ");
		catPicsBuilder.append(CatPicturesTable.CREATED + " NUMERIC");
		catPicsBuilder.append(");");
		String sql = catPicsBuilder.toString();
		Log.i(TAG, "Creating DB table with string: '" + sql + "'");
		db.execSQL(sql);
		
		/* Comments Table */
		StringBuilder commentsBuilder = new StringBuilder();
		commentsBuilder.append("CREATE TABLE " + CommentsTable.TABLE_NAME + " (");
		commentsBuilder.append(CommentsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
		commentsBuilder.append(CommentsTable._STATUS + " INTEGER, ");
		commentsBuilder.append(CommentsTable._RESULT + " INTEGER, ");
		commentsBuilder.append(CommentsTable.REF_ID + " TEXT, ");
		commentsBuilder.append(CommentsTable.CAT_PICTURE_ID + " TEXT, ");
		commentsBuilder.append(CommentsTable.COMMENT_TEXT + " TEXT, ");
		commentsBuilder.append(CatPicturesTable.AUTHOR + " TEXT, ");
		commentsBuilder.append(CatPicturesTable.CREATED + " NUMERIC");
		commentsBuilder.append(");");
		sql = commentsBuilder.toString();
		Log.i(TAG, "Creating DB table with string: '" + sql + "'");
		db.execSQL(sql);
	}

}
