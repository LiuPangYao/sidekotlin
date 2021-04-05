package com.slideproject.sidekotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slideproject.sidekotlin.asset.AssetData
import com.slideproject.sidekotlin.databinding.AssetItemListBinding

class AssetAdapter (private var assetList: ArrayList<AssetData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class itemHolder(var viewBinding: AssetItemListBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            AssetItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return itemHolder(binding)
    }

    override fun getItemCount(): Int {
        return assetList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder = holder as itemHolder
        holder.viewBinding.txtRank.text = assetList[position].rank
        holder.viewBinding.txtName.text = assetList[position].name
        holder.viewBinding.txtAsset.text = assetList[position].asset
        holder.viewBinding.txtAssetCompany.text = assetList[position].company
    }
}