package cdn.mycalculator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



    class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("PIN_PREF", MODE_PRIVATE)
        val pin = sharedPreferences.getString("user_pin", null)

        if (pin == null) {
            // Belum pernah set PIN, masuk ke PinSetupActivity
            startActivity(Intent(this, RegisterActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish() // tutup launcher agar tidak bisa balik ke sini lagi
    }
}
