package com.bhaskar.photobook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bhaskar.photobook.databinding.MainRecyclerRowLayoutBinding
import com.bhaskar.photobook.models.ApiModel
import com.bhaskar.photobook.models.ApiModelItem

class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.MyViewHolder>() {
    private lateinit var binding: MainRecyclerRowLayoutBinding
    private var data = ApiModel()
    class MyViewHolder (private val dataBinding: MainRecyclerRowLayoutBinding): RecyclerView.ViewHolder(dataBinding.root) {
        fun bind (dataElement: ApiModelItem) {
            dataBinding.data = dataElement
            dataBinding.executePendingBindings()
        }
    }

    fun setData(dataObject: ApiModel) {
        data = dataObject
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = MainRecyclerRowLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}