package com.example.noted.controllers.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.noted.R
import com.example.noted.controllers.notes.LoaderActivity
import com.example.noted.model.auth.UserDataModel
import com.example.noted.views.auth.LoginView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*


class LoginFragment : Fragment() {

    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userModel : UserDataModel? = null
        var loginView : LoginView = LoginView(view)

        val btnLogIn : Button = view.findViewById(R.id.login_btn_login)
        val btnSignIn : Button = view.findViewById(R.id.login_btn_signin)
        val tvResetPassword : TextView = view.findViewById(R.id.login_tv_forget_password)


        val preferencesUserSign = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)


        if(preferencesUserSign.contains("email") and preferencesUserSign.contains("password")) {
            userModel = UserDataModel(preferencesUserSign.getString("email","default").toString(), preferencesUserSign.getString("password","default").toString())

            runBlocking {
                var dsUserInfoFromDB = getUserInfo(userModel = userModel!!)
                userModel!!.checkUserValidation(dsUserInfoFromDB)
            }

            if (userModel?.getUserValidation()){
                var intentToWorkActivity = Intent(this.requireContext(), LoaderActivity::class.java)
                intentToWorkActivity.putExtra("userModel", userModel)
                startActivity(intentToWorkActivity)
            }


        }

        btnLogIn.setOnClickListener {
            userModel = UserDataModel(loginView.getTextEmailField(), loginView.getTextPasswordField())
            runBlocking {
                var dsUserInfoFromDB = getUserInfo(userModel = userModel!!)
                   userModel!!.checkUserValidation(dsUserInfoFromDB)
            }

            if (userModel?.getUserValidation() == true){
                userModel?.writeUser(requireContext())
                var intentToWorkActivity = Intent(this.requireContext(), LoaderActivity::class.java)
                intentToWorkActivity.putExtra("userModel", userModel)
                startActivity(intentToWorkActivity)
            }
            else{
                loginView.setTextErrorMessage(R.string.login_unknown_user_message.toString())
            }
        }

        btnSignIn.setOnClickListener{
            var fragmentSignIn = RegistrationFragment()
            var transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.auth_fragment_holder, fragmentSignIn)
        }

        tvResetPassword.setOnClickListener{
            var fragmentResetPassword = PasswordForgetFragment()
            var transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.auth_fragment_holder,fragmentResetPassword).commit()
        }




    }

    suspend fun getUserInfo(userModel : UserDataModel) : DataSnapshot {
        lateinit var result : DataSnapshot
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users")
        ref.child(userModel.getEmail()).get().addOnSuccessListener {
            result = it
        }.await()
        return result
    }


}

