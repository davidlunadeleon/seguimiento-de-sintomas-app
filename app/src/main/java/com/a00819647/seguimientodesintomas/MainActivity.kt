package com.a00819647.seguimientodesintomas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a00819647.seguimientodesintomas.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val userid = intent.getStringExtra("user_id")
        val emailid = intent.getStringExtra("email_id")

        binding.userId.text = "User ID :: $userid"
        binding.emailId.text = "User ID :: $emailid"

        binding.button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.symptomsButton.setOnClickListener {
            //startActivity(Intent(this, SymptomsFragment::class.java))
            val intent = Intent(this, SymptomsFragment::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("user_id", userid)
                putExtra("email_id", emailid)
            }
            startActivity(intent)
            finish()
        }

        binding.medicineButton.setOnClickListener {
            val intent = Intent(this, MedicineFragment::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("user_id", userid)
                putExtra("email_id", emailid)
            }
            startActivity(intent)
            finish()
        }

        binding.profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileFragment::class.java))
            finish()
        }
    }
}