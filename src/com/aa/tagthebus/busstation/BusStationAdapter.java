package com.aa.tagthebus.busstation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aa.tagthebus.R;
import com.aa.tagthebus.busstation.APIResponse.BusStation;
import com.aa.tagthebus.utils.ActivityLauncherUtils;

/**
 * Created by Aude on 2/2/2015.
 */
public class BusStationAdapter extends RecyclerView.Adapter<BusStationAdapter.BusStationViewHolder>{
	private List<BusStation> data;
	private static Context context;

	public List<BusStation> getData() {
		return data;
	}
	public void setData(List<BusStation> data) {
		this.data = data;
		notifyDataSetChanged();
	}
	public static Context getContext() {
		return context;
	}
	public static void setContext(Context context) {
		BusStationAdapter.context = context;
	}



	public BusStationAdapter(){
		data = new ArrayList<APIResponse.BusStation>();
	}
	public BusStationAdapter(Context context, List<BusStation> data){
		this.data = data;
		BusStationAdapter.context = context;
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	@Override
	public void onBindViewHolder(BusStationViewHolder busStationViewHolder, int i) {
		BusStation busStation = data.get(i);
		busStationViewHolder.busStationNameTV.setText(busStation.getStreet_name());
		busStationViewHolder.busStationId = busStation.getId();
	}

	@Override
	public BusStationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View itemView = LayoutInflater.
				from(viewGroup.getContext()).
				inflate(R.layout.item_bus_station, viewGroup, false);

		return new BusStationViewHolder(itemView);
	}

	public static class BusStationViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
		protected TextView busStationNameTV;
		protected String busStationId;

		public BusStationViewHolder(View v) {
			super(v);
			busStationNameTV =  (TextView) v.findViewById(R.id.busStationNameTV);
			v.setOnClickListener(this);
		}

		@Override
		public void onClick(View arg0) {
			Map<String, String> params = new HashMap<String, String>();
			params.put(BusStationActivity.BUS_STATION_ID, busStationId);
			ActivityLauncherUtils.launchActivity((Activity) context, ActivityLauncherUtils.BUS_PICTURE, params);

		}
	}

}
