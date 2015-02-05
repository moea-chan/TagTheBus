package com.aa.tagthebus.buspicture;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aa.tagthebus.R;
import com.aa.tagthebus.contentprovider.BusPictureProvider;
import com.aa.tagthebus.contentprovider.DBContract.BusPicture;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BusPictureSimpleAdapter extends SimpleCursorAdapter {

	Cursor cursor;
	Context context;

	public BusPictureSimpleAdapter(Context context, int layout, Cursor c, String[] from,
			int[] to, int flags) {
		super(context, layout, c, from, to, flags);

		this.cursor = c;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_bus_picture, parent, false); 

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.titleTV = (TextView)convertView.findViewById(R.id.busPictureTitleTV);
			viewHolder.pictureIV = (ImageView)convertView.findViewById(R.id.busPictureIV);
			viewHolder.creationDateTV = (TextView)convertView.findViewById(R.id.busPictureDateTV);
			viewHolder.loadingBusPicturePB = (ProgressBar)convertView.findViewById(R.id.loadingBusPicturePB);
			viewHolder.removePicture = (ImageButton)convertView.findViewById(R.id.removePictureIB);
			convertView.setTag(viewHolder);
		}

		cursor.moveToPosition(position);
		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		//set title
		String title = cursor.getString(cursor.getColumnIndex(BusPicture.COLUMN_NAME_TITLE));
		viewHolder.titleTV.setText(title);

		//set creation date
		String creationDate = cursor.getString(cursor.getColumnIndex(BusPicture.COLUMN_NAME_CREATION_DATE));

		viewHolder.creationDateTV.setText(creationDate);		

		//load picture
		Uri pictureUri = Uri.parse(cursor.getString(cursor.getColumnIndex(BusPicture.COLUMN_NAME_URI)));
		loadBusPicture(viewHolder.pictureIV, viewHolder.loadingBusPicturePB, pictureUri);

		//set remove picture listener
		onRemovePictureClickListener(viewHolder.removePicture, position);		

		return convertView;
	}

	private void onRemovePictureClickListener(final ImageButton removePicture,
			final int curPosition) {
		//delete picture from db 
		removePicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cursor.moveToPosition(curPosition);
				int currentPictureId = cursor.getInt(cursor.getColumnIndex(BusPicture._ID));
				String[] args = {""};
				Uri uri = BusPictureProvider.BUS_PICTURE_CONTENT_URI.buildUpon().appendPath(String.valueOf(currentPictureId)).build();
				context.getContentResolver().delete(uri, "", args);
			}
		});
	}

	private void loadBusPicture(ImageView pictureIV,
			final ProgressBar loadingBusPicturePB, Uri pictureUri) {
		loadingBusPicturePB.setVisibility(View.VISIBLE);
		Picasso.with(context)
		.load(pictureUri)
		.error(R.drawable.ic_action_error)
		.fit()
		.centerCrop()
		.into(pictureIV, new Callback() {
			@Override public void onSuccess() {
				loadingBusPicturePB.setVisibility(View.GONE);
			} 
			@Override
			public void onError() {
				loadingBusPicturePB.setVisibility(View.GONE);
				Toast.makeText(context, R.string.error_generic, Toast.LENGTH_LONG).show();
			} 
		});
	}

	static class ViewHolder {
		private TextView titleTV;
		private TextView creationDateTV;
		private ImageView pictureIV;
		private ProgressBar loadingBusPicturePB;
		private ImageButton removePicture;
	}
}
