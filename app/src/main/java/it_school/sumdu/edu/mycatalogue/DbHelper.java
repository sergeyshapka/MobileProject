package it_school.sumdu.edu.mycatalogue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "catalogueDb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "catalogueItems";
    private static final String KEY_ID = "Number";
    private static final String KEY_NAME = "Name";
    private static final String KEY_AMOUNT = "Amount";

    private static final String KEY_PRICE = "Price";
    private static final String KEY_DESCRIPTION = "ItemDescription";
    private static final String KEY_IMAGE = "Image";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_AMOUNT + " TEXT," + KEY_PRICE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_IMAGE + " BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNewItem(CatalogueItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, item.getName());
        contentValues.put(KEY_AMOUNT, item.getAmount());
        contentValues.put(KEY_PRICE, item.getPrice());
        contentValues.put(KEY_DESCRIPTION, item.getItemDescription());
        contentValues.put(KEY_IMAGE, item.getImage());
        db.insert(TABLE_NAME, null, contentValues);
    }

    public CatalogueItem getItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_AMOUNT, KEY_PRICE, KEY_DESCRIPTION,
                        KEY_IMAGE}, KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        CatalogueItem item = new CatalogueItem( cursor.getInt(0),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3),cursor.getString(4),cursor.getBlob(5));
        cursor.close();
        return item;
    }

    public ArrayList<CatalogueItem> getAllItems() {
        ArrayList<CatalogueItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_AMOUNT, KEY_PRICE, KEY_DESCRIPTION,
                KEY_IMAGE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                CatalogueItem item = new CatalogueItem( cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),cursor.getBlob(5));
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    public int updateItem(CatalogueItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, item.getName());
        contentValues.put(KEY_AMOUNT, item.getAmount());
        contentValues.put(KEY_PRICE, item.getPrice());
        contentValues.put(KEY_DESCRIPTION, item.getItemDescription());
        contentValues.put(KEY_IMAGE, item.getImage());
        return db.update(TABLE_NAME, contentValues, KEY_ID + "=?",
                new String[]{String.valueOf(item.getNumber())});
    }

    public void deleteItem(CatalogueItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(item.getNumber())});
        db.close();
    }
}
