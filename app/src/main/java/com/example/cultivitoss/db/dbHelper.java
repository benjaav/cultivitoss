package com.example.cultivitoss.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    private static final String BASE_NOMBRE = "db_tienda.db";
    private static final int BASE_VERSION = 1;
    protected static final String TABLE_CATEGORIAS = "t_categorias";

    public dbHelper(@Nullable Context context) {
        super(context, BASE_NOMBRE, null, BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_CATEGORIAS + "("  +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "nombre TEXT NOT NULL)");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_CATEGORIAS + "(id,nombre )VALUES"+
                "(1, 'Tomates'),"+
                "(2, 'Cebollas'),"+
                "(3, 'Lechugas'),"+
                "(4, 'Apio'),"+
                "(5, 'Choclo')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIAS);
        onCreate(sqLiteDatabase);
    }
}