package com.rczubak.refrigerator


import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_add.*


class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {


    lateinit var docRef: CollectionReference
    lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            context!!,
            R.array.food_array,
            android.R.layout.simple_spinner_dropdown_item
        ) //,  android.R.layout.simple_spinner_dropdown_item
        food_spinner.adapter = adapter
        food_spinner.onItemSelectedListener = this
        addProductBtn.setOnClickListener {
            docRef = FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().currentUser!!.email.toString())
                .collection("products")
            saveProduct()
        }

        setHasOptionsMenu(true)


    }

    private fun saveProduct() {
        val name = newNameTxt.text.toString()
        val quantity = newQuantityTxt.text.toString()
        val date = dateTxt.text.toString()
        println(quantity.toInt())

        if (!name.trim().isEmpty() && !quantity.trim().isEmpty() && !date.trim().isEmpty() && dateFormatCheck(
                date
            ) && quantity.trim().toInt() > 0
        ) {
            docRef.add(Product(name, Integer.parseInt(quantity), category, date))
                .addOnSuccessListener {
                    Toast.makeText(context, R.string.product_added, Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(context, R.string.product_failed, Toast.LENGTH_SHORT).show()
                }

            Navigation.findNavController(view!!).navigate(R.id.ListFragment)
        } else if (name.trim().isEmpty()) {
            Toast.makeText(context, R.string.product_name_empty, Toast.LENGTH_SHORT).show()
        } else if (quantity.trim().isEmpty() || quantity.trim().toInt() <= 0) {
            Toast.makeText(context, R.string.product_quantity_empty, Toast.LENGTH_SHORT).show()
        } else if (date.trim().isEmpty() || dateFormatCheck(date)) {
            Toast.makeText(context, R.string.product_date_empty, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, R.string.product_failed, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.addmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val itemid = item.itemId

        if (itemid == R.id.saveItem) {
            docRef = FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().currentUser!!.email.toString())
                .collection("products")
            saveProduct()
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        //doNothing
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        category = parent?.getItemAtPosition(position).toString()
    }

    private fun dateFormatCheck(date: String): Boolean {

        if (date[2] != '.' || date[2] != '.' || date.length != 10) {
            Toast.makeText(context, R.string.wrong_date_format_toast, Toast.LENGTH_SHORT).show()
            return false
        }

        val day: Int
        val month: Int
        val year: Int

        val dayString = date.substring(0, 2)
        val monthString = date.substring(3, 5)


        try {
            day = Integer.parseInt(dayString)
            month = Integer.parseInt(monthString)


        } catch (e: NumberFormatException) {
            Toast.makeText(context, R.string.date_nan, Toast.LENGTH_SHORT).show()
            return false
        }


        if (day > 31 || day < 1 || month > 12 || day < 1) {
            Toast.makeText(context, R.string.wrong_date, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
