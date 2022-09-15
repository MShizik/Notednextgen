package com.example.noted.controllers.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noted.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        var fragmentToChange = LoginFragment()
        var tmpBundle : Bundle = Bundle()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.auth_fragment_holder, fragmentToChange)
                .commit()

    }
}