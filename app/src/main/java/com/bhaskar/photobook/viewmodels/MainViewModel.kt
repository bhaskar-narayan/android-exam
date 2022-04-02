package com.bhaskar.photobook.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bhaskar.photobook.adapters.MainRecyclerAdapter
import com.bhaskar.photobook.clients.ApiClient
import com.bhaskar.photobook.constants.Logs.API_CALL_ERROR
import com.bhaskar.photobook.models.ApiModel
import com.bhaskar.photobook.models.ApiModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private var data = MutableLiveData<ApiModel>()
    private var mainAdapter = MainRecyclerAdapter()


    fun getMainAdapter(): MainRecyclerAdapter {
        return mainAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMainAdapter(data: ApiModel) {
        mainAdapter.setData(data)
        mainAdapter.notifyDataSetChanged()
    }

    fun getDataObserver(): MutableLiveData<ApiModel> {
        return data
    }

    fun callListImageApi(page: Int, limit: Int) {
        val apiCall = ApiClient.create().getAllData(page, limit)
        apiCall.enqueue(object : Callback<ApiModel> {
            override fun onFailure(call: Call<ApiModel>, t: Throwable) {
                Log.d(API_CALL_ERROR, "Api Call Error: $t")
            }
            override fun onResponse(call: Call<ApiModel>, response: Response<ApiModel>) {
                if (response.isSuccessful && response.body() != null)
                    data.postValue(response.body())
            }
        })
    }
}