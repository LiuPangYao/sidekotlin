package com.slideproject.sidekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.slideproject.sidekotlin.data.UserManager
import com.slideproject.sidekotlin.data.dataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StartupActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    var age = 0
    var name = ""
    var company = ""

    lateinit var tv_age : TextView
    lateinit var tv_company : TextView
    lateinit var tv_name : TextView

    lateinit var et_age : EditText
    lateinit var et_name : EditText
    lateinit var et_company : EditText

    lateinit var btn_save : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        tv_age = findViewById(R.id.tv_age)
        tv_company = findViewById(R.id.tv_company)
        tv_name = findViewById(R.id.tv_name)

        et_age = findViewById(R.id.et_age)
        et_name = findViewById(R.id.et_name)
        et_company = findViewById(R.id.et_company)

        btn_save = findViewById(R.id.btn_save)

        //Get reference to our userManager class
        userManager = UserManager(dataStore)
        buttonSave()
        observeData()
    }

    private fun observeData() {

        //Updates age
        userManager.userAgeFlow.asLiveData().observe(this, {
            if (it != null) {
                age = it
                tv_age.text = it.toString()
            }
        })

        //Updates name
        userManager.userNameFlow.asLiveData().observe(this, {
            if (it != null) {
                name = it
                tv_name.text = it
            }
        })

        //Updates company
        userManager.userCompanyFlow.asLiveData().observe(this, {
            if (it != null) {
                company = it
                tv_company.text = it
            }
        })
    }

    private fun buttonSave() {
        //Gets the user input and saves it
        btn_save.setOnClickListener {
            name = et_name.text.toString()
            company = et_company.text.toString()
            age = et_age.text.toString().toInt()

            //Stores the values
            GlobalScope.launch {
                userManager.storeUser(age, name, company)
            }
        }
    }
}