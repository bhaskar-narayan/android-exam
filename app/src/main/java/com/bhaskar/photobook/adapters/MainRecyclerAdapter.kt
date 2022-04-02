package com.bhaskar.photobook.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhaskar.photobook.databinding.MainRecyclerRowLayoutBinding
import com.bhaskar.photobook.models.ApiModel
import com.bhaskar.photobook.models.ApiModelItem
import com.bumptech.glide.Glide
import java.net.URL


class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.MyViewHolder>() {
    private lateinit var binding: MainRecyclerRowLayoutBinding
    private var data = ApiModel()

    class MyViewHolder(private val dataBinding: MainRecyclerRowLayoutBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(dataElement: ApiModelItem) {
            dataBinding.data = dataElement
            dataBinding.executePendingBindings()
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImageUrl")
        fun loadImageFromUrl(imageView: ImageView, url: String) {
//            val executor = Executors.newSingleThreadExecutor()
//            val handler = Handler(Looper.getMainLooper())
//            var image: Bitmap? = null
//            executor.execute {
//                try {
//                    val `in` = java.net.URL(url).openStream()
//                    image = BitmapFactory.decodeStream(`in`)
//                    handler.post {
//                        imageView.setImageBitmap(image)
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
            Glide.with(imageView)
                .load(url)
                .circleCrop()
                .into(imageView)
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