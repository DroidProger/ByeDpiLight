package com.droidproger.byedpilight.services

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.droidproger.byedpilight.data.START_ACTION
import com.droidproger.byedpilight.data.STOP_ACTION

object ServiceManager {
    private val TAG: String = ServiceManager::class.java.simpleName

    fun start(context: Context) {//, mode: Mode
        Log.i(TAG, "Starting VPN")
        val intent = Intent(context, ByeDpiVpnService::class.java)
        intent.action = START_ACTION
        ContextCompat.startForegroundService(context, intent)
    }

    fun stop(context: Context) {
        Log.i(TAG, "Stopping VPN")
        val intent = Intent(context, ByeDpiVpnService::class.java)
        intent.action = STOP_ACTION
        ContextCompat.startForegroundService(context, intent)
    }
}
