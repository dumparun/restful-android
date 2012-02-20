package mn.aug.restfulandroid.service;

public interface CatPicturesService {

	public static final String METHOD_EXTRA = "com.jeremyhaberman.restfulandroid.service.METHOD_EXTRA";
	public static final String METHOD_GET = "GET";
	public static final String RESOURCE_TYPE_EXTRA = "com.jeremyhaberman.restfulandroid.service.RESOURCE_TYPE_EXTRA";
	public static final int RESOURCE_TYPE_CAT_PICTURES = 1;
	public static final String SERVICE_CALLBACK = "com.jeremyhaberman.restfulandroid.service.SERVICE_CALLBACK";
	public static final String ORIGINAL_INTENT_EXTRA = "com.jeremyhaberman.restfulandroid.service.ORIGINAL_INTENT_EXTRA";
	
	CatPicturesProcessorCallback makeCatPicturesProcessorCallback();

}