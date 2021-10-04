package com.example.androidmobilebootcampfifthweek

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.androidmobilebootcampfifthweek.databinding.ActivityMainBinding
import com.example.androidmobilebootcampfifthweek.network.ConnectionLiveData


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var connectionLiveData : ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        checkNetworkConnection()

    }

    private fun checkNetworkConnection() {

        connectionLiveData = ConnectionLiveData(application)

        connectionLiveData.observe(this, { isConnected ->

            if (isConnected){
                binding.fragContainer.visibility = View.VISIBLE
                binding.noInternetConn.visibility = View.GONE
            }

            else{
                binding.fragContainer.visibility = View.GONE
                binding.noInternetConn.visibility = View.VISIBLE
            }

        })
    }


}
