package mn.aug.restfulandroid.provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

public class CatPicturesProvider extends ContentProvider {

	public static final String TAG = CatPicturesProvider.class.getSimpleName();

	// "projection" map of all the cat pictures table columns
	private static HashMap<String, String> timelineProjectionMap;

	// URI matcher for validating URIs
	private static final UriMatcher uriMatcher;

	// URI matcher ID for the cat pictures pattern
	private static final int MATCHER_CAT_PICTURES = 1;

	// URI matcher ID for the single cat picture ID pattern
	private static final int MATCHER_CAT_PICTURE_ID = 2;

	// Handle to our ProviderDbHelper.
	private ProviderDbHelper dbHelper;

	/**
	 * The MIME type of a directory of cat pictures
	 */
	private static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.restfulandroid.catpicture";

	/**
	 * The MIME type of a single cat picture
	 */
	private static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.restfulandroid.catpicture";

	// static 'setup' block
	static {
		// Build up URI matcher
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		// Add a pattern to route URIs terminated with just "catpicture"
		uriMatcher.addURI(CatPicturesConstants.AUTHORITY, CatPicturesTable.TABLE_NAME,
				MATCHER_CAT_PICTURES);

		// Add a pattern to route URIs terminated with a cat picture ID
		uriMatcher.addURI(CatPicturesProviderContract.AUTHORITY,
				CatPicturesTable.TABLE_NAME + "/#", MATCHER_CAT_PICTURE_ID);

		// Create and initialize a projection map that returns all columns,
		// This map returns a column name for a given string. The two are
		// usually equal, but we need this structure
		// later, down in .query()
		timelineProjectionMap = new HashMap<String, String>();
		for (String column : CatPicturesTable.ALL_COLUMNS) {
			timelineProjectionMap.put(column, column);
		}
	}

	@Override
	public boolean onCreate() {
		this.dbHelper = new ProviderDbHelper(this.getContext());
		/* if there are any issues, they'll be reported as exceptions */
		return true;
	}

	@Override
	public int delete(Uri uri, String whereClause, String[] whereValues) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		String finalWhere;
		int deletedRowsCount;

		// Perform the delete based on URI pattern
		db.beginTransaction();
		try {
			switch (uriMatcher.match(uri)) {
			case MATCHER_CAT_PICTURES:
				/*
				 * Delete all the cat pictures matching the where column/value
				 * pairs
				 */
				deletedRowsCount = db.delete(CatPicturesTable.TABLE_NAME, whereClause, whereValues);
				break;

			case MATCHER_CAT_PICTURE_ID:
				/* Delete the cat picture with the given ID */
				String catPictureId = uri.getPathSegments().get(
						CatPicturesTable.CAT_PICTURE_ID_PATH_POSITION);
				finalWhere = CatPicturesTable._ID + " = " + catPictureId;
				if (whereClause != null) {
					finalWhere = finalWhere + " AND " + whereClause;
				}

				// Perform the delete.
				deletedRowsCount = db.delete(CatPicturesTable.TABLE_NAME, finalWhere, whereValues);
				break;

			// If the incoming URI is invalid, throws an exception.
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
			}
		} finally {
			db.endTransaction();
		}

		// Notify observers of the the change
		getContext().getContentResolver().notifyChange(uri, null);

		// Returns the number of rows deleted.
		return deletedRowsCount;
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		// Validate the incoming URI.
		if (uriMatcher.match(uri) != MATCHER_CAT_PICTURES) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			throw new SQLException("ContentValues arg for .insert() is null, cannot insert row.");
		}

		long newRowId = this.dbHelper.getWritableDatabase().insert(CatPicturesTable.TABLE_NAME,
				null, values);

		if (newRowId > 0) { // if rowID is -1, it means the insert failed
			// Build a new cat picture URI with the new cat picture's ID
			// appended to it.
			Uri catPictureUri = ContentUris.withAppendedId(CatPicturesTable.CONTENT_ID_URI_BASE,
					newRowId);
			// Notify observers that our data changed.
			getContext().getContentResolver().notifyChange(catPictureUri, null);
			return catPictureUri;
		}

		/* insert failed; halt and catch fire */
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public Cursor query(Uri uri, String[] selectedColumns, String whereClause,
			String[] whereValues, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(CatPicturesTable.TABLE_NAME);

		// Choose the projection and adjust the "where" clause based on URI
		// pattern-matching.
		switch (uriMatcher.match(uri)) {
		case MATCHER_CAT_PICTURES:
			qb.setProjectionMap(timelineProjectionMap);
			break;

		/*
		 * asking for a single cat picture - use the cat pictures projection,
		 * but add a where clause to only return the one cat picture
		 */
		case MATCHER_CAT_PICTURE_ID:
			qb.setProjectionMap(timelineProjectionMap);
			// Find the cat picture ID itself in the incoming URI
			String id = uri.getPathSegments().get(CatPicturesTable.CAT_PICTURE_ID_PATH_POSITION);
			qb.appendWhere(CatPicturesTable._ID + "=" + id);
			break;

		default:
			// If the URI doesn't match any of the known patterns, throw an
			// exception.
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		
		// the two nulls here are 'grouping' and 'filtering by group'
		Cursor cursor = qb.query(db, selectedColumns, whereClause, whereValues, null, null,
				sortOrder);

		// Tell the Cursor about the URI to watch, so it knows when its source
		// data changes
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues updateValues, String whereClause, String[] whereValues) {
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		int updatedRowsCount;
		String finalWhere;

		db.beginTransaction();
		// Perform the update based on the incoming URI's pattern
		try {
			switch (uriMatcher.match(uri)) {

			case MATCHER_CAT_PICTURES:
				// Perform the update and return the number of rows updated.
				updatedRowsCount = db.update(CatPicturesTable.TABLE_NAME, updateValues,
						whereClause, whereValues);
				break;

			case MATCHER_CAT_PICTURE_ID:
				String id = uri.getPathSegments()
						.get(CatPicturesTable.CAT_PICTURE_ID_PATH_POSITION);
				finalWhere = CatPicturesTable._ID + " = " + id;

				// if we were passed a 'where' arg, add that to our 'finalWhere'
				if (whereClause != null) {
					finalWhere = finalWhere + " AND " + whereClause;
				}
				updatedRowsCount = db.update(CatPicturesTable.TABLE_NAME, updateValues, finalWhere,
						whereValues);
				break;

			default:
				// Incoming URI pattern is invalid: halt & catch fire.
				throw new IllegalArgumentException("Unknown URI " + uri);
			}
		} finally {
			db.endTransaction();
		}

		/*
		 * Gets a handle to the content resolver object for the current context,
		 * and notifies it that the incoming URI changed. The object passes this
		 * along to the resolver framework, and observers that have registered
		 * themselves for the provider are notified.
		 */
		if (updatedRowsCount > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		// Returns the number of rows updated.
		return updatedRowsCount;
	}

	// Default bulkInsert is terrible. Make it better!
	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		this.validateOrThrow(uri);
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		db.beginTransaction();
		int insertedCount = 0;
		long newRowId = -1;
		try {
			for (ContentValues cv : values) {
				newRowId = this.insert(uri, cv, db);
				insertedCount++;
			}
			db.setTransactionSuccessful();
			// Build a new Node URI appended with the row ID of the last node to
			// get inserted in the batch
			Uri nodeUri = ContentUris
					.withAppendedId(CatPicturesTable.CONTENT_ID_URI_BASE, newRowId);
			// Notify observers that our data changed.
			getContext().getContentResolver().notifyChange(nodeUri, null);
			return insertedCount;

		} finally {
			db.endTransaction();
		}
	}

	// Used by our implementation of builkInsert
	private long insert(Uri uri, ContentValues initialValues, SQLiteDatabase writableDb) {
		// NOTE: this method does not initiate a transaction - this is up to the
		// caller!
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			throw new SQLException("ContentValues arg for .insert() is null, cannot insert row.");
		}

		long newRowId = writableDb.insert(CatPicturesTable.TABLE_NAME, null, values);
		if (newRowId == -1) { // if rowID is -1, it means the insert failed
			throw new SQLException("Failed to insert row into " + uri); // Insert
																		// failed:
																		// halt
																		// and
																		// catch
																		// fire.
		}
		return newRowId;
	}

	private void validateOrThrow(Uri uri) {
		// Validate the incoming URI.
		if (uriMatcher.match(uri) != MATCHER_CAT_PICTURES) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case MATCHER_CAT_PICTURES:
			return CONTENT_TYPE;
		case MATCHER_CAT_PICTURE_ID:
			return CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
		File file = new File(this.getContext().getFilesDir(), uri.getPath());
		ParcelFileDescriptor parcel = ParcelFileDescriptor.open(file,
				ParcelFileDescriptor.MODE_READ_ONLY);
		return parcel;
	}
}
