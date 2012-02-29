package mn.aug.restfulandroid.activity;

import mn.aug.restfulandroid.R;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import mn.aug.restfulandroid.rest.resource.CatPictures;
import mn.aug.restfulandroid.service.CatPicturesServiceHelper;
import mn.aug.restfulandroid.util.Logger;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;

public class CatPicturesActivity extends ListActivity {

	private static final String TAG = CatPicturesActivity.class.getSimpleName();

	private Long requestId;
	/**
	 * Used to send and retrieve data from the underlying api. 
	 */
	private CatPicturesServiceHelper mCatPicturesServiceHelper;
	/**
	 * Receives callbacks from the service helper
	 */
	private BroadcastReceiver requestReceiver;
	private IntentFilter filter = new IntentFilter(CatPicturesServiceHelper.ACTION_REQUEST_RESULT);


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// ALLOW PROGRESS SPINNER IN TITLE BAR
		// THIS MUST COME BEFORE setContentView()!
		this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setProgressBarIndeterminateVisibility(false);

		setContentView(R.layout.home);

		this.requestReceiver = new CatPicturesReceiver();

		// GET EXISTING PICTURES TO INIT LIST
		Cursor cursor = getContentResolver().query(CatPictures.CONTENT_URI,
				CatPicturesTable.DISPLAY_COLUMNS, null, null,
				CatPicturesTable.CREATED + " DESC");
		
		startManagingCursor(cursor);

		// CREATE THE ADAPTER USING THE CURSOR
		// THIS BINDS THE DATA IN THE CURSOR TO THE VIEW FOR EACH ROW IN THE LIST

		CatPicsCursorAdapter mAdapter = new CatPicsCursorAdapter(this, cursor);

		// SET THIS ADAPTER AS YOUR LISTACTIVITY'S ADAPTER
		setListAdapter(mAdapter);

	}

	@Override
	protected void onResume() {
		super.onResume();

		/*
		 * 1. Register for callbacks broadcast from the CatPicturesServiceHelper
		 */
		this.registerReceiver(this.requestReceiver, this.filter);

		/*
		 * 2. See if we've already made a request. 
		 * a. If not, make the request
		 * b. If so, check if it is still in progress or was completed while we were paused
		 */
		mCatPicturesServiceHelper = new CatPicturesServiceHelper(this);

		if (requestId == null) {
			requestId = mCatPicturesServiceHelper.getCatPictures();
			// show progress spinner
			setProgressBarIndeterminateVisibility(true);
		} else if (mCatPicturesServiceHelper.isRequestPending(requestId)) {
			// show progress spinner
			setProgressBarIndeterminateVisibility(true);
		} else {
			// stop progress spinner, request already received, data updated
			setProgressBarIndeterminateVisibility(false);
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		// Unregister for broadcast
		try {
			this.unregisterReceiver(requestReceiver);
		} catch (IllegalArgumentException e) {
			Logger.warn(TAG, "Likely receiver wasn't registered, ok to ignore");
		}
	}

	class CatPicturesReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			// Returns the id of original request
			long resultRequestId = intent.getLongExtra(
					CatPicturesServiceHelper.EXTRA_REQUEST_ID, 0);

			Logger.debug(TAG, "Received intent " + intent.getAction() + ", request ID "
					+ resultRequestId);
			
			// check if this was OUR request
			if (resultRequestId == requestId) {
				
				// This was our request, stop the progress spinner
				CatPicturesActivity.this.setProgressBarIndeterminateVisibility(false);
				Logger.debug(TAG, "Result is for our request ID");

				// What was the result of our request?
				int resultCode = intent.getIntExtra(CatPicturesServiceHelper.EXTRA_RESULT_CODE,
						0);

				Logger.debug(TAG, "Result code = " + resultCode);

				// HERE WE COULD GIVE SOME FEEDBACK TO USER INDICATING IF DATA HAS BEEN UPDATED
				// OR IF AN ERROR HAS OCCURED
				if (resultCode == 200) {
					Logger.info(TAG, "Request Succeeded");
				} else {
					Logger.warn(TAG, "Error executing request:" + resultCode);					
				}
			} else {
				// IGNORE, wasn't for our request
				Logger.debug(TAG, "Result is NOT for our request ID");
			}

		}
	}

}
