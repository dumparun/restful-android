package mn.aug.restfulandroid.provider;

import android.net.Uri;

public class CatPicturesConstants {

	public static final String TABLE_NAME = "catpictures";

	public static final String AUTHORITY = "mn.aug.restfulandroid.catpictures";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

	// Columns in the cat pictures database
	public static final String NAME = "name";

}
