package com.rczubak.refrigerator

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.layout_product.*
import kotlinx.android.synthetic.main.layout_product.view.*
import java.util.*

class FSProductAdapter(val options: FirestoreRecyclerOptions<Product>) : FirestoreRecyclerAdapter<Product, FSProductAdapter.FSProductHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FSProductHolder {

        return FSProductHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_product,parent,false))
    }

    override fun onBindViewHolder(holder: FSProductHolder, position: Int, model: Product) {

        holder.view.nameTxt.text = model.name
        holder.view.quantityTxt.text = "x"+model.quantity.toString()
        holder.view.categoryTxt.text = model.category
        holder.view.expirationDateTxt.text = model.expirationDate


        holder.view.imageView2.setImageResource(when(model.category.toLowerCase()){
            "nabiał"-> R.drawable.nabial
            "pieczywo" -> R.drawable.pieczywo
            "słodycze" -> R.drawable.slodycze
            "warzywa" -> R.drawable.warzywa
            "owoce" -> R.drawable.owoce
            "mięso" -> R.drawable.mieso
            "makarony" -> R.drawable.makarony
            else -> R.drawable.questionmark
        })

        holder.view.addBtn.setOnClickListener {
            model.quantity+=1
            notifyDataSetChanged()
            snapshots.getSnapshot(position).reference.update("quantity",model.quantity)
        }

        holder.view.deleteBtn.setOnClickListener {
            model.quantity-=1
            notifyDataSetChanged()
            snapshots.getSnapshot(position).reference.update("quantity",model.quantity)
            if(model.quantity==0)
            {
                snapshots.getSnapshot(position).reference.delete()
            }
        }
    }

    class FSProductHolder(val view: View) : RecyclerView.ViewHolder(view)

}