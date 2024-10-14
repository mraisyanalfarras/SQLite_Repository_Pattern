package com.riyanto.belajarsqlite.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.riyanto.belajarsqlite.models.Mahasiswa;

import java.util.ArrayList;

public class MahasiswaRepository {

    private static DatabaseHelper databaseHelper;

    public MahasiswaRepository(Context context) {
        databaseHelper = Singleton.getInstance(context);
    }

    public ArrayList<Mahasiswa> tampilMahasiswa() {
        ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mahasiswa", null);

        while (cursor.moveToNext()) {
            mahasiswaList.add(
                new Mahasiswa(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
                )
            );
        }
        return mahasiswaList;
    }

    public void tambahMahasiswa(Mahasiswa m) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String sql = String.format("INSERT INTO mahasiswa (nim, nama, prodi) VALUES ('%s', '%s', '%s')", m.getNim(), m.getNama(), m.getProdi());
        db.execSQL(sql);
    }

    // Mengupdate data mahasiswa berdasarkan NIM
    public void updateMahasiswa(String nimLama, Mahasiswa m) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String sql = String.format(
                "UPDATE mahasiswa SET nama = '%s', prodi = '%s' WHERE nim = '%s'",
                m.getNama(), m.getProdi(), m.getNim()
        );
        db.execSQL(sql);
    }

    // Menghapus mahasiswa berdasarkan NIM
    public void deleteMahasiswa(String nim) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String sql = String.format("DELETE FROM mahasiswa WHERE nim = '%s'", nim);
        db.execSQL(sql);
    }

    public Mahasiswa getMahasiswaByNim(String nim) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mahasiswa WHERE nim = ?", new String[]{nim});
        Mahasiswa mahasiswa = null;

        if (cursor.moveToFirst()) {
            mahasiswa = new Mahasiswa(
                    cursor.getString(0), // NIM
                    cursor.getString(1), // Nama
                    cursor.getString(2)  // Prodi
            );
        }
        cursor.close(); // Pastikan untuk menutup cursor
        return mahasiswa;
    }

}
