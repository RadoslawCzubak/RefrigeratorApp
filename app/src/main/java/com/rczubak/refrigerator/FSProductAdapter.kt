package com.rczubak.refrigerator


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.android.synthetic.main.layout_product.view.*
import java.text.SimpleDateFormat
import java.util.*


typealias DataChangedListener = (count: Int) -> Unit
typealias ErrorListener = (error: FirebaseFirestoreException) -> Unit

class FSProductAdapter(
    options: FirestoreRecyclerOptions<Product>,
    private val onDataChangedListener: DataChangedListener = {},
    private val onErrorListener: ErrorListener = {}
) : FirestoreRecyclerAdapter<Product, FSProductAdapter.FSProductHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FSProductHolder {

        return FSProductHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_product,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FSProductHolder, position: Int, model: Product) {

        holder.view.nameTxt.text = model.name
        holder.view.quantityTxt.text = "x" + model.quantity.toString()
        holder.view.categoryTxt.text = model.category
        holder.view.expirationDateTxt.text = model.expirationDate



        holder.view.imageView2.setImageResource(
            when (model.category.toLowerCase()) {
                "dairy" -> R.drawable.diary
                "bread" -> R.drawable.bread
                "sweeties" -> R.drawable.sweeties
                "vegetable" -> R.drawable.vegetable
                "fruit" -> R.drawable.fruit
                "meat" -> R.drawable.meat
                else -> R.drawable.questionmark
            }
        )

        when (checkDate(model.expirationDate)) {
            -1 -> holder.view.expirationDateTxt.setTextColor(Color.RED)
            0 -> holder.view.expirationDateTxt.setTextColor(Color.rgb(252, 127, 3))
        }

        holder.view.addBtn.setOnClickListener {
            model.quantity += 1

            notifyDataSetChanged()
            snapshots.getSnapshot(position).reference.update("quantity", model.quantity)
        }

        holder.view.deleteBtn.setOnClickListener {
            model.quantity -= 1
            notifyDataSetChanged()
            snapshots.getSnapshot(position).reference.update("quantity", model.quantity)
            if (model.quantity == 0) {
                snapshots.getSnapshot(position).reference.delete()
            }
        }
    }

    override fun onDataChanged() =
        onDataChangedListener.invoke(itemCount)

    fun getNumberOfItem(): Boolean {
        return snapshots.isEmpty()
    }

    private fun checkDate(productDate: String): Int {
        val sdf = SimpleDateFormat("ddMMyyyy")
        val currentTime = sdf.format(Date())

        val currDay = currentTime.substring(0, 2).toInt()
        val currMonth = currentTime.substring(2, 4).toInt()
        val currYear = currentTime.substring(4).toInt()
        val pDay = productDate.substring(0, 2).toInt()
        val pMonth = productDate.substring(3, 5).toInt()
        val pYear = productDate.substring(6).toInt()




        if ((currDay > pDay && currMonth >= pMonth && currYear >= pYear) || (currMonth > pMonth && currYear >= pMonth) || currYear > pYear) {
            return -1
        } else if ((currDay == pDay && currMonth >= pMonth && currYear >= pYear) || (currMonth > pMonth && currYear >= pMonth) || currYear > pYear) {
            return 0
        } else {
            return 1
        }

    }


    class FSProductHolder(val view: View) : RecyclerView.ViewHolder(view)

}