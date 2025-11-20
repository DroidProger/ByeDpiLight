package com.droidproger.byedpilight


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.droidproger.byedpilight.data.ServiceStatus

class DataModel: ViewModel() {


    var textMinLines: Int = 3
    var isCmdEdit by mutableStateOf(false)
    var cmdLine by mutableStateOf("")
    var isPortEdit by mutableStateOf(false)
    var proxyPort by mutableIntStateOf(value = 1080)

    var isDnsEdit by mutableStateOf(false)
    var dnsIp by mutableStateOf(value = "1.1.1.1")
    var ipv6enabled  by mutableStateOf(value = false)
    var showAbout by mutableStateOf(value = false)
    var showWarn by mutableStateOf(value = false)
    fun getIsCmdEdit(): Boolean{
        return isCmdEdit
    }

    fun setIsCmdEdit(isEdit: Boolean){
        isCmdEdit = isEdit
    }
    var serviceStatus by mutableStateOf(value = ServiceStatus.Disconnected)

    fun btnTextRes(): Int{
        when(serviceStatus) {
            ServiceStatus.Disconnected -> return R.string.connect
            ServiceStatus.Connected -> return R.string.disconnect
            ServiceStatus.Failed -> return R.string.connect
        }
    }

    fun statusTextRes(): Int{
        when(serviceStatus) {
            ServiceStatus.Disconnected -> return R.string.disconnected
            ServiceStatus.Connected -> return R.string.connected
            ServiceStatus.Failed -> return R.string.disconnected
        }
    }
}