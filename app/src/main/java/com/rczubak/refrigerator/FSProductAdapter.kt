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
        holder.view.categoryTxt.text = model.name
        holder.view.expirationDateTxt.text = model.expirationDate

        if(!checkExpirationDate(model.expirationDate)){
            holder.view.expirationDateTxt.setTextColor(Color.RED)
            println("here")
        }

        holder.view.imageView2.setImageResource(when(model.image){
            1 -> R.drawable.nabial
            else -> R.drawable.nabial
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


    private fun checkExpirationDate( date:String): Boolean{

        if(date.length<11) {return true}

        val day = Integer.parseInt(date.substring(0,2))
        val month = Integer.parseInt(date.substring(3,5))
        val year = Integer.parseInt(date.substring(6))

        val currDay = Date().day
        val currMonth = Date().month
        val currYear = Date().year

        println(currDay)
        println(currMonth)
        println(currYear)

        if(day<=currDay+2 && month <= currMonth && year<= currYear){
            return false
        }
        return true

    }

}