package com.dicoding.racode.jakartasweatherprediction.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.racode.jakartasweatherprediction.utils.Constant

@Database(
    version = 1,
    entities = [
        CityTable::class,
        WeatherTable::class
    ],
    exportSchema = false
)
abstract class DatabaseWeather : RoomDatabase() {
    abstract fun noteDao(): CityDao
    abstract fun weatherDao(): WeatherDao

    companion object {

        private var INSTANCE: DatabaseWeather? = null

        fun getInstance(context: Context): DatabaseWeather {
            if (INSTANCE == null) {
                synchronized(DatabaseWeather::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        DatabaseWeather::class.java, Constant.Table.DB_NAME
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }

}