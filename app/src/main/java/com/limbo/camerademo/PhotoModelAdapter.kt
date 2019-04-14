package com.limbo.camerademo

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FoodModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

}

class PhotoModelAdapter(private var foods: MutableList<Food>): RecyclerView.Adapter<FoodModelViewHolder>() {
}