package com.a00819647.seguimientodesintomas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a00819647.seguimientodesintomas.databinding.FragmentMedicineLogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_symptoms_log.*

class MedicineLogFragment : AppCompatActivity() {

    private lateinit var binding: FragmentMedicineLogBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var medicineRecyclerView: RecyclerView
    private lateinit var medicineArrayList: ArrayList<Medicine>
    private lateinit var medicineAdapter: MedicineAdapter
    private lateinit var db: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMedicineLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        medicineRecyclerView = binding.medicineRecyclerview
        medicineRecyclerView.layoutManager = LinearLayoutManager(this)
        medicineRecyclerView.setHasFixedSize(true)

        medicineArrayList = arrayListOf()

        medicineAdapter = MedicineAdapter(medicineArrayList)

        medicineRecyclerView.adapter = medicineAdapter

        EventChangeListener()

    }

    private fun EventChangeListener() {
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        val userid = user?.uid
        db = FirebaseFirestore.getInstance()
        db.collection("/Patients/$userid/MedicineIntakes")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore Error: ", error.message.toString())
                        return
                    }

                    for (dc : DocumentChange in value?.documentChanges!!) {
                        if(dc.type == DocumentChange.Type.ADDED) {
                            medicineArrayList.add(dc.document.toObject(Medicine::class.java))
                        }
                    }
                    medicineAdapter.notifyDataSetChanged()
                }
            })


    }

}