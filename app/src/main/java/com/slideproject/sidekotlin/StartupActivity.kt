package com.slideproject.sidekotlin

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.slideproject.sidekotlin.data.UserManager
import com.slideproject.sidekotlin.data.dataStore
import com.slideproject.sidekotlin.databinding.StartupActivityBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


const val EXTRA_MESSAGE_DATE = "com.sideproject.sidekotlin.MESSAGE.DATE"
const val EXTRA_MESSAGE_NAME = "com.sideproject.sidekotlin.MESSAGE,NAME"

class StartupActivity : AppCompatActivity() {

    private lateinit var binding: StartupActivityBinding
    lateinit var userManager: UserManager
    var userAge = 0
    var userName = ""
    var userCompany = ""
    var CHANNEL_ID = "NOTIFICATION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StartupActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        userManager = UserManager(dataStore)
        observeData()
        buttonSave()
        testCsv()

        binding.buttonShare.setOnClickListener {
            val intent = Intent(this, ShareActivity::class.java).apply {
            }
            startActivity(intent)
        }

        // notification
        binding.buttonNotification.setOnClickListener{
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            val builder: Notification.Builder = Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_help)
                .setColor(ContextCompat.getColor(this, R.color.holo_red_dark))
                .setContentTitle("Test Title")
                .setContentText("This is a test of text")

            val channel: NotificationChannel

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = NotificationChannel(
                    "TEST_NOTIFY_ID", "Notify Test", NotificationManager.IMPORTANCE_HIGH
                )
                builder.setChannelId("TEST_NOTIFY_ID")
                manager.createNotificationChannel(channel)
            } else {
                builder.setDefaults(Notification.DEFAULT_ALL)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
            }

            manager.notify(0, builder.build())
        }
    }

    private fun observeData() {
        //Updates age
        userManager.userAgeFlow.asLiveData().observe(this, {
            if (it != null) {
                userAge = it
                binding.etAge.setText(it.toString())
            }
        })

        //Updates name
        userManager.userNameFlow.asLiveData().observe(this, {
            if (it != null) {
                userName = it
                binding.etName.setText(it)
            }
        })

        //Updates company
        userManager.userCompanyFlow.asLiveData().observe(this, {
            if (it != null) {
                userCompany = it
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

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val intent = Intent(this, RecyclerViewActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE_DATE, currentDate)
                putExtra(EXTRA_MESSAGE_NAME, userName)
            }
            startActivity(intent)
        }
    }

    private fun testCsv() {
        binding.btnCsvTest.setOnClickListener {
            val intent = Intent(this, CsvActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }
}