package mn.aug.restfulandroid.processor;
import android.os.Bundle;

public interface ResourceProcessor {

	void getResource(ResourceProcessorCallback callback, Bundle params);
	void postResource(ResourceProcessorCallback callback, Bundle params);

}