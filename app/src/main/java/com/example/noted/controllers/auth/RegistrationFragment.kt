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

        btnNext.setOnClickListener {
            when ( iState ){
                0->{
                    userModel?.setEmail(viewRegistration.getTextMainField())
                    if ( userModel.checkEmail() ){
                        viewRegistration.setTextMainHint(R.string.registration_key_word_hint.toString())
                        iState++
                    }
                    else viewRegistration.setTextErrorMessage(R.string.registration_wrong_email_message.toString())
                }
                1->{
                    if ( userModel.checkKeyWord() ){
                        viewRegistration.setTextMainHint(R.string.registration_password_hint.toString())
                        iState++
                    }
                    else viewRegistration.setTextErrorMessage(R.string.registration_wrong_key_word_message.toString())
                }

                2->{
                    if ( userModel.checkPassword() ){
                        viewRegistration.setTextMainHint(R.string.registration_repeat_password_hint.toString())
                        iState++
                    }
                    else viewRegistration.setTextErrorMessage(R.string.registration_wrong_password.toString())
                }

                3->{
                    if ( userModel.checkRepeatPassword(viewRegistration.getTextMainField()) ){
                        userModel?.writeDataToDatabase()
                        var intentToWorkActivity = Intent(this.requireContext(), LoaderActivity::class.java)
                        intentToWorkActivity.putExtra("userModel", userModel)
                        startActivity(Intent(this.requireContext(), LoaderActivity::class.java))
                    }
                    else viewRegistration.setTextErrorMessage(R.string.registration_wrong_repeat_password.toString())

                }
            }
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