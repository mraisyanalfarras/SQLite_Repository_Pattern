package com.riyanto.belajarsqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.riyanto.belajarsqlite.helpers.MahasiswaRepository;
import com.riyanto.belajarsqlite.models.Mahasiswa;

public class EditActivity extends AppCompatActivity {

    EditText etNim, etNama;
    Spinner spProdi;
    Button btnUpdate, btnHapus;
    MahasiswaRepository mahasiswaRepository;
    String nimLama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Inisialisasi view
        etNim = findViewById(R.id.et_nim);
        etNama = findViewById(R.id.et_nama);
        spProdi = findViewById(R.id.sp_prodi);
        btnUpdate = findViewById(R.id.btn_update);
        btnHapus = findViewById(R.id.btn_hapus);

        // Inisialisasi repository
        mahasiswaRepository = new MahasiswaRepository(this);

        // Ambil data mahasiswa dari Intent
        Mahasiswa mhs = getIntent().getParcelableExtra("mahasiswa");

        // Isi form dengan data yang diterima
        etNim.setText(mhs.getNim());
        etNama.setText(mhs.getNama());

        // Setup Spinner dengan array program studi
        String[] arrProdi = {"Manajemen Informatika", "Teknik Listrik"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrProdi);
        spProdi.setAdapter(adapter);

        // Set Spinner ke program studi yang sesuai
        if (mhs != null) {
            int position = adapter.getPosition(mhs.getProdi());
            spProdi.setSelection(position); // Pilih item sesuai prodi dari objek mahasiswa
        }

        btnUpdate.setOnClickListener(v -> {
            String nimBaru = etNim.getText().toString();
            String namaBaru = etNama.getText().toString();
            String prodiBaru = spProdi.getSelectedItem().toString();


            Mahasiswa mahasiswaBaru = new Mahasiswa(nimBaru, namaBaru, prodiBaru);

            // Memperbarui data mahasiswa melalui repository dengan NIM lama
            mahasiswaRepository.updateMahasiswa(nimLama, mahasiswaBaru);

            Toast.makeText(this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();

            // Kembali ke activity sebelumnya setelah update
            Intent intentBack = new Intent(this, MainActivity.class);
            intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentBack);
        });

        // Ketika tombol Hapus ditekan (untuk menghapus data)
        btnHapus.setOnClickListener(v -> {
            mahasiswaRepository.deleteMahasiswa(nimLama);
            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();

            // Kembali ke MainActivity setelah menghapus data
            Intent intentBack = new Intent(this, MainActivity.class);
            intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentBack);
        });
    }
}
