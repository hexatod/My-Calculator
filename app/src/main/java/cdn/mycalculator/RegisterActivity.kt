package cdn.mycalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cdn.mycalculator.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)
        val savedUsername = sharedPref.getString("user_username", null)

        // Kalau akun sudah ada, langsung ke login
        if (!savedUsername.isNullOrEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val pass1 = binding.editTextPassword1.text.toString()
            val pass2 = binding.editTextPassword2.text.toString()

            if (username.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            } else if (pass1.length < 6) {
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
            } else if (pass1 == pass2) {
                with(sharedPref.edit()) {
                    putString("user_username", username)
                    putString("user_password", pass1)
                    apply()
                }
                Toast.makeText(this, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
