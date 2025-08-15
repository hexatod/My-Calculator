package cdn.mycalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cdn.mycalculator.databinding.ActivityLoginBinding
import androidx.appcompat.app.AlertDialog
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)
        val savedUsername = sharedPref.getString("user_username", null)
        val savedPassword = sharedPref.getString("user_password", null)

        // Kalau akun belum ada, balik ke register
        if (savedUsername.isNullOrEmpty() || savedPassword.isNullOrEmpty()) {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString()

            if (username == savedUsername && password == savedPassword) {
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            AlertDialog.Builder(this@LoginActivity)
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin reset password?")
                .setPositiveButton("Ya") { _, _ ->
                    // Hapus data login
                    val sharedPref = getSharedPreferences("LOGIN_PREF", MODE_PRIVATE)
                    sharedPref.edit().clear().apply()

                    // Arahkan ke register
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }


    }
}
