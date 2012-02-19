package mn.aug.restfulandroid.rest;

import java.net.URI;
import java.util.List;
import java.util.Map;

import mn.aug.restfulandroid.rest.RestMethodFactory.Method;
import mn.aug.restfulandroid.rest.resource.CatPictures;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

public class GetCatPicturesRestMethod extends AbstractRestMethod<CatPictures> {

	private static final String TAG = GetCatPicturesRestMethod.class.getSimpleName();
	
	private Context mContext;

	private static final URI CAT_PICTURES_URI = URI.create("www.reddit.com/r/catpictures/.json");

	private Map<String, List<String>> headers;

	public GetCatPicturesRestMethod(Context context, Map<String, List<String>> headers) {
		mContext = context.getApplicationContext();
		this.headers = headers;
	}

	@Override
	protected Request buildRequest() {

		Request request = new Request(Method.GET, CAT_PICTURES_URI, headers, null);
		return request;
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

}
