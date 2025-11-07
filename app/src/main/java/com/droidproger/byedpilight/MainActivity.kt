package com.droidproger.byedpilight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.alexandernfdv.dpiapp.data.Mode
import com.droidproger.byedpilight.ui.MainScreen
import com.droidproger.byedpilight.ui.SettingsScreen
import com.droidproger.byedpilight.ui.theme.ComposeAppTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val dataModel = DataModel()

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefStore = PrefStore(applicationContext)//
        lifecycleScope.launch {
            dataModel.cmdLine = prefStore.cmdLine.first()
            dataModel.proxyPort = prefStore.proxyPort.first()
            dataModel.dnsIp = prefStore.dnsIp.first()
            dataModel.ipv6enabled = prefStore.ipv6Enable.first()
        }
        enableEdgeToEdge()
        setContent {
            ComposeAppTheme {
                //val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                //val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                //val safeContent = WindowInsets.safeContent.asPaddingValues()
                //Scaffold(modifier = Modifier.fillMaxSize().padding(safeContent)) { //innerPadding ->
                    CreateUi(
                        prefStore
                    )
                //}
            }
        }
    }
}

@Composable
fun CreateUi(prefStore: PrefStore) {//,activity: Activity
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main"){
        composable("main"){
            MainScreen(navController)//,activity
        }
        composable("settings"){
            SettingsScreen(navController, prefStore)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAppTheme {
        CreateUi(prefStore = PrefStore(LocalContext.current))
    }
}