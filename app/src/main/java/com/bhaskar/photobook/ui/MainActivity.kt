package com.bhaskar.photobook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhaskar.photobook.R
import com.bhaskar.photobook.constants.Logs.API_CALL_RESPONSE
import com.bhaskar.photobook.databinding.ActivityMainBinding
import com.bhaskar.photobook.models.ApiModelItem
import com.bhaskar.photobook.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        makeApiCall()
        populateMainAdapter()
    }

    private fun populateMainAdapter() {
        mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL)
            addItemDecoration(decoration)
        }
        binding.executePendingBindings()
    }

    private fun makeApiCall() {
        viewModel.callListImageApi(1, 100)
        viewModel.getDataObserver().observe(this, Observer {
            if (it != null) {
                viewModel.setMainAdapter(it)
                Log.d(API_CALL_RESPONSE, it.toString())
            }
            else
                Toast.makeText(this@MainActivity, getString(R.string.fetching_error), Toast.LENGTH_SHORT).show()
        })
    }
}