package com.a00819647.seguimientodesintomas

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.a00819647.seguimientodesintomas.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : AppCompatActivity() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        val userid = user?.uid

        db = FirebaseFirestore.getInstance()

        db.collection("Patients").document("$userid")
            .get()
            .addOnSuccessListener {
                val data = it.toObject(UserItem::class.java)

                val name = data?.name
                val lastName = data?.lastName
                val dateOfBirth = data?.dateOfBirth
                val email = data?.email
                val phone = data?.phone

                binding.matProfileName.hint = name.toString()
                binding.matProfileLastName.hint = lastName.toString()
                binding.matProfileDateofbirth.hint = dateOfBirth.toString()
                binding.matProfileEmail.hint = email.toString()
                binding.matProfilePhone.hint = phone.toString()
            }

        binding.matProfileDateofbirth.setOnClickListener {
            showDatePickerDialog()
        }

        binding.updateButton.setOnClickListener() {
            var name = ""
            var lastName = ""
            var dateOfBirth = ""
            var email = ""
            var phone = ""



            db.collection("Patients").document("$userid")
                .get()
                .addOnSuccessListener {
                    val data = it.toObject(UserItem::class.java)
                    name = data?.name.toString()
                    lastName = data?.lastName.toString()
                    dateOfBirth = data?.dateOfBirth.toString()
                    email = data?.email.toString()
                    phone = data?.phone.toString()
                }

            if((binding.profileName.text != null) and (binding.profileName.text.toString() != name)) {
                name = binding.profileName.text.toString()
                db.collection("Patients").document("$userid").update("name", binding.profileName.text.toString())
            }
            if((binding.profileLastName.text != null) and (binding.profileLastName.text.toString() != lastName)) {
                lastName = binding.profileLastName.text.toString()
                db.collection("Patients").document("$userid").update("lastName", binding.profileLastName.text.toString())
            }
            if((binding.profileDateofbirth != null) and (binding.profileDateofbirth.text.toString() != dateOfBirth)) {
                dateOfBirth = binding.profileDateofbirth.text.toString()
                db.collection("Patients").document("$userid").update("dateOfBirth", binding.profileDateofbirth.text.toString())
            }
            if((binding.profileEmail.text != null) and (binding.profileEmail.text.toString() != email)) {
                email = binding.profileEmail.text.toString()
                db.collection("Patients").document("$userid").update("email", binding.profileEmail.text.toString())
            }
            if((binding.profilePhone.text != null) and (binding.profilePhone.text.toString() != phone)) {
                phone = binding.profilePhone.text.toString()
                db.collection("Patients").document("$userid").update("phone", binding.profilePhone.text.toString())
            }
            Toast.makeText(this, "Datos actualizados exitosamente", Toast.LENGTH_LONG).show()
            finish()

        }




    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener{ _, year, month, day ->
            val selectDate = day.toString() + " / " + (month + 1) + " / " + year
            binding.profileDateofbirth.setText(selectDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }


}



