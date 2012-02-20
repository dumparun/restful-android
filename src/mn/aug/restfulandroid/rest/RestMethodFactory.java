package mn.aug.restfulandroid.rest;

import java.util.List;
import java.util.Map;

import mn.aug.restfulandroid.provider.CatPicturesConstants;
import mn.aug.restfulandroid.rest.method.GetCatPicturesRestMethod;
import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;

public class RestMethodFactory {

	private static RestMethodFactory instance;
	private static Object lock = new Object();
	private UriMatcher uriMatcher;
	private Context mContext;

	private static final int CAT_PICTURES = 1;

	private RestMethodFactory(Context context) {
		mContext = context.getApplicationContext();
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(CatPicturesConstants.AUTHORITY, CatPicturesConstants.TABLE_NAME, CAT_PICTURES);
	}

	public static RestMethodFactory getInstance(Context context) {
		synchronized (lock) {
			if (instance == null) {
				instance = new RestMethodFactory(context);
			}
		}

		return instance;
	}

	public RestMethod getRestMethod(Uri resourceUri, Method method,
			Map<String, List<String>> headers, byte[] body) {

		switch (uriMatcher.match(resourceUri)) {
		case CAT_PICTURES:
			if (method == Method.GET) {
				return new GetCatPicturesRestMethod(mContext, headers);
			}
			break;
		}

		return null;
	}

	public static enum Method {
		GET, POST, PUT, DELETE
	}

}
