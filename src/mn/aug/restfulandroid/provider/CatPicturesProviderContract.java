package mn.aug.restfulandroid.provider;

import android.net.Uri;

public final class CatPicturesProviderContract {

	public static final String AUTHORITY = "mn.aug.restfulandroid.catpicturesprovider";

	// Cat pictures table contract
	public static final class CatPicturesTable implements ResourceTable {

		public static final String TABLE_NAME = "catpictures";

		// URI DEFS
		static final String SCHEME = "content://";
		public static final String URI_PREFIX = SCHEME + AUTHORITY;
		private static final String URI_PATH_CAT_PICTURES = "/" + TABLE_NAME;

		// Note the slash on the end of this one, as opposed to the
		// URI_PATH_TIMELINE, which has no slash.
		private static final String URI_PATH_CAT_PICTURE_ID = "/" + TABLE_NAME + "/";

		public static final int CAT_PICTURE_ID_PATH_POSITION = 1;

		// content://mn.aug.restfulandroid.catpicturesprovider/catpictures
		public static final Uri CONTENT_URI = Uri.parse(URI_PREFIX + URI_PATH_CAT_PICTURES);

		// content://mn.aug.restfulandroid.catpicturesprovider/catpictures/ -- used
		// for content provider insert() call

		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY
				+ URI_PATH_CAT_PICTURES);
		
		// content://mn.aug.restfulandroid.timelineprovider/timeline/#
		public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY
				+ URI_PATH_CAT_PICTURES + "#");

		public static final String[] ALL_COLUMNS;
		public static final String[] DISPLAY_COLUMNS;

		static {
			ALL_COLUMNS = new String[] {
					CatPicturesTable._ID,
					CatPicturesTable._STATUS,
					CatPicturesTable._RESULT,
					CatPicturesTable.ID,
					CatPicturesTable.TITLE,
					CatPicturesTable.URL,
					CatPicturesTable.AUTHOR,
					CatPicturesTable.THUMBNAIL,
					CatPicturesTable.CREATED
				};

			DISPLAY_COLUMNS = new String[] {
					CatPicturesTable._ID,
					CatPicturesTable.THUMBNAIL,
					CatPicturesTable.TITLE
				};
		}

		/**
		 * Column name for the cat picture ID
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String ID = "id";

		/**
		 * Column name for cat picture title
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String TITLE = "title";
		
		/**
		 * Column name for cat picture url
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String URL = "url";
		
		/**
		 * Column name for cat picture author
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String AUTHOR = "author";
		
		/**
		 * Column name for cat picture thumbnail
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String THUMBNAIL = "thumbnail";

		/**
		 * Column name for the creation date
		 * <P>
		 * Type: LONG (UNIX timestamp)
		 * </P>
		 */
		public static final String CREATED = "timestamp";

		// Prevent instantiation of this class
		private CatPicturesTable() {
		}
	}

	private CatPicturesProviderContract() {
		// disallow instantiation
	}

}
