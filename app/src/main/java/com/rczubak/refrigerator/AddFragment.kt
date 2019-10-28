package com.rczubak.refrigerator


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.layout_product.*

/**
 * A simple [Fragment] subclass.
 */
class AddFragment : Fragment() {

    lateinit var docRef: CollectionReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)


    }

    private fun saveProduct(){
        val name = newNameTxt.text.toString()
        val quantity = newQuantityTxt.text.toString()
        val date = dateTxt.text.toString()

        println("Clicked")
        if(!name.trim().isEmpty() && !quantity.trim().isEmpty() && !date.trim().isEmpty()){
            docRef.add(Product(name,Integer.parseInt(quantity),"nabial",date,1))
            Toast.makeText(context,"Product already added!", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view!!).navigate(R.id.action_AddFragment_to_ListFragment)
        }
        else{
            Toast.makeText(context,"Product adding failed!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.addmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val itemid = item.itemId

        if(itemid==R.id.saveItem)
        {
            docRef = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().currentUser!!.email.toString()).collection("products")
            saveProduct()
        }

        return super.onOptionsItemSelected(item)
    }


}
