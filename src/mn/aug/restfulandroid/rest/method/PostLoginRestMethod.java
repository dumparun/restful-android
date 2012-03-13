package mn.aug.restfulandroid.rest.method;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import mn.aug.restfulandroid.rest.Request;
import mn.aug.restfulandroid.rest.method.RestMethodFactory.Method;
import mn.aug.restfulandroid.rest.resource.Login;

import org.json.JSONObject;

import android.content.Context;

public class PostLoginRestMethod extends AbstractRestMethod<Login> {

	private static final String TAG = PostLoginRestMethod.class.getSimpleName();

	private Context mContext;

	private static final String BASE_URI = "http://www.reddit.com/api/login";

	private static final String PARAM_KEY_API_TYPE = "api_type";
	private static final String PARAM_KEY_USERNAME = "user";
	private static final String PARAM_KEY_PASSWORD = "passwd";
	
	private URI mUri;

	private Login mLogin;

	public PostLoginRestMethod(Context context, Login login) {
		mContext = context.getApplicationContext();
		mLogin = login;
		mUri = buildUri();
	}

	@Override
	protected Request buildRequest() {

		Map<String, String> params = new HashMap<String, String>();
		params.put(PARAM_KEY_API_TYPE, "json");
		params.put(PARAM_KEY_USERNAME, mLogin.getUsername());
		params.put(PARAM_KEY_PASSWORD, mLogin.getPassword());

		String body = buildQueryString(params);

		Request request = new Request(Method.POST, mUri, null, body.getBytes());
		return request;
	}

	private URI buildUri() {
		String uriString = BASE_URI;
		return URI.create(uriString);
	}

	@Override
	protected Login parseResponseBody(String responseBody) throws Exception {

		JSONObject body = new JSONObject(responseBody);
		JSONObject json = body.getJSONObject("json");
		JSONObject data = json.getJSONObject("data");
		String cookie = data.getString("cookie");
		String modhash = data.getString("modhash");

		mLogin.setCookie(cookie);
		mLogin.setModhash(modhash);

		return mLogin;
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
