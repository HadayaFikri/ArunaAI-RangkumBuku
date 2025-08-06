package edu.unikom.tubesuas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.unikom.tubesuas.data.SummaryItem
import android.widget.ImageButton

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAdapter
    private lateinit var btnHapusSemua: Button
    private var historyList = mutableListOf<SummaryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed() // Atau finish()
        }

        // Inisialisasi komponen
        recyclerView = findViewById(R.id.recyclerRiwayat)  // Pastikan ID-nya benar di XML!
        btnHapusSemua = findViewById(R.id.btnHapusRiwayat)

        // Ambil riwayat
        historyList = HistoryStorage.getHistory(this).toMutableList()

        // Setup adapter dengan fungsi hapus item
        adapter = HistoryAdapter(
            historyList,
            onItemClick = { clickedItem ->
                // Saat item di-klik, jalankan kode untuk membuka detail
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("EXTRA_SUMMARY", clickedItem) // Kirim objek SummaryItem
                startActivity(intent)
            },
            onDeleteClick = { itemToDelete ->
                // Saat tombol hapus di item di-klik, panggil fungsi hapusItem
                hapusItem(itemToDelete)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Tombol Hapus Semua
        btnHapusSemua.setOnClickListener {
            if (historyList.isEmpty()) {
                Toast.makeText(this, "Tidak ada riwayat yang bisa dihapus.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Hapus Semua Riwayat")
                .setMessage("Apakah kamu yakin ingin menghapus semua riwayat?")
                .setPositiveButton("Ya") { _, _ ->
                    historyList.clear()
                    HistoryStorage.saveHistory(this, historyList)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Semua riwayat dihapus.", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    private fun hapusItem(item: SummaryItem) {
        historyList.remove(item)
        HistoryStorage.saveHistory(this, historyList)
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Item dihapus.", Toast.LENGTH_SHORT).show()
    }
}
