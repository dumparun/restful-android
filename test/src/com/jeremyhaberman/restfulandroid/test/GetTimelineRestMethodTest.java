package com.jeremyhaberman.restfulandroid.test;

import mn.aug.restfulandroid.rest.GetTimelineRestMethod;
import mn.aug.restfulandroid.rest.RestMethodResult;
import mn.aug.restfulandroid.rest.resource.TwitterTimeline;

import android.test.AndroidTestCase;

public class GetTimelineRestMethodTest extends AndroidTestCase {

	public void testExecute() {
		
		GetTimelineRestMethod method = new GetTimelineRestMethod(null);
		RestMethodResult<TwitterTimeline> timeline = method.execute();
		assertNotNull(timeline);
	}

}
