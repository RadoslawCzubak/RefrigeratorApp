package com.rczubak.refrigerator


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var email: String;
    private lateinit var password: String;
    private lateinit var mAuth: FirebaseAuth;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()





        loginBtnL.setOnClickListener {

            email = emailTxt.text.toString()
            password = passTxt.text.toString()

            println(email+" "+ password)
            if(password!=null && email!=null){
                mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener{
                    Navigation.findNavController(view!!).navigate(R.id.action_loginFragment_to_listFragment)

                }
            }
        }

        registerBtnL.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registrationFragment)
        }


    }


}
