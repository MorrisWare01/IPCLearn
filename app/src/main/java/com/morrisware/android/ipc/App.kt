package com.morrisware.android.ipc

import android.app.Application
import android.util.Log
import com.morrisware.android.ipc.ktx.getAppProcess

/**
 * Created by MorrisWare on 2018/10/15.
 * Email: MorrisWare01@gmail.com
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("APP", "onCreate${this.toString()} ${getAppProcess()?.processName}")
    }

}