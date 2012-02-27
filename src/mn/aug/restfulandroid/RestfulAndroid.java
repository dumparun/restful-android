package mn.aug.restfulandroid;

import java.io.File;

import mn.aug.restfulandroid.util.Logger;
import android.app.Application;

public class RestfulAndroid extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Logger.setAppTag(getString(R.string.app_log_tag));
		Logger.setLevel(Logger.DEBUG);

	}

	public File getThumbnailsDir() {
		return new File(getFilesDir(), "thumbnails");

	}

}
