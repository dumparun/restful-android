package mn.aug.restfulandroid.activity;

import mn.aug.restfulandroid.R;
import mn.aug.restfulandroid.activity.base.RESTfulListActivity;
import mn.aug.restfulandroid.provider.CatPicturesConstants;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import mn.aug.restfulandroid.rest.resource.CatPictures;
import mn.aug.restfulandroid.security.AuthorizationManager;
import mn.aug.restfulandroid.service.CatPicturesServiceHelper;
import mn.aug.restfulandroid.util.Logger;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class CatPicturesActivity extends ListActivity {

	private static final String TAG = CatPicturesActivity.class.getSimpleName();

	private Long requestId;
	private BroadcastReceiver requestReceiver;

	private CatPicturesServiceHelper mCatPicturesServiceHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);

		// SOME CODE
		Cursor cursor = getContentResolver().query(CatPictures.CONTENT_URI,
				CatPicturesTable.DISPLAY_COLUMNS, null, null, null);
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

		// String name = getNameFromContentProvider();
		// if (name != null) {
		// showNameToast(name);
		// }

		/*
		 * 1. Register for broadcast from TwitterServiceHelper
		 * 
		 * 2. See if we've already made a request. a. If so, check the status.
		 * b. If not, make the request (already coded below).
		 */

		IntentFilter filter = new IntentFilter(CatPicturesServiceHelper.ACTION_REQUEST_RESULT);
		requestReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				long resultRequestId = intent.getLongExtra(
						CatPicturesServiceHelper.EXTRA_REQUEST_ID, 0);

				Logger.debug(TAG, "Received intent " + intent.getAction() + ", request ID "
						+ resultRequestId);

				if (resultRequestId == requestId) {

					Logger.debug(TAG, "Result is for our request ID");

					int resultCode = intent.getIntExtra(CatPicturesServiceHelper.EXTRA_RESULT_CODE,
							0);

					Logger.debug(TAG, "Result code = " + resultCode);

					if (resultCode == 200) {
						Logger.debug(TAG, "Updating UI with new data");
					}
				} else {
					Logger.debug(TAG, "Result is NOT for our request ID");
				}

			}
		};

		mCatPicturesServiceHelper = CatPicturesServiceHelper.getInstance(this);

		this.registerReceiver(requestReceiver, filter);

		if (requestId == null) {
			requestId = mCatPicturesServiceHelper.getCatPictures();
			// show spinner
		} else if (mCatPicturesServiceHelper.isRequestPending(requestId)) {
			// show spinner
		} 

	}

	@Override
	protected void onPause() {
		super.onPause();

		// Unregister for broadcast
		if (requestReceiver != null) {
			try {
				this.unregisterReceiver(requestReceiver);
			} catch (IllegalArgumentException e) {
				Logger.error(TAG, e.getLocalizedMessage(), e);
			}
		}
	}

}
