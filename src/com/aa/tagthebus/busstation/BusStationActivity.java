package com.aa.tagthebus.busstation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.aa.tagthebus.R;
import com.aa.tagthebus.buspicture.BusPictureActivity;
import com.aa.tagthebus.busstation.APIResponse.BusStation;


public class BusStationActivity extends ActionBarActivity {
	
	public final static String BUS_STATION_ID = "busStationId";
	
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
		private List<BusStation> busStationList;
		
		
		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_bus_station, container, false);

			busStationService = new BusStationService();
			busStationLV = (ListView)rootView.findViewById(R.id.busStationLV);
			busStationLV.setOnItemClickListener(new OnItemClickListener() {
				  @Override
				  public void onItemClick(AdapterView<?> parent, View view,
				    int position, long id) {
					  Intent intent = new Intent(getActivity(), BusPictureActivity.class);
					  intent.putExtra(BUS_STATION_ID, busStationList.get(position).getId());
					  startActivity(intent);
				  }
				});
			getBusStations();
			return rootView;
		}

		private void getBusStations(){
			busStationService.getBusStations(new Callback<APIResponse>() {

				@Override
				public void failure(RetrofitError retrofitError) {
					Log.d("debug", retrofitError.getMessage());
				}

				@Override
				public void success(APIResponse data, Response arg1) {
					busStationList = data.getData().getNearstations();
					if (busStationList.size() > 0) {
						Collections.sort(busStationList, new Comparator<BusStation>(){
					        @Override
							public int compare(BusStation o1, BusStation o2) {
					           return o1.getStreet_name().compareToIgnoreCase(o2.getStreet_name());
					        }
					    });
						busStationAdapter = new BusStationAdapter(getActivity().getApplicationContext(), busStationList);
						busStationLV.setAdapter(busStationAdapter);
					}
				}
			});
		}
	}
}
