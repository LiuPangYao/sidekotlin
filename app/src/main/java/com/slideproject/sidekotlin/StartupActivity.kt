package com.slideproject.sidekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.slideproject.sidekotlin.data.UserManager
import com.slideproject.sidekotlin.data.dataStore
import com.slideproject.sidekotlin.databinding.StartupActivityBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StartupActivity : AppCompatActivity() {

    lateinit var userManager: UserManager

    var userAge = 0
    var userName = ""
    var userCompany = ""

    /*lateinit var et_age : EditText
    lateinit var et_name : EditText
    lateinit var et_company : EditText
    lateinit var btn_save : Button*/

    private lateinit var binding: StartupActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StartupActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*et_age = findViewById(R.id.et_age)
        et_name = findViewById(R.id.et_name)
        et_company = findViewById(R.id.et_company)

        btn_save = findViewById(R.id.btn_save)*/

        //Get reference to our userManager class
        userManager = UserManager(dataStore)

        buttonSave()
        observeData()
    }

    private fun observeData() {
        //Updates age
        userManager.userAgeFlow.asLiveData().observe(this, {
            if (it != null) {
                userAge = it
                //binding.tvAge.text = it.toString()
                binding.etAge.setText(it.toString())
            }
        })

        //Updates name
        userManager.userNameFlow.asLiveData().observe(this, {
            if (it != null) {
                userName = it
                //binding.tvName.text = it
                binding.etName.setText(it)
            }
        })

        //Updates company
        userManager.userCompanyFlow.asLiveData().observe(this, {
            if (it != null) {
                userCompany = it
                //binding.tvCompany.text = it
                binding.etCompany.setText(it)
            }
        })
    }

    private fun buttonSave() {
        //Gets the user input and saves it
        binding.btnSave.setOnClickListener {
            userName = binding.etName.text.toString()
            userCompany = binding.etCompany.text.toString()
            userAge = binding.etAge.text.toString().toInt()

            //Stores the values
            GlobalScope.launch {
                userManager.storeUser(userAge, userName, userCompany)
            }
        }
    }
}