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

        val viewForgetPassword = ForgetPasswordView(view);
        var userDataModel : ResetPasswordModel? = null;

        val btnNext : Button = view.findViewById(R.id.reset_btn_reset)
        val btnBack : AppCompatButton = view.findViewById(R.id.reset_btn_back)

        var iState : Int = 0;

        btnNext.setOnClickListener {
            if(iState == 0) {
                userDataModel = ResetPasswordModel(
                    viewForgetPassword.getTextFirstField(),
                    viewForgetPassword.getTextSecondField(),
                    "wrong"
                )
                runBlocking {
                    val job = launch {
                        var dsUserInfoFromDB = getUserInfo(userModel = userDataModel!!)
                        if(dsUserInfoFromDB == null){
                            viewForgetPassword.setTextErrorMessage(R.string.reset_unknown_user_message.toString())
                            return@launch
                        }
                        userDataModel!!.checkUserValidation(dsUserInfoFromDB)
                    }
                    job.join()
                }

                if (userDataModel?.userValidation == true) {
                    viewForgetPassword.changeToNextStep()
                    iState = 1
                }
                else viewForgetPassword.showBaseErrorMessage()
            }
            else{
                if(viewForgetPassword.getTextFirstField() == viewForgetPassword.getTextSecondField()) {
                    userDataModel?.setPassword(viewForgetPassword.getTextFirstField())
                    if(userDataModel?.checkPassword() == true){
                        userDataModel?.changeInfoInDatabase()
                        userDataModel?.changeRememberedInfo(requireContext())
                        var intentToWorkActivity = Intent(this.requireContext(), LoaderActivity::class.java)
                        intentToWorkActivity.putExtra("userModel", userDataModel)
                        startActivity(Intent(this.requireContext(), LoaderActivity::class.java))
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

    suspend fun getUserInfo(userModel : UserDataModel) : DataSnapshot {
        lateinit var result : DataSnapshot
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users")
        ref.child(userModel.getEmail()).get().addOnSuccessListener {
            result = it
        }.await()
        return result
    }

    companion object {

    }
}