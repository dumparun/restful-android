package mn.aug.restfulandroid.mock;

import mn.aug.restfulandroid.rest.Request;
import mn.aug.restfulandroid.rest.Response;
import mn.aug.restfulandroid.rest.RestClient;

public class MockRestClient implements RestClient {

	private Response mResponse;
	
	public void setResponse(Response response) {
		mResponse = response;
	}
	
	@Override
	public Response execute(Request request) {
		return mResponse;
	}

}
