package com.a00819647.seguimientodesintomas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a00819647.seguimientodesintomas.databinding.FragmentSymptomsLogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_symptoms_log.*

class SymptomsLogFragment : AppCompatActivity() {

    private lateinit var binding: FragmentSymptomsLogBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var symptomsRecyclerView: RecyclerView
    private lateinit var symptomArrayList: ArrayList<Symptom>
    private lateinit var symptomAdapter: SymptomAdapter
    private lateinit var db: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSymptomsLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val userid = auth.currentUser?.uid
        symptomsRecyclerView = binding.symptomsRecyclerview
        symptomsRecyclerView.layoutManager = LinearLayoutManager(this)
        symptomsRecyclerView.setHasFixedSize(true)

        symptomArrayList = arrayListOf()

        symptomAdapter = SymptomAdapter(symptomArrayList)

        symptomsRecyclerView.adapter = symptomAdapter

        EventChangeListener()

    }

    private fun EventChangeListener() {
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        val userid = user?.uid
        db = FirebaseFirestore.getInstance()
        db.collection("/Patients/$userid/Symptoms")
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
                            symptomArrayList.add(dc.document.toObject(Symptom::class.java))
                        }
                    }
                    symptomAdapter.notifyDataSetChanged()
                }
            })


    }

}