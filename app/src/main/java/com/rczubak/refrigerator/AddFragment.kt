package com.rczubak.refrigerator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.addBtn
import kotlinx.android.synthetic.main.layout_product.*

/**
 * A simple [Fragment] subclass.
 */
class AddFragment : Fragment() {

    lateinit var docRef: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addBtn.setOnClickListener {
        docRef = FirebaseFirestore.getInstance().document("simpleData/product")



        }
    }

    private fun saveProduct(){
        val name = newNameTxt.text.toString()
        val quantity = newQuantityTxt.text.toString()
        val date = dateTxt.toString()

        println("Clicked")
        if(!name.isEmpty() && !quantity.isEmpty() && !date.isEmpty()){
            var dataToSave = mapOf("Name" to name, "quantity" to quantity, "date" to date)
            docRef.set(dataToSave).addOnSuccessListener {
                Toast.makeText(context,"Product added!", Toast.LENGTH_LONG).show()
                println("Poszło")
            }.addOnFailureListener {
                Toast.makeText(context,"Product adding failed!", Toast.LENGTH_LONG).show()
                println("nieposzło")
            }
        }
    }


}
