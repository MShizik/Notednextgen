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

class RegistrationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userModel : RegistrationDataModel = RegistrationDataModel("","","")
        val viewRegistration = RegistrationView(view)

        val btnNext : Button = view.findViewById(R.id.registration_btn_next)
        val btnBack : Button = view.findViewById(R.id.registration_btn_back)

        var iState : Int = 0
        var error : Boolean = false

        btnNext.setOnClickListener {
            when ( iState ){
                0->{
                    userModel?.setEmail(viewRegistration.getTextMainField())
                    error = if ( userModel.checkEmail() ){
                        iState++
                        false
                    } else true
                }
                1->{
                    error = if ( userModel.checkKeyWord() ){
                        iState++
                        false
                    } else true
                }

                2->{
                    error = if ( userModel.checkPassword() ){
                        iState++
                        false
                    } else true
                }

                3->{
                    if ( userModel.checkRepeatPassword(viewRegistration.getTextMainField()) ){
                        userModel.writeDataToDatabase()
                        userModel.saveUserData(requireContext())
                        var intentToWorkActivity = Intent(this.requireContext(), LoaderActivity::class.java)
                        intentToWorkActivity.putExtra("userModel", userModel)
                        startActivity(Intent(this.requireContext(), LoaderActivity::class.java))
                    }
                    else error = true

                }
            }
            viewRegistration.changeStep(iState + 1)
            if( error ) viewRegistration.showError(101 + iState)
        }

        btnBack.setOnClickListener {
            when( iState ){
                0->{
                    var fragmentLogIn = LoginFragment()
                    var transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.auth_fragment_holder,fragmentLogIn).commit()
                }
                1->viewRegistration.setTextMainHint(R.string.registration_email_hint.toString())
                2->viewRegistration.setTextMainHint(R.string.registration_key_word_hint.toString())
                3->viewRegistration.setTextMainHint(R.string.registration_password_hint.toString())
            }
            viewRegistration.setTextMessage(R.string.registration_tv_greeting.toString())
            iState--
        }
    }



    companion object {

    }
}