package com.a00819647.seguimientodesintomas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicineAdapter(private val medicineList:ArrayList<Medicine>) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineAdapter.MedicineViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.medicine_item, parent, false)
        return MedicineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MedicineAdapter.MedicineViewHolder, position: Int) {
        val medicine : Medicine = medicineList[position]
        holder.name.text = medicine.name
        holder.description.text = medicine.description
        holder.amount.text = medicine.amount
        holder.date.text = medicine.date
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    public class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.tvMedicineName)
        val amount : TextView = itemView.findViewById(R.id.tvMedicineAmount)
        val description : TextView = itemView.findViewById(R.id.tvMedicineDescription)
        val date : TextView = itemView.findViewById(R.id.tvMedicineDate)
    }

}