package tmj5.project.nihonmosquelocator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zira on 1/1/2017.
 * NIM 4314122014
 */

public class OpenHelperSqlite extends SQLiteOpenHelper {

    static final String DB_NAME = "db_mosque";
    static final Integer DB_VERSION = 1;

    public OpenHelperSqlite(Context context) { super (context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE Fav" +
                        "(id TEXT PRIMARY KEY" +
                        ", name TEXT" +
                        ", prefecture TEXT" +
                        ", address TEXT" +
                        ", email TEXT" +
                        ", website TEXT" +
                        ", photo TEXT" +
                        ", lat NUMBER" +
                        ", lon NUMBER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Fav");
        onCreate(db);
    }
}
