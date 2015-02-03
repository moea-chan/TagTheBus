package com.aa.tagthebus.busstation;

import java.util.List;

import com.aa.tagthebus.R;
import com.aa.tagthebus.busstation.APIResponse.BusStation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Aude on 2/2/2015.
 */
public class BusStationAdapter extends BaseAdapter{
    private Context context;
    private List<BusStation> data;

    public BusStationAdapter(Context context, List<BusStation> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public BusStation getItem(int arg0) {
        return data.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_bus_station, parent, false);
        }
        final BusStation busStation = getItem(position);
        if(busStation != null){
            final TextView busStationNameTV = (TextView)convertView.findViewById(R.id.busStationNameTV);
            busStationNameTV.setText(busStation.getStreet_name());
        }

        return convertView;
    }
}
