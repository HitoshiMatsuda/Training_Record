package jp.co.futureantiques.trainingrecord.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
    private DBHelper mHelper;
    private SQLiteDatabase db;
    protected int[] wBox;
    protected int[] fBox;
    protected int num;

    public DBManager(Context context) {
        mHelper = new DBHelper(context);
    }

    //TrainRecordFragment用
    public Cursor select() {
        db = mHelper.getWritableDatabase();
        return db.rawQuery("SELECT id , chest, back, lower, shoulder, run, rest, creation_date FROM TRAINING_TABLE", null);
    }

    //追加処理
    //WeightRegisterActivity
    public void insert(String weight, String fat) {
        db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("weight", Integer.parseInt(weight));
        contentValues.put("fat", Integer.parseInt(fat));
        db.insert("WEIGHT_TABLE", null, contentValues);

        Log.i("Insert_Success", "Insertに成功しました。");
        db.close();
    }

    //追加処理
    //TrainingRegisterActivity
    public void insertTrain(String menu, String data){

    }

    //MainActivity.Chart(Weight)
    public int[] wSelect() {
        if (db == null) {
            db = mHelper.getReadableDatabase();
        }
        Cursor cursor = db.query(
                "WEIGHT_TABLE",
                new String[]{"id", "weight"},
                null,
                null,
                null,
                null,
                null
        );
        //WeightDataへ格納する
        cursor.moveToFirst();
        //カラムの数を取得する
        num = cursor.getCount();
        wBox = new int[num];
        //ループでwBox(体重データ)へ格納する
        for (int i = 0; i < num; i++) {
            wBox[i] = Integer.parseInt(cursor.getString(cursor.getColumnIndex("weight")));
            cursor.moveToNext();
        }
        Log.i("xBox_Insert", "xBoxへ格納しました。");
        return wBox;
    }

    //MainActivity.Chart(Fat)
    public int[] fSelect() {
        if (db == null) {
            db = mHelper.getReadableDatabase();
        }
        Cursor cursor = db.query(
                "WEIGHT_TABLE",
                new String[]{"id", "fat"},
                null,
                null,
                null,
                null,
                null
        );
        //WeightDataへ格納する
        cursor.moveToFirst();
        //カラムの数を取得する
        num = cursor.getCount();
        fBox = new int[num];
        //ループでwBox(体重データ)へ格納する
        for (int i = 0; i < num; i++) {
            fBox[i] = Integer.parseInt(cursor.getString(cursor.getColumnIndex("fat")));
            cursor.moveToNext();
        }
        Log.i("fBox_Insert", "fBoxへ格納しました。");
        return fBox;
    }

    //更新処理
    public void UpData(int id, int weight, int fat) {
        db = mHelper.getWritableDatabase();
        //カラム(id)の初期値が44であるから
        int keyId = id + 43;
        String Id = String.valueOf(keyId);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Id);
        contentValues.put("weight", weight);
        contentValues.put("fat", fat);
        db.update("WEIGHT_TABLE"
                , contentValues
                , "id = ?"
                , new String[]{Id}
        );
        db.close();
    }

    //保留
    /*//SecondActivity.TextView
    public StringBuilder reRode(Context context) {
        if (mHelper == null) {
            mHelper = new DBHelper(context);
        }
        if (db == null) {
            db = mHelper.getReadableDatabase();
        }
        Cursor cursor = db.query(
                "WEIGHT_MASTER",
                new String[]{"id", "weight", "fat"},
                null,
                null,
                null,
                null,
                null
        );

        //WeightDataへ格納する
        cursor.moveToFirst();

        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < cursor.getCount(); i++) {
            sBuilder.append(cursor.getString(cursor.getColumnIndex("id")));
            sBuilder.append("\n");
            sBuilder.append(cursor.getString(cursor.getColumnIndex("weight")));
            sBuilder.append("\n");
            sBuilder.append(cursor.getString(cursor.getColumnIndex("fat")));
            sBuilder.append("\n");
            sBuilder.append("------------------------------\n");
            cursor.moveToNext();
        }
        cursor.close();
        return sBuilder;
    }*/

    /*//データベースの削除
    public void AllDelete(){
        db = mHelper.getWritableDatabase();
        db.execSQL("DELETE FROM WEIGHT_MASTER");
        Log.i("log_DB_DELETE", "テーブル内のデータが削除されました");
        //auto_incrementをリセットしたい！！！
        //↑とりあえず必要ないんじゃない？更新できればいい！
        //db.execSQL("ALTER TABLE WEIGHT_MASTER DROP COLUMN id");
        //Log.i("log_DB_RESET", "idがリセットされました");
        db.close();
    }*/
}
