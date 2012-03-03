package mn.aug.restfulandroid.activity;


import mn.aug.restfulandroid.R;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CommentsTable;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CommentsCursorAdapter extends CursorAdapter {

	public CommentsCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		String comment = cursor.getString(cursor.getColumnIndex(CommentsTable.COMMENT_TEXT));
		String author = cursor.getString(cursor.getColumnIndex(CommentsTable.AUTHOR));
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.commentView.setText(comment);	
		holder.authorView.setText(author);	

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View listItemView =  LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);

		ViewHolder holder = new ViewHolder();
		holder.commentView = (TextView) listItemView.findViewById(R.id.comment_text);
		holder.authorView = (TextView) listItemView.findViewById(R.id.author);
		
		listItemView.setTag(holder);

		return listItemView;
	}

	static class ViewHolder {
		TextView commentView;
		TextView authorView;		
	}

}
