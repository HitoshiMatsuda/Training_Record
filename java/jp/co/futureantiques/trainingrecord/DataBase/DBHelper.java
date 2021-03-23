package jp.co.futureantiques.trainingrecord.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    //データベースの名前
    private static final String DBNAME = "WEIGHT_MASTER";
    //データベースのバージョン
    private static final int VERSION = 1;
    //固定値（カラム名）
    //TABLE1(体重テーブル)
    private static final String ID = "id";
    private static final String WEIGHT = "weight";
    private static final String FAT = "fat";
    private static final String MEMO = "memo";
    private static final String CREATION_DATE = "creation_date";

    //TRAINING_MASTER_TABLE
    private static final String MUSCLE_NAME = "muscle_name";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String JOIN_KEY = "join_key";

    //TODAY_TRAINING_RECORD_TABLE
    private static final String HEAVY = "heavy";
    private static final String FIRST = "first_set";
    private static final String SECOND = "second_set";
    private static final String THIRD = "third_set";
    private static final String FOURTH = "fourth_set";
    private static final String SPAN = "sets_span";

    //TRAINING_MENU_TABLE
    private static final String TRAINING_NAME = "training_name";


    //コンストラクタ
    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    //データベースが作成された際に実行
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Log_DB_Create", "dbHelperクラスのonCreateが実行されました。");

        //体重テーブル
        //ID|体重|体脂肪率|メモ|日付|削除フラグ
        db.execSQL("CREATE TABLE WEIGHT_TABLE("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WEIGHT + " INTEGER, "
                + FAT + " INTEGER, "
                + MEMO + " TEXT, "
                + CREATION_DATE + " TIMESTAMP DEFAULT (DATETIME(CURRENT_TIMESTAMP,'LOCALTIME'))) "
        );

        //TRAINING_MASTER_TABLE
        //id|muscle_id|muscle_name|year|month|day
        db.execSQL("CREATE TABLE TRAINING_MASTER_TABLE("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MUSCLE_NAME + " TEXT, "
                + YEAR + " TEXT, "
                + MONTH + " TEXT, "
                + DAY + " TEXT, "
                + JOIN_KEY + " TEXT) "
        );

        //TODAY_TRAINING_RECORD_TABLE
        //id|master_join_id|muscle_id|muscle_name|training_id|training_name|heavy|menu_sets|sets_span
        db.execSQL("CREATE TABLE TODAY_TRAINING_RECORD_TABLE("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + JOIN_KEY + " TEXT, "
                + TRAINING_NAME + " TEXT, "
                + HEAVY + " TEXT, "
                + FIRST + " TEXT, "
                + SECOND + " TEXT, "
                + THIRD + " TEXT, "
                + FOURTH + " TEXT, "
                + SPAN + " TEXT) "
        );

        //MUSCLE_TABLE
        //id|muscle_id|muscle_name
        db.execSQL("CREATE TABLE MUSCLE_TABLE("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MUSCLE_NAME + " TEXT )"
        );

        //TRAINING_MENU_TABLE
        //id|muscle_name|training_name
        db.execSQL("CREATE TABLE TRAINING_MENU_TABLE("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MUSCLE_NAME + " TEXT, "
                + TRAINING_NAME + " TEXT )"
        );
    }

    //データベースのバージョンアップの際に実行
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DROPしても大丈夫？
        //テーブルの削除
        db.execSQL("DROP TABLE IF EXISTS WEIGHT_TABLE");
        //テーブルの新規作成
        onCreate(db);
    }

    //データベースが開かれた際に実行
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.i("log_DB_Open", "DBが開かれました");
    }
}
