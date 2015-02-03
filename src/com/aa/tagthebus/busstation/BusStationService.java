package com.aa.tagthebus.busstation;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;

import com.google.gson.Gson;

/**
 * Created by Aude on 2/2/2015.
 */
public class BusStationService {
    private BusServiceStub service;
    private final String API_DOMAIN = "http://barcelonaapi.marcpous.com/";
    RequestInterceptor requestInterceptor;
    Gson gson;

    public interface BusServiceStub {

        // get all barcelona bus stations
        @GET("/bus/nearstation/latlon/41.3985182/2.1917991/1.json")
        public void busStation(Callback<APIResponse> callback);
    }

    public BusStationService(){
        requestInterceptor = new RequestInterceptor() {
			@Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };
        
        
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_DOMAIN)
                .setRequestInterceptor(requestInterceptor)
                .build();

        service = restAdapter.create(BusServiceStub.class);
    }

    public void getBusStations(Callback<APIResponse> callback) {
        service.busStation(callback);
    }

    public class APIResponse{
    	private String code;
    	private APIData data;
    	
    	public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public APIData getData() {
			return data;
		}
		public void setData(APIData data) {
			this.data = data;
		}
    }
    public class APIData{
    	private List<BusStation> nearstations;

		public List<BusStation> getNearstations() {
			return nearstations;
		}

		public void setNearstations(List<BusStation> nearstations) {
			this.nearstations = nearstations;
		}
    	
    }
}
