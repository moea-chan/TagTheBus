package com.aa.tagthebus.busstation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aa.tagthebus.R;
import com.aa.tagthebus.busstation.APIResponse.BusStation;
import com.aa.tagthebus.utils.NetworkUtils;


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



	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private BusStationService busStationService;
		private BusStationAdapter busStationAdapter;
		private RecyclerView busStationRV;
		private List<BusStation> busStationList;


		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_bus_station, container, false);

			busStationService = new BusStationService();

			// invisible button only displayed if no network connection found
			final Button callWebServiceB = (Button)rootView.findViewById(R.id.callWebServiceB);
			callWebServiceB.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					callWebServiceB.setVisibility(View.GONE);
					checkConnectivityToLaunchWebService(rootView, callWebServiceB);
				}
			});
			recyclerViewSetup(rootView);
			checkConnectivityToLaunchWebService(rootView, callWebServiceB);

			return rootView;
		}

		private void recyclerViewSetup(View v) {
			busStationRV = (RecyclerView)v.findViewById(R.id.busStationRV);

			LinearLayoutManager llm = new LinearLayoutManager(getActivity());
			llm.setOrientation(LinearLayoutManager.VERTICAL);
			busStationRV.setLayoutManager(llm);
			busStationAdapter = new BusStationAdapter();
			busStationRV.setAdapter(busStationAdapter);
		}

		//if not connected to network, display a button to retry after connection is started
		private void checkConnectivityToLaunchWebService(final View rootView,
				final Button callWebServiceB) {
			if(NetworkUtils.isConnected(getActivity()) && (busStationList == null || busStationList.size() == 0)){
				getBusStations(rootView);
			}
			else{
				callWebServiceB.setVisibility(View.VISIBLE);
				Toast.makeText(getActivity(), R.string.error_network, Toast.LENGTH_LONG).show();
			}
		}

		//call web service to get all bus stations
		private void getBusStations(View view){
			final ProgressBar loadingBusStationPB = (ProgressBar) view.findViewById(R.id.loadingBusStationPB);

			loadingBusStationPB.setVisibility(View.VISIBLE);
			busStationService.getBusStations(new Callback<APIResponse>() {

				@Override
				public void failure(RetrofitError retrofitError) {
					Log.d("debug", retrofitError.getMessage());
					loadingBusStationPB.setVisibility(View.GONE);
					Toast.makeText(getActivity(), R.string.error_generic, Toast.LENGTH_LONG).show();
				}

				@Override
				public void success(APIResponse data, Response arg1) {
					busStationList = data.getData().getNearstations();
					if (busStationList.size() > 0) {
						//sort results on street name
						Collections.sort(busStationList, new Comparator<BusStation>(){
							@Override
							public int compare(BusStation o1, BusStation o2) {
								return o1.getStreet_name().compareToIgnoreCase(o2.getStreet_name());
							}
						});
						BusStationAdapter.setContext(getActivity());
						busStationAdapter.setData(busStationList);
						//						busStationRV.setAdapter(busStationAdapter);



						loadingBusStationPB.setVisibility(View.GONE);
					}
				}
			});
		}
	}
}
