package com.aa.tagthebus.busstation;


import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Created by Aude on 2/2/2015.
 */
public class BusStationDeserializer implements JsonDeserializer<BusStation> {
    @Override
    public BusStation deserialize(JsonElement arg0, Type arg1,
                            JsonDeserializationContext arg2) throws JsonParseException {
        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer

        JsonElement nestedElement = arg0.getAsJsonObject().get("data");
        if(nestedElement == null){
            final BusStation busStation = new BusStation();


            final JsonObject jsonObject = arg0.getAsJsonObject();
            /*if(!jsonObject.get("avatar").isJsonNull())
                busStation.setAvatar(jsonObject.get("avatar").getAsString());
            if(!jsonObject.get("username").isJsonNull())
                busStation.setUsername(jsonObject.get("username").getAsString());
            if(!jsonObject.get("storyCount").isJsonNull())
                busStation.setStoryCount(jsonObject.get("storyCount").getAsInt());
            if(!jsonObject.get("bio").isJsonNull())
                busStation.setBio(jsonObject.get("bio").getAsString());
            if(!jsonObject.get("birthdate").isJsonNull())
                busStation.setBirthdate(jsonObject.get("birthdate").getAsString());

            if(!jsonObject.get("following").isJsonNull())
                busStation.setFollowing(jsonObject.get("following").getAsInt());
            if(!jsonObject.get("followers").isJsonNull())
                busStation.setFollowers(jsonObject.get("followers").getAsInt());
            if(!jsonObject.get("verified").isJsonNull())
                busStation.setVerified(jsonObject.get("verified").getAsInt());
            */
            busStation.setId(jsonObject.get("id").getAsString());
            return busStation;
        }
        else
            return new Gson().fromJson(nestedElement, BusStation.class);
    }

	
}
