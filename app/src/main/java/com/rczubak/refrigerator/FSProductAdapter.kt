package com.rczubak.refrigerator


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.android.synthetic.main.layout_product.view.*


typealias DataChangedListener = (count: Int) -> Unit
typealias ErrorListener = (error: FirebaseFirestoreException) -> Unit

class FSProductAdapter( options: FirestoreRecyclerOptions<Product>,
                        private val onDataChangedListener: DataChangedListener = {},
                        private val onErrorListener: ErrorListener = {}) : FirestoreRecyclerAdapter<Product, FSProductAdapter.FSProductHolder>(options){



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

    override fun onDataChanged() =
        onDataChangedListener.invoke(itemCount)

    fun getNumberOfItem():Boolean{
        return snapshots.isEmpty()
    }

    /*private fun checkDate(productDate: String): Boolean {
        val sdf = SimpleDateFormat("ddMMyyyy")
        val currentTime = sdf.format(Date())
        println(currentTime)

        val day = currentTime.substring(0,2);
        val month = currentTime.substring(2,4)
        val year = currentTime.substring(4)
        val pDay =
        val pMonth =
        val pYear =

        if(day)

        return true
    }*/




    class FSProductHolder(val view: View) : RecyclerView.ViewHolder(view)

}