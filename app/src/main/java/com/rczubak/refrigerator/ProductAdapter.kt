package com.rczubak.refrigerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_product.view.*

class ProductAdapter(var products: MutableList<Product>) : RecyclerView.Adapter< ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_product,parent,false))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val product = products[position]

        holder.view.nameTxt.text = product.name
        holder.view.quantityTxt.text = "x"+product.quantity.toString()
        holder.view.categoryTxt.text = product.category
        holder.view.expirationDateTxt.text = product.expirationDate
        holder.view.imageView2.setImageResource(when(product.image){
            1 -> R.drawable.nabial
            else -> R.drawable.nabial

        })

        holder.view.addBtn.setOnClickListener {
            product.quantity+=1
            notifyDataSetChanged()
        }

        holder.view.deleteBtn.setOnClickListener {
            product.quantity-=1
            notifyDataSetChanged()
            if(product.quantity<=0)
            {
                products.removeAt(position)
            }
        }

    }



    class ProductViewHolder(val view: View): RecyclerView.ViewHolder(view)
}