package com.aa.tagthebus.busstation;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bus_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private List<BusStation> busStationsList;
        private BusStationService busStationService;
        private BusStationAdapter busStationAdapter;
        private ListView busStationLV;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_bus_station, container, false);

            busStationService = new BusStationService();
            busStationsList = new ArrayList<BusStation>();
            busStationLV = (ListView)getActivity().findViewById(R.id.busStationLV);
            getBusStations();
            return rootView;
        }

        private void getBusStations(){
            Observable<List<BusStation>> busStationListObservable = busStationService.getBusStations();

            busStationListObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<BusStation>>() {
                        @Override
                        public void onCompleted() {
                            if (busStationsList.size() > 0) {
                                busStationAdapter = new BusStationAdapter(getActivity().getApplicationContext(), busStationsList);
                                busStationLV.setAdapter(busStationAdapter);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e != null) {
                                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                if (e.getMessage() != null)
                                    Log.d("debug", e.getMessage());
                            }
                        }

                        @Override
                        public void onNext(List<BusStation> arg0) {
                            for (BusStation busStation : arg0) {
                                busStationsList.add(busStation);
                            }
                        }
                    });
        }
    }
}
