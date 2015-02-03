package com.aa.tagthebus.busstation;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;

/**
 * Created by Aude on 2/2/2015.
 */
public class BusStationService {
    private BusServiceStub service;
    private final String API_DOMAIN = "http://barcelonaapi.marcpous.com/";
    private RequestInterceptor requestInterceptor;

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

    
}
