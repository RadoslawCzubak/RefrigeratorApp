package com.rczubak.refrigerator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val products = mutableListOf(
            Product(1,"Jajka",23,"Nabiał","22.11.2020",1),
            Product(1,"Mleko",23,"Nabiał","28.10.2020",1),
            Product(1,"Chleb",23,"Produkty zbożowe","22.11.2020",1),
            Product(1,"Makaron",23,"Makarony","22.11.2020",1),
            Product(1,"Marchew",23,"Warzywa","22.11.2020",1),
            Product(1,"Jabłko",23,"Owoce","22.11.2020",1),
            Product(1,"Czekolada",23,"Słodycze","22.11.2020",1)

        )

        val auth = FirebaseAuth.getInstance()
        println("username: " + auth.currentUser)

        recyclerViewProducts.layoutManager = LinearLayoutManager(this.context)
        recyclerViewProducts.adapter = ProductAdapter(products)

    }


}
