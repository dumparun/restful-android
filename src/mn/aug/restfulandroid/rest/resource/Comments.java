package mn.aug.restfulandroid.rest.resource;

import java.util.ArrayList;
import java.util.List;

import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CommentsTable;

import org.json.JSONArray;
import org.json.JSONException;

import android.net.Uri;

public class Comments implements Resource {

	public static final Uri CONTENT_URI = CommentsTable.CONTENT_URI;

	List<Comment> comments;

	/**
	 * Construct CatPictures from its JSON representation
	 * 
	 * @param commentsArray
	 * @throws IllegalArgumentException
	 *             - if the JSON does not contain the required keys
	 */
	public Comments(JSONArray commentsArray) {
		int count = commentsArray.length();

		comments = new ArrayList<Comment>();

		try {
			for (int i = 0; i < count; i++) {
				comments.add(new Comment(commentsArray.getJSONObject(i).getJSONObject("data")));
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException("Error constructing Comment. "
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * Get the list of comments
	 * 
	 * @return list of comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

}
