package mn.aug.restfulandroid;

import android.app.Application;
import android.content.Context;

import mn.aug.restfulandroid.R;
import mn.aug.restfulandroid.rest.RestClient;
import mn.aug.restfulandroid.rest.RestClientImpl;
import mn.aug.restfulandroid.util.Logger;

public class RestfulAndroid extends Application {

	private static Context mAppContext;
	private static RestClient mRestClient;

	@Override
	public void onCreate() {
		super.onCreate();

		mAppContext = getApplicationContext();

		Logger.setAppTag(getString(R.string.app_log_tag));
		Logger.setLevel(Logger.DEBUG);
	}

	/**
	 * Returns the application's context. Useful for classes that need a Context
	 * but don't inherently have one.
	 * 
	 * @return application context
	 */
	public static Context getAppContext() {
		return mAppContext;
	}

	/**
	 * Sets the RestClient implementation for the app
	 * 
	 * @param client
	 */
	public static void setRestClient(RestClient client) {
		mRestClient = client;
	}

	/**
	 * Get the current RestClient
	 * 
	 * @return
	 */
	public static RestClient getRestClient() {
		if (mRestClient == null) {
			mRestClient = new RestClientImpl();
		}
		return mRestClient;
	}

	/**
	 * Re-initializes all singletons to the default implementation
	 */
	public static void reset() {
		mRestClient = new RestClientImpl();
	}

}
