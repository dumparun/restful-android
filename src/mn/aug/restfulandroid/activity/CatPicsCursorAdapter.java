package mn.aug.restfulandroid.activity;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import mn.aug.restfulandroid.R;
import mn.aug.restfulandroid.RestfulAndroid;
import mn.aug.restfulandroid.provider.CatPicturesProviderContract.CatPicturesTable;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CatPicsCursorAdapter extends CursorAdapter {

	private RestfulAndroid app;

	public CatPicsCursorAdapter(Context context, Cursor c) {
		super(context, c);
		app = (RestfulAndroid)context.getApplicationContext();
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		String title = cursor.getString(cursor.getColumnIndex(CatPicturesTable.TITLE));
		String thumbnailUrl = cursor.getString(cursor.getColumnIndex(CatPicturesTable.THUMBNAIL));
		File thumbnail = getLocalThumbnailFile(thumbnailUrl);

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.titleView.setText(title);	
		holder.thumbView.setImageURI(Uri.parse(thumbnail.getPath()));

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

	private File getLocalThumbnailFile(String thumbUrl){

		Uri thumbnailUri = Uri.parse(thumbUrl);
		String filename = thumbnailUri.getLastPathSegment();
		File thumb = new File(app.getThumbnailsDir(), filename);
		if(!thumb.exists()){
			// download the file in the background
			new ThumbnailDownloadTask(app.getThumbnailsDir()).execute(thumbnailUri);
		}

		return thumb;

	}


	static class ViewHolder {
		TextView titleView;
		ImageView thumbView;
	}

	class ThumbnailDownloadTask extends AsyncTask<Uri, Void, Void>{

		private File thumbDir;

		public ThumbnailDownloadTask(File thumbnailDirectory){
			this.thumbDir = thumbnailDirectory;
		}

		@Override
		protected void onPostExecute(Void result) {
			CatPicsCursorAdapter.this.notifyDataSetChanged();
		}



		@Override
		protected Void doInBackground(Uri... params) {
			
			HttpURLConnection urlConnection = null;
			OutputStream os = null;
			InputStream in = null;
			
			try {
				if(!this.thumbDir.exists()){
					this.thumbDir.mkdirs();
				}
				
				URL thumbUrl = new URL(params[0].toString());
				String filename = params[0].getLastPathSegment();

				urlConnection = (HttpURLConnection) thumbUrl.openConnection();
				in = new BufferedInputStream(urlConnection.getInputStream());

				File thumbfile = new File(this.thumbDir, filename);
				os = new BufferedOutputStream(new FileOutputStream(thumbfile));

				byte[] b = new byte[2048];
				int count;
				
				while ((count = in.read(b)) > 0) {
					os.write(b, 0, count);
				}
			

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}finally {
			try {
				in.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
}
