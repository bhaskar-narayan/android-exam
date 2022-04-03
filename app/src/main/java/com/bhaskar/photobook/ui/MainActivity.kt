package com.bhaskar.photobook.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhaskar.photobook.R
import com.bhaskar.photobook.adapters.MainRecyclerAdapter
import com.bhaskar.photobook.constants.Constant.LIMIT
import com.bhaskar.photobook.constants.Logs.SCROLL_CHECK
import com.bhaskar.photobook.databinding.ActivityMainBinding
import com.bhaskar.photobook.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainRecyclerAdapter
    private lateinit var timeHandler: Handler
    private lateinit var timeRunnable: Runnable
    private var time: Long = 5000
    private var page = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainAdapter = MainRecyclerAdapter(this@MainActivity)
        makeApiCall()
        populateMainAdapter()
        timeHandler = Handler(Looper.getMainLooper())
        timeRunnable = Runnable {
            Toast.makeText(this@MainActivity, "User inactive for ${time/1000} secs!", Toast.LENGTH_SHORT).show()
        }
        startHandler()
        mainScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                makeApiCall()
                populateMainAdapter()
            }
        })
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.d(SCROLL_CHECK, "onTouchEvent: ")
        stopHandler()
        startHandler()
    }

    private fun stopHandler() {
        timeHandler.removeCallbacks(timeRunnable)
    }

    private fun startHandler() {
        timeHandler.postDelayed(timeRunnable, time)
    }

    private fun populateMainAdapter() {
        mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }
        binding.executePendingBindings()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun makeApiCall() {
        viewModel.callListApi(page, LIMIT)
        viewModel.getDataObserver().observe(this, Observer {
            if (it != null) {
                mainAdapter.setData(it)
                mainAdapter.notifyDataSetChanged()
            }
            else
                Toast.makeText(this@MainActivity, getString(R.string.fetching_error), Toast.LENGTH_SHORT).show()
        })
    }
}