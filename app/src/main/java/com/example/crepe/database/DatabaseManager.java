package com.example.crepe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String RIDE_TABLE = "ride";
    public static final String COLUMN_RIDE_ID = "rideId";
    public static final String COLUMN_CREATOR_USER_ID = "creatorUserId";
    public static final String COLUMN_CAPACITY = "capacity";
    public static final String COLUMN_PEOPLE = "people";
    public static final String COLUMN_COLLECTOR_START_DATE = "collectorStartDate";
    public static final String COLUMN_COLLECTOR_START_TIME = "collectorStartTime";
    public static final String COLUMN_COLLECTOR_DESTINATION = "destination";
    public static final String COLUMN_COLLECTOR_DEPARTURE = "departure";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COLLECTOR_STATUS = "collectorStatus";

    public static final String USER_TABLE = "usertable";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_TEL = "tel";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_TRIPS = "trips";
    public static final String COLUMN_USER_NAME = "userName";

//    public static final String DATA_TABLE = "data";
//    public static final String COLUMN_DATA_ID = "dataId";
//    public static final String COLUMN_TIMESTAMP = "timestamp";
//    public static final String COLUMN_DATA_CONTENT = "dataContent";
//    private static final String COLUMN_USER_TIME_CREATED = "userTimeCreated";
//    private static final String COLUMN_USER_LAST_TIME_EDITED = "userTimeLastEdited";
//
//    private static final String DATAFIELD_TABLE = "datafield";
//    private static final String COLUMN_DATAFIELD_ID = "datafieldId";
//    private static final String COLUMN_GRAPH_QUERY = "graphQuery";
//    private static final String COLUMN_DATAFIELD_NAME = "datafieldName";
//    private static final String COLUMN_DATAFIELD_TIME_CREATED = "datafieldTimeCreated";
//    private static final String COLUMN_DATAFIELD_TIME_LAST_EDITED = "datafieldTimeLastEdited";
//    private static final String COLUMN_DATAFIELD_IS_DEMONSTRATED = "datafieldIsDemonstrated";


    private static final List<String> tableList = new ArrayList<>(Arrays.asList(RIDE_TABLE, USER_TABLE));

    // Create table statements
    private final String createRideTableStatement = "CREATE TABLE IF NOT EXISTS " + RIDE_TABLE + " (" + COLUMN_RIDE_ID + " VARCHAR PRIMARY KEY, " +
            "            " + COLUMN_CREATOR_USER_ID + " VARCHAR, " +
            "            " + COLUMN_CAPACITY + " VARCHAR, " +
            "            " + COLUMN_PEOPLE + " VARCHAR, " +
            "            " + COLUMN_DESCRIPTION + " VARCHAR, " +
            "            " + COLUMN_COLLECTOR_START_DATE + " BIGINT, " +
            "            " + COLUMN_COLLECTOR_START_TIME + " BIGINT, " +
            "            " + COLUMN_COLLECTOR_DESTINATION + " VARCHAR, " +
            "            " + COLUMN_COLLECTOR_DEPARTURE + " VARCHAR," +
            "            " + COLUMN_COLLECTOR_STATUS + " VARCHAR)";

    private final String createUserTableStatement = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" + COLUMN_USER_ID + " VARCHAR PRIMARY KEY, " +
            "            " + COLUMN_USER_NAME + " VARCHAR, " +
            "            " + COLUMN_PHOTO + " VARCHAR, " +
            "            " + COLUMN_TEL + " VARCHAR, " +
            "            " + COLUMN_GENDER + " VARCHAR, " +
            "            " + COLUMN_TRIPS + " VARCHAR);";

//    private final String createDataFieldTableStatement = "CREATE TABLE IF NOT EXISTS " + DATAFIELD_TABLE + " (" + COLUMN_DATAFIELD_ID + " VARCHAR PRIMARY KEY, " +
//            "            " + COLUMN_RIDE_ID + " VARCHAR, " +
//            "            " + COLUMN_GRAPH_QUERY + " VARCHAR, " +
//            "            " + COLUMN_DATAFIELD_NAME + " VARCHAR, " +
//            "            " + COLUMN_DATAFIELD_TIME_CREATED + " BIGINT, " +
//            "            " + COLUMN_DATAFIELD_TIME_LAST_EDITED + " BIGINT, " +
//            "            " + COLUMN_DATAFIELD_IS_DEMONSTRATED + " BOOLEAN, " +
//            "            " + "FOREIGN KEY(" + COLUMN_RIDE_ID + ") REFERENCES " + RIDE_TABLE + "(" + COLUMN_RIDE_ID + "));";
//
//    private final String createDataTableStatement = "CREATE TABLE IF NOT EXISTS " + DATA_TABLE + " (" + COLUMN_DATA_ID + " VARCHAR PRIMARY KEY, " +
//            "            " + COLUMN_DATAFIELD_ID + " VARCHAR, " +
//            "            " + COLUMN_USER_ID + " VARCHAR, " +
//            "            " + COLUMN_TIMESTAMP + " BIGINT, " +
//            "            " + COLUMN_DATA_CONTENT + "VARCHAR, " +
//            "            " + "FOREIGN KEY(" + COLUMN_DATAFIELD_ID + ") REFERENCES " + DATAFIELD_TABLE + "(" + COLUMN_DATAFIELD_ID + "), " +
//            "            " + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + USER_TABLE + "(" + COLUMN_USER_ID + "));" ;


    // constructor
    public DatabaseManager(@Nullable Context context) {
        super(context, "crepe.db", null, 1);
    }

    // will be called the first time a database is accessed.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // generate new tables
        sqLiteDatabase.execSQL(createRideTableStatement);
        sqLiteDatabase.execSQL(createUserTableStatement);
    }

    // called if the database version number changes. prevents the app from crashing
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // clear all 2 tables in the database
    public void clearDatabase(Context c) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(String tableName: tableList) {
            db.delete(tableName, "1", null);
        }
        Toast.makeText(c, "Clear database success!", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public Boolean addOneRide(Ride ride) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RIDE_ID, ride.getRideId());
        cv.put(COLUMN_CREATOR_USER_ID, ride.getCreatorUserId());
        cv.put(COLUMN_PEOPLE, ride.getPeople());
        cv.put(COLUMN_CAPACITY, ride.getCapacity());
        cv.put(COLUMN_COLLECTOR_START_DATE, ride.getRideDate());
        cv.put(COLUMN_COLLECTOR_START_TIME, ride.getRideTime());
        cv.put(COLUMN_COLLECTOR_DEPARTURE, ride.getDeparture());
        cv.put(COLUMN_COLLECTOR_DESTINATION, ride.getDestination());
        cv.put(COLUMN_DESCRIPTION, ride.getDescription());
        cv.put(COLUMN_COLLECTOR_STATUS, ride.getCollectorStatus());

        long insert = db.insert(RIDE_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public void removeRideById(String collectorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // the result equals to the number of entries being deleted

        int result = db.delete(RIDE_TABLE, "collectorId = " + collectorId, null);
        if(result > 0) {
            Log.i("database", "successfully deleted " + result + " collectors with id " + collectorId);
        } else {
            Log.i("database", "remove collector error, current collectors: " + getAllRides().toString());
        }
        db.close();
    }

    // a method to get all collectors in the database
    public List<Ride> getAllRides() {
        List<Ride> rideList = new ArrayList<>();

        String sqlString = "SELECT * FROM " + RIDE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlString, null);

        // if the cursor actually got something
        if (cursor.moveToFirst()) {
            do {
                // parse every info from cursor
                String rideID = cursor.getString(0);
                String creatorUserID = cursor.getString(1);
                String capacity = cursor.getString(2);
                String people = cursor.getString(3);
                String description = cursor.getString(4);
                String rideStartDate = cursor.getString(5);
                String rideStartTime = cursor.getString(6);
                String destination = cursor.getString(7);
                String departure = cursor.getString(8);

                Ride receivedRide = new Ride(rideID, creatorUserID, rideStartDate, rideStartTime, capacity, people, destination, departure,description);
                rideList.add(receivedRide);

            } while(cursor.moveToNext());
        }

        else {
            // do nothing since it's empty
            Log.i("", "The collector list is empty.");
        }
        cursor.close();
        db.close();
        return rideList;
    }

    public Boolean addOneUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_ID, user.getUserId());
        cv.put(COLUMN_USER_NAME, user.getName());
        cv.put(COLUMN_PHOTO, user.getPhoto());
        cv.put(COLUMN_TEL, user.getTel());
        cv.put(COLUMN_GENDER, user.getGender());
        cv.put(COLUMN_TRIPS, user.getTrips());

        long insert = db.insert(USER_TABLE, null, cv);
        db.close();
        return insert != -1;
    }

    public Boolean checkIfUserExists(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USER_ID + " = \"" + userId + "\"";
        Cursor cursor = db.rawQuery(query, null);
        int cursorCount = cursor.getCount();

        db.close();
        cursor.close();

        if (cursorCount <= 0) {

            return false;
        } else {
            return true;
        }
    }

