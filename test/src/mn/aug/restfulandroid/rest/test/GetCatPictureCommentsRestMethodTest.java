package mn.aug.restfulandroid.rest.test;

import java.net.URI;
import java.util.List;

import mn.aug.restfulandroid.TestUtil;
import mn.aug.restfulandroid.mock.MockRestClient;
import mn.aug.restfulandroid.rest.Response;
import mn.aug.restfulandroid.rest.RestMethodResult;
import mn.aug.restfulandroid.rest.method.GetCatPictureCommentsRestMethod;
import mn.aug.restfulandroid.rest.resource.Comment;
import mn.aug.restfulandroid.rest.resource.Comments;
import mn.aug.restfulandroid.test.R;

import org.apache.http.HttpStatus;

import android.test.InstrumentationTestCase;

public class GetCatPictureCommentsRestMethodTest extends InstrumentationTestCase {

	private GetCatPictureCommentsRestMethod mGetCatPictureCommentsRestMethod;
	private MockRestClient mRestClient;

	protected void setUp() throws Exception {
		super.setUp();

		mGetCatPictureCommentsRestMethod = new GetCatPictureCommentsRestMethod(getInstrumentation().getContext(),
				"qj3qy");
		mRestClient = new MockRestClient();
		mGetCatPictureCommentsRestMethod.setRestClient(mRestClient);
	}
	
	public void testGetURI() {
		URI expectedUri = URI.create("http://www.reddit.com/r/catpictures/comments/qj3qy/.json");
		URI uri = mGetCatPictureCommentsRestMethod.getURI();
		assertTrue(uri.toASCIIString().equalsIgnoreCase(expectedUri.toASCIIString()));
	}

	public void testExecute() throws Exception {

		mRestClient.setResponse(new Response(HttpStatus.SC_OK, null, TestUtil
				.getRawResourceAsString(getInstrumentation().getContext(),
						R.raw.get_cat_picture_comments_200_response).getBytes()));

		RestMethodResult<Comments> getCatPicsRestMethodResult = mGetCatPictureCommentsRestMethod
				.execute();

		assertTrue(getCatPicsRestMethodResult.getStatusCode() == HttpStatus.SC_OK);
		Comments catPictureCommentsResource = getCatPicsRestMethodResult.getResource();
		assertNotNull(catPictureCommentsResource);
		List<Comment> catPictures = catPictureCommentsResource.getComments();
		assertTrue(catPictures.size() == 7);

		Comment comment = catPictures.get(0);
		assertTrue(comment.getId().equalsIgnoreCase("c3y1yes"));
		assertTrue(comment.getAuthor().equalsIgnoreCase("michael_keaton"));
		assertTrue(comment.getText().equalsIgnoreCase("lol windows xp"));
		
	}
}
