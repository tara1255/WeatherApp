package com.dicoding.racode.jakartasweatherprediction.koin

import android.app.Application
import com.dicoding.racode.jakartasweatherprediction.room.DatabaseWeather
import com.dicoding.racode.jakartasweatherprediction.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {

    private val appModule = module {

        single { DatabaseWeather.getInstance(androidContext()) }

        // MyViewModel ViewModel
        viewModel { MainViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule)
        }
    }
}