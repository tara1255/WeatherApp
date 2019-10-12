package com.dicoding.racode.jakartasweatherprediction.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.racode.jakartasweatherprediction.utils.Constant

@Entity(
    tableName = Constant.Table.WEATHER
)
class WeatherTable(@PrimaryKey val id: Long,
                   val time: Long,
                   val main: String?,
                   val mainDesc: String?,
                   val temp: Double?
                   )