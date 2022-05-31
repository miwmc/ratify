package com.mcm.ratify

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.MultiDex
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import com.mcm.ratify.worker.FirebaseInitWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidApp
class RatifyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        val request =
            PeriodicWorkRequestBuilder<FirebaseInitWorker>(
                1,
                TimeUnit.DAYS
            ).build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                FirebaseInitWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }
}