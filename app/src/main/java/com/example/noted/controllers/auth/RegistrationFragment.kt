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

        var dmRegistrationModel : RegistrationDataModel = RegistrationDataModel()
        var vRegistration : RegistrationView = RegistrationView(view)

        val btnNextStep : Button = view.findViewById(R.id.registration_btn_next)
        val btnBackStep : Button = view.findViewById(R.id.registration_btn_back)

        var countSteps : Int = 1

        btnNextStep.setOnClickListener {
            when(countSteps){
                1->{
                    if(dmRegistrationModel.checkEmail(vRegistration.getTextMainField())){
                        dmRegistrationModel.setEmail(vRegistration.getTextMainField());
                        vRegistration.changeStep(++countSteps)
                    }
                    else{
                        vRegistration.showError(101)
                    }
                }
                2->{
                    if(dmRegistrationModel.checkKeyWord(vRegistration.getTextMainField())){
                        dmRegistrationModel.setKeyWord(vRegistration.getTextMainField())
                        vRegistration.changeStep(++countSteps)
                    }else{
                        vRegistration.showError(102)
                    }
                }
                3->{
                    if(dmRegistrationModel.checkPassword(vRegistration.getTextMainField())){
                        dmRegistrationModel.setPassword(vRegistration.getTextMainField())
                        vRegistration.changeStep(++countSteps)
                    }else{
                        vRegistration.showError(103)
                    }
                }
                4->{
                    if(dmRegistrationModel.getPassword().equals(vRegistration.getTextMainField())){
                        dmRegistrationModel.writeDataToDatabase()
                        dmRegistrationModel.writeUser(requireContext())
                        var intentToWorkActivity = Intent(this.requireContext(), LoaderActivity::class.java)
                        intentToWorkActivity.putExtra("userModel", dmRegistrationModel.createUserDataModel())
                        startActivity(intentToWorkActivity)
                    }
                    else{
                        vRegistration.showError(104)
                    }
                }
            }
        }
        btnBackStep.setOnClickListener {
            if(countSteps==1){
                var fragmentLogIn = LoginFragment()
                var transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.auth_fragment_holder, fragmentLogIn)
            }
            else {
                vRegistration.changeStep(--countSteps)
            }
        }
    }


}