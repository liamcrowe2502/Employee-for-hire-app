package org.wit.placemark.main

import android.app.Application
import org.wit.placemark.models.EmployeeJSONStore
import org.wit.placemark.models.EmployeeStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    lateinit var employees: EmployeeStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        employees = EmployeeJSONStore(applicationContext)
        i("Placemark started")
    }
}