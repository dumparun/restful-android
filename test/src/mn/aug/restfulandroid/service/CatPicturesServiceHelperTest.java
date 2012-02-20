package mn.aug.restfulandroid.service;

import org.apache.http.HttpStatus;

import mn.aug.restfulandroid.RestfulAndroid;
import mn.aug.restfulandroid.mock.MockCatPicturesService;
import mn.aug.restfulandroid.mock.TestContext;
import mn.aug.restfulandroid.rest.RestMethodFactory.Method;
import android.content.Intent;
import android.os.ResultReceiver;
import android.test.InstrumentationTestCase;

public class CatPicturesServiceHelperTest extends InstrumentationTestCase {

	/*
	 * Tests CatPicturesServiceHelper.getInstance(Context) and
	 * CatPicturesServiceHelper.isRequestPending(long), but only single
	 * requests. We should probably add tests for multiple requests with various
	 * statues.
	 */

	private long originalRequestId;
	private TestContext mTestContext;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mTestContext = new TestContext(getInstrumentation().getContext());
		RestfulAndroid.setCatPicturesServiceClass(MockCatPicturesService.class);
	}

	@Override
	protected void tearDown() throws Exception {
		/*
		 * The test context will be newly instantiated for each test. To ensure
		 * the CatPicturesServiceHelper gets the new context, we need to clear
		 * it's instance, allowing it to re-initialize for each test
		 */
		CatPicturesServiceHelper.clearInstance();
		super.tearDown();
	}

	public void testGetInstance() {
		assertNotNull(CatPicturesServiceHelper.getInstance(mTestContext));
	}

	public void testGetCatPictures() throws InterruptedException {

		MockCatPicturesService.setResultCode(HttpStatus.SC_OK);

		CatPicturesServiceHelper helper = CatPicturesServiceHelper.getInstance(mTestContext);
		originalRequestId = helper.getCatPictures();

		/*
		 * The implementation of CatPicturesServiceHelper.getCatPictures() will
		 * fire startService(Intent). The mock context passed in when getting
		 * the singleton instance of CatPicturesServiceHelper overrides the
		 * normal Context.startService(Intent) functionality and holds on to the
		 * Intent for later retrieval rather than actually starting the service.
		 * However, it does create an instance of MockCatPicturesService and
		 * calls onHandleIntent (but it waits until releaseSemaphore() is called
		 * before calling the callback to the service helper.
		 */
		Intent intent = mTestContext.getStartServiceIntents().get(0);
		assertNotNull(intent);

		/*
		 * Assert that the values put into the Intent by
		 * CatPicturesServiceHelper are correct.
		 */
		String actualMethod = intent.getStringExtra(CatPicturesService.METHOD_EXTRA);
		int actualResourceType = intent.getIntExtra(CatPicturesService.RESOURCE_TYPE_EXTRA, 0);
		ResultReceiver receiver = (ResultReceiver) intent
				.getParcelableExtra(CatPicturesService.SERVICE_CALLBACK);
		long resultRequestId = intent.getLongExtra(CatPicturesServiceHelper.EXTRA_REQUEST_ID, 0);
		assertTrue(actualMethod.equalsIgnoreCase(Method.GET.toString()));
		assertTrue(actualResourceType == CatPicturesService.RESOURCE_TYPE_CAT_PICTURES);
		assertNotNull(receiver);
		assertTrue(originalRequestId == resultRequestId);

		/*
		 * Release the semaphore, allowing onHandleIntent() to proceed with the
		 * callback.
		 */
		MockCatPicturesService.releaseOnHandleIntent();

		/* Make sure the callback has time to occur */
		Thread.sleep(500);

		/*
		 * Assert that the broadcast sent in the callback to
		 * CatPicturesServiceHelper has the correct values
		 */
		Intent broadcastIntent = mTestContext.getSendBroadcastIntents().get(0);
		assertNotNull(broadcastIntent);
		assertTrue(broadcastIntent.getAction().equalsIgnoreCase(
				CatPicturesServiceHelper.ACTION_REQUEST_RESULT));
		assertTrue(broadcastIntent.getLongExtra(CatPicturesServiceHelper.EXTRA_REQUEST_ID, 0) == originalRequestId);
		assertTrue(broadcastIntent.getIntExtra(CatPicturesServiceHelper.EXTRA_RESULT_CODE, 0) == HttpStatus.SC_OK);

	}

	public void testIsRequestPending() throws InterruptedException {

		/* See testGetInstance() for an explanation of what's going on here */

		MockCatPicturesService.setResultCode(HttpStatus.SC_OK);

		CatPicturesServiceHelper helper = CatPicturesServiceHelper.getInstance(mTestContext);
		originalRequestId = helper.getCatPictures();

		Intent intent = mTestContext.getStartServiceIntents().get(0);
		assertNotNull(intent);

		String actualMethod = intent.getStringExtra(CatPicturesService.METHOD_EXTRA);
		int actualResourceType = intent.getIntExtra(CatPicturesService.RESOURCE_TYPE_EXTRA, 0);
		ResultReceiver receiver = (ResultReceiver) intent
				.getParcelableExtra(CatPicturesService.SERVICE_CALLBACK);
		long resultRequestId = intent.getLongExtra(CatPicturesServiceHelper.EXTRA_REQUEST_ID, 0);

		assertTrue(actualMethod.equalsIgnoreCase(Method.GET.toString()));
		assertTrue(actualResourceType == CatPicturesService.RESOURCE_TYPE_CAT_PICTURES);
		assertNotNull(receiver);
		assertTrue(originalRequestId == resultRequestId);

		/*
		 * Verify the request is pending before releasing mock service to make
		 * the callback
		 */
		assertTrue(helper.isRequestPending(originalRequestId));

		MockCatPicturesService.releaseOnHandleIntent();
		Thread.sleep(500);

		Intent broadcastIntent = mTestContext.getSendBroadcastIntents().get(0);
		assertNotNull(broadcastIntent);
		assertTrue(broadcastIntent.getAction().equalsIgnoreCase(
				CatPicturesServiceHelper.ACTION_REQUEST_RESULT));
		assertTrue(broadcastIntent.getLongExtra(CatPicturesServiceHelper.EXTRA_REQUEST_ID, 0) == originalRequestId);
		assertTrue(broadcastIntent.getIntExtra(CatPicturesServiceHelper.EXTRA_RESULT_CODE, 0) == HttpStatus.SC_OK);

		assertFalse(helper.isRequestPending(originalRequestId));
	}

}