//    public Boolean addOneUser(String userId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        long timeCreated = Calendar.getInstance().getTimeInMillis();
//
//        cv.put(COLUMN_USER_ID, userId);
//        // use empty string for current user name, will change later using function updateUserName
//        cv.put(COLUMN_USER_NAME, "");
//        cv.put(COLUMN_USER_TIME_CREATED, timeCreated);
//        // use the current time for last edited
//        cv.put(COLUMN_USER_LAST_TIME_EDITED, timeCreated);
//
//        long insert = db.insert(USER_TABLE, null, cv);
//        db.close();
//        return insert != -1;
//    }

    public String getUsername(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_USER_NAME + " from " + USER_TABLE + " where " + COLUMN_USER_ID + "= \"" + userId + "\"";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int usernameColumnIndex = cursor.getColumnIndex(COLUMN_USER_NAME);
        String username;
        try {
            username = cursor.getString(usernameColumnIndex);
        }catch(Exception e){
            username = "N/A";
        }

        db.close();
        cursor.close();
        return username;
    }

    // Use this function to update the database when the user set their name in the left panel
    public Boolean updateUserName(String userId, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, username);
        // rows represent the number of rows updated
        int rows = db.update(USER_TABLE, cv,  "userId = ?" , new String[] {userId} );

        db.close();
        return (rows > 0);
    }

