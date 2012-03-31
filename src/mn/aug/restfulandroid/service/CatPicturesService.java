package mn.aug.restfulandroid.service;

import mn.aug.restfulandroid.processor.CatPicturesProcessorFactory;
import mn.aug.restfulandroid.processor.ResourceProcessor;
import mn.aug.restfulandroid.processor.ResourceProcessorCallback;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class CatPicturesService extends IntentService implements CatPicturesServiceContract {

	private static final int REQUEST_INVALID = -1;

	public CatPicturesService() {
		super("DefaultCatPicturesService");
	}

	@Override
	protected void onHandleIntent(Intent requestIntent) {

		// Get request data from Intent
		int resourceType = requestIntent.getIntExtra(CatPicturesServiceContract.RESOURCE_TYPE_EXTRA, -1);
		String method = requestIntent.getStringExtra(CatPicturesServiceContract.METHOD_EXTRA);
		Bundle parameters = requestIntent.getBundleExtra(CatPicturesServiceContract.EXTRA_REQUEST_PARAMETERS);
		ResultReceiver serviceHelperCallback = requestIntent.getParcelableExtra(CatPicturesServiceContract.SERVICE_CALLBACK_EXTRA);
		ResourceProcessor processor = CatPicturesProcessorFactory.getInstance(this).getProcessor(resourceType);

		if (processor == null){
			if(serviceHelperCallback != null){
				serviceHelperCallback.send(REQUEST_INVALID, bundleOriginalIntent(requestIntent));
			}
			return;
		} 
		
		ResourceProcessorCallback processorCallback = makeCatPicturesProcessorCallback(requestIntent,serviceHelperCallback);


		if (method.equalsIgnoreCase(METHOD_GET)) {
			processor.getResource(processorCallback, parameters);
		} else if (method.equalsIgnoreCase(METHOD_POST)){
			processor.postResource(processorCallback, parameters);
		} else if(serviceHelperCallback != null) {
			serviceHelperCallback.send(REQUEST_INVALID, bundleOriginalIntent(requestIntent));
		}

	}

	private ResourceProcessorCallback makeCatPicturesProcessorCallback(final Intent originalIntent, 
			final ResultReceiver serviceReceiver) {

		ResourceProcessorCallback callback = new ResourceProcessorCallback() {

			@Override
			public void send(int resultCode, String resourceId) {
				if(serviceReceiver != null){
				serviceReceiver.send(resultCode, bundleOriginalIntent(originalIntent));
				}
			}
		};
		return callback;
	}

	private Bundle bundleOriginalIntent(Intent originalIntent) {

		Bundle originalRequest = new Bundle();
		originalRequest.putParcelable(ORIGINAL_INTENT_EXTRA, originalIntent);
		return originalRequest;
	}

}
