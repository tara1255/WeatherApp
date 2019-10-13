package com.dicoding.racode.jakartasweatherprediction.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.racode.jakartasweatherprediction.model.City
import com.dicoding.racode.jakartasweatherprediction.model.Listt
import com.dicoding.racode.jakartasweatherprediction.model.WeatherResponse
import com.dicoding.racode.jakartasweatherprediction.network.EndPoint
import com.dicoding.racode.jakartasweatherprediction.room.CityTable
import com.dicoding.racode.jakartasweatherprediction.room.DatabaseWeather
import com.dicoding.racode.jakartasweatherprediction.room.WeatherTable
import com.dicoding.racode.jakartasweatherprediction.utils.toDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    private val mDb: DatabaseWeather by inject()

    val responseSate = MutableLiveData<ResponseState>()
    val cityResult: LiveData<CityTable> = mDb.cityDao().loadCityLiveData()
    val weatherResult: LiveData<List<WeatherTable>> =
        mDb.weatherDao().loadAllWeatherLiveData().apply {
            observeForever {
                val listDay = it.groupBy { itDay ->
                    val ms = itDay.time
                    ms.toDate("EEE, dd MMM yyy")
                }
                listDataDay.value = listDay
            }
        }
    val listDataDay = MutableLiveData<Map<String, List<WeatherTable>>>()

    private fun saveCity(city: City) {
        val cityTable = CityTable(id = city.id, name = city.name)
        mDb.cityDao().deleteCityAll()
        mDb.cityDao().insertCity(cityTable)
    }

    private fun saveWeatherList(list: List<Listt>) {

        val listWeather: ArrayList<WeatherTable> = arrayListOf()
        list.forEach {
            listWeather.add(
                WeatherTable(
                    id = it.dt,
                    main = it.weather[0].main,
                    mainDesc = it.weather[0].description,
                    time = it.dt,
                    temp = it.main?.temp
                )
            )
        }
        mDb.weatherDao().deleteWeatherAll()
        mDb.weatherDao().insertWeather(listWeather)
    }

    fun getData() {
        responseSate.postValue(ResponseState.Requesting)
        val postServices = EndPoint()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val weatherResponse = postServices.getPostsAsync().await()
                val city = weatherResponse.city
                val list = weatherResponse.list

                if (city != null && list != null) {

                    saveCity(city)
                    saveWeatherList(list)

                    responseSate.postValue(ResponseState.OnResponse(weatherResponse))

                }
            } catch (e: Exception) {
                responseSate.postValue(ResponseState.OnFailed(e))
            }
        }
    }


}

sealed class ResponseState {
    object Requesting : ResponseState()
    data class OnFailed(val t: Exception) : ResponseState()
    data class OnResponse(val city: WeatherResponse?) : ResponseState()

}