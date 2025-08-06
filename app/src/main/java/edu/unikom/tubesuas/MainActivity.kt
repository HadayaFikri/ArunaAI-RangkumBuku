package edu.unikom.tubesuas

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import edu.unikom.tubesuas.data.SummaryItem
import edu.unikom.tubesuas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeTextInput()
    }

    private fun setupListeners() {
        // Tombol untuk buka halaman riwayat
        binding.iconLeft.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        // Tombol untuk buka halaman FAQ
        binding.iconProfile.setOnClickListener {
            val intent = Intent(this, FaqActivity::class.java)
            startActivity(intent)
        }

        // Tombol kirim
        binding.btnSubmit.setOnClickListener {
            val prompt = binding.inputText.text.toString().trim()

            if (prompt.isEmpty()) {
                binding.outputText.text = "Masukkan kutipan atau ringkasan terlebih dahulu."
                return@setOnClickListener
            }

            binding.progressBar.visibility = View.VISIBLE

            OpenRouterClient.kirimPrompt(prompt) { hasil ->
                runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    binding.outputText.text = hasil ?: "Gagal mengambil respon"

                    // Simpan ke riwayat
                    val newItem = SummaryItem(
                        prompt = prompt,
                        response = hasil ?: "Gagal mengambil respon",
                        timestamp = System.currentTimeMillis()
                    )
                    HistoryStorage.addItem(this, newItem)
                }
            }
        }

        // =============================================================
        // KODE FOKUS OTOMATIS DITAMBAHKAN DI SINI
        // =============================================================
        binding.inputText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Ketika EditText mendapatkan fokus dan teksnya kosong
                if (binding.inputText.text!!.trim().isEmpty()) {
                    val initialText = "Rangkumkan Buku "
                    binding.inputText.setText(initialText)
                    // Pindahkan kursor ke akhir teks
                    binding.inputText.setSelection(initialText.length)
                }
            }
        }
    }

    private fun observeTextInput() {
        // Atur ikon awal (brain/AI)
        binding.btnSubmit.setImageResource(R.drawable.idle) // Ganti nama jika perlu

        // Tambahkan TextWatcher untuk memantau perubahan teks
        binding.inputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    // Jika teks kosong, ubah ke ikon AI
                    binding.btnSubmit.setImageResource(R.drawable.idle)
                } else {
                    // Jika ada teks, ubah ke ikon kirim
                    binding.btnSubmit.setImageResource(R.drawable.send) // Ganti nama jika perlu
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}