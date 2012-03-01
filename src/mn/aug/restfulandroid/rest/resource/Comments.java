package mn.aug.restfulandroid.rest.resource;

import java.util.List;

import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CommentsTable;

import org.json.JSONArray;

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

//		catPictures = new ArrayList<CatPicture>();
//
//		try {
//			for (int i = 0; i < count; i++) {
//				catPictures.add(new CatPicture(commentsArray.getJSONObject(i).getJSONObject(
//						"data")));
//			}
//		} catch (JSONException e) {
//			throw new IllegalArgumentException("Error constructing CatPictures. "
//					+ e.getLocalizedMessage());
//		}
	}

	/**
	 * Get the list of cat pictures
	 * 
	 * @return list of cat pictures
	 */
	public List<Comment> getComments() {
		return comments;
	}

}
