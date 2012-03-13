package mn.aug.restfulandroid.security;

import mn.aug.restfulandroid.rest.resource.Login;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoginManager {

	private SharedPreferences mSharedPreferences;

	public LoginManager(Context context) {
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context
				.getApplicationContext());
	}

	public Login getLogin() {
		String username = mSharedPreferences.getString(Login.KEY_USERNAME, "");
		String cookie = mSharedPreferences.getString(Login.KEY_COOKIE, "");
		String modhash = mSharedPreferences.getString(Login.KEY_MODHASH, "");

		return new Login(username, cookie, modhash);
	}

	public void save(Login login) {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(Login.KEY_USERNAME, login.getUsername());
		editor.putString(Login.KEY_COOKIE, login.getCookie());
		editor.putString(Login.KEY_MODHASH, login.getModHash());
		editor.commit();
	}
}
