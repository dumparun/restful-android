package mn.aug.restfulandroid.service;

import android.content.Context;



public class CatPicturesProcessorFactory {

	private static CatPicturesProcessorFactory mSingleton;
	private CatPicturesProcessor mProcessor;

	public void setDefaultProcessor(CatPicturesProcessor processor) {
		mProcessor = processor;
	}

	public CatPicturesProcessor getProcessor() {
		return mProcessor;
	}

	public static CatPicturesProcessorFactory getInstance(Context context) {
		if (mSingleton == null) {
			mSingleton = new CatPicturesProcessorFactory(context.getApplicationContext());
		}
		return mSingleton;
	}
	
	private CatPicturesProcessorFactory(Context context) {
		mProcessor = new DefaultCatPicturesProcessor(context);
	};

}
