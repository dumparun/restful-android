package com.jeremyhaberman.restfulandroid.test;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import junit.framework.Assert;
import mn.aug.restfulandroid.R;
import mn.aug.restfulandroid.rest.resource.Tweet;
import mn.aug.restfulandroid.rest.resource.TwitterTimeline;

import org.json.JSONArray;
import org.json.JSONTokener;

import android.test.InstrumentationTestCase;



public class TwitterTimelineTest extends InstrumentationTestCase{

	JSONArray timelineData;

	@Override
    protected void setUp() throws Exception {

		if(timelineData == null){
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
			
			JSONTokener tokener = new JSONTokener(json);
			timelineData = (JSONArray) tokener.nextValue();
		}
	}
	
	public void testGetTweetsFromTimeline(){
		TwitterTimeline timeline = new TwitterTimeline(timelineData);
		List<Tweet> tweets = timeline.getTweets();
		Assert.assertNotNull(tweets);
		Assert.assertTrue(tweets.size() == 20);
		
	}

}
