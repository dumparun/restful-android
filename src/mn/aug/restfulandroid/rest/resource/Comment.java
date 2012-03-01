package mn.aug.restfulandroid.rest.resource;

import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import mn.aug.restfulandroid.util.JSONUtil;

import org.json.JSONObject;

import android.content.ContentValues;

public class Comment implements Resource {

	private String id;
	private String author;
	private String text;
	
	/**
	 * Construct for a Comment from its JSON representation
	 * 
	 * @param json
	 * @throws IllegalArgumentException
	 *             - if the JSON does not contain the required keys
	 */
	public Comment(JSONObject json) {

	}

	public String getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}
	
	

	public ContentValues toContentValues() {
		ContentValues rowData = new ContentValues();
		
		return rowData;
	}
}
