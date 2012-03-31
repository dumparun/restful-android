package mn.aug.restfulandroid.activity;


import java.util.Date;

import mn.aug.restfulandroid.R;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CommentsTable;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.RESOURCE_TRANSACTION_FLAG;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CommentsCursorAdapter extends CursorAdapter {

	public CommentsCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		String comment = cursor.getString(cursor.getColumnIndexOrThrow(CommentsTable.COMMENT_TEXT));
		String author = cursor.getString(cursor.getColumnIndexOrThrow(CommentsTable.AUTHOR));
		Long commentDate = cursor.getLong(cursor.getColumnIndexOrThrow(CommentsTable.CREATED));
		int transactionStatus = cursor.getInt(cursor.getColumnIndexOrThrow(CommentsTable._STATUS));
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.commentView.setText(comment);	
		holder.authorView.setText(author);	
		
		
		
		if((transactionStatus & RESOURCE_TRANSACTION_FLAG.TRANSACTING) == RESOURCE_TRANSACTION_FLAG.TRANSACTING){
			holder.postingView.setVisibility(View.VISIBLE);
			holder.progress.setVisibility(View.VISIBLE);
		} else {
			holder.postingView.setVisibility(View.GONE);
			holder.progress.setVisibility(View.GONE);
			
			//show comment date
			if(commentDate != null){				
				CharSequence dateStr = android.text.format.DateFormat.format("MM/dd/yyyy", new Date(commentDate));
				holder.dateView.setText(dateStr);
			}
		}
		

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View listItemView =  LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);

		ViewHolder holder = new ViewHolder();
		holder.commentView = (TextView) listItemView.findViewById(R.id.comment_text);
		holder.authorView = (TextView) listItemView.findViewById(R.id.author);
		holder.dateView = (TextView) listItemView.findViewById(R.id.comment_date);
		holder.postingView = (TextView) listItemView.findViewById(R.id.posting);
		holder.progress = (ProgressBar) listItemView.findViewById(R.id.progressBar);
		
		listItemView.setTag(holder);

		return listItemView;
	}

	static class ViewHolder {
		TextView commentView;
		TextView authorView;		
		TextView dateView;		
		TextView postingView;		
		ProgressBar progress;		
	}

}
