package jp.co.futureantiques.trainingrecord.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import jp.co.futureantiques.trainingrecord.TrainingData;

public class DBManager {
    private final DBHelper mHelper;
    private SQLiteDatabase db;
    protected int[] wBox;
    protected int[] fBox;
    protected int num;

    public DBManager(Context context) {
        mHelper = new DBHelper(context);
    }

    //TrainingRecordFragment
    //トレーニング記録取得（日付と筋群のみ）
    public Cursor selectDayRecord() {
        db = mHelper.getReadableDatabase();
        return db.rawQuery("SELECT id as _id , muscle_name , year , month , day , join_key FROM TRAINING_MASTER_TABLE", null);
    }


    //TrainingNameEditActivity
    //筋群一覧取得
    public Cursor selectMuscle() {
        db = mHelper.getReadableDatabase();
        return db.rawQuery("SELECT id as _id, muscle_name FROM MUSCLE_TABLE", null);
    }

    //TrainingNameRegisterActivity
    //全トレーニングメニューを取得
    public Cursor selectAllTrainingMenu() {
        db = mHelper.getReadableDatabase();
        return db.rawQuery("SELECT id as _id, training_name FROM TRAINING_MENU_TABLE", null);
    }


    //TrainingRegisterActivity
    //日付ごとのトレーニング記録取得
    public Cursor selectTodayMenu(String joinKey) {
        db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id as _id, join_key , training_name , heavy , first_set , second_set , third_set , fourth_set , sets_span " +
                        "FROM TODAY_TRAINING_RECORD_TABLE " +
                        "WHERE join_key = ?",
                new String[]{joinKey});
        return cursor;
    }


    //SelectTrainingMenuActivity
    //筋群ごとのメニュー取得
    public Cursor selectTrainingMenu(String key) {
        db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id as _id, muscle_name , training_name " +
                        "FROM TRAINING_MENU_TABLE " +
                        "WHERE muscle_name = ?",
                new String[]{key});
        return cursor;
    }


    //TrainingNameEditActivity
    //更新前のトレーニングメニュー取得(選択)
    public TrainingData selectMenuEdit(String key) {
        db = mHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id , muscle_name , training_name " +
                        "FROM TRAINING_MENU_TABLE " +
                        "WHERE id = ?",
                new String[]{key});

        cursor.moveToFirst();
        TrainingData trainingData = new TrainingData();
        String oldMenu = cursor.getString(cursor.getColumnIndex("training_name"));
        trainingData.setMenu(oldMenu);
        return trainingData;
    }


    //TrainingNameEditActivity
    //トレーニング名更新処理
    public void trainMenuUpDate(String key, String menu) {
        db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("training_name", menu);
        db.update("TRAINING_MENU_TABLE"
                , contentValues
                , "id = ?"
                , new String[]{key}
        );
        db.close();
    }


    //TrainingDetailEditActivity
    //トレーニングメニューごとの記録更新処理
    public void trainingDetailRecordUpdate(String key, String
            heavy, String first, String second, String third, String fourth) {
        db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("heavy", heavy);
        contentValues.put("first_set", first);
        contentValues.put("second_set", second);
        contentValues.put("third_set", third);
        contentValues.put("fourth_set", fourth);
        db.update("TODAY_TRAINING_RECORD_TABLE"
                , contentValues
                , "id = ?"
                , new String[]{key}
        );
        db.close();
    }


    //TrainingNameEditActivity
    //トレーニングメニュー削除処理
    public void trainMenuDelete(String key) {
        db = mHelper.getWritableDatabase();
        db.delete("TRAINING_MENU_TABLE", "id = ?", new String[]{key});
        db.close();
    }


    //TrainingEditActivity
    //トレーニングメニュー削除処理
    public void trainingDelete(String key) {
        db = mHelper.getWritableDatabase();
        db.delete("TRAINING_MASTER_TABLE", "id = ?", new String[]{key});
        db.close();
    }


    //TrainingEditActivity
    //トレーニング詳細記録の削除
    public void trainingRecordDelete(String joinKey) {
        db = mHelper.getWritableDatabase();
        db.delete("TODAY_TRAINING_RECORD_TABLE", "join_key = ?", new String[]{joinKey});
        db.close();
    }


    //TrainingDetailEditActivity
    //トレーニング詳細記録削除処理
    public void deleteTrainingDetailRecord(String key) {
        db = mHelper.getWritableDatabase();
        db.delete("TODAY_TRAINING_RECORD_TABLE", "id = ?", new String[]{key});
        db.close();
    }

    //TrainingDetailRegisterActivity
    //トレーニング詳細記録の更新
    public TrainingData detailTrainingRecordSelect(String key) {
        db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id as _id, join_key , training_name , heavy , first_set , second_set , third_set , fourth_set , sets_span " +
                        "FROM TODAY_TRAINING_RECORD_TABLE " +
                        "WHERE id = ?",
                new String[]{key});

        cursor.moveToFirst();
        TrainingData trainingData = new TrainingData();
        String oldMenu = cursor.getString(cursor.getColumnIndex("training_name"));
        String oldHeavy = cursor.getString(cursor.getColumnIndex("heavy"));
        String oldFirst = cursor.getString(cursor.getColumnIndex("first_set"));
        String oldSecond = cursor.getString(cursor.getColumnIndex("second_set"));
        String oldThird = cursor.getString(cursor.getColumnIndex("third_set"));
        String oldFourth = cursor.getString(cursor.getColumnIndex("fourth_set"));

        trainingData.setMenu(oldMenu);
        trainingData.setHeavyWeight(oldHeavy);
        trainingData.setFirst(oldFirst);
        trainingData.setSecond(oldSecond);
        trainingData.setThird(oldThird);
        trainingData.setFourth(oldFourth);
        return trainingData;
    }


    //TrainingEditActivity
    //筋群編集機能
    public TrainingData selectEdit(String key) {
        db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id, muscle_name, year, month, day " +
                        "FROM TRAINING_MASTER_TABLE " +
                        "WHERE id = ?",
                new String[]{key});

        //TrainingDataへ格納
        cursor.moveToFirst();
        TrainingData trainingData = new TrainingData();
        String oldMenu = cursor.getString(cursor.getColumnIndex("muscle_name"));
        String oldYear = cursor.getString(cursor.getColumnIndex("year"));
        String oldMonth = cursor.getString(cursor.getColumnIndex("month"));
        String oldDay = cursor.getString(cursor.getColumnIndex("day"));

        trainingData.setMenu(oldMenu);
        trainingData.setYear(oldYear);
        trainingData.setMonth(oldMonth);
        trainingData.setDay(oldDay);

        return trainingData;
    }


    //TrainingEditActivity
    //データ更新処理
    public void trainUpDate(String key, String menu, String year, String month, String day) {
        db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("muscle_name", menu);
        contentValues.put("year", year);
        contentValues.put("month", month);
        contentValues.put("day", day);
        db.update("TRAINING_MASTER_TABLE"
                , contentValues
                , "id = ?"
                , new String[]{key}
        );
        db.close();
    }


    //WeightRegisterActivity
    //追加処理
    public void insert(String weight, String fat) {
        db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("weight", Integer.parseInt(weight));
        contentValues.put("fat", Integer.parseInt(fat));
        db.insert("WEIGHT_TABLE", null, contentValues);

        Log.i("Insert_Success", "Insertに成功しました。");
        db.close();
    }


    //TrainingRegisterActivity
    //追加処理(日付と筋群のみ)
    public void insertTodayTraining(String muscle, String year, String month, String day, String joinKey) {
        db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("muscle_name", muscle);
        contentValues.put("year", year);
        contentValues.put("month", month);
        contentValues.put("day", day);
        contentValues.put("join_key", joinKey);
        db.insert("TRAINING_MASTER_TABLE", null, contentValues);

        Log.i("TInsert_Success", "トレーニング記録を追加しました。");
        db.close();
    }

    //TrainingDetailRegisterActivity
    //追加処理
    public void insertTodayTraining(String joinKey, String training_name, String
            heavy, String first, String second, String third, String fourth) {
        db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("join_key", joinKey);
        contentValues.put("training_name", training_name);
        contentValues.put("heavy", heavy);
        contentValues.put("first_set", first);
        contentValues.put("second_set", second);
        contentValues.put("third_set", third);
        contentValues.put("fourth_set", fourth);
        db.insert("TODAY_TRAINING_RECORD_TABLE", null, contentValues);

        Log.i("TInsert_Success", "トレーニング記録を追加しました。");
        db.close();
    }


    //TrainingNameRegisterActivity
    public void insertMenu(String muscle, String menu) {
        db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("muscle_name", muscle);
        contentValues.put("training_name", menu);
        db.insert("TRAINING_MENU_TABLE", null, contentValues);

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
