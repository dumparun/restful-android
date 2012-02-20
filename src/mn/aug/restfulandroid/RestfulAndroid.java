package mn.aug.restfulandroid;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import mn.aug.restfulandroid.rest.RestClient;
import mn.aug.restfulandroid.rest.RestClientImpl;
import mn.aug.restfulandroid.service.CatPicturesProcessor;
import mn.aug.restfulandroid.service.CatPicturesService;
import mn.aug.restfulandroid.service.DefaultCatPicturesProcessor;
import mn.aug.restfulandroid.service.DefaultCatPicturesService;
import mn.aug.restfulandroid.util.Logger;
import android.app.Application;
import android.content.Context;

public class RestfulAndroid extends Application {

	private static Context mAppContext;
	private static RestClient mRestClient;
	private static Class<? extends CatPicturesService> mCatPicturesServiceClass = DefaultCatPicturesService.class;
	private static Class<? extends CatPicturesProcessor> mCatPicturesProcessorClass = DefaultCatPicturesProcessor.class;

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
		mCatPicturesServiceClass = DefaultCatPicturesService.class;
	}

	public static void setCatPicturesServiceClass(Class<? extends CatPicturesService> c) {
		mCatPicturesServiceClass = c;
	}

	public static Class<? extends CatPicturesService> getCatPicturesServiceClass() {
		return mCatPicturesServiceClass;
	}

	public static CatPicturesProcessor getCatPicturesProcessor(Context context) {
		try {
			Constructor<? extends CatPicturesProcessor> constructor = mCatPicturesProcessorClass
					.getConstructor(android.content.Context.class);
			return constructor.newInstance(context);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setCatPicturesProcessorClass(Class<? extends CatPicturesProcessor> c) {
		mCatPicturesProcessorClass = c;
	}

}
