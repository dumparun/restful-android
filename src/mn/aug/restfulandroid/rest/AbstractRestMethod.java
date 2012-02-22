package mn.aug.restfulandroid.rest;

import java.util.List;
import java.util.Map;

import mn.aug.restfulandroid.RestfulAndroid;
import mn.aug.restfulandroid.rest.resource.Resource;
import mn.aug.restfulandroid.security.AuthorizationManager;
import mn.aug.restfulandroid.security.RequestSigner;
import mn.aug.restfulandroid.util.Logger;
import android.content.Context;

public abstract class AbstractRestMethod<T extends Resource> implements RestMethod<T> {

	private static final String DEFAULT_ENCODING = "UTF-8";

	public RestMethodResult<T> execute() {

		Request request = buildRequest();
		if (requiresAuthorization()) {
			RequestSigner signer = AuthorizationManager.getInstance(getContext());
			signer.authorize(request);
		}
		Response response = doRequest(request);
		return buildResult(response);
	}

	protected abstract Context getContext();

	/**
	 * Subclasses can overwrite for full control, eg. need to do special
	 * inspection of response headers, etc.
	 * 
	 * @param response
	 * @return
	 */
	protected RestMethodResult<T> buildResult(Response response) {

		int status = response.status;
		String statusMsg = "";
		String responseBody = null;
		T resource = null;

		try {
			responseBody = new String(response.body, getCharacterEncoding(response.headers));
			logResponse(status, responseBody);
			resource = parseResponseBody(responseBody);
		} catch (Exception ex) {
			// TODO Should we set some custom status code?
			status = 506; // spec only defines up to 505
			statusMsg = ex.getMessage();
		}
		return new RestMethodResult<T>(status, statusMsg, resource);
	}

	/**
	 * Returns the log tag for the class extending AbstractRestMethod
	 * 
	 * @return log tag
	 */
	protected abstract String getLogTag();

	/**
	 * Build the {@link Request}.
	 * 
	 * @return Request for this REST method
	 */
	protected abstract Request buildRequest();

	/**
	 * Determines whether the REST method requires authentication
	 * 
	 * @return <code>true</code> if authentication is required,
	 *         <code>false</code> otherwise
	 */
	protected boolean requiresAuthorization() {
		return true;
	}

	protected abstract T parseResponseBody(String responseBody) throws Exception;

	private Response doRequest(Request request) {

		RestClient client = RestfulAndroid.getRestClient();
		logRequest(request);
		return client.execute(request);
	}

	private String getCharacterEncoding(Map<String, List<String>> headers) {
		// TODO get value from headers
		return DEFAULT_ENCODING;
	}

	private void logRequest(Request request) {
		Logger.debug(getLogTag(), "Request: " + request.getMethod().toString() + " "
				+ request.getRequestUri().toASCIIString());
	}

	private void logResponse(int status, String responseBody) {
		Logger.debug(getLogTag(), "Response: status=" + status + ", body=" + responseBody);
	}

}
