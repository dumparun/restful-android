package mn.aug.restfulandroid.rest.resource;

import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CommentsTable;
import mn.aug.restfulandroid.util.JSONUtil;

import org.json.JSONObject;

import android.content.ContentValues;
import android.os.Bundle;

public class Comment implements Resource {

	private String id;
	private String author;
	private String text;
	private String catPictureId;
	private long createDate;
	
	/**
	 * Construct for a Comment from its JSON representation
	 * 
	 * @param json
	 * @throws IllegalArgumentException
	 *             - if the JSON does not contain the required keys
	 */
	public Comment(JSONObject json) {

		this.id = JSONUtil.getString(json, "id");
		this.author = JSONUtil.getString(json, "author");
		this.text = JSONUtil.getString(json, "body");
	}
	
	/**
	 * Builds a Comment from a map of input values
	 * @param values
	 */
	public Comment(Bundle values){
		this.text = values.getString(CommentsTable.COMMENT_TEXT);
		this.author = values.getString(CommentsTable.AUTHOR);
		this.catPictureId = values.getString(CommentsTable.CAT_PICTURE_ID);
	}

	public String getId() {
		return id;
	}
	
	public String getCatPictureId() {
		return catPictureId;
	}

	public String getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}
	
	public long getCreationDate(){
		return createDate;
	}
	
	

	public ContentValues toContentValues() {
		ContentValues rowData = new ContentValues();
		rowData.put(CommentsTable.REF_ID, this.id);
		rowData.put(CommentsTable.COMMENT_TEXT, this.text);
		rowData.put(CommentsTable.AUTHOR, this.author);
		rowData.put(CommentsTable.CREATED, this.createDate);
		
		return rowData;
	}
}
