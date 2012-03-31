package mn.aug.restfulandroid.processor;

import java.util.List;

import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CommentsTable;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.RESOURCE_TRANSACTION_FLAG;
import mn.aug.restfulandroid.rest.method.GetCommentsRestMethod;
import mn.aug.restfulandroid.rest.method.PostCommentRestMethod;
import mn.aug.restfulandroid.rest.method.RestMethod;
import mn.aug.restfulandroid.rest.method.RestMethodResult;
import mn.aug.restfulandroid.rest.resource.CatPictures;
import mn.aug.restfulandroid.rest.resource.Comment;
import mn.aug.restfulandroid.rest.resource.Comments;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

/**
 * The CatPictureCommentsProcessor is a POJO for processing cat pictures
 * requests. For this pattern, there is one Processor for each resource type.
 * 
 * @author Brad Armstrong
 */
public class CatPictureCommentsProcessor implements ResourceProcessor {

	protected static final String TAG = CatPictureCommentsProcessor.class.getSimpleName();

	private Context mContext;

	public CatPictureCommentsProcessor(Context context) {
		mContext = context;
	}

	@Override
	public void getResource(ResourceProcessorCallback callback, Bundle params) {

		// (1) Call the REST method
		// Create a RESTMethod class that knows how to assemble the URL,
		// and performs the HTTP operation.

		String mCatPictureId = params.getString(CommentsTable.CAT_PICTURE_ID);
		String apiCatPicId = getCatPicRefid(mCatPictureId);
		RestMethod<Comments> method = new GetCommentsRestMethod(mContext, apiCatPicId);
		RestMethodResult<Comments> result = method.execute();

		/*
		 * (2) Insert-Update the ContentProvider status, and insert the result
		 * on success Parsing the JSON response (on success) and inserting into
		 * the content provider
		 */

		addNewComments(result,mCatPictureId);

		// (3) Operation complete callback to Service

		callback.send(result.getStatusCode(), null);

	}


	@Override
	public void postResource(ResourceProcessorCallback callback, Bundle params) {

		Comment newComment = new Comment(params);

		// (1) Insert the the resource using the ContentProvider 
		// and mark status as POSTING
		Uri commentUri = addNewComment(newComment);

		// (2) Call the REST method
		// Create a RESTMethod class that knows how to assemble the URL,
		// and performs the HTTP operation.

		String catPictureRefId = getCatPictureRefId(newComment.getCatPictureId());

		RestMethod<Comment> method = new PostCommentRestMethod(mContext, catPictureRefId, newComment);
		RestMethodResult<Comment> result = method.execute();

		/*
		 * (3) Update the resource using the ContentProvider.
		 * Update the status and result
		 */
		updateNewComment(commentUri, result);

		// (3) Operation complete callback to Service
		callback.send(result.getStatusCode(), commentUri.getLastPathSegment());
	}

	private String getCatPictureRefId(String catPictureId) {

		Cursor catPicturesCursor = mContext.getContentResolver().query(
				ContentUris.withAppendedId(CatPictures.CONTENT_URI, Long.parseLong(catPictureId)),
				new String[] { CatPicturesTable.REF_ID }, null, null, null);
		if (catPicturesCursor.moveToNext()) {
			return catPicturesCursor.getString(0);
		} else {
			return "";
		}
	}

	private void addNewComments(RestMethodResult<Comments> result, String catPictureId) {

		if(result.getStatusCode() == 200){
			Comments catPictureComments = result.getResource();
			List<Comment> comments = catPictureComments.getComments();
			ContentResolver cr = this.mContext.getContentResolver();
			long existingCommentTimestamp = getMostRecentCommentTimestamp(catPictureId);

			// insert/update row for each cat picture in the list
			for (Comment comment : comments) {
				if(comment.getCreationDate() > existingCommentTimestamp){
					ContentValues values = comment.toContentValues();
					values.put(CommentsTable._STATUS, RESOURCE_TRANSACTION_FLAG.COMPLETE);
					values.put(CommentsTable._RESULT, result.getStatusCode());
					values.put(CommentsTable.CAT_PICTURE_ID, catPictureId);
					cr.insert(Comments.CONTENT_URI, values);
				}
			}
		}
	}

	private Uri addNewComment(Comment comment) {

		ContentResolver cr = this.mContext.getContentResolver();

		ContentValues insertValues = new ContentValues();
		insertValues.put(CommentsTable.COMMENT_TEXT, comment.getText());
		insertValues.put(CommentsTable.AUTHOR, "You");  //TODO where to get user??
		insertValues.put(CommentsTable.CAT_PICTURE_ID, comment.getCatPictureId());
		insertValues.put(CommentsTable._STATUS, RESOURCE_TRANSACTION_FLAG.TRANSACTING | RESOURCE_TRANSACTION_FLAG.POST);

		return cr.insert(Comments.CONTENT_URI, insertValues);

	}

	private void updateNewComment(Uri commentUri, RestMethodResult<Comment> result){
		ContentResolver cr = this.mContext.getContentResolver();

		ContentValues values = result.getResource().toContentValues();
		values.put(CommentsTable._STATUS, RESOURCE_TRANSACTION_FLAG.COMPLETE);
		values.put(CommentsTable._RESULT, result.getStatusCode());
		cr.update(commentUri, values, null, null);

	}

	/**
	 * Returns the id of the cat picture as known by the api
	 * @param mCatPictureId local database id
	 * @return remote api id
	 */
	private String getCatPicRefid(String mCatPictureId) {

		ContentResolver cr = this.mContext.getContentResolver();
		String whereClause = CatPicturesTable._ID + "=?";
		String[] whereValues = {mCatPictureId};
		Cursor cursor = cr.query(CatPictures.CONTENT_URI, new String[]{CatPicturesTable.REF_ID}, whereClause, whereValues, null);
		try{
			if(cursor.moveToFirst()){
				return cursor.getString(0);
			}
		} finally {
			cursor.close();
		}

		return null;
	}

	private long getMostRecentCommentTimestamp(String mCatPictureId) {
		ContentResolver contentResolver = mContext.getContentResolver();
		String whereClause = CommentsTable.CAT_PICTURE_ID + "=?";
		String[] whereValues = {mCatPictureId};
		Cursor cursor = contentResolver.query(Comments.CONTENT_URI,
				new String[] { CatPicturesTable.CREATED }, whereClause, whereValues, CatPicturesTable.CREATED
				+ " DESC LIMIT 1");
		if (cursor.moveToNext()) {
			return cursor.getLong(0);
		} else {
			return 0L;
		}
	}


}