package mn.aug.restfulandroid.service;

import android.content.Context;

public class CatPicturesProcessorFactory {

	private static CatPicturesProcessorFactory mSingleton;
	private ResourceProcessor mProcessor;

	public void setDefaultProcessor(ResourceProcessor processor) {
		mProcessor = processor;
	}

	public ResourceProcessor getProcessor() {
		return mProcessor;
	}

	public static CatPicturesProcessorFactory getInstance(Context context) {
		if (mSingleton == null) {
			mSingleton = new CatPicturesProcessorFactory(context.getApplicationContext());
		}
		return mSingleton;
	}
	
	private CatPicturesProcessorFactory(Context context) {
		mProcessor = new CatPicturesProcessor(context);
	};

}
