package com.riyanto.belajarsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.riyanto.belajarsqlite.adapters.MahasiswaAdapter;
import com.riyanto.belajarsqlite.helpers.DatabaseHelper;
import com.riyanto.belajarsqlite.helpers.MahasiswaRepository;
import com.riyanto.belajarsqlite.helpers.Singleton;
import com.riyanto.belajarsqlite.models.Mahasiswa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewMahasiswa;
    static DatabaseHelper databaseHelper;
    static ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();
    static MahasiswaAdapter adapter;
    FloatingActionButton floatingActionButton;
    static MahasiswaRepository mahasiswaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMahasiswa    = findViewById(R.id.lv_mahasiswa);
        floatingActionButton = findViewById(R.id.fab_tambah);

        mahasiswaRepository  = new MahasiswaRepository(this);

        tampilMahasiswa();


        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TambahActivity.class);
            startActivity(intent);
        });

        // Menangani klik pada item di ListView untuk update
        listViewMahasiswa.setOnItemClickListener((parent, view, position, id) -> {
            Mahasiswa mahasiswa = mahasiswaList.get(position);
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("mahasiswa", mahasiswa);
            startActivity(intent);
        });

        // Menangani klik panjang pada item di ListView untuk delete
        listViewMahasiswa.setOnItemLongClickListener((parent, view, position, id) -> {
            onDelete(mahasiswaList.get(position).getNim(), position);
            return true; // Mengindikasikan bahwa event telah ditangani
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mahasiswaList.clear();
        tampilMahasiswa();
        adapter.notifyDataSetChanged();
    }

    private void tampilMahasiswa() {
        mahasiswaList = mahasiswaRepository.tampilMahasiswa();
        adapter = new MahasiswaAdapter(this, mahasiswaList);

        listViewMahasiswa.setAdapter(adapter);
    }



    private void onDelete(String nim, int position) {
        mahasiswaRepository.deleteMahasiswa(nim); // Menghapus mahasiswa dari database
        mahasiswaList.remove(position); // Menghapus mahasiswa dari list
        adapter.notifyDataSetChanged(); // Memberitahu adapter untuk memperbarui tampilan
    }
}