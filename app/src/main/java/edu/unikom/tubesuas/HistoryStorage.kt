package edu.unikom.tubesuas

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.unikom.tubesuas.data.SummaryItem
import java.io.File

object HistoryStorage {

    private const val FILE_NAME = "history.json"
    private val gson = Gson()

    /**
     * Menyimpan keseluruhan list ke file, menimpa data lama.
     * FUNGSI INI YANG AKAN ANDA PANGGIL SETELAH MENGHAPUS ITEM.
     */
    fun saveHistory(context: Context, history: List<SummaryItem>) {
        val json = gson.toJson(history)
        // PERBAIKAN: Menggunakan file, bukan SharedPreferences
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    /**
     * Menambah satu item baru ke dalam history.
     * (Sebelumnya bernama 'save')
     */
    fun addItem(context: Context, item: SummaryItem) {
        val list = getHistory(context).toMutableList()
        list.add(0, item) // Tambah ke atas
        saveHistory(context, list) // Panggil fungsi saveHistory yang sudah konsisten
    }

    /**
     * Menghapus semua riwayat dari file.
     * (Sebelumnya bernama 'hapusRiwayat')
     */
    fun clearAllHistory(context: Context) {
        // Cara paling aman adalah menyimpan list kosong ke file
        saveHistory(context, emptyList())
    }

    /**
     * Mengambil semua riwayat dari file.
     * FUNGSI INI SUDAH BENAR, TIDAK PERLU DIUBAH.
     */
    fun getHistory(context: Context): List<SummaryItem> {
        val file = File(context.filesDir, FILE_NAME)
        // Tambahan: Pengecekan jika file belum ada, langsung kembalikan list kosong
        if (!file.exists()) {
            return emptyList()
        }

        return try {
            val json = context.openFileInput(FILE_NAME).bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<SummaryItem>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}