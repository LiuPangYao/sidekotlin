package com.slideproject.sidekotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slideproject.sidekotlin.databinding.ActivityRecyclerViewBinding
import kotlin.collections.ArrayList

class RecyclerViewActivity : AppCompatActivity() {

    private var TAG = "RecyclerViewActivity"
    private lateinit var binding: ActivityRecyclerViewBinding

    var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var stockAdapterＣontainer: StockAdapter

    val mStockList: ArrayList<Stock> = ArrayList<Stock>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        Log.d(TAG, "Receive RecyclerViewActivity content : $message")

        prepareStockList()

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
}