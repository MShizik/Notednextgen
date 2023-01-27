package com.example.noted.controllers.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.noted.R
import com.example.noted.controllers.notes.LoaderActivity
import com.example.noted.model.auth.RegistrationDataModel
import com.example.noted.views.auth.RegistrationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class RegistrationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userModel = RegistrationDataModel(stKeyWord = "", stEmail = "", stPassword = "")
        val viewRegistration = RegistrationView(view,resources)

        val btnNext : Button = view.findViewById(R.id.registration_btn_next)
        val btnBack : Button = view.findViewById(R.id.registration_btn_back)

        var iState = 0
        var error = false

        btnNext.setOnClickListener {
            when ( iState ){
                0->{
                    userModel.setEmail( stEmail = viewRegistration.getTextMainField())
                    var bCheckIfUserExist = false
                    runBlocking {
                        val job = launch {
                            bCheckIfUserExist = FirebaseDatabase.getInstance().reference.child(userModel.getEmail()).get().addOnSuccessListener {}.await().value != null
                            error = if ( userModel.checkEmail() &&  !bCheckIfUserExist){
                                iState++
                                false
                            } else true
                        }
                        job.join()
                    }
                }
                1->{
                    userModel.setKeyWord(stKeyWord =  viewRegistration.getTextMainField())
                    error = if ( userModel.checkKeyWord() ){
                        iState++
                        false
                    } else true
                }

                2->{
                    userModel.setPassword(stPassword = viewRegistration.getTextMainField())
                    error = if ( userModel.checkPassword() ){
                        iState++
                        false
                    } else true
                }

                3->{
                    if ( userModel.checkRepeatPassword(stRepeatedPassword = viewRegistration.getTextMainField()) ){
                        userModel.writeDataToDatabase()
                        userModel.saveUserData(requireContext())
                        var intentToWorkActivity = Intent(this.requireContext(), LoaderActivity::class.java)
                        intentToWorkActivity.putExtra("userModel", userModel)
                        startActivity(intentToWorkActivity)
                    }
                    else error = true

                }
            }
            if( error ) viewRegistration.showError(iErrorID = 101 + iState)
            else viewRegistration.changeStep(iStepCount = iState + 1)

        }

        btnBack.setOnClickListener {
            when( iState ){
                0->{
                    var fragmentLogIn = LoginFragment()
                    var transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.auth_fragment_holder,fragmentLogIn).commit()
                }
                1->viewRegistration.setTextMainHint(resources.getString(R.string.registration_email_hint))
                2->viewRegistration.setTextMainHint(resources.getString(R.string.registration_key_word_hint))
                3->viewRegistration.setTextMainHint(resources.getString(R.string.registration_password_hint))
            }
            viewRegistration.setTextMessage(resources.getString(R.string.registration_tv_greeting))
            iState--
        }
    }

}