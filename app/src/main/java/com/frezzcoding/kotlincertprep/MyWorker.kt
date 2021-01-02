package com.frezzcoding.kotlincertprep

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    override fun doWork(): Result {
        println("do work")
        //here run some defferable work
        //deferrable means work that can is guaranteed to be executed but not immediately, depending on the device state. e.g. backing up pictures BUT NOT instant messaging
        return Result.success()
    }

}