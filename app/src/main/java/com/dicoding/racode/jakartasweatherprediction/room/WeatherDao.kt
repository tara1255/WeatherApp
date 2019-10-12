package com.dicoding.racode.jakartasweatherprediction.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.racode.jakartasweatherprediction.utils.Constant

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(city: List<WeatherTable>)

    @Query("DELETE FROM ${Constant.Table.WEATHER}")
    fun deleteWeatherAll()

    @Query("SELECT * FROM ${Constant.Table.WEATHER}")
    fun loadAllWeatherLiveData(): LiveData<List<WeatherTable>>

}