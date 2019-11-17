package com.rczubak.refrigerator


import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
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
    lateinit var adapter: FSProductAdapter

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
                .build(),
            { count -> showHideNoData(count > 0) }
        )
        this.adapter=adapter

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

        if (itemId == R.id.InfoFragment) {
            view!!.findNavController().navigate(R.id.action_ListFragment_to_InfoFragment,null, NavOptions.Builder().setPopUpTo(R.id.ListFragment, false).build())
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkRecycler(adapter: FSProductAdapter){
        println(adapter.getNumberOfItem())
        if(adapter.getNumberOfItem()==true){
            recyclerViewProducts.visibility = View.INVISIBLE
            emptyTxt.visibility = View.VISIBLE
            emptyImage.visibility = View.VISIBLE
            (emptyImage.drawable as AnimationDrawable).start()
        }

        else{
            recyclerViewProducts.visibility = View.VISIBLE
            emptyTxt.visibility = View.GONE
            emptyImage.visibility = View.GONE
            (emptyImage.drawable as AnimationDrawable).stop()
        }
    }


    fun showHideNoData(haveData: Boolean){
        if (recyclerViewProducts==null){return}
        recyclerViewProducts?.isVisible = haveData
        emptyImage.isVisible = !haveData
        emptyTxt.isVisible = !haveData
        shoppingBtn.isVisible = !haveData

        if(haveData)
        (emptyImage.drawable as AnimationDrawable).stop()
        else{
            (emptyImage.drawable as AnimationDrawable).start()
        }
    }


}

