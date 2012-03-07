package mn.aug.restfulandroid.service;

import android.content.Context;

public class CatPictureCommentsProcessorFactory {

	private static CatPictureCommentsProcessorFactory mSingleton;
	private ResourceProcessor mProcessor;

	public void setDefaultProcessor(ResourceProcessor processor) {
		mProcessor = processor;
	}

	public ResourceProcessor getProcessor() {
		return mProcessor;
	}

	public static CatPictureCommentsProcessorFactory getInstance(Context context) {
		if (mSingleton == null) {
			mSingleton = new CatPictureCommentsProcessorFactory(context.getApplicationContext());
		}
		return mSingleton;
	}
	
	private CatPictureCommentsProcessorFactory(Context context) {
		mProcessor = new CatPictureCommentsProcessor(context);
	};

}
