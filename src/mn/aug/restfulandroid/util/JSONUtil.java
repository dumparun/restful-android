package mn.aug.restfulandroid.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

	private static final String TAG = JSONUtil.class.getSimpleName();

	/**
	 * Get the String associated with the key in a JSONObject
	 * 
	 * @param json
	 *            the JSONObject from which to extract the String
	 * @param key
	 *            the key
	 * 
	 * @throws IllegalArgumentException
	 *             if the key is not found
	 * @return
	 */
	public static String getString(JSONObject json, String key) {
		try {
			if (json.has(key)) {
				return json.getString(key);
			} else {
				throw new IllegalArgumentException(key + " is null");
			}
		} catch (JSONException e) {
			Logger.error(TAG, e.getLocalizedMessage(), e);
			return null;
		}

	}

	/**
	 * Get the String associated with the key in a JSONObject
	 * 
	 * @param json
	 *            the JSONObject from which to extract the String
	 * @param key
	 *            the key
	 * 
	 * @throws IllegalArgumentException
	 *             if the key is not found
	 * @return
	 */
	public static long getLong(JSONObject json, String key) {
		try {
			if (json.has(key)) {
				return json.getLong(key);
			} else {
				throw new IllegalArgumentException(key + " is null");
			}
		} catch (JSONException e) {
			Logger.error(TAG, e.getLocalizedMessage(), e);
			// TODO this doesn't seem right; 0 is a valid UNIX timestamp
			return 0;
		}
	}
}
