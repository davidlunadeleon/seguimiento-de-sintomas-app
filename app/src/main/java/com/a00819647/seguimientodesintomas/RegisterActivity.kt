package com.a00819647.seguimientodesintomas

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.a00819647.seguimientodesintomas.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.registerBirth.setOnClickListener {
            showDatePickerDialog()
        }

        binding.registerButton.setOnClickListener {
            evalRegister()
        }

        binding.loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener{_, year, month, day ->
            val selectDate = day.toString() + " / " + (month + 1) + " / " + year
            binding.registerBirth.setText(selectDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun evalRegister() {
        when {
            // Caso 1: No hay valor en el apartado de Nombre
            TextUtils.isEmpty(binding.registerName.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "¡Por favor, inserte su nombre!", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(binding.registerLastName.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "¡Por favor, inserte su apellido!", Toast.LENGTH_LONG).show()
            }
            // Caso 2: No hay valor en el apartado de Fecha de Nacimiento
            TextUtils.isEmpty(binding.registerBirth.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "¡Por favor, inserte su fecha de nacimiento!", Toast.LENGTH_LONG).show()
            }
            // Caso 3: No hay valor en el apartado de Teléfono
            TextUtils.isEmpty(binding.registerPhone.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "¡Por favor, inserte su número telefónico!", Toast.LENGTH_LONG).show()
            }
            // Caso 4: No hay valor en el apartado de Email
            TextUtils.isEmpty(binding.registerEmail.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "¡Por favor, inserte un correo electrónico!", Toast.LENGTH_LONG).show()
            }
            // Caso 5: No hay valor en el apartado de Contraseña
            TextUtils.isEmpty(binding.registerPsswd.text.toString().trim{ it <= ' ' }) -> {
                Toast.makeText(this, "¡Por favor, inserte una contraseña!", Toast.LENGTH_LONG).show()
            }
            else -> {
                registerFirebase()
            }
        }
    }

    private fun registerFirebase() {
        val email: String = binding.registerEmail.text.toString().trim{ it <= ' ' }
        val password: String = binding.registerPsswd.text.toString().trim{ it <= ' ' }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    val user = task.result!!.user!!
                    val intent = Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra("user_id", user.uid)
                        putExtra("email_id", email)
                    }
                    startActivity(intent)
                    createUserData(user.uid)
                    finish()
                } else {
                    Toast.makeText(this, "¡Error: No se pudo crear el usuario!", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun createUserData(uid: String) {

        val id = uid
        val name = binding.registerName.text.toString().trim{ it <= ' ' }
        val lastName = binding.registerLastName.text.toString().trim{ it <= ' ' }
        val dateOfBirth = binding.registerBirth.text.toString().trim{ it <= ' ' }
        val phone = binding.registerPhone.text.toString().trim{ it <= ' ' }
        val email = binding.registerEmail.text.toString().trim{ it <= ' ' }

        val hashMap = hashMapOf<String, Any>(
            "id" to id,
            "name" to name,
            "lastName" to lastName,
            "dateOfBirth" to dateOfBirth,
            "phone" to phone,
            "email" to email
        )
        db = FirebaseFirestore.getInstance()
        db.collection("Patients").document("$id").set(hashMap)
    }
}



class DatePickerFragment : DialogFragment() {

    private var listener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), listener, year, month, day)
    }

    companion object {
        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.listener = listener
            return fragment
        }
    }
}