package jp.co.futureantiques.trainingrecord.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import jp.co.futureantiques.trainingrecord.TrainingData;

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
    public Cursor selectAll() {
        db = mHelper.getReadableDatabase();
        return db.rawQuery("SELECT id as _id, train_menu , year , month , day FROM TRAINING_TABLE", null);
    }

    //EditActivity
    //トレーニング記録編集用
    public TrainingData selectEdit(String key) {
        db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id, train_menu, year, month, day " +
                        "FROM TRAINING_TABLE " +
                        "WHERE id = ?",
                new String[]{key});

        //TrainingDataへ格納
        cursor.moveToFirst();
        TrainingData trainingData = new TrainingData();
        String oldMenu = cursor.getString(cursor.getColumnIndex("train_menu"));
        String oldYear = cursor.getString(cursor.getColumnIndex("year"));
        String oldMonth = cursor.getString(cursor.getColumnIndex("month"));
        String oldDay = cursor.getString(cursor.getColumnIndex("day"));

        trainingData.setMenu(oldMenu);
        trainingData.setYear(oldYear);
        trainingData.setMonth(oldMonth);
        trainingData.setDay(oldDay);

        return trainingData;
    }

    //データ更新処理
    //EditActivity
    public void trainUpDate(String key, String menu, String year, String month, String day) {
        db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("train_menu", menu);
        contentValues.put("year", year);
        contentValues.put("month", month);
        contentValues.put("day", day);
        db.update("TRAINING_TABLE"
                , contentValues
                , "id = ?"
                , new String[]{key}
        );
        db.close();
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
    public void insertTrain(String menu, String year, String month, String day) {
        db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("train_menu", menu);
        contentValues.put("year", year);
        contentValues.put("month", month);
        contentValues.put("day", day);
        db.insert("TRAINING_TABLE", null, contentValues);

        Log.i("TInsert_Success", "トレーニング記録を追加しました。");
        db.close();
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
}
