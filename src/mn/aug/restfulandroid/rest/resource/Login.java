package mn.aug.restfulandroid.rest.resource;

public class Login implements Resource {

	private String mUsername;
	private String mPassword;
	private String mCookie;

	public Login(String username, String password) {
		mUsername = username;
		mPassword = password;
	}
	
	public String getUsername() {
		return mUsername;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setCookie(String cookie) {
		mCookie = cookie;
	}
	
	public String getCookie() {
		return mCookie;
	}

}
