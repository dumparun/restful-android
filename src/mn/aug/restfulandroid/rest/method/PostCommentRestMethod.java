package mn.aug.restfulandroid.rest.method;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mn.aug.restfulandroid.rest.Request;
import mn.aug.restfulandroid.rest.method.RestMethodFactory.Method;
import mn.aug.restfulandroid.rest.resource.Comment;
import mn.aug.restfulandroid.security.LoginManager;
import mn.aug.restfulandroid.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

public class PostCommentRestMethod extends AbstractRestMethod<Comment> {

	private static final String PARAM_KEY_API_TYPE = "api_type";

	private static final String TAG = PostCommentRestMethod.class.getSimpleName();

	private Context mContext;

	private static final String BASE_URI = "http://www.reddit.com/api/comment";

	private static final String PARAM_KEY_PARENT = "parent";
	private static final String PARAM_KEY_TEXT = "text";
	
	private URI mUri;

	private Comment mComment;

	private String mParent;
	
	private LoginManager loginMgr;

	public PostCommentRestMethod(Context context, String catPictureRefId, Comment comment) {
		mContext = context.getApplicationContext();
		mParent = "t3_" + catPictureRefId;
		mComment = comment;
		mUri = buildUri();
		loginMgr = new LoginManager(context);
	}

	@Override
	protected Request buildRequest() {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(PARAM_KEY_API_TYPE, "json");
		params.put(PARAM_KEY_PARENT, mParent);
		params.put(PARAM_KEY_TEXT, mComment.getText());

		String body = buildQueryString(params);

		Request request = new Request(Method.POST, mUri, null, body.getBytes());
		return request;
	}

	private URI buildUri() {
		String uriString = BASE_URI;
		return URI.create(uriString);
	}

	@Override
	protected Comment parseResponseBody(String responseBody) throws Exception {

		JSONObject body = new JSONObject(responseBody);
		JSONObject json = body.getJSONObject("json");
		JSONArray errors = json.getJSONArray("errors");
		if (errors.length() != 0) {
			JSONArray error = errors.getJSONArray(0);
			
			throw new Exception((String) error.get(0));
		}
		
		// TODO Get updated values from API
		/*
		 * Reddit returns web garbage in response to post, probably need to find better api,
		 * we should be able to parse an id, author and creation timestamp from the response.
		 * Just hard-code for now.
		 */
		JSONObject commentData = ((JSONObject)(json.getJSONObject("data").getJSONArray("things").get(0))).getJSONObject("data");
		mComment.setId(JSONUtil.getString(commentData,"id"));
		mComment.setCreateDate(new Date().getTime());
		mComment.setAuthor(loginMgr.getLogin().getUsername());
		
		return mComment;
	}

	@Override
	protected Context getContext() {
		return mContext;
	}

	@Override
	protected boolean requiresAuthorization() {
		return true;
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
