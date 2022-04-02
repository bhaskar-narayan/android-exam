package com.bhaskar.photobook.viewmodels

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bhaskar.photobook.clients.ApiClient
import com.bhaskar.photobook.constants.Logs
import com.bhaskar.photobook.constants.Logs.API_CALL_ERROR
import com.bhaskar.photobook.models.ApiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class MainViewModel: ViewModel() {
    private var data = MutableLiveData<ApiModel>()
    private var visibility = false

    fun getDataObserver(): MutableLiveData<ApiModel> {
        return data
    }

    fun callListImageApi(page: Int, limit: Int) {
        val apiCall = ApiClient.create().getAllData(1, 20)
        visibility = true
        apiCall.enqueue(object : Callback<ApiModel> {
            override fun onFailure(call: Call<ApiModel>, t: Throwable) {
                Log.d(API_CALL_ERROR, "Api Call Error: $t")
            }
            override fun onResponse(call: Call<ApiModel>, response: Response<ApiModel>) {
                if (response.isSuccessful && response.body() != null) {
                    data.value = response.body()
                    Log.d(Logs.API_CALL_RESPONSE, response.body().toString())
                    visibility = false
                }
            }
        })
    }
}