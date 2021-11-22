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
import com.a00819647.seguimientodesintomas.databinding.FragmentMedicineBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MedicineFragment : AppCompatActivity() {

    private lateinit var binding: FragmentMedicineBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth




        binding.medicineDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.medicineButton.setOnClickListener {
            evalMedicine()
        }







    }

    private fun registerMedicine() {
        val userid = intent.getStringExtra("user_id")
        val emailid = intent.getStringExtra("email_id")
        val comment = binding.medicineDescription.text.toString().trim{ it <= ' ' }
        val date = binding.medicineDate.text.toString().trim{ it <= ' ' }
        val amount = binding.medicineQty.text.toString().trim{ it <= ' ' }
        val name = binding.medicineName.text.toString().trim{ it <= ' ' }

        val hashMap = hashMapOf<String, Any>(
            "comment" to comment,
            "date" to date,
            "amount" to amount,
            "name" to name
        )
        FirebaseUtils().fireStoreDatabase.collection("/Patients/$userid/MedicineIntakes").add(hashMap)
        Toast.makeText(this, "Medicacion registrada con exito", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("user_id", userid)
            putExtra("email_id", emailid)
        }
        startActivity(intent)
        finish()
    }

    private fun evalMedicine() {
        when {

            TextUtils.isEmpty(binding.medicineDescription.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "Por favor, inserte una descripcion", Toast.LENGTH_LONG).show()
            }

            TextUtils.isEmpty(binding.medicineDate.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "Por favor, inserte una fecha", Toast.LENGTH_LONG).show()
            }

            TextUtils.isEmpty(binding.medicineQty.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "Por favor, inserte una cantidad", Toast.LENGTH_LONG).show()
            }

            TextUtils.isEmpty(binding.medicineName.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "Por favor, inserte un nombre", Toast.LENGTH_LONG).show()
            }
            else -> {
                registerMedicine()
            }
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener{_, year, month, day ->
            val selectDate = day.toString() + " / " + (month + 1) + " / " + year
            binding.medicineDate.setText(selectDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }

}
