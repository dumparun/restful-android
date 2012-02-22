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

public class CatPicturesActivity extends RESTfulListActivity {

	private static final String TAG = CatPicturesActivity.class.getSimpleName();

	private Long requestId;
	private BroadcastReceiver requestReceiver;

	private CatPicturesServiceHelper mCatPicturesServiceHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentResId(R.layout.home);
		setRefreshable(true);

		super.onCreate(savedInstanceState);

		// SOME CODE
		Cursor cursor = getContentResolver().query(CatPictures.CONTENT_URI,
				CatPicturesTable.DISPLAY_COLUMNS, null, null, null);
		startManagingCursor(cursor);

		// THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO

		int[] to = new int[] { R.id.id, R.id.thumbnail, R.id.title };

		// CREATE THE ADAPTER USING THE CURSOR POINTING TO THE DESIRED DATA AS
		// WELL AS THE LAYOUT INFORMATION

		SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.cat_pictures_list_item, cursor,
				CatPicturesTable.DISPLAY_COLUMNS, to);

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

					setRefreshing(false);

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
			setRefreshing(true);
			requestId = mCatPicturesServiceHelper.getCatPictures();
		} else if (mCatPicturesServiceHelper.isRequestPending(requestId)) {
			setRefreshing(true);
		} else {
			setRefreshing(false);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.logout:
			AuthorizationManager.getInstance(this).logout();
			Intent login = new Intent(this, LoginActivity.class);
			startActivity(login);
			finish();
			break;
		case R.id.about:
			Intent about = new Intent(this, AboutActivity.class);
			startActivity(about);
			break;
		}
		return false;
	}

	@Override
	protected void refresh() {
		requestId = mCatPicturesServiceHelper.getCatPictures();
	}
}
