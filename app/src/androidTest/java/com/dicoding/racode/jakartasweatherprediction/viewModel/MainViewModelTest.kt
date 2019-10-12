package com.dicoding.racode.jakartasweatherprediction.viewModel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.racode.jakartasweatherprediction.room.CityTable
import com.dicoding.racode.jakartasweatherprediction.room.DatabaseWeather
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.KoinComponent

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object : KoinComponent {
        private lateinit var db: DatabaseWeather
    }

    private val dummyCity = CityTable(id = 1642911, name = "Jakarta")

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseWeather::class.java).build()
    }

    @Test
    fun saveCityTest() {
        db.cityDao().insertCity(dummyCity)
        db.cityDao().loadCityLiveData().apply {
            observeForever {
                assertEquals(dummyCity, it)
            }
        }
    }


}