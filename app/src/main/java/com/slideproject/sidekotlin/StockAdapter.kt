package com.slideproject.sidekotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slideproject.sidekotlin.databinding.StockListItemBinding

class StockAdapter(private var stockList: ArrayList<Stock>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class itemHolder(var viewBinding: StockListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            StockListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return itemHolder(binding)
    }

    override fun getItemCount(): Int {
        return stockList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder = holder as itemHolder
        holder.viewBinding.stockName.text = stockList[position].name
        holder.viewBinding.stockDeal.text = stockList[position].deal
        holder.viewBinding.stockQuoteChange.text = stockList[position].quoteChange
        holder.viewBinding.stockIncrease.text = stockList[position].increase
    }
}