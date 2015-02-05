package com.aa.tagthebus.buspicture;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.aa.tagthebus.R;
import com.aa.tagthebus.contentprovider.BusPictureProvider;
import com.aa.tagthebus.contentprovider.DBContract.BusPicture;
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
		}
		TextView titleTV = (TextView)convertView.findViewById(R.id.busPictureTitleTV);
		TextView creationDateTV = (TextView)convertView.findViewById(R.id.busPictureDateTV);;
		ImageView pictureIV = (ImageView)convertView.findViewById(R.id.busPictureIV);
		
		cursor.moveToPosition(position);

		//title
		String title = cursor.getString(cursor.getColumnIndex(BusPicture.COLUMN_NAME_TITLE));
		titleTV.setText(title);

		//creation date
		String creationDate = cursor.getString(cursor.getColumnIndex(BusPicture.COLUMN_NAME_CREATION_DATE));
		creationDateTV.setText(creationDate);
		
		
		//picture
		Uri pictureUri = Uri.parse(cursor.getString(cursor.getColumnIndex(BusPicture.COLUMN_NAME_URI)));
		
//		progressBar.setVisibility(View.VISIBLE);
		
		final ImageButton removePicture = (ImageButton)convertView.findViewById(R.id.removePictureIB);
//		addFollow.setVisibility(View.GONE);
		
		final int curPosition = position;
		
		//ajoute aux amis le user courant au click sur bouton
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
		//charge l'image du user
		Picasso.with(context)
		.load(pictureUri)
		.error(R.drawable.ic_launcher)
		.fit()
		.centerCrop()
		.into(pictureIV);
//				, new EmptyCallback() {
//			@Override public void onSuccess() {
////				progressBar.setVisibility(View.GONE);
//				addFollow.setVisibility(View.VISIBLE);
//			} 
//			@Override
//			public void onError() {
////				cprogressBar.setVisibility(View.GONE);
//				addFollow.setVisibility(View.VISIBLE);
//			} 
//		});

		return convertView;
	}



}
