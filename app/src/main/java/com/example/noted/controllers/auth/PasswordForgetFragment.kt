package com.example.noted.controllers.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.noted.R
import com.example.noted.controllers.notes.LoaderActivity
import com.example.noted.model.auth.ResetPasswordModel
import com.example.noted.views.auth.ForgetPasswordView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import android.content.Intent


class PasswordForgetFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password_forget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        var UserResetPasswordModel : ResetPasswordModel? = null
        var ForgetPasswordView = ForgetPasswordView(view)
        var bCheckValidation : Boolean = false
        var bPasswordValidation : Boolean = false

        val btnNextStep : Button = view.findViewById(R.id.reset_btn_reset)
        val btnBack : Button = view.findViewById(R.id.reset_btn_back)

        btnNextStep.setOnClickListener{
            if(!bCheckValidation) {

                UserResetPasswordModel = ResetPasswordModel(
                    ForgetPasswordView.getTextFirstField(),
                    ForgetPasswordView.getTextSecondField()
                )
                runBlocking {
                    UserResetPasswordModel!!.checkUserValidation(getUserData(UserResetPasswordModel!!))
                }
                bCheckValidation = UserResetPasswordModel!!.getUserValidation()
                if(!bCheckValidation){
                    ForgetPasswordView.showBaseErrorMessage()
                }
                else{
                    ForgetPasswordView.changeToNextStep()
                }
            }
            else if (!bPasswordValidation){
                if (UserResetPasswordModel!!.checkPassword(ForgetPasswordView.getTextFirstField())){
                    ForgetPasswordView.showPasswordShortErrorMessage()
                }else if (!ForgetPasswordView.getTextFirstField().equals(ForgetPasswordView.getTextSecondField())){
                    ForgetPasswordView.showPasswordMatchErrorMessage()
                }
                else{
                    UserResetPasswordModel!!.changeInfoInDatabase(stPassword = ForgetPasswordView.getTextFirstField())
                    var intentToWorkActivity = Intent(requireContext(), LoaderActivity::class.java)
                    intentToWorkActivity.putExtra("userModel", UserResetPasswordModel!!.getUserDataModel())
                    startActivity(intentToWorkActivity)
                }
            }

        }

        btnBack.setOnClickListener {
            if(!bCheckValidation){
                var fragmentLogIn = LoginFragment()
                var transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.auth_fragment_holder, fragmentLogIn)
            }
            else{
                ForgetPasswordView.changeToBackStep()
                bCheckValidation = false
                bPasswordValidation = false
            }

        }

    }

    suspend fun getUserData(userDataModel : ResetPasswordModel) : DataSnapshot{
        lateinit var result : DataSnapshot
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users")
        ref.child(userDataModel.getEmail()).get().addOnSuccessListener {
            result = it
        }.await()
        return result
    }
}