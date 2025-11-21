package com.droidproger.byedpilight.utility

import android.util.Log

fun collectLogs(): String? =
    try {
        Runtime.getRuntime()
            .exec("logcat *:D -d")
            .inputStream.bufferedReader()
            .use { it.readText() }
    } catch (e: Exception) {
        Log.e("DpiApp", "Failed to collect logs", e)
        null
    }