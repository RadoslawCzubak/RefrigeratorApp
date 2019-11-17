package com.rczubak.refrigerator


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.emailTxt
import kotlinx.android.synthetic.main.fragment_registration.loginBtnL
import kotlinx.android.synthetic.main.fragment_registration.passTxt
import kotlinx.android.synthetic.main.fragment_registration.registerBtnL


class RegistrationFragment : Fragment() {
    
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var password2: String
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        registerBtnL.setOnClickListener {

            password2 = confirmTxt.text.toString()
            email = emailTxt.text.toString()
            password = passTxt.text.toString()

            println(email + " " + password + " " + password2)
            if (password != password2) {
                Toast.makeText(this.context,R.string.password_diff , Toast.LENGTH_SHORT).show()

            } else if (password.length < 8 || password.toLowerCase() == password) {
                Toast.makeText(
                    context,
                    R.string.password_req,
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                if (password2 != null && password != null && email != null) {


                    val view = it


                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {

                            val product = mapOf("Name" to "First product")
                            FirebaseFirestore.getInstance()
                                .document("/users/" + FirebaseAuth.getInstance().currentUser!!.email.toString())
                                .set(product)
                            Toast.makeText(context, R.string.reg_success, Toast.LENGTH_LONG)
                                .show()
                            Navigation.findNavController(view)
                                .navigate(R.id.action_registrationFragment_to_loginFragment)
                        }

                }

            }
        }

        loginBtnL.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registrationFragment_to_loginFragment))
    }

}