//    public void removeUserById(String userId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        // the result equals to the number of entries being deleted
//
//        int result = db.delete(USER_TABLE, "userId = " + userId, null);
//        if(result > 0) {
//            Log.i("database", "successfully deleted " + result + " users with id " + userId);
//        } else {
//            Log.i("database", "remove user error, current users: " + getAllUsers().toString());
//        }
//        db.close();
//    }

    public User getOneUserByID(String searchId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> userList = new ArrayList<>();
        String getAllUsersQuery = "SELECT * FROM " + USER_TABLE;

        Cursor cursor = db.rawQuery(getAllUsersQuery, null);

        if(cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(0);
                String userName = cursor.getString(1);
                String userPhoto = cursor.getString(2);
                String userTel = cursor.getString(3);
                String userGender = cursor.getString(4);
                String userTrips = cursor.getString(5);

                if(userId.equals(searchId)){
                    User receivedUser = new User(userId, userName, userPhoto, userTel, userGender, userTrips);
                    return receivedUser;
                }
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        User received_user = new User();
        return received_user;
    }

//    public Boolean addData(Data data) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_DATA_ID, data.getDataId());
//        cv.put(COLUMN_DATAFIELD_ID, data.getDataFieldId());
//        cv.put(COLUMN_USER_ID, data.getUserId());
//        cv.put(COLUMN_TIMESTAMP, data.getTimestamp());
//        cv.put(COLUMN_DATA_CONTENT, data.getDataContent());
//
//        long result = db.insert(DATA_TABLE, null, cv);
//        db.close();
//        return result != -1;
//    }
//
//    public void removeDataById(String dataId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        // the result equals to the number of entries being deleted
//
//        int result = db.delete(DATA_TABLE, "dataId = " + dataId, null);
//        if(result > 0) {
//            Log.i("database", "successfully deleted " + result + " data entries with id " + dataId);
//        } else {
//            Log.i("database", "remove data entry by data id error, current data entries: " + getAllData().toString());
//        }
//        db.close();
//    }

//    public void removeDataByDatafieldId(String datafieldId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        // the result equals to the number of entries being deleted
//
//        int result = db.delete(DATA_TABLE, "datafieldId = " + datafieldId, null);
//        if(result > 0) {
//            Log.i("database", "successfully deleted " + result + " data entries from datafield " + datafieldId);
//        } else {
//            Log.i("database", "remove data entry by datafield Id error, current data entries: " + getAllData().toString());
//        }
//        db.close();
//    }

//    public void removeDataByUserId(String userId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        // the result equals to the number of entries being deleted
//
//        int result = db.delete(DATA_TABLE, "userId = " + userId, null);
//        if(result > 0) {
//            Log.i("database", "successfully deleted " + result + " data entries from user " + userId);
//        } else {
//            Log.i("database", "remove data entry by user id error, current data entries: " + getAllData().toString());
//        }
//        db.close();
//    }

//    public List<Data> getAllData() {
//        List<Data> dataList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String getAllDataQuery = "SELECT * FROM " + DATA_TABLE;
//
//        Cursor cursor = db.rawQuery(getAllDataQuery, null);
//
//        if(cursor.moveToFirst()) {
//            do {
//
//                String dataId = cursor.getString(0);
//                String dataFieldId = cursor.getString(1);
//                String userId = cursor.getString(2);
//                Long timestamp = cursor.getLong(3);
//                String dataContent = cursor.getString(4);
//
//                Data receivedData = new Data(dataId, dataFieldId, userId, timestamp, dataContent);
//
//                dataList.add(receivedData);
//
//            } while (cursor.moveToNext());
//        } else {
//            Log.i("", "The data list is empty.");
//        }
//
//        db.close();
//        cursor.close();
//
//        return dataList;
//    }

//    public List<Data> getDataForCollector(Ride ride) {
//        List<Data> dataList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String getAllDataQuery = "SELECT * FROM " + DATA_TABLE + " WHERE " + COLUMN_RIDE_ID + " = \"" + ride.getRideId() + "\";";
//
//        Cursor cursor = db.rawQuery(getAllDataQuery, null);
//
//        if(cursor.moveToFirst()) {
//            do {
//
//                String dataId = cursor.getString(0);
//                String dataFieldId = cursor.getString(1);
//                String userId = cursor.getString(2);
//                Long timestamp = cursor.getLong(3);
//                String dataContent = cursor.getString(4);
//
//                Data receivedData = new Data(dataId, dataFieldId, userId, timestamp, dataContent);
//
//                dataList.add(receivedData);
//
//            } while (cursor.moveToNext());
//        } else {
//            Log.i("", "Cannot find data for the specified collector ID. Try another collector instead?");
//        }
//
//        db.close();
//        cursor.close();
//
//        return dataList;
//    }


//    public Boolean DataField(Datafield dataField) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(COLUMN_DATAFIELD_ID, dataField.getDataFieldId());
//        cv.put(COLUMN_RIDE_ID, dataField.getCollectorId());
//        cv.put(COLUMN_GRAPH_QUERY, dataField.getGraphQuery());
//        cv.put(COLUMN_PEOPLE, dataField.getName());
//        cv.put(COLUMN_DATAFIELD_TIME_CREATED, dataField.getTimeCreated());
//        cv.put(COLUMN_DATAFIELD_TIME_LAST_EDITED, dataField.getTimelastEdited());
//        cv.put(COLUMN_DATAFIELD_IS_DEMONSTRATED, dataField.getDemonstrated());
//
//        long result = db.insert(DATAFIELD_TABLE, null, cv);
//        db.close();
//        return result != -1;
//    }
//
//    public void removeDatafieldById(String datafieldId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        // the result equals to the number of entries being deleted
//
//        int result = db.delete(DATAFIELD_TABLE, "datafieldId = " + datafieldId, null);
//        if(result > 0) {
//            Log.i("database", "successfully deleted " + result + " datafield with id " + datafieldId);
//        } else {
//            Log.i("database", "remove datafield by id error, current datafields: " + getAllDatafields().toString());
//        }
//        db.close();
//    }
//    public void removeDatafieldByCollectorId(String collectorId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        // the result equals to the number of entries being deleted
//
//        int result = db.delete(DATAFIELD_TABLE, "collectorId = " + collectorId, null);
//        if(result > 0) {
//            Log.i("database", "successfully deleted " + result + " datafield from collector " + collectorId);
//        } else {
//            Log.i("database", "remove datafield by collector id error, current datafields: " + getAllDatafields().toString());
//        }
//        db.close();
//    }

//    public List<Datafield> getAllDatafields() {
//        List<Datafield> dataList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String getAllDataQuery = "SELECT * FROM " + DATAFIELD_TABLE;
//
//        Cursor cursor = db.rawQuery(getAllDataQuery, null);
//
//        if(cursor.moveToFirst()) {
//            do {
//
//                String dataFieldId = cursor.getString(0);
//                String collectorId = cursor.getString(1);
//                String graphQuery = cursor.getString(2);
//                String name = cursor.getString(3);
//                Long timeCreated = cursor.getLong(4);
//                Long timeLastEdited = cursor.getLong(5);
//                Boolean isDemonstrated = cursor.getInt(6) != 0;
//                Datafield receivedDatafield = new Datafield(dataFieldId, collectorId, graphQuery, name, timeCreated, timeLastEdited, isDemonstrated);
//
//                dataList.add(receivedDatafield);
//
//            } while (cursor.moveToNext());
//        } else {
//            Log.i("", "The datafield list is empty.");
//        }
//
//        db.close();
//        cursor.close();
//
//        return dataList;
//    }

//    public List<Datafield> getDatafieldForCollector(Ride ride) {
//        List<Datafield> dataList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String getAllDataQuery = "SELECT * FROM " + DATAFIELD_TABLE + " WHERE " + COLUMN_RIDE_ID + " = \"" + ride.getRideId() + "\";";
//
//        Cursor cursor = db.rawQuery(getAllDataQuery, null);
//
//        if(cursor.moveToFirst()) {
//            do {
//
//                String dataFieldId = cursor.getString(0);
//                String collectorId = cursor.getString(1);
//                String graphQuery = cursor.getString(2);
//                String name = cursor.getString(3);
//                Long timeCreated = cursor.getLong(4);
//                Long timeLastEdited = cursor.getLong(5);
//                Boolean isDemonstrated = cursor.getInt(6) != 0;
//                Datafield receivedDatafield = new Datafield(dataFieldId, collectorId, graphQuery, name, timeCreated, timeLastEdited, isDemonstrated);
//
//                dataList.add(receivedDatafield);
//
//            } while (cursor.moveToNext());
//        } else {
//            Log.i("", "Cannot find datafield for the specified collector ID. Try another collector instead?");
//        }
//
//        db.close();
//        cursor.close();
//
//        return dataList;
//    }

    // update statuses for all collectors to database;
    // yeah this is not the most efficient, but there won't be that many collectors anyways
    public void updateCollectorStatus(Ride ride) {
        String updateStatement = "UPDATE " + RIDE_TABLE + " SET " + COLUMN_COLLECTOR_STATUS + " =\'" + ride.getCollectorStatus() + "\' WHERE " + COLUMN_RIDE_ID + "=" + ride.getRideId();
        Cursor c = getWritableDatabase().rawQuery(updateStatement, null);
        c.moveToFirst();
        c.close();
    }

}
