package com.aa.tagthebus.busstation;

import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import rx.Observable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        public Observable<List<BusStation>> busStation();
    }

    public BusStationService(){
        requestInterceptor = new RequestInterceptor() {
			@Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };
        gson = new GsonBuilder()
                .registerTypeAdapter(BusStation.class, new BusStationDeserializer())
                .create();
        
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_DOMAIN)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor)
                .build();

        service = restAdapter.create(BusServiceStub.class);
    }

    public Observable<List<BusStation>> getBusStations() {
        return service.busStation();
    }


}
