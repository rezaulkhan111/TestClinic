package com.example.testclinic

import android.app.Application
import com.example.testclinic.di.ApplicationComponent
import com.example.testclinic.di.DaggerApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThisApplication : Application() {
    val applicationComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}