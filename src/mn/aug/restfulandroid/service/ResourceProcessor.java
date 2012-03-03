package mn.aug.restfulandroid.service;

public interface ResourceProcessor {

	void getResource(ResourceProcessorCallback callback);
	void postResource(ResourceProcessorCallback callback);

}