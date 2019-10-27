package com.rczubak.refrigerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.layout_product.view.*

class FSProductAdapter(val options: FirestoreRecyclerOptions<Product>) : FirestoreRecyclerAdapter<Product, FSProductAdapter.FSProductHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FSProductHolder {

        return FSProductHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_product,parent,false))

    }

    override fun onBindViewHolder(holder: FSProductHolder, position: Int, model: Product) {

        holder.view.nameTxt.text = model.name
        holder.view.quantityTxt.text = "x"+model.quantity.toString()
        holder.view.categoryTxt.text = model.name
        holder.view.expirationDateTxt.text = model.name
        holder.view.imageView2.setImageResource(when(model.image){
            1 -> R.drawable.nabial
            else -> R.drawable.nabial
        })



    }

    class FSProductHolder(val view: View) : RecyclerView.ViewHolder(view)


}