package mn.aug.restfulandroid.rest.test;

import java.util.List;

import mn.aug.restfulandroid.TestUtil;
import mn.aug.restfulandroid.mock.MockRestClient;
import mn.aug.restfulandroid.rest.Response;
import mn.aug.restfulandroid.rest.RestMethodResult;
import mn.aug.restfulandroid.rest.method.GetCatPicturesRestMethod;
import mn.aug.restfulandroid.rest.resource.CatPicture;
import mn.aug.restfulandroid.rest.resource.CatPictures;
import mn.aug.restfulandroid.test.R;

import org.apache.http.HttpStatus;

import android.test.InstrumentationTestCase;

public class GetCatPicturesRestMethodTest extends InstrumentationTestCase {

	private GetCatPicturesRestMethod mGetCatPicturesRestMethod;
	private MockRestClient mRestClient;

	protected void setUp() throws Exception {
		super.setUp();

		mGetCatPicturesRestMethod = new GetCatPicturesRestMethod(getInstrumentation().getContext(),
				null);
		mRestClient = new MockRestClient();
		mGetCatPicturesRestMethod.setRestClient(mRestClient);
	}

	public void testExecute() throws Exception {

		mRestClient.setResponse(new Response(HttpStatus.SC_OK, null, TestUtil
				.getRawResourceAsString(getInstrumentation().getContext(),
						R.raw.cat_pictures_response).getBytes()));

		RestMethodResult<CatPictures> getCatPicsRestMethodResult = mGetCatPicturesRestMethod
				.execute();

		assertTrue(getCatPicsRestMethodResult.getStatusCode() == HttpStatus.SC_OK);
		CatPictures catPicturesResource = getCatPicsRestMethodResult.getResource();
		assertNotNull(catPicturesResource);
		List<CatPicture> catPictures = catPicturesResource.getCatPictures();
		assertTrue(catPictures.size() == 25);

		CatPicture catPic = catPictures.get(0);
		assertTrue(catPic.getId().equalsIgnoreCase("pv9a8"));
		assertTrue(catPic.getTitle().equalsIgnoreCase(
				"I can't believe r/aww did not appreciate this little redhead."));
		assertTrue(catPic.getUrl().equalsIgnoreCase(
				"http://softkittenwarmkitten.files.wordpress.com/2012/01/dsc01456.jpg"));
		assertTrue(catPic.getAuthor().equalsIgnoreCase("Toobaditsme"));
//		assertTrue(catPic.getThumbnail().equalsIgnoreCase(
//				"http://f.thumbs.redditmedia.com/X8--2Vu534F0uZW5.jpg"));
	}
}
