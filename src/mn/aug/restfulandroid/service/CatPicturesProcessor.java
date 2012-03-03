package mn.aug.restfulandroid.service;

import java.util.List;

import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import mn.aug.restfulandroid.rest.RestMethod;
import mn.aug.restfulandroid.rest.RestMethodResult;
import mn.aug.restfulandroid.rest.method.GetCatPicturesRestMethod;
import mn.aug.restfulandroid.rest.resource.CatPicture;
import mn.aug.restfulandroid.rest.resource.CatPictures;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

/**
 * The DefaultCatPicturesProcessor is a POJO for processing cat pictures
 * requests. For this pattern, there is one Processor for each resource type.
 * 
 * @author Peter Pascale
 */
public class CatPicturesProcessor implements ResourceProcessor {

	protected static final String TAG = CatPicturesProcessor.class.getSimpleName();

	private Context mContext;

	public CatPicturesProcessor(Context context) {
		mContext = context;
	}

	@Override
	public void getResource(ResourceProcessorCallback callback) {

		/*
		 * Processor is a POJO - Processor for each resource type - Processor
		 * can handle each method on the resource that is supported. - Processor
		 * needs a callback (which is how the request gets back to the service)
		 * - Processor uses a RESTMethod - created through a
		 * RESTMethodFactory.create(parameterized) or .createGetTimeline()
		 * 
		 * First iteration had a callback that updated the content provider with
		 * the resources. But the eventual implementation simply block for the
		 * response and do the update.
		 */

		// (4) Insert-Update the ContentProvider with a status column and
		// results column
		// Look at ContentProvider example, and build a content provider
		// that tracks the necessary data.

		// (5) Call the REST method
		// Create a RESTMethod class that knows how to assemble the URL,
		// and performs the HTTP operation.

		String newestId = getNewestCatPictureId();
		RestMethod<CatPictures> method = new GetCatPicturesRestMethod(mContext, newestId);
		RestMethodResult<CatPictures> result = method.execute();

		/*
		 * (8) Insert-Update the ContentProvider status, and insert the result
		 * on success Parsing the JSON response (on success) and inserting into
		 * the content provider
		 */

		updateContentProvider(result);

		// (9) Operation complete callback to Service

		callback.send(result.getStatusCode(), null);

	}
	
	@Override
	public void postResource(ResourceProcessorCallback callback) {
		//TODO
	}

	private String getNewestCatPictureId() {
		ContentResolver contentResolver = mContext.getContentResolver();
		Cursor cursor = contentResolver.query(CatPictures.CONTENT_URI,
				new String[] { CatPicturesTable.REF_ID }, null, null, CatPicturesTable.CREATED
						+ " DESC LIMIT 1");
		if (cursor.moveToNext()) {
			return cursor.getString(0);
		} else {
			return null;
		}
	}

	private void updateContentProvider(RestMethodResult<CatPictures> result) {

		CatPictures catPictures = result.getResource();
		List<CatPicture> catPics = catPictures.getCatPictures();
		ContentResolver cr = this.mContext.getContentResolver();

		// insert/update row for each cat picture in the list
		for (CatPicture catPic : catPics) {
			cr.insert(CatPictures.CONTENT_URI, catPic.toContentValues());
		}

	}
}