package edu.unikom.tubesuas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.unikom.tubesuas.data.SummaryItem
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    private val items: MutableList<SummaryItem>, // Ubah ke MutableList jika Anda ingin menghapus item
    private val onItemClick: (SummaryItem) -> Unit,
    private val onDeleteClick: (SummaryItem) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    // ViewHolder di-setup untuk menangani view dan listener sekali saja
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Ganti ID di bawah ini jika berbeda dengan file layout item Anda (misal: R.layout.item_history)
        private val txtPrompt: TextView = view.findViewById(R.id.txtPrompt)
        private val txtTime: TextView = view.findViewById(R.id.txtTime)
        private val btnHapus: Button = view.findViewById(R.id.btnHapusItem)

        init {
            // 1. SETUP LISTENER UNTUK SELURUH ITEM (INI YANG HILANG)
            // Saat seluruh area item diklik, ini akan dijalankan untuk membuka detail.
            itemView.setOnClickListener {
                // Cek posisi adapter untuk menghindari error jika item sudah dihapus
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(items[adapterPosition])
                }
            }

            // 2. SETUP LISTENER UNTUK TOMBOL HAPUS (Dibuat lebih efisien)
            btnHapus.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onDeleteClick(items[adapterPosition])
                }
            }
        }

        // Fungsi untuk mengisi data ke view
        fun bind(item: SummaryItem) {
            txtPrompt.text = item.prompt
            txtTime.text = formatDate(item.timestamp) // Saya aktifkan lagi fungsi waktu
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Ganti R.layout.item_summary jika nama file layout Anda berbeda
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_summary, parent, false)
        return ViewHolder(view)
    }

    // onBindViewHolder sekarang jadi sangat simpel, hanya memanggil bind
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    // Fungsi helper untuk memformat waktu
    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("id", "ID"))
        return sdf.format(Date(timestamp))
    }
}
