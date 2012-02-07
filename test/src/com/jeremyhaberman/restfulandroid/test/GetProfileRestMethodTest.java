package com.jeremyhaberman.restfulandroid.test;

import mn.aug.restfulandroid.rest.GetProfileRestMethod;
import mn.aug.restfulandroid.rest.RestMethodResult;
import mn.aug.restfulandroid.rest.resource.Profile;
import android.test.AndroidTestCase;


public class GetProfileRestMethodTest extends AndroidTestCase {

	public void testExecute() {
		
		GetProfileRestMethod method = new GetProfileRestMethod();
		RestMethodResult<Profile> profile = method.execute();
		assertNotNull(profile);
	}

}
