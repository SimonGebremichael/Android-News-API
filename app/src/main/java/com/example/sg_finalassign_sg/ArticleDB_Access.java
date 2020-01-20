
package com.example.sg_finalassign_sg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class ArticleDB_Access extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "userManager";
    private static final String TABLE = "users";

    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String PASS = "password";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String INTERESTS = "interests";

    public ArticleDB_Access(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_USER = "CREATE TABLE " + TABLE  +
                " (" +
                ID + " INTEGER PRIMARY KEY NOT NULL," +
                NAME + " TEXT,"  +
                PASS + " TEXT,"  +
                EMAIL + " TEXT," +
                PHONE + " TEXT," +
                INTERESTS + " TEXT)";

        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addPlayer(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(NAME, user.getName());
        vals.put(PASS, user.getPassword());
        vals.put(EMAIL, user.getEmail());
        vals.put(PHONE, user.getPhone());
        vals.put(INTERESTS, user.getInterests());

        db.insert(TABLE, null, vals);
        db.close();
    }

    public User getPlayerByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cur = db.query(TABLE, new String[]{ID, NAME, PASS, EMAIL, PHONE, INTERESTS}, NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, "1");

        if (cur != null){ cur.moveToFirst(); }

        User player = null;
        try {
            player = new User(Integer.parseInt(cur.getString(0)), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5));

        } catch (Exception e){
            return null;
        }

        return player;
    }

    public List<User> getAllUsers(){
        List<User> Users = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                User player = new User();
                player.setId(Integer.parseInt(cursor.getString(0)));
                player.setName(cursor.getString(1));
                player.setPassword(cursor.getString(2));
                player.setEmail(cursor.getString(3));
                player.setPhone(cursor.getString(4));
                player.setInterests(cursor.getString(5));
                Users.add(player);

            }while(cursor.moveToNext());
        }
        return Users;
    }


    public int getAmountOfPlayers(){
        int k = 0;
        String amount = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(amount, null);
        k = cur.getCount();
        cur.close();

        return  k;
    }



//    public  void deletePlayer(playerDB player){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE, ID + "=?",
//                new String[]{String.valueOf(player.get_id())});
//        db.close();
//    }


//    public ArrayList<HashMap<String, String>> getArrayOfPlayers(){
//        ArrayList<HashMap<String, String>> test = new ArrayList<HashMap<String, String>>();
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " ORDER BY " + WINS + " DESC",null );
//
//        while (cursor.moveToNext()) {
//            HashMap<String, String> meme = new HashMap<String, String>();
//            meme.put("name", cursor.getString(1));
//            meme.put("wins", cursor.getString(2));
//            meme.put("losses", cursor.getString(3));
//            meme.put("ties", cursor.getString(4));
//            test.add(meme);
//        }
//
//        cursor.close();
//        db.close();
//
//        return test;
//    }

//    public boolean updatePlayerName(playerDB player, String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ID, player.get_id());
//        values.put(NAME, name);
//        values.put(WINS, player.getWins());
//        values.put(LOSSES, player.getLosses());
//        values.put(TIES, player.getTies());
//
//        db.update(TABLE, values, ID + "=?", new String[]{String.valueOf(player.get_id())});
//        return true;
//    }
}

