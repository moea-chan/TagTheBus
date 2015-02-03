package com.aa.tagthebus.buspicture;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.aa.tagthebus.R;

import contentprovider.DBContract.BusPicture;

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

		cursor.moveToPosition(position);

		//pseudo
		String title = cursor.getString(cursor.getColumnIndex(BusPicture.COLUMN_NAME_TITLE));
		titleTV.setText(title);

		//image
		String creationDate = cursor.getString(cursor.getColumnIndex(BusPicture.COLUMN_NAME_CREATION_DATE));
		creationDateTV.setText(creationDate);
//		progressBar.setVisibility(View.VISIBLE);
		
//		final Button addFollow = ButterKnife.findById(convertView, R.id.add_follow);
//		addFollow.setVisibility(View.GONE);
		
		final int curPosition = position;
		
		//ajoute aux amis le user courant au click sur bouton
//		addFollow.setOnClickListener(new View.OnClickListener() {
//            @Override
//			public void onClick(View v) {
//        		cursor.moveToPosition(curPosition);
//        		int currentUserId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
//        		
//        		ContentValues newVvalues = new ContentValues();
//        		newVvalues.put(Users.COLUMN_NAME_FOLLOWED, 1);
//        		context.getContentResolver().update(
//        				ContentUris.withAppendedId(GeolocProvider.FOLLOWERS_CONTENT_URI, currentUserId), newVvalues, null, null);
//            }
//        });
		//charge l'image du user
//		Picasso.with(context)
//		.load(avatarPicUrl)
//		.error(R.drawable.ic_launcher)
//		.resize(120, 120)
//		.centerCrop()
//		.into(userImageView, new EmptyCallback() {
//			@Override public void onSuccess() {
//				progressBar.setVisibility(View.GONE);
//				addFollow.setVisibility(View.VISIBLE);
//			} 
//			@Override
//			public void onError() {
//				progressBar.setVisibility(View.GONE);
//				addFollow.setVisibility(View.VISIBLE);
//			} 
//		});

		return convertView;
	}



}
