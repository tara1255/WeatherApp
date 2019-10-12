package com.dicoding.racode.jakartasweatherprediction.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.racode.jakartasweatherprediction.R
import com.dicoding.racode.jakartasweatherprediction.room.WeatherTable
import com.dicoding.racode.jakartasweatherprediction.utils.toDate
import net.cachapa.expandablelayout.ExpandableLayout
import java.util.*

class WeatherDayAdapter(var listDay: Map<String, List<WeatherTable>>) : RecyclerView.Adapter<WeatherDayAdapter.ViewHolder>() {

    var listDayKey: ArrayList<String> = arrayListOf()
    private lateinit var mRecyclerView: RecyclerView
    private var listHour: List<WeatherTable> = arrayListOf()
    private var selectedItem = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_day, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listDay.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listDay[listDayKey[position]]?.get(0)


        listHour = listDay[listDayKey[position]] ?: arrayListOf()

        if (data != null) {
            val date = data.time
            holder.tvDay.text = date.toDate("EEEE")
            holder.tvTemperature.text = data.temp.toString().plus("°")
        }

        if (data != null) {
            val weatherIcon = when (data.main) {
                "Rain" -> R.drawable.ic_icon___cloud_rain
                "Clouds" -> R.drawable.ic_icon___cloud_cloudy
                else -> R.drawable.ic_icon___sun
            }

            Glide.with(holder.itemView.context)
                .load(weatherIcon)
                .into(holder.ivWeather)
        }

        holder.elHour.setInterpolator(DecelerateInterpolator())
        holder.elHour.setOnExpansionUpdateListener { _, state ->
            if (state != ExpandableLayout.State.COLLAPSED) {
                try {
                    mRecyclerView.smoothScrollToPosition(holder.adapterPosition)
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                }
            }
        }

        holder.rvHour.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        val hourAdapter = WeatherHourAdapter(listHour)
        holder.rvHour.adapter = hourAdapter

        val pos = holder.adapterPosition
        val isSelected = pos == selectedItem
        holder.elHour.setExpanded(isSelected, false)
        if (isSelected) holder.elDay.setExpanded(false, false)
        else holder.elDay.setExpanded(true, false)
        holder.itemView.setOnClickListener {
            val holderAfter =
                mRecyclerView.findViewHolderForAdapterPosition(selectedItem) as ViewHolder?
            if (holderAfter != null && holder.itemView != holderAfter.itemView) {
                holderAfter.elHour.collapse()
                holderAfter.elDay.expand()
                if (holderAfter.elHour.isExpanded) {
                    holderAfter.elHour.collapse()
                    holderAfter.elDay.expand()
                }
            }
            if (holder.adapterPosition == selectedItem) {
                selectedItem = -1
                holder.itemView.isSelected = false

                holder.elHour.collapse()
                holder.elDay.expand()
            } else {
                selectedItem = holder.adapterPosition
                holder.itemView.isSelected = true
                holder.elHour.expand()
                holder.elDay.collapse()
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDay: TextView = itemView.findViewById(R.id.tv_day)
        var ivWeather: ImageView = itemView.findViewById(R.id.iv_weather)
        var tvTemperature: TextView = itemView.findViewById(R.id.tv_temperature)
        var rvHour: RecyclerView = itemView.findViewById(R.id.rv_hour)
        var elHour: ExpandableLayout = itemView.findViewById(R.id.el_hour)
        var elDay: ExpandableLayout = itemView.findViewById(R.id.el_day)
    }

}
