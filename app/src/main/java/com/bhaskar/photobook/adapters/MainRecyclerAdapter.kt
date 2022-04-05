package com.bhaskar.photobook.adapters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhaskar.photobook.databinding.MainRecyclerRowLayoutBinding
import com.bhaskar.photobook.models.ApiModel
import com.bhaskar.photobook.models.ApiModelItem
import com.bhaskar.photobook.ui.DetailApiActivity
import com.bhaskar.photobook.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


class MainRecyclerAdapter(private val context: MainActivity) : RecyclerView.Adapter<MainRecyclerAdapter.MyViewHolder>() {
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
        fun loadImageFromUrl(imageView: AppCompatImageView, url: String) {
            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            var image: Bitmap? = null
            CoroutineScope(Dispatchers.IO).launch {
                executor.execute {
                    try {
                        val `in` = java.net.URL(url).openStream()
                        image = BitmapFactory.decodeStream(`in`)
                        handler.post {
                            imageView.setImageBitmap(image)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
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
        binding.mainAdapterImageView.setOnClickListener {
            val intent = Intent(context, DetailApiActivity::class.java)
            intent.putExtra("id", data[position].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = data.size
}