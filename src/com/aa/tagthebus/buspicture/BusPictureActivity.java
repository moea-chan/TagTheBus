package com.aa.tagthebus.buspicture;

import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.aa.tagthebus.R;
import com.aa.tagthebus.busstation.BusStationActivity;
import com.aa.tagthebus.contentprovider.BusPictureProvider;
import com.aa.tagthebus.contentprovider.DBContract.BusPicture;
import com.aa.tagthebus.utils.ActivityLauncherUtils;
import com.aa.tagthebus.utils.ImageUtils;

public class BusPictureActivity extends ActionBarActivity{
	
	public final static String PICTURE_URI = "pictureUri";
	
	private Uri pictureUri;
	private String busStationId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_picture);
		
		Intent intent = getIntent();
		busStationId = intent.getStringExtra(BusStationActivity.BUS_STATION_ID);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_bus_picture, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_take_picture){
			startPhotoActivity();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void startPhotoActivity() {
		// create Intent to take a picture and return control to the calling application
		Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// create a file to save the image
		pictureUri = ImageUtils.getOutputMediaFileUri(ImageUtils.MEDIA_TYPE_IMAGE); 
		// set the image filename
		intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri); 

		// start the image capture Intent
		startActivityForResult(intentPhoto, ImageUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == ImageUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
				launchNamePictureActivity();
			} 
		}
		else if (resultCode == RESULT_CANCELED) {
			// User cancelled the image capture
		} 

		else {
			Toast.makeText(getApplicationContext(), R.string.error_generic, Toast.LENGTH_LONG).show();
		}
	}

	// Image captured and saved to fileUri specified in the Intent
	public void launchNamePictureActivity() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(PICTURE_URI, pictureUri.toString());
		params.put(BusStationActivity.BUS_STATION_ID, busStationId);
		ActivityLauncherUtils.launchActivity(this, ActivityLauncherUtils.PICTURE_VALIDATION, params);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements LoaderCallbacks<Cursor> {
		private BusPictureSimpleAdapter adapter;
		private ListView busPictureList;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_bus_picture,
					container, false);
			
			
			busPictureList = (ListView)rootView.findViewById(R.id.busPictureLV);
			getLoaderManager().initLoader(0, null, this);
			return rootView;
		}

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			String[] projection = {
					BaseColumns._ID,
					BusPicture.COLUMN_NAME_TITLE,
					BusPicture.COLUMN_NAME_CREATION_DATE,
					BusPicture.COLUMN_NAME_URI
			};
			Loader<Cursor> cursorLoader = null;
			switch (id) {
			case 0:
				String selection = BusPicture.COLUMN_NAME_BUS_STATION_ID + "=?";
			    String[] selectionArgs = {((BusPictureActivity) getActivity()).busStationId};

				return new CursorLoader(getActivity().getApplicationContext(),
						BusPictureProvider.BUS_PICTURE_CONTENT_URI, projection, selection, selectionArgs, null);
			default:
				break;
			}
			return cursorLoader;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			String[] fromColumns = new String[] { 
					BusPicture.COLUMN_NAME_TITLE, 
					BusPicture.COLUMN_NAME_CREATION_DATE,
					BusPicture.COLUMN_NAME_URI
			};
			int[] toControlIds = new int[] { 
					R.id.busPictureTitleTV, 
					R.id.busPictureDateTV,
					R.id.busPictureIV
			};

			adapter = new BusPictureSimpleAdapter(
					getActivity().getApplicationContext(), 
					R.layout.item_bus_picture, 
					data, 
					fromColumns, 
					toControlIds, 0);
			busPictureList.setAdapter(adapter);
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {}
	}
}
