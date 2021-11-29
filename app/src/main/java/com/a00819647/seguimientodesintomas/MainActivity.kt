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
            finish()
        }

        val userid = intent.getStringExtra("user_id")
        val emailid = intent.getStringExtra("email_id")

        binding.userId.text = "User ID :: $userid"
        binding.emailId.text = "User Email :: $emailid"

        binding.button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.symptomsButton.setOnClickListener {
            //startActivity(Intent(this, SymptomsFragment::class.java))
            val intent = Intent(this, SymptomsFragment::class.java).apply {
                putExtra("user_id", userid)
                putExtra("email_id", emailid)
            }
            startActivity(intent)
        }

        binding.medicineButton.setOnClickListener {
            val intent = Intent(this, MedicineFragment::class.java).apply {
                putExtra("user_id", userid)
                putExtra("email_id", emailid)
            }
            startActivity(intent)
        }

        binding.profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileFragment::class.java))
        }

        binding.symptomLogButton.setOnClickListener {
            startActivity(Intent(this, SymptomsLogFragment::class.java))
        }

        binding.medicineLogButton.setOnClickListener {
            startActivity(Intent(this, MedicineLogFragment::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.signOut()
        finish()
    }
}