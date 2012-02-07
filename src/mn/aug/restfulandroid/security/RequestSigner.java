package mn.aug.restfulandroid.security;

import mn.aug.restfulandroid.rest.Request;

/**
 * Interface for an OAuth request signer
 * 
 * @author jeremy
 * 
 */
public interface RequestSigner {
	
	/**
	 * Adds the required OAuth information to a Request
	 * @param conn
	 */
	public void authorize(Request request);

}
