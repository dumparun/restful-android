package mn.aug.restfulandroid.rest.method;

import java.net.URI;

import android.content.Context;
import mn.aug.restfulandroid.rest.Request;
import mn.aug.restfulandroid.rest.resource.Comment;
import mn.aug.restfulandroid.rest.resource.Comments;

public class PostCommentRestMethod extends AbstractRestMethod<Comment> {

	private Comment comment;
	
	public PostCommentRestMethod(Context mContext, Comment newComment) {
		this.comment = newComment;
	}

	@Override
	public RestMethodResult<Comment> execute() {
		// TODO Auto-generated method stub
		return new RestMethodResult<Comment>(506, "NOT IMPLEMENTED", null);
	}

	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected URI getURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getLogTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Request buildRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Comment parseResponseBody(String responseBody) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
