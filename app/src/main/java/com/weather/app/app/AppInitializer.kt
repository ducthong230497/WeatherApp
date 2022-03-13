package com.weather.app.app

import android.content.Context
import androidx.startup.Initializer
import com.weather.app._di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class AppInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        startKoin {
            androidContext(context)
            val moduleList = ArrayList<Module>()
            moduleList.addAll(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    domainModule,
                    presenterModule
                )
            )
            modules(moduleList)
        }
    }

    override fun dependencies() = emptyList<Class<out Initializer<*>>>()

}
