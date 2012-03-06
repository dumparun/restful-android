package mn.aug.restfulandroid.mock;

import java.util.concurrent.Semaphore;

import mn.aug.restfulandroid.service.CatPicturesProcessor;
import android.content.Context;

public class MockCatPicturesProcessor {
	
}

//public class MockCatPicturesProcessor implements CatPicturesProcessor {
//
//	private static int mResultCode;
//	private static Semaphore mSemaphore;
//
//	public MockCatPicturesProcessor(Context context) {
//		mSemaphore = new Semaphore(0);
//	}
//
//	@Override
//	public void getCatPictures(CatPicturesProcessorCallback callback) {
//
//		try {
//			mSemaphore.acquire();
//		} catch (InterruptedException e) {}
//
//		callback.send(mResultCode);
//	}
//
//	public static void setResultCode(int code) {
//		mResultCode = code;
//	}
//	
//	public static void releaseSemaphore() {
//		mSemaphore.release();
//	}
//
//}
