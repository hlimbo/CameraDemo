package com.limbo.camerademo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NutritionRecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nutritionLabel = view.findViewById<TextView>(R.id.nutritional_label)
    val nutritionValue = view.findViewById<TextView>(R.id.nutritional_value)
}

class PhotoModelAdapter(private var nutritionList: MutableList<NutritionRecordModel>): RecyclerView.Adapter<NutritionRecordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionRecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nutritional_list_item, parent, false)
        return NutritionRecordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nutritionList.size
    }

    override fun onBindViewHolder(holder: NutritionRecordViewHolder, position: Int) {
        val nutritionRecord = nutritionList[position]

        holder.nutritionLabel.text = nutritionRecord.label
        holder.nutritionValue.text = nutritionRecord.value
    }

    fun insertNutritionRecords(nutritionRecord: NutritionRecordModel) {
        nutritionList.add(nutritionRecord)
        notifyDataSetChanged()
    }
}