package com.slideproject.sidekotlin

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slideproject.sidekotlin.databinding.ActivityRecyclerViewBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.collections.ArrayList

class RecyclerViewActivity : AppCompatActivity() {

    private var TAG = "RecyclerViewActivity"
    private lateinit var binding: ActivityRecyclerViewBinding
    var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var stockAdapterＣontainer: StockAdapter
    val mStockList: ArrayList<Stock> = ArrayList<Stock>()

    var messageDate: String? = ""
    var messageName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Get the Intent that started this activity and extract the string
        messageDate = intent.getStringExtra(EXTRA_MESSAGE_DATE)
        messageName = intent.getStringExtra(EXTRA_MESSAGE_NAME)
        Log.d(TAG, "Receive RecyclerViewActivity content : $messageDate , $messageName")

        prepareStockList()

        CoroutineScope(Dispatchers.IO).launch {
            storeData()
            getLoginData()
        }

        stockAdapterＣontainer = StockAdapter(mStockList)
        stockAdapterＣontainer.notifyDataSetChanged()
        binding.recyclerView.adapter = stockAdapterＣontainer
        layoutManager = LinearLayoutManager(binding.recyclerView.context)
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun prepareStockList() {
        var stock1 = Stock("中信金", "預計 25", "0.25", "1.16")
        mStockList.add(stock1)
        var stock2 = Stock("華南金", "預計 23", "0.15", "0.16")
        mStockList.add(stock2)
        var stock3 = Stock("第一金", "預計 30", "-0.25", "-1.16")
        mStockList.add(stock3)
        var stock4 = Stock("永豐金", "預計 25", "1.25", "1.26")
        mStockList.add(stock4)
        var stock5 = Stock("玉山金", "預計 30", "3.25", "1.36")
        mStockList.add(stock5)
    }

    private suspend fun getLoginData() {
        Log.d(TAG, "getLoginData: execute")
        loginDataStore.data
            .collect {
                Log.d(TAG, "DataStore - Proto Data")
                Log.d(TAG, "username : ${it.userName}")
                Log.d(TAG, "loginTime : ${it.loginTime}")
                Log.d(TAG, "stock List size : ${it.stockListSize}")
            }
    }

    private suspend fun storeData() {
        Log.d(TAG, "storeData: execute")
        loginDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setLoginTime(messageDate)
                .setUserName(messageName)
                .setStockListSize(mStockList.size)
                .build()
        }
    }

    private val Context.loginDataStore: DataStore<Login> by dataStore(
        fileName = "login_settings.pb",
        serializer = SettingsSerializer
    )
}