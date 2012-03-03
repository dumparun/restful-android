package mn.aug.restfulandroid.activity;

import java.io.File;

import mn.aug.restfulandroid.R;
import mn.aug.restfulandroid.RestfulAndroid;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CommentsTable;
import mn.aug.restfulandroid.rest.resource.CatPictures;
import mn.aug.restfulandroid.rest.resource.Comments;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CommentActivity extends Activity {

	public static final String EXTRA_CAT_PICTURE_ID = "catPicId";

	private RestfulAndroid app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.comments);
		app = (RestfulAndroid)getApplicationContext();

		String postId = getIntent().getStringExtra(EXTRA_CAT_PICTURE_ID);

		initPostFields(postId);
		initComments(postId);

	}

	private void initPostFields(String postId){

		String whereClause = CatPicturesTable._ID + "=?";
		String[] whereValues = {postId};
		Cursor cursor = getContentResolver().query(CatPictures.CONTENT_URI, CatPicturesTable.DISPLAY_COLUMNS, whereClause, whereValues, null);
		if(cursor.moveToFirst()){
			String title = cursor.getString(cursor.getColumnIndex(CatPicturesTable.TITLE));
			String thumbnailUrl = cursor.getString(cursor.getColumnIndex(CatPicturesTable.THUMBNAIL_URL));				
			Uri thumbnailUri = Uri.parse(thumbnailUrl);
			String filename = thumbnailUri.getLastPathSegment();
			File thumbFile = new File(app.getThumbnailsDir(), filename);

			TextView titleView = (TextView)findViewById(R.id.post_title);
			ImageView thumbView = (ImageView)findViewById(R.id.cat_picture);

			titleView.setText(title);
			thumbView.setImageURI(Uri.parse(thumbFile.getPath()));
		}
	}

	private void initComments(String postId){
		
		// GET EXISTING COMMENTS TO INIT LIST
		String whereClause = CommentsTable.CAT_PICTURE_ID + "=?";
		String[] whereValues = {postId};
		
		Cursor cursor = getContentResolver().query(Comments.CONTENT_URI,
				CommentsTable.DISPLAY_COLUMNS, whereClause, whereValues,
				CommentsTable.CREATED + " DESC");

		startManagingCursor(cursor);

		// CREATE THE ADAPTER USING THE CURSOR
		// THIS BINDS THE DATA IN THE CURSOR TO THE VIEW FOR EACH ROW IN THE LIST

		CatPicsCursorAdapter mAdapter = new CatPicsCursorAdapter(this, cursor);

		// SET THIS ADAPTER AS YOUR LISTACTIVITY'S ADAPTER
		ListView listView = (ListView)findViewById(R.id.comments_list);
		listView.setAdapter(mAdapter);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}




}
