package mn.aug.restfulandroid.rest;

import java.net.URI;
import java.util.List;
import java.util.Map;

import mn.aug.restfulandroid.rest.RestMethodFactory.Method;
import mn.aug.restfulandroid.rest.resource.TwitterTimeline;
import mn.aug.restfulandroid.security.AuthorizationManager;

import org.json.JSONArray;
import org.json.JSONObject;


public class GetTimelineRestMethod extends AbstractRestMethod<TwitterTimeline>{
	
	private static final URI TIMELINE_URI = URI.create("https://api.twitter.com/1/statuses/home_timeline.json");
	
	private Map<String, List<String>> headers;
	
	public GetTimelineRestMethod(Map<String, List<String>> headers){
		this.headers = headers;
	}

	@Override
	protected Request buildRequest() {
		
		Request request = new Request(Method.GET, TIMELINE_URI, headers, null);
		return request;
	}

	@Override
	protected TwitterTimeline parseResponseBody(String responseBody) throws Exception {
		
		JSONArray json = new JSONArray(responseBody);
		return new TwitterTimeline(json);
		
	}

}
