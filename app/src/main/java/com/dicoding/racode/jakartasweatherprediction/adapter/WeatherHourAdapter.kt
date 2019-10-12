package com.dicoding.racode.jakartasweatherprediction.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.racode.jakartasweatherprediction.R
import com.dicoding.racode.jakartasweatherprediction.room.WeatherTable
import com.dicoding.racode.jakartasweatherprediction.utils.toDate

class WeatherHourAdapter(var listHour: List<WeatherTable>) :
    RecyclerView.Adapter<WeatherHourAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_hour, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listHour.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listHour[position]

        val date = data.time
        holder.tvHour.text = date.toDate("ha")

        val weather = data.main
        if (!weather.isNullOrEmpty()) {
            val weatherIcon = when (weather) {
                "Rain" -> R.drawable.ic_icon___cloud_rain
                "Clouds" -> R.drawable.ic_icon___cloud_cloudy
                else -> R.drawable.ic_icon___sun
            }
            Glide.with(holder.itemView.context)
                .load(weatherIcon)
                .into(holder.ivWeather)
        }
        holder.tvTemperature.text = data.temp.toString().plus("°")
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvHour: TextView = itemView.findViewById(R.id.tv_hour)
        var ivWeather: ImageView = itemView.findViewById(R.id.iv_weather)
        var tvTemperature: TextView = itemView.findViewById(R.id.tv_temperature)
    }

}
