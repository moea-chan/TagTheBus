package com.aa.tagthebus.buspicture;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aa.tagthebus.R;
import com.aa.tagthebus.busstation.BusStationActivity;

import contentprovider.BusPictureProvider;
import contentprovider.DBContract.BusPicture;

public class BusPictureActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_picture);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus_picture, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
			Intent intent = getActivity().getIntent();
			String message = intent.getStringExtra(BusStationActivity.BUS_STATION_ID);
			TextView test = (TextView)rootView.findViewById(R.id.textView1);
			test.setText(message);
			busPictureList = (ListView)rootView.findViewById(R.id.busPictureLV);
			return rootView;
		}
		
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			String[] projection = {
				    BaseColumns._ID,
				    BusPicture.COLUMN_NAME_TITLE,
				    BusPicture.COLUMN_NAME_CREATION_DATE
				    };
			Loader<Cursor> cursorLoader = null;
			switch (id) {
			case 0:
				return new CursorLoader(getActivity().getApplicationContext(),
						   BusPictureProvider.BUS_PICTURE_CONTENT_URI, projection, null, null, null);
			default:
				break;
			}
			return cursorLoader;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			String[] fromColumns = new String[] { 
					BusPicture.COLUMN_NAME_TITLE, 
					BusPicture.COLUMN_NAME_CREATION_DATE 
					};
			int[] toControlIds = new int[] { 
					R.id.busPictureTitleTV, 
					R.id.busPictureDateTV 
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
