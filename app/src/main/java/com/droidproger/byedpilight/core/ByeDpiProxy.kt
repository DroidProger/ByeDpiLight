package com.droidproger.byedpilight.core

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ByeDpiProxy {
    companion object {
        init {
            System.loadLibrary("byedpi")
        }
    }

    private val mutex = Mutex()
    private var fd = -1

    suspend fun startProxy(cmd: Array<String>): Int {//preferences: ByeDpiProxyPreferences
        val fd = createSocket(cmd)//preferences
        if (fd < 0) {
            return -1 // TODO: should be error code
        }
        return jniStartProxy(fd)
    }

    suspend fun stopProxy(): Int {
        mutex.withLock {
            if (fd < 0) {
                throw IllegalStateException("Proxy is not running")
            }

            val result = jniStopProxy(fd)
            if (result == 0) {
                fd = -1
            }
            return result
        }
    }

    private suspend fun createSocket(cmd: Array<String>): Int = //preferences: ByeDpiProxyPreferences
        mutex.withLock {
            if (fd >= 0) {
                throw IllegalStateException("Proxy is already running")
            }

            val fd = jniCreateSocketWithCommandLine(cmd)//createSocketFromPreferences(preferences) //preferences.args
            if (fd < 0) {
                return -1
            }
            this.fd = fd
            fd
        }


    private external fun jniCreateSocketWithCommandLine(args: Array<String>): Int

    private external fun jniStartProxy(fd: Int): Int //

    private external fun jniStopProxy(fd: Int): Int//
}