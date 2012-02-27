package mn.aug.restfulandroid.service;

import java.util.UUID;

import mn.aug.restfulandroid.mock.MockCatPicturesProcessor;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.test.InstrumentationTestCase;

public class CatPicturesServiceTest extends InstrumentationTestCase {

	/*
	 * TODO This could use some additional tests (e.g. for bad parameters passed
	 * to the service)
	 */

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		CatPicturesProcessorFactory processorFactory = CatPicturesProcessorFactory
				.getInstance(getInstrumentation().getContext());
		processorFactory.setDefaultProcessor(new MockCatPicturesProcessor(getInstrumentation()
				.getContext()));
	}

	public void testOnHandleIntent() throws Exception {

		MockCatPicturesProcessor.setResultCode(200);

		final long requestId = UUID.randomUUID().getLeastSignificantBits();

		final Intent expectedOriginalIntent = new Intent(getInstrumentation().getTargetContext(),
				DefaultCatPicturesService.class);

		ResultReceiver serviceCallback = new ResultReceiver(null) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				assertTrue(resultCode == 200);
				Intent actualOriginalIntent = resultData
						.getParcelable(CatPicturesService.ORIGINAL_INTENT_EXTRA);

				assertTrue(actualOriginalIntent.getStringExtra(CatPicturesService.METHOD_EXTRA)
						.equalsIgnoreCase(CatPicturesService.METHOD_GET));
				assertTrue(actualOriginalIntent.getIntExtra(CatPicturesService.RESOURCE_TYPE_EXTRA,
						-1) == CatPicturesService.RESOURCE_TYPE_CAT_PICTURES);
				assertTrue(actualOriginalIntent.getLongExtra(
						CatPicturesServiceHelper.EXTRA_REQUEST_ID, -1) == requestId);
			}
		};

		expectedOriginalIntent.putExtra(CatPicturesService.METHOD_EXTRA,
				CatPicturesService.METHOD_GET);
		expectedOriginalIntent.putExtra(CatPicturesService.RESOURCE_TYPE_EXTRA,
				CatPicturesService.RESOURCE_TYPE_CAT_PICTURES);
		expectedOriginalIntent.putExtra(CatPicturesService.SERVICE_CALLBACK_EXTRA, serviceCallback);
		expectedOriginalIntent.putExtra(CatPicturesServiceHelper.EXTRA_REQUEST_ID, requestId);

		getInstrumentation().getTargetContext().startService(expectedOriginalIntent);

		/* make sure service has time to start */
		Thread.sleep(1000);

		/* let the callback occur (assertions above in onReceiveResult() */
		MockCatPicturesProcessor.releaseSemaphore();

		/* allow time for callback and assertions */
		Thread.sleep(2000);
	}

}
