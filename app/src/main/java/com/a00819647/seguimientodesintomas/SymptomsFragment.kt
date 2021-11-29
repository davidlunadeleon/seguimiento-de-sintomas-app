package com.a00819647.seguimientodesintomas

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.a00819647.seguimientodesintomas.databinding.FragmentSymptomsBinding
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class SymptomsFragment : AppCompatActivity() {

    private lateinit var binding: FragmentSymptomsBinding
    private lateinit var auth: FirebaseAuth

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSymptomsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth




       binding.symptomDate.setOnClickListener {
           showDatePickerDialog()
       }

        binding.symptomButton.setOnClickListener {
            evalSymptom()
       }







    }

    private fun registerSymptom() {
        val userid = intent.getStringExtra("user_id")
        val emailid = intent.getStringExtra("email_id")
        val comment = binding.symptomDescription.text.toString().trim{ it <= ' ' }
        val date = binding.symptomDate.text.toString().trim{ it <= ' ' }
        val intensity = binding.symptomSlider.value
        val name = binding.symptomName.text.toString().trim{ it <= ' ' }

        val hashMap = hashMapOf<String, Any>(
            "comment" to comment,
            "date" to date,
            "intensity" to intensity,
            "name" to name
        )
        FirebaseUtils().fireStoreDatabase.collection("/Patients/$userid/Symptoms").add(hashMap)
        Toast.makeText(this, "Sintoma registrado con exito", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun evalSymptom() {
        when {

            TextUtils.isEmpty(binding.symptomDescription.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "Por favor, inserte una descripcion", Toast.LENGTH_LONG).show()
            }

            TextUtils.isEmpty(binding.symptomDate.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "Por favor, inserte una fecha", Toast.LENGTH_LONG).show()
            }

            TextUtils.isEmpty(binding.symptomSlider.value.toString()) -> {
                Toast.makeText(this, "Por favor, inserte una intensidad", Toast.LENGTH_LONG).show()
            }

            TextUtils.isEmpty(binding.symptomName.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "Por favor, inserte un nombre", Toast.LENGTH_LONG).show()
            }
            else -> {
                registerSymptom()
            }
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener{_, year, month, day ->
            val selectDate = day.toString() + " / " + (month + 1) + " / " + year
            binding.symptomDate.setText(selectDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }

}
