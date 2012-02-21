package mn.aug.restfulandroid.rest.resource;

import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import mn.aug.restfulandroid.util.JSONUtil;

import org.json.JSONObject;

import android.content.ContentValues;

public class CatPicture implements Resource {

	private String id;
	private String title;
	private String url;
	private String author;
	private String thumbnail;

	/**
	 * Construct for a CatPicture from its JSON representation
	 * 
	 * @param json
	 * @throws IllegalArgumentException
	 *             - if the JSON does not contain the required keys
	 */
	public CatPicture(JSONObject json) {

		this.id = JSONUtil.getString(json, "id");
		this.title = JSONUtil.getString(json, "title");
		this.url = JSONUtil.getString(json, "url");
		this.author = JSONUtil.getString(json, "author");
		this.thumbnail = JSONUtil.getString(json, "thumbnail");
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getAuthor() {
		return author;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public ContentValues toContentValues() {
		ContentValues rowData = new ContentValues();
		rowData.put(CatPicturesTable._ID, this.id);
		rowData.put(CatPicturesTable.TITLE, this.title);
		rowData.put(CatPicturesTable.URL, this.url);
		rowData.put(CatPicturesTable.AUTHOR, this.author);
		
		return rowData;
	}
}
