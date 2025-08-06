package edu.unikom.tubesuas

import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.unikom.tubesuas.data.SummaryItem

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val detailTitle = findViewById<TextView>(R.id.detailTitle)
        val detailResponse = findViewById<TextView>(R.id.detailResponse)

        btnBack.setOnClickListener {
            onBackPressed()
        }

        // Ambil data SummaryItem yang dikirim dari HistoryActivity
        val summaryItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_SUMMARY", SummaryItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<SummaryItem>("EXTRA_SUMMARY")
        }

        // Tampilkan data ke TextView
        summaryItem?.let { item ->
            detailTitle.text = item.prompt
            detailResponse.text = item.response
        }
    }
}