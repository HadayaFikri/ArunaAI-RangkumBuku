package edu.unikom.tubesuas

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.unikom.tubesuas.data.FaqItem

class FaqActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val recyclerView: RecyclerView = findViewById(R.id.faq_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // OnClickListener untuk tombol kembali
        btnBack.setOnClickListener {
            onBackPressed() // Atau finish()
        }

        val faqData = listOf(
            FaqItem("Apa itu Aruna AI?", "Aruna AI adalah aplikasi yang menggunakan kecerdasan buatan untuk merangkum teks dari buku atau dokumen. Pengguna bisa memasukkan kutipan atau teks untuk mendapatkan ringkasan singkat dan poin-poin penting secara otomatis."),
            FaqItem("Bagaimana cara kerjanya?", "Aruna AI menggunakan Large Language Model dari API openrouter.ai untuk menganalisis teks yang Anda masukkan, mengidentifikasi ide-ide kunci, dan menyusunnya menjadi ringkasan yang mudah dipahami. Proses ini dilakukan secara instan."),
            FaqItem("Apakah saya bisa merangkum seluruh buku?", "Untuk saat ini, Aruna AI dirancang untuk merangkum kutipan atau bagian teks yang lebih pendek. Merangkum seluruh buku mungkin membutuhkan waktu lama dan memakan banyak sumber daya."),
            FaqItem("Kenapa ringkasannya kadang terasa aneh atau tidak masuk akal?", "Terkadang, ringkasan yang dihasilkan bisa terasa aneh jika teks sumbernya tidak jelas, terdapat ejaan yang salah, atau memiliki konteks yang ambigu. Cobalah periksa kembali teks yang Anda masukkan."),
            FaqItem("Apakah ada batasan jumlah karakter?", "Ya, ada batasan karakter untuk memastikan proses merangkum berjalan cepat dan efisien. Jika teks Anda terlalu panjang, Anda bisa membaginya menjadi beberapa bagian."),
            FaqItem("Tutorial Prompting Agar Menghasilkan Rangkuman Yang Relevan",
                "Rangkumkan Buku: [Judul Buku]\n" +
                        "Bab: [Nomor/Nama Bab]\n" +
                        "Penulis: [Nama Penulis] (opsional)\n" +
                        "Panjang ringkasan: [Singkat/Sedang/Detail]\n\n" +
                        "Contoh:\n" +
                        "Rangkumkan Buku: Atomic Habits\n" +
                        "Bab: Bab 1 - The Surprising Power of Atomic Habits\n" +
                        "Penulis: James Clear\n" +
                        "Panjang ringkasan: Sedang"),
            FaqItem("Temukan Saya Di?", "✉️ Email: fikrihadaya@gmail.com"),
            // Tambahkan item FAQ lainnya di sini
        )

        val adapter = FaqAdapter(faqData)
        recyclerView.adapter = adapter
    }
}