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
		// URI_PATH_CAT_PICTURES, which has no slash.
		private static final String URI_PATH_CAT_PICTURE_ID = "/" + TABLE_NAME + "/";

		public static final int CAT_PICTURE_ID_PATH_POSITION = 1;

		// content://mn.aug.restfulandroid.catpicturesprovider/catpictures
		public static final Uri CONTENT_URI = Uri.parse(URI_PREFIX + URI_PATH_CAT_PICTURES);

		// content://mn.aug.restfulandroid.catpicturesprovider/catpictures/ -- used
		// for content provider insert() call

		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY
				+ URI_PATH_CAT_PICTURES);
		
		// content://mn.aug.restfulandroid.catpicturesprovider/catpictures/#
		public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY
				+ URI_PATH_CAT_PICTURES + "#");

		public static final String[] ALL_COLUMNS;
		public static final String[] DISPLAY_COLUMNS;

		static {
			ALL_COLUMNS = new String[] {
					CatPicturesTable._ID,
					CatPicturesTable._STATUS,
					CatPicturesTable._RESULT,
					CatPicturesTable.REF_ID,
					CatPicturesTable.TITLE,
					CatPicturesTable.URL,
					CatPicturesTable.AUTHOR,
					CatPicturesTable.THUMBNAIL_URL,
					CatPicturesTable.CREATED
				};

			DISPLAY_COLUMNS = new String[] {
					CatPicturesTable._ID,
					CatPicturesTable.THUMBNAIL_URL,
					CatPicturesTable.TITLE,
					CatPicturesTable.AUTHOR
				};
		}

		/**
		 * Column name for the cat picture ID
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String REF_ID = "ref_id";

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
		public static final String THUMBNAIL_URL = "thumbnail_url";
		
		/**
		 * Column name for cat picture comments url
		 * <P>
		 * Type: TEXT
		 * </P>
		 */
		public static final String COMMENTS_URL = "comments_url";

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
	
	// Cat pictures table contract
		public static final class CommentsTable implements ResourceTable {

			public static final String TABLE_NAME = "comments";

			// URI DEFS
			static final String SCHEME = "content://";
			public static final String URI_PREFIX = SCHEME + AUTHORITY;
			private static final String URI_PATH_ALL_COMMENTS = "/" + TABLE_NAME;

			// Note the slash on the end of this one, as opposed to the
			// URI_PATH_CAT_PICTURES, which has no slash.
			private static final String URI_PATH_COMMENT_ID = "/" + TABLE_NAME + "/";

			public static final int COMMENT_ID_PATH_POSITION = 1;

			// content://mn.aug.restfulandroid.catpicturesprovider/comments
			public static final Uri CONTENT_URI = Uri.parse(URI_PREFIX + URI_PATH_ALL_COMMENTS);

			// content://mn.aug.restfulandroid.catpicturesprovider/comments/#id -- used
			// for content provider insert() call

			public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY
					+ URI_PATH_ALL_COMMENTS);
			
			// content://mn.aug.restfulandroid.catpicturesprovider/comments/#
			public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY
					+ URI_PATH_COMMENT_ID + "#");

			public static final String[] ALL_COLUMNS;
			public static final String[] DISPLAY_COLUMNS;

			static {
				ALL_COLUMNS = new String[] {
						CommentsTable._ID,
						CommentsTable._STATUS,
						CommentsTable._RESULT,
						CommentsTable.REF_ID,
						CommentsTable.CAT_PICTURE_ID,
						CommentsTable.AUTHOR,
						CommentsTable.COMMENT_TEXT,
						CommentsTable.CREATED
					};

				DISPLAY_COLUMNS = new String[] {
						CommentsTable._ID,
						CommentsTable.COMMENT_TEXT,
						CommentsTable.AUTHOR
					};
			}

			/**
			 * Column name for the comment api assigned ID
			 * <P>
			 * Type: TEXT
			 * </P>
			 */
			public static final String REF_ID = "ref_id";
			
			/**
			 * Column name for the foreign-key to the CatPicturesTable
			 * <P>
			 * Type: TEXT
			 * </P>
			 */
			public static final String CAT_PICTURE_ID = "cat_pic_id";

			/**
			 * Column name for comment text
			 * <P>
			 * Type: TEXT
			 * </P>
			 */
			public static final String COMMENT_TEXT = "comment";
			
			/**
			 * Column name for comment author
			 * <P>
			 * Type: TEXT
			 * </P>
			 */
			public static final String AUTHOR = "author";
			

			/**
			 * Column name for the creation date
			 * <P>
			 * Type: LONG (UNIX timestamp)
			 * </P>
			 */
			public static final String CREATED = "timestamp";

			// Prevent instantiation of this class
			private CommentsTable() {
			}
		}

	private CatPicturesProviderContract() {
		// disallow instantiation
	}

}
