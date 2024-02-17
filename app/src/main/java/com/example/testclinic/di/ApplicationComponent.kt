package com.example.testclinic.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Author: Rezaul Khan
 * github: https://github.com/rezaulkhan111
 */

@Singleton
@Component(
    modules = [NetworkModule::class]
)
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}