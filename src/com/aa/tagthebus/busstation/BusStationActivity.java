package com.aa.tagthebus.busstation;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aa.tagthebus.R;


public class BusStationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_station);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();

		}
	}


	//    @Override
	//    public boolean onCreateOptionsMenu(Menu menu) {
	//        // Inflate the menu; this adds items to the action bar if it is present.
	//        getMenuInflater().inflate(R.menu.menu_bus_list, menu);
	//        return true;
	//    }
	//
	//    @Override
	//    public boolean onOptionsItemSelected(MenuItem item) {
	//        // Handle action bar item clicks here. The action bar will
	//        // automatically handle clicks on the Home/Up button, so long
	//        // as you specify a parent activity in AndroidManifest.xml.
	//        int id = item.getItemId();
	//
	//        //noinspection SimplifiableIfStatement
	//        if (id == R.id.action_settings) {
	//            return true;
	//        }
	//
	//        return super.onOptionsItemSelected(item);
	//    }

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private BusStationService busStationService;
		private BusStationAdapter busStationAdapter;
		private ListView busStationLV;

		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_bus_station, container, false);

			busStationService = new BusStationService();
			busStationLV = (ListView)rootView.findViewById(R.id.busStationLV);
			getBusStations();
			return rootView;
		}

		private void getBusStations(){
			busStationService.getBusStations(new Callback<BusStationService.APIResponse>() {

				@Override
				public void failure(RetrofitError retrofitError) {
					Log.d("debug", retrofitError.getMessage());
				}

				@Override
				public void success(BusStationService.APIResponse data, Response arg1) {
					if (data.getData().getNearstations().size() > 0) {
						busStationAdapter = new BusStationAdapter(getActivity().getApplicationContext(), data.getData().getNearstations());
						busStationLV.setAdapter(busStationAdapter);
					}
				}
			});
		}
	}
}
