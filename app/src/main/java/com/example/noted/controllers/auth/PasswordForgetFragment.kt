package com.example.noted.controllers.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.noted.R
import com.example.noted.controllers.notes.LoaderActivity
import com.example.noted.model.auth.ResetPasswordModel
import com.example.noted.model.auth.UserDataModel
import com.example.noted.views.auth.ForgetPasswordView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


class PasswordForgetFragment : Fragment() {

    var result : DataSnapshot? = null
    val database = FirebaseDatabase.getInstance()
    val ref = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password_forget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewForgetPassword = ForgetPasswordView(view,requireContext());
        var userDataModel : ResetPasswordModel? = null;

        val btnNext : Button = view.findViewById(R.id.reset_btn_reset)
        val btnBack : AppCompatButton = view.findViewById(R.id.reset_btn_back)

        var iState = 0;

        btnNext.setOnClickListener {
            if(iState == 0) {
                userDataModel = ResetPasswordModel(
                    viewForgetPassword.getTextFirstField(),
                    viewForgetPassword.getTextSecondField(),
                    "wrong"
                )
                runBlocking {
                    val job = launch {
                        var dsUserInfoFromDB = ref.child(userDataModel!!.getEmail()).get().addOnSuccessListener {}.await()
                        if(dsUserInfoFromDB == null){
                            viewForgetPassword.setTextErrorMessage(resources.getString(R.string.reset_unknown_user_message))
                            return@launch
                        }
                        userDataModel!!.setEmailValid(true)
                        userDataModel!!.checkUserValidation(dsUserInfoFromDB)
                    }
                    job.join()
                }

                if (userDataModel?.getPassValid() == true) {
                    viewForgetPassword.changeToNextStep()
                    iState = 1
                }
                else if(userDataModel?.getEmailValid() == true) viewForgetPassword.showBaseErrorMessage()

            }
            else{
                if(viewForgetPassword.getTextFirstField() == viewForgetPassword.getTextSecondField()) {
                    userDataModel?.setPassword(viewForgetPassword.getTextFirstField())
                    if(userDataModel?.checkPassword() == true){
                        userDataModel?.changeInfoInDatabase()
                        userDataModel?.changeRememberedInfo(requireContext())
                        var intentToWorkActivity = Intent(this.requireContext(), LoaderActivity::class.java)
                        intentToWorkActivity.putExtra("userModel", userDataModel)
                        startActivity(intentToWorkActivity)
                    }
                    else viewForgetPassword.showPasswordShortErrorMessage()
                }
                else viewForgetPassword.showPasswordMatchErrorMessage()

            }
        }

        btnBack.setOnClickListener {
            if (iState == 0){
                var fragmentLogIn = LoginFragment()
                var transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.auth_fragment_holder,fragmentLogIn).commit()
            }
            else viewForgetPassword.changeToBackStep()

        }
    }

    companion object {

    }
}