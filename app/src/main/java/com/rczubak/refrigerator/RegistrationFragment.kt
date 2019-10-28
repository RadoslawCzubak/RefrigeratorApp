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

/**
 * A simple [Fragment] subclass.
 */
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
        println("instance alive!")
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



          registerBtnL.setOnClickListener {

              password2 = confirmTxt.text.toString()
              email = emailTxt.text.toString()
              password = passTxt.text.toString()
              println("clicked")
              println(email +" "+ password+ " " +password2)
              if(password != password2){
                  Toast.makeText(this.context,"Passwords are different!", Toast.LENGTH_SHORT).show()

              }
              else if (password.length<8 || password.toLowerCase() == password ){
                  Toast.makeText(context,"Hasło musi mieć 8 znaków i 1 wielką litere", Toast.LENGTH_SHORT).show()
              }
              else{

                  if(password2!=null && password!=null &&    email!=null) {


                       val view = it
                      println("Po nullach")

                      auth.createUserWithEmailAndPassword(email, password)
                          .addOnSuccessListener {

                              val product = mapOf("Name" to "First product")
                              FirebaseFirestore.getInstance().document("/users/"+ FirebaseAuth.getInstance().currentUser!!.email.toString()).set(product)
                              Toast.makeText(context,"Registration successful!", Toast.LENGTH_LONG).show()
                              Log.d(TAG, "registration succesfill")
                              Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_loginFragment)
                      }

                  }




              }
        }

        loginBtnL.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registrationFragment_to_loginFragment))
    }

}
