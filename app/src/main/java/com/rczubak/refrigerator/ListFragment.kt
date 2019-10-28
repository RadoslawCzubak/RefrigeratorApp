package com.rczubak.refrigerator


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    lateinit var db: FirebaseFirestore
    lateinit var collection: CollectionReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        val user = FirebaseAuth.getInstance().currentUser!!.email.toString()
        println(user.toString())
        db = FirebaseFirestore.getInstance()
        collection = db.collection("users").document(user).collection("products")

        setupRecyclerView()

    }

    private fun setupRecyclerView() {

        val query = collection.orderBy("name")

        val adapter = FSProductAdapter(
            FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product::class.java)
                .build()
        )

        recyclerViewProducts.layoutManager = LinearLayoutManager(context)
        recyclerViewProducts.adapter = adapter

        adapter.startListening()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.info_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val itemId = item.itemId

        if (itemId == R.id.infoItem) {
            Navigation.findNavController(view!!).navigate(R.id.action_ListFragment_to_infoFragment)
        }

        return super.onOptionsItemSelected(item)
    }

}

