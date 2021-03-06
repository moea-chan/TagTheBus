package com.aa.tagthebus.buspicture;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Fragment;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aa.tagthebus.R;
import com.aa.tagthebus.busstation.BusStationActivity;
import com.aa.tagthebus.contentprovider.BusPictureProvider;
import com.aa.tagthebus.contentprovider.DBContract.BusPicture;
import com.aa.tagthebus.utils.ActivityLauncherUtils;
import com.squareup.picasso.Picasso;

public class BusPictureValidationActivity extends ActionBarActivity {
	private Uri pictureUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_picture_validation);

		pictureUri = Uri.parse(getIntent().getStringExtra(BusPictureActivity.PICTURE_URI));

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_bus_picture_validation, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_picture_validate) {
			String pictureName = ((EditText)findViewById(R.id.pictureNameET)).getText().toString();
			if (pictureName != null && pictureName.length() > 0)
				savePictureinDB(pictureName);
			else
				Toast.makeText(getApplicationContext(), R.string.error_picture_name_missing, Toast.LENGTH_LONG).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void savePictureinDB(String pictureName){
		// Create a new map of values
		ContentValues values = new ContentValues();
		values.put(BusPicture.COLUMN_NAME_URI, pictureUri.toString());
		values.put(BusPicture.COLUMN_NAME_BUS_STATION_ID, getIntent().getStringExtra(BusStationActivity.BUS_STATION_ID));

		DateFormat df = new SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault());
		String date = df.format(Calendar.getInstance().getTime());
		values.put(BusPicture.COLUMN_NAME_CREATION_DATE, date);
		values.put(BusPicture.COLUMN_NAME_TITLE, pictureName);

		getContentResolver().insert(BusPictureProvider.BUS_PICTURE_CONTENT_URI, values);
		ActivityLauncherUtils.launchActivity(this, ActivityLauncherUtils.BUS_PICTURE, null);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_bus_picture_validation, container, false);

			setBackgroundPicture(rootView);
			return rootView;
		}

		private void setBackgroundPicture(View rootView) {
			if(((BusPictureValidationActivity) this.getActivity()).pictureUri != null){
				ImageView busPictureIV = (ImageView)rootView.findViewById(R.id.busPictureIV);
				Picasso.with(getActivity().getApplicationContext())
				.load(((BusPictureValidationActivity) this.getActivity()).pictureUri)
				.fit()
				.centerCrop()
				.into(busPictureIV);
			}
		}
	}
}
