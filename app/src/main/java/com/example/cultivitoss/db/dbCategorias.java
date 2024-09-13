package com.example.cultivitoss.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class dbCategorias extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cultivos.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "cultivos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TIPO_CULTIVO = "tipoCultivo";
    private static final String COLUMN_FECHA_CULTIVO = "fechaCultivo";
    private static final String COLUMN_FECHA_COSECHA = "fechaCosecha";

    public dbCategorias(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIPO_CULTIVO + " TEXT, " +
                COLUMN_FECHA_CULTIVO + " INTEGER, " +
                COLUMN_FECHA_COSECHA + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertarCultivo(String tipoCultivo, Date fechaCultivo, Date fechaCosecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIPO_CULTIVO, tipoCultivo);
        values.put(COLUMN_FECHA_CULTIVO, fechaCultivo.getTime());
        values.put(COLUMN_FECHA_COSECHA, fechaCosecha.getTime());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void actualizarCultivo(int id, String nuevoTipoCultivo, Date nuevaFechaCosecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIPO_CULTIVO, nuevoTipoCultivo);
        values.put(COLUMN_FECHA_COSECHA, nuevaFechaCosecha.getTime());

        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void eliminarCultivo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<com.example.cultivitoss.db.Cultivo> obtenerTodosLosCultivos() {
        List<com.example.cultivitoss.db.Cultivo> cultivos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String tipoCultivo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_CULTIVO));
                @SuppressLint("Range") long fechaCultivoMillis = cursor.getLong(cursor.getColumnIndex(COLUMN_FECHA_CULTIVO));
                @SuppressLint("Range") long fechaCosechaMillis = cursor.getLong(cursor.getColumnIndex(COLUMN_FECHA_COSECHA));
                Date fechaCultivo = new Date(fechaCultivoMillis);
                Date fechaCosecha = new Date(fechaCosechaMillis);

                com.example.cultivitoss.db.Cultivo cultivo = new Cultivo(id, tipoCultivo, fechaCultivo, fechaCosecha);
                cultivos.add(cultivo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cultivos;
    }
}