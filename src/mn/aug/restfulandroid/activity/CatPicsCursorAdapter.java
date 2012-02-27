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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CatPicsCursorAdapter extends CursorAdapter {

	private static String DEFAULT_THUMBNAIL_URL = "default";
	private static String SELF_THUMBNAIL_URL = "self";

	private RestfulAndroid app;

	public CatPicsCursorAdapter(Context context, Cursor c) {
		super(context, c);
		app = (RestfulAndroid)context.getApplicationContext();
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		String title = cursor.getString(cursor.getColumnIndex(CatPicturesTable.TITLE));
		String author = cursor.getString(cursor.getColumnIndex(CatPicturesTable.AUTHOR));
		String thumbnailUrl = cursor.getString(cursor.getColumnIndex(CatPicturesTable.THUMBNAIL));

		Log.d("Adapter","Row[" + title + ":" + author + ":" + thumbnailUrl +"]");
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.titleView.setText(title);	
		holder.authorView.setText(author);	

		// not all entries have valie thumbnail urls
		if(thumbnailUrl != null 
				&& !DEFAULT_THUMBNAIL_URL.equals(thumbnailUrl)
				&& !SELF_THUMBNAIL_URL.equals(thumbnailUrl)){
			File thumbnail = getLocalThumbnailFile(thumbnailUrl);
			holder.thumbView.setImageURI(Uri.parse(thumbnail.getPath()));
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View listItemView =  LayoutInflater.from(context).inflate(R.layout.cat_pictures_list_item, parent, false);

		ViewHolder holder = new ViewHolder();
		holder.titleView = (TextView) listItemView.findViewById(R.id.title);
		holder.authorView = (TextView) listItemView.findViewById(R.id.author);
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
		TextView authorView;		
		ImageView thumbView;
	}

	class ThumbnailDownloadTask extends AsyncTask<Uri, Void, Boolean>{

		private File thumbDir;

		public ThumbnailDownloadTask(File thumbnailDirectory){
			this.thumbDir = thumbnailDirectory;
		}

		@Override
		protected void onPostExecute(Boolean loaded) {
			if(loaded){
				CatPicsCursorAdapter.this.notifyDataSetChanged();
			}
		}



		@Override
		protected Boolean doInBackground(Uri... params) {

			HttpURLConnection urlConnection = null;
			OutputStream os = null;
			InputStream in = null;
			boolean loaded = false;

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

				loaded = true;

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}finally {
				try {
					if(in != null){
						in.close();
					}
					if(os != null){
						os.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return loaded;
		}
	}
}
