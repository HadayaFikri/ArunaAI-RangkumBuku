package edu.unikom.tubesuas

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            // Arahkan ke WalkthroughActivity
            startActivity(Intent(this, WalkthroughActivity::class.java))
            finish()
        }, 1000) // 2 detik
    }
}