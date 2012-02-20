package mn.aug.restfulandroid.mock;

import java.util.ArrayList;
import java.util.List;

import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.test.mock.MockContext;

/**
 * Context for testing
 * 
 * @author jeremyhaberman
 * 
 */
public class TestContext extends MockContext {

	/** the context of the test project */
	private Context mTestContext;

	private List<Intent> mStartServiceIntents = new ArrayList<Intent>();
	private List<Intent> mSendBroadcastIntents = new ArrayList<Intent>();

	/**
	 * Used to hold onto a reference to the test project's context. You can get
	 * this test context using {@link Instrumentation#getContext()}.
	 * 
	 * @param context
	 */
	public TestContext(Context context) {
		mTestContext = context;
	}

	/**
	 * Gets the package name of this test project
	 */
	@Override
	public String getPackageName() {
		return "mn.aug.restfulandroid.test";
	}

	/**
	 * Overrides the default implementation of
	 * {@link Context#getApplicationContext()}, returning this test context
	 * instead.
	 */
	@Override
	public Context getApplicationContext() {
		return this;
	}

	/**
	 * Mocks starting a service.
	 */
	@Override
	public ComponentName startService(final Intent service) {

		mStartServiceIntents.add(service);

		ComponentName component = service.getComponent();
		if (component.getClassName().equalsIgnoreCase(
				"mn.aug.restfulandroid.mock.MockCatPicturesService")) {

			final MockCatPicturesService catPicsService = new MockCatPicturesService();
			new Thread(new Runnable() {

				@Override
				public void run() {
					catPicsService.onHandleIntent(service);
				}
			}).start();

		}

		return component;
	}

	/**
	 * Returns the Intents that have been used with
	 * Context.startService(Intent).
	 * 
	 * @return list of Intents
	 */
	public List<Intent> getStartServiceIntents() {
		return mStartServiceIntents;
	}

	/**
	 * Returns the resources for the test Context (i.e. the resources of the
	 * test project).
	 */
	@Override
	public Resources getResources() {
		return mTestContext.getResources();
	}

	/**
	 * Mocks sending a broadcast. The Intent(s) passed in can be retrieved via
	 * {@link #getSendBroadcastIntents()}.
	 */
	@Override
	public void sendBroadcast(Intent intent) {
		mSendBroadcastIntents.add(intent);
	}

	/**
	 * Returns the Intents that have been broadcast via
	 * Context.sendBroadcast(Intent)
	 * 
	 * @return
	 */
	public List<Intent> getSendBroadcastIntents() {
		return mSendBroadcastIntents;
	}

}
