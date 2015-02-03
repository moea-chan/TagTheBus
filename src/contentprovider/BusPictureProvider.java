package contentprovider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import contentprovider.DBContract.BusPicture;

public class BusPictureProvider extends ContentProvider {

	static final String PROVIDER_NAME = "com.aa.tagthebus";
	static final String URL = "content://" + PROVIDER_NAME;

	public static final Uri BUS_PICTURE_CONTENT_URI = Uri.parse(URL + "/" + BusPicture.TABLE_NAME);


	static final String _ID = "_id";

	private static HashMap<String, String> BUS_PICTURE_PROJECTION_MAP;

	static final int BUS_PICTURE = 1;

	static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, BusPicture.TABLE_NAME, BUS_PICTURE);
	}

	/**
	 * Database specific constant declarations
	 */
	private SQLiteDatabase db;
	static final String DATABASE_NAME = "TagTheBus";
	static final int DATABASE_VERSION = 1;



	private static final String SQL_DELETE_BUS_PICTURE = "DROP TABLE IF EXISTS " + BusPicture.TABLE_NAME + ";";
	private static final String SQL_CREATE_BUS_PICTURE =
			"CREATE TABLE " + BusPicture.TABLE_NAME + " (" +
					BusPicture._ID + " INTEGER PRIMARY KEY," +
					BusPicture.COLUMN_NAME_BUS_STATION_ID + " INTEGER," +
					BusPicture.COLUMN_NAME_CREATION_DATE + " TEXT," +
					BusPicture.COLUMN_NAME_TITLE + " TEXT," +
					BusPicture.COLUMN_NAME_URI + " TEXT" +
					" );";

	/**
	 * Helper class that actually creates and manages the provider's underlying
	 * data repository.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_BUS_PICTURE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(SQL_DELETE_BUS_PICTURE);
			onCreate(db);
		}

		@Override
		public void onDowngrade(SQLiteDatabase db, int oldVersion,
				int newVersion) {
			onUpgrade(db, oldVersion, newVersion);
		}
	}

	@Override
	synchronized public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		/**
		 * Create a writable database which will trigger its creation if it
		 * doesn't already exist.
		 */
		db = dbHelper.getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	synchronized public Uri insert(Uri uri, ContentValues values) {
		/**
		 * Add a new record
		 */
		long rowID;

		switch (uriMatcher.match(uri)) {
		case BUS_PICTURE:
			rowID = db.insert(BusPicture.TABLE_NAME, null, values);
			if (rowID > 0){
				Uri _uri = ContentUris.withAppendedId(BUS_PICTURE_CONTENT_URI, rowID);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
			throw new SQLException("Failed to add a record into " + uri);
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}


	}

	@Override
	synchronized public Cursor query(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		Cursor c = null;

		switch (uriMatcher.match(uri)) {

		case BUS_PICTURE:
			qb.setTables(BusPicture.TABLE_NAME);
			qb.setProjectionMap(BUS_PICTURE_PROJECTION_MAP);
			c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder, null);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		/**
		 * register to watch a content URI for changes
		 */
		if (c != null) {
			c.setNotificationUri(getContext().getContentResolver(), uri);
		}

		return c;
	}

	@Override
	synchronized public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;

		switch (uriMatcher.match(uri)){
		case BUS_PICTURE:
			String id = uri.getPathSegments().get(1);
			count = db.delete( BusPicture.TABLE_NAME, _ID + " = " + id, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	

	@Override
	public String getType(Uri uri) {
		// switch (uriMatcher.match(uri)){
		// /**
		// * Get all student records
		// */
		// case STUDENTS:
		// return "vnd.android.cursor.dir/vnd.example.students";
		// /**
		// * Get a particular student
		// */
		// case STUDENT_ID:
		// return "vnd.android.cursor.item/vnd.example.students";
		// default:
		// throw new IllegalArgumentException("Unsupported URI: " + uri);
		// }
		return "TODO";
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
