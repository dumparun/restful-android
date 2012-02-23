package mn.aug.restfulandroid.activity;


import mn.aug.restfulandroid.R;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CatPicsCursorAdapter extends CursorAdapter {

	
	
	public CatPicsCursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String title = cursor.getString(cursor.getColumnIndex(CatPicturesTable.TITLE));
		String thumbnail = cursor.getString(cursor.getColumnIndex(CatPicturesTable.THUMBNAIL));
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.titleView.setText(title);		

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View listItemView =  LayoutInflater.from(context).inflate(R.layout.cat_pictures_list_item, parent, false);
		
		ViewHolder holder = new ViewHolder();
		holder.titleView = (TextView) listItemView.findViewById(R.id.title);
		holder.thumbView = (ImageView) listItemView.findViewById(R.id.thumbnail);
		
		listItemView.setTag(holder);
		
		return listItemView;
	}
	
	static class ViewHolder {
		TextView titleView;
		ImageView thumbView;
	}

}
