package edu.unikom.tubesuas // PERBAIKAN: Menggunakan nama package yang konsisten

import android.content.Intent // TAMBAHAN: Import untuk Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import edu.unikom.tubesuas.data.WalkthroughItem // PERBAIKAN: Menyamakan nama package
import edu.unikom.tubesuas.databinding.ActivityWalkthroughBinding

class WalkthroughActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkthroughBinding
    private lateinit var walkthroughAdapter: WalkthroughAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkthroughBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Buat daftar item untuk slider
        val items = listOf(
            WalkthroughItem(
                R.drawable.kid1, // Pastikan gambar ini ada di res/drawable
                "Ask questions about any book and get instant summaries with your AI reading companion."
            ),
            WalkthroughItem(
                R.drawable.kid2, // Pastikan gambar ini ada di res/drawable
                "Explore different genres and discover your next favorite read effortlessly."
            ),
            WalkthroughItem(
                R.drawable.kid4, // Pastikan gambar ini ada di res/drawable
                "Join a community of readers and share your thoughts on various books."
            )
        )

        // Inisialisasi adapter dan hubungkan ke ViewPager
        walkthroughAdapter = WalkthroughAdapter(items)
        binding.walkthroughViewPager.adapter = walkthroughAdapter

        // Hubungkan TabLayout dengan ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.walkthroughViewPager) { tab, position ->
            // Kosongkan karena kita hanya butuh indikator titik
        }.attach()

        // TAMBAHAN: Memberi fungsi pada tombol "Continue"
        binding.btnContinue.setOnClickListener {
            // Buat niat untuk pindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Tutup activity ini agar tidak bisa kembali lagi
            finish()
        }
    }
}