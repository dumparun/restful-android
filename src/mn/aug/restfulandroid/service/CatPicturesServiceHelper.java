package mn.aug.restfulandroid.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * CatPictures API
 * 
 * @author jeremyhaberman
 */
public class CatPicturesServiceHelper {

	@SuppressWarnings("unused")
	private static final String TAG = CatPicturesServiceHelper.class.getSimpleName();

	public static String ACTION_REQUEST_RESULT = "REQUEST_RESULT";
	public static String EXTRA_REQUEST_ID = "EXTRA_REQUEST_ID";
	public static String EXTRA_RESULT_CODE = "EXTRA_RESULT_CODE";

	private static final String catPicturesHashKey = "CAT_PICTURES";

	// TODO: refactor the key
	private Map<String, Long> pendingRequests = new HashMap<String, Long>();
	private Context mAppContext;

	private Class<? extends CatPicturesService> mCatPicturesServiceClass;

	public CatPicturesServiceHelper(Context context) {
		this.mAppContext = context.getApplicationContext();

		mCatPicturesServiceClass = DefaultCatPicturesService.class;
	}

	/**
	 * Initiates a request to get cat pictures via the CatPicturesService
	 * 
	 * @return request ID
	 */
	public long getCatPictures() {

		long requestId = generateRequestID();
		pendingRequests.put(catPicturesHashKey, requestId);

		ResultReceiver serviceCallback = new ResultReceiver(null) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				handleGetCatPicturesResult(resultCode, resultData);
			}
		};

		Intent intent = new Intent(this.mAppContext, mCatPicturesServiceClass);
		intent.putExtra(CatPicturesService.METHOD_EXTRA, CatPicturesService.METHOD_GET);
		intent.putExtra(CatPicturesService.RESOURCE_TYPE_EXTRA,
				CatPicturesService.RESOURCE_TYPE_CAT_PICTURES);
		intent.putExtra(CatPicturesService.SERVICE_CALLBACK_EXTRA, serviceCallback);
		intent.putExtra(EXTRA_REQUEST_ID, requestId);

		this.mAppContext.startService(intent);

		return requestId;
	}
	
	public void setCatPicturesServiceClass(Class<? extends CatPicturesService> service) {
		mCatPicturesServiceClass = service;
	}

	private long generateRequestID() {
		long requestId = UUID.randomUUID().getLeastSignificantBits();
		return requestId;
	}

	/**
	 * Determines whether a request is pending
	 * 
	 * @param requestId
	 *            the ID of a previous request
	 * @return <code>true</code> if the request is pending, <code>false</code>
	 *         otherwise
	 */
	public boolean isRequestPending(long requestId) {
		return this.pendingRequests.containsValue(requestId);
	}

	/**
	 * Handles the result code and data during a callback from
	 * CatPicturesService
	 * 
	 * @param resultCode
	 *            the result code
	 * @param resultData
	 *            the data
	 */
	private void handleGetCatPicturesResult(int resultCode, Bundle resultData) {

		Intent origIntent = (Intent) resultData
				.getParcelable(CatPicturesService.ORIGINAL_INTENT_EXTRA);

		if (origIntent != null) {
			long requestId = origIntent.getLongExtra(EXTRA_REQUEST_ID, 0);

			pendingRequests.remove(catPicturesHashKey);

			Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
			resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
			resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

			mAppContext.sendBroadcast(resultBroadcast);
		}
	}

}
