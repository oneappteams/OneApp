package mind.com.oneapp.Framework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Kiran on 08-02-2016.
 */
public class DB extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OneApp.db";

    public static final String TABLE_CATEGORIES = "tblCategories";
    public static final String TABLE_WEBSITES = "tblWebsites";
    public static final String TABLE_TRENDING = "tblTrending";

    //Category table values
    private static final String COL_CATEGORY_ID = "categoryID";
    private static final String COL_CATEGORY_NAME = "categoryName";
    private static final String COL_CATEGORY_OPENED_COUNT = "openedCount";

    private String CREATE_CATEGORY_TABLE = "CREATE TABLE `"+TABLE_CATEGORIES+"` (`"+COL_CATEGORY_ID+"`	INTEGER PRIMARY KEY AUTOINCREMENT, `"+COL_CATEGORY_NAME+"`	TEXT,	`"+COL_CATEGORY_OPENED_COUNT+"`	INTEGER)";

    //Website table values
    private static final String COL_WEB_ID = "webId";
    private static final String COL_WEB_TITLE = "webTitle";
    private static final String COL_HOME_URL = "homeUrl";
    private static final String COL_TOTAL_URL = "totalUrl";
    private static final String COL_WEBSITE_OPENED_COUNT = "openedCount";

    private String CREATE_WEBSITE_TABLE = "CREATE TABLE `"+TABLE_WEBSITES+"` (`"+COL_WEB_ID+"`	INTEGER PRIMARY KEY AUTOINCREMENT, `"+COL_WEB_TITLE+"`	TEXT,	`"+COL_CATEGORY_NAME+"`	TEXT,	`"+COL_HOME_URL+"`	TEXT,	`"+COL_TOTAL_URL+"`	TEXT,	`"+COL_WEBSITE_OPENED_COUNT+"`	INTEGER)";

    //Trending table values
    private static final String COL_CATEGORY = "category";
    private static final String COL_TITLE = "title";
    private static final String COL_SUB_TITLE = "subTitle";
    private static final String COL_URL = "url";

    private String CREATE_TRENDING_TABLE = "CREATE TABLE `"+TABLE_TRENDING+"` (`"+COL_CATEGORY+"`	TEXT, `"+COL_TITLE+"`	TEXT,	`"+COL_SUB_TITLE+"`	TEXT,	`"+COL_URL+"`	TEXT)";

    private SQLiteDatabase	db;

        public DB(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            db =  this.getWritableDatabase();
        }
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_WEBSITE_TABLE);
        db.execSQL(CREATE_TRENDING_TABLE);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
//        db.execSQL("DROP ");
//
//        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertTrendingData(ArrayList<RowData> data){
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRENDING, null);
//       if(cursor.moveToFirst())
       db.execSQL("delete from "+ TABLE_TRENDING);
        ContentValues values = new ContentValues();
        for(int i=0; i<data.size();i++){
            RowData rowData = data.get(i);
            values.put(DB.COL_CATEGORY, rowData.getCategory());
            values.put(DB.COL_TITLE, rowData.getTitle());
            values.put(DB.COL_SUB_TITLE, rowData.getSubTitle());
            values.put(DB.COL_URL, rowData.getUrl());

            db.insert(TABLE_TRENDING, null, values);
        }
    }
    public ArrayList<RowData> getTrendingData(String category){
        Cursor cursor = null;
        if(category.equals("OverAll")){
            cursor = db.rawQuery("SELECT * FROM " + TABLE_TRENDING, null);
        } else{
            cursor = db.rawQuery("SELECT * FROM " + TABLE_TRENDING + " WHERE " + COL_CATEGORY + "='" + category +"'", null);
        }

        String msgDate = null;
        ArrayList<RowData> rowDataList = new ArrayList<RowData>();

        if(cursor.moveToFirst()){
            do{
                RowData rowitem = new RowData(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                rowDataList.add(rowitem);
            }while(cursor.moveToNext());
        }
        return rowDataList;
    }
}
