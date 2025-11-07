package com.droidproger.byedpilight.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.VpnService
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.droidproger.byedpilight.R
import com.droidproger.byedpilight.data.ServiceStatus
import com.droidproger.byedpilight.dataModel
import com.droidproger.byedpilight.services.ServiceManager
import com.droidproger.byedpilight.ui.theme.ComposeAppTheme
import java.io.IOException
import kotlin.text.toByteArray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController){
    val TAG = "DpiApp"
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val vpnRegister = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                ServiceManager.start(context)
            } else {
                Toast.makeText(context, R.string.vpnPermissionDenied, Toast.LENGTH_SHORT).show()
            }
        }
    val logsRegister =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val logs = collectLogs()
                if (logs == null) {
                    Toast.makeText(context,R.string.logsFailed,Toast.LENGTH_SHORT).show()
                } else {
                    val uri = it.data?.data ?: run {
                        Log.e("DpiApp", "No data in result")
                        return@rememberLauncherForActivityResult //@launch
                    }
                    context.contentResolver.openOutputStream(uri)?.use {
                        try {
                            it.write(logs.toByteArray())
                        } catch (e: IOException) {
                            Log.e(TAG, "Failed to save logs", e)
                        }
                    } ?: run {
                        Log.e(TAG, "Failed to open output stream")
                    }
                }
        }
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Column (
        Modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding)
    ){
        TopAppBar(
            title = {
                Text(stringResource(R.string.app_name) )
            },
            actions = {
                IconButton(
                    onClick = {
                        navController.navigate("settings")
                    }
                )
                {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "settings"
                    )
                }
                IconButton(
                    onClick = {
                        menuExpanded = true
                    }
                )
                {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "menu"
                    )
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.saveLogs)) },
                            onClick = {
                                val intent =
                                    Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                                        addCategory(Intent.CATEGORY_OPENABLE)
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TITLE, "byedpi.log")
                                    }

                                logsRegister.launch(intent)
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.about)) },
                            onClick = {
                                menuExpanded = false
                                dataModel.showAbout = true
                            }
                        )
                    }
                }
            }

        )
        Column (
            Modifier.fillMaxSize(),
            Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ElevatedButton(
                onClick = {
                    if (dataModel.serviceStatus == ServiceStatus.Connected){
                        ServiceManager.stop(context)
                    }else{
                        val intentPrepare = VpnService.prepare(context)
                        if (intentPrepare != null) {
                            vpnRegister.launch(intentPrepare)
                        } else {
                            ServiceManager.start(context)//, Mode.VPN
                        }
                    }
                },
            ){
                Text ( stringResource(dataModel.btnTextRes()))
            }
            Text ( stringResource(dataModel.statusTextRes()))
        }
    }
    if (dataModel.showAbout){
        AboutScreen()
    }
}

private fun collectLogs(): String? =
    try {
        Runtime.getRuntime()
            .exec("logcat *:D -d")
            .inputStream.bufferedReader()
            .use { it.readText() }
    } catch (e: Exception) {
        Log.e("DpiApp", "Failed to collect logs", e)
        null
    }

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ComposeAppTheme {
        MainScreen(navController = rememberNavController())
    }
}