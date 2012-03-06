package mn.aug.restfulandroid.rest.method;

import java.net.URI;

import mn.aug.restfulandroid.rest.AbstractRestMethod;
import mn.aug.restfulandroid.rest.Request;
import mn.aug.restfulandroid.rest.RestMethodFactory.Method;
import mn.aug.restfulandroid.rest.resource.CatPictures;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

public class GetCatPicturesRestMethod extends AbstractRestMethod<CatPictures> {

	private static final String TAG = GetCatPicturesRestMethod.class.getSimpleName();

	private Context mContext;

	private static final String BASE_URI = "http://www.reddit.com/r/catpictures/new/.json?sort=new&count=25";

	/* The id of the most recent cat picture we have */
	private String mNewestId;

	private URI mUri;

	public GetCatPicturesRestMethod(Context context, String newestId) {
		mContext = context.getApplicationContext();
		mNewestId = newestId;
		mUri = buildUri();
	}

	@Override
	protected Request buildRequest() {
		Request request = new Request(Method.GET, mUri, null, null);
		return request;
	}

	private URI buildUri() {
		String uriString = BASE_URI;
		if (mNewestId != null) {
			uriString += "&before=t3_" + mNewestId;
		}
		return URI.create(uriString);
	}

	@Override
	protected CatPictures parseResponseBody(String responseBody) throws Exception {

		JSONObject redditResponse = new JSONObject(responseBody);
		JSONObject data = redditResponse.getJSONObject("data");
		JSONArray catPicturesArray = data.getJSONArray("children");

		return new CatPictures(catPicturesArray);
	}

	@Override
	protected Context getContext() {
		return mContext;
	}

	@Override
	protected boolean requiresAuthorization() {
		return false;
	}

	@Override
	protected String getLogTag() {
		return TAG;
	}

	@Override
	protected URI getURI() {
		return mUri;
	}

}
