package com.a00819647.seguimientodesintomas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.a00819647.seguimientodesintomas.databinding.ActivityLoginBinding
import com.a00819647.seguimientodesintomas.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            evalLogin()
        }
    }

    private fun evalLogin() {
        when {
            // Caso 4: No hay valor en el apartado de Email
            TextUtils.isEmpty(binding.loginEmail.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "¡Por favor, inserte un correo electrónico!", Toast.LENGTH_LONG).show()
            }
            // Caso 5: No hay valor en el apartado de Contraseña
            TextUtils.isEmpty(binding.loginPsswd.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "¡Por favor, inserte una contraseña!", Toast.LENGTH_LONG).show()
            }
            else -> {
                loginFirebase()
            }
        }
    }

    public override fun onStart() {
        super.onStart()


    }

    private fun loginFirebase() {
        val email = binding.loginEmail.text.toString().trim{ it <= ' ' }
        val password = binding.loginPsswd.text.toString().trim{ it <= ' ' }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    val user = auth.currentUser!!
                    val intent = Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra("user_id", user.uid)
                        putExtra("email_id", email)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "¡Error: No se iniciar la sesión!", Toast.LENGTH_LONG).show()
                }
            }
    }
}