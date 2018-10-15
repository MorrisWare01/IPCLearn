package com.morrisware.android.ipc.ktx

import android.app.ActivityManager
import android.content.Context
import android.os.Process

/**
 * Created by MorrisWare on 2018/10/15.
 * Email: MorrisWare01@gmail.com
 */
fun Context.getAppProcess(): ActivityManager.RunningAppProcessInfo? {
    val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val currentPid = Process.myPid()
    for (appProcess in activityManager.runningAppProcesses) {
        if (currentPid == appProcess.pid) {
            return appProcess
        }
    }
    return null
}

