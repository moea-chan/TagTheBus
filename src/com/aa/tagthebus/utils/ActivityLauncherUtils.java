package com.aa.tagthebus.utils;



import java.util.Map;

import com.aa.tagthebus.buspicture.BusPictureActivity;
import com.aa.tagthebus.buspicture.BusPictureValidationActivity;
import com.aa.tagthebus.busstation.BusStationActivity;

import android.app.Activity;
import android.content.Intent;

public class ActivityLauncherUtils {
	public static int BUS_PICTURE = 0;
	public static int BUS_STATION = 1;
	public static int PICTURE_VALIDATION = 2;

	public static void launchActivity(Activity activitySrc, int activityTarget, Map<String, String>params){
		Intent intent;
		switch (activityTarget) {
		case 0:
			intent = new Intent(activitySrc, BusPictureActivity.class);
			break;
		case 1:
			intent = new Intent(activitySrc, BusStationActivity.class);
			break;
		case 2:
			intent = new Intent(activitySrc, BusPictureValidationActivity.class);
			break;

		default:
			intent = new Intent(activitySrc, BusStationActivity.class);
			break;
		}

		if(params != null){
			for(Map.Entry<String, String> param : params.entrySet()){
				intent.putExtra(param.getKey(), param.getValue());
			}
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		activitySrc.startActivity(intent);
	}
}
