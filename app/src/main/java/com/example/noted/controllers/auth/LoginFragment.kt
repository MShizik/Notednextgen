package com.example.noted.controllers.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val tvSignUp : TextView = view.findViewById(R.id.login_tv_signup)
        val tvResetPassword : TextView = view.findViewById(R.id.login_tv_forget_password)



        btnLogIn.setOnClickListener {
            val preferencesUserData = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
            if((preferencesUserData.contains("email") and preferencesUserData.contains("password")))
                userModel = UserDataModel(preferencesUserData.getString("user",loginView.getTextEmailField()).toString(), preferencesUserData.getString("password",loginView.getTextPasswordField()).toString())


            userModel = UserDataModel(loginView.getTextEmailField(), loginView.getTextPasswordField())
            runBlocking {
                val job = launch {
                    var dsUserInfoFromDB = getUserInfo(userModel = userModel!!)
                    if (dsUserInfoFromDB == null) {
                        userModel!!.setEmailValid(false)
                        loginView.setTextErrorMessage(resources.getString(R.string.login_unknown_user_message))
                        return@launch
                    }
                    userModel!!.setEmailValid(true)
                    userModel!!.checkUserValidation(dsUserInfoFromDB)
                }
                job.join()
            }



            if (userModel?.getPassValid() == true){
                if(!(preferencesUserData.contains("email") and preferencesUserData.contains("password")))
                    userModel?.saveUserData(context = requireContext())
                var intentToWorkActivity = Intent(this.requireContext(), LoaderActivity::class.java)
                intentToWorkActivity.putExtra("userModel", userModel)
                startActivity(intentToWorkActivity)
            }
            else{
                if (userModel?.getEmailValid() == true) {
                    loginView.setTextErrorMessage(resources.getString(R.string.login_wrong_password_message))
                }
            }
        }

        tvResetPassword.setOnClickListener{
            var fragmentResetPassword = PasswordForgetFragment()
            var transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.auth_fragment_holder,fragmentResetPassword).commit()
        }

        tvSignUp.setOnClickListener{
            var fragmentRegistration = RegistrationFragment()
            var transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.auth_fragment_holder,fragmentRegistration).commit()
        }


    }

    suspend fun getUserInfo(userModel : UserDataModel) : DataSnapshot? {
        var result : DataSnapshot? = null
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users")
        ref.child(userModel.getEmail()).get().addOnSuccessListener {
            result = it
        }
        return result
    }


}

