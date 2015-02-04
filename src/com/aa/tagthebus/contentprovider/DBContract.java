package com.aa.tagthebus.contentprovider;

import android.provider.BaseColumns;

public final class DBContract {
	
    // To prevent someone from accidentally instantiating the   
	// contract class, give it an empty and/or private constructor.
    private DBContract() {}

    
    /* Inner class that defines the table Medias contents */
    public static abstract class BusPicture implements BaseColumns {
        public static final String TABLE_NAME = "busPicture";
        public static final String COLUMN_NAME_URI = "uri";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CREATION_DATE = "creationDate";
        public static final String COLUMN_NAME_BUS_STATION_ID = "busStationId";
    }
}
