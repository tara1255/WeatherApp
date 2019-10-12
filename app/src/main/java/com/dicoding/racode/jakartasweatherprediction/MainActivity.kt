package com.dicoding.racode.jakartasweatherprediction

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.racode.jakartasweatherprediction.adapter.WeatherDayAdapter
import com.dicoding.racode.jakartasweatherprediction.room.WeatherTable
import com.dicoding.racode.jakartasweatherprediction.utils.toDate
import com.dicoding.racode.jakartasweatherprediction.viewModel.MainViewModel
import com.dicoding.racode.jakartasweatherprediction.viewModel.ResponseState
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private var listDay: Map<String, List<WeatherTable>> = mapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        srl_data.setOnRefreshListener {
            mainViewModel.getData()
        }

        setData()

        mainViewModel.getData()

    }

    private fun setData() {

        rv_day.layoutManager = LinearLayoutManager(this)
        val dayAdapter = WeatherDayAdapter(listDay)
        rv_day.adapter = dayAdapter

        mainViewModel.listDataDay.observe(this, Observer {
            if (it.isNotEmpty()) {
                val listKey = arrayListOf<String>()
                it.forEach { (s, _) ->
                    listKey.add(s)
                }
                dayAdapter.listDay = it
                dayAdapter.listDayKey = listKey
                dayAdapter.notifyDataSetChanged()
            }
        })

        mainViewModel.responseSate.observe(this, Observer {
            when (it) {
                is ResponseState.Requesting -> {
                    progressBar.visibility = View.VISIBLE
                }
                is ResponseState.OnFailed -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Please check your internet connection for the latest data updates", Toast.LENGTH_LONG).show()
                }
                is ResponseState.OnResponse -> {
                    pb_weather.visibility = View.GONE
                    rv_day.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
            srl_data.isRefreshing = false
        })

        mainViewModel.cityResult.observe(this, Observer {
            if (it != null) {
                TransitionManager.beginDelayedTransition(constraintLayout)
                tv_city.text = it.name
            }
        })

        mainViewModel.weatherResult.observe(this, Observer {
            if (it != null && !it.isNullOrEmpty()) {

                pb_weather.visibility = View.GONE
                rv_day.visibility = View.VISIBLE

                TransitionManager.beginDelayedTransition(constraintLayout)

                val date = it[0].time
                tv_date.text = date.toDate("EEEE, dd MMM yyy")

                val weather = it[0].main
                if (!weather.isNullOrEmpty()) {
                    tv_weather.text = weather

                    val weatherIcon = when (weather) {
                        "Rain" -> R.drawable.ic_icon___cloud_rain
                        "Clouds" -> R.drawable.ic_icon___cloud_cloudy
                        else -> R.drawable.ic_icon___sun
                    }

                    Glide.with(this)
                        .load(weatherIcon)
                        .into(iv_weather)
                }

                val temperature = it[0].temp
                tv_temperature.text = temperature.toString().plus("°")

            }
        })

    }
}
