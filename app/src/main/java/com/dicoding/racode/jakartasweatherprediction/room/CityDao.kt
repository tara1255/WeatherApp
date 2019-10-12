package com.dicoding.racode.jakartasweatherprediction.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.racode.jakartasweatherprediction.utils.Constant

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: CityTable)

    @Query("DELETE FROM ${Constant.Table.CITY}")
    fun deleteCityAll()

    @Query("SELECT * FROM ${Constant.Table.CITY}")
    fun loadCityLiveData(): LiveData<CityTable>

}