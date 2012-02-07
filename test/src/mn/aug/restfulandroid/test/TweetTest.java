package mn.aug.restfulandroid.test;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import mn.aug.restfulandroid.R;
import mn.aug.restfulandroid.rest.resource.Tweet;

import org.json.JSONArray;
import org.json.JSONObject;

import android.test.InstrumentationTestCase;



public class TweetTest extends InstrumentationTestCase{

	JSONObject tweetData;

	@Override
    protected void setUp() throws Exception {

		if(tweetData == null){
			InputStream is = getInstrumentation().getContext().getResources().openRawResource(R.raw.timeline_response);
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}

			String json = writer.toString();
			JSONArray timeline = new JSONArray(json);
			tweetData = (JSONObject) timeline.get(0);
		}
	}
	
	
	public void testGetMessage(){
		Tweet tweet = new Tweet(tweetData);
		assertEquals("Brady time! Vamos por otro touchdown", tweet.getMessage());		
	}
	
	public void testGetAuthorName(){
		Tweet tweet = new Tweet(tweetData);
		assertEquals("ManuelAcosta10", tweet.getAuthorName());		
	}

}
