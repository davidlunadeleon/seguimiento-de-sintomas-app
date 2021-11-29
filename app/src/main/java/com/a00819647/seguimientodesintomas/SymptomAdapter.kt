package com.a00819647.seguimientodesintomas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SymptomAdapter(private val symptomList:ArrayList<Symptom>) : RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomAdapter.SymptomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.symptom_item, parent, false)
        return SymptomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SymptomAdapter.SymptomViewHolder, position: Int) {
        val symptom : Symptom = symptomList[position]
        holder.name.text = symptom.name
        holder.comment.text = symptom.comment
        holder.intensity.text = symptom.intensity.toString()
        holder.date.text = symptom.date
    }

    override fun getItemCount(): Int {
        return symptomList.size
    }

    public class SymptomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.tvSymptomName)
        val intensity : TextView = itemView.findViewById(R.id.tvSymptomIntensity)
        val comment : TextView = itemView.findViewById(R.id.tvSymptomDescription)
        val date : TextView = itemView.findViewById(R.id.tvSymptomDate)
    }

}