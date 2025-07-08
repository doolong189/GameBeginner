package dev.hoanglong180903.gamebeginer.app

import android.app.Application
import android.app.ActivityManager
import android.os.Process
import java.lang.Exception
import java.lang.RuntimeException

class ApplicationScope : Application() {
    override fun onCreate() {
        super.onCreate()

        val processId = Process.myPid()
        val activityManager =
            applicationContext.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        var isMainAppProcess = true
        try {
            for (processInfo in activityManager.runningAppProcesses) {
                if (processInfo.pid == processId && processInfo.processName != null && (processInfo.processName.endsWith(
                        ":acra"
                    ) || processInfo.processName.endsWith(":outbox"))
                ) {
                    isMainAppProcess = false
                    break
                }
            }
        } catch (e: Exception) {
        }
        if (!isMainAppProcess) {
            throw RuntimeException("We're in a buddy build process but ended up in main app code. This is bad!")
        }
    }
}