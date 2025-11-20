package com.droidproger.byedpilight

import android.content.pm.PackageManager.FEATURE_LEANBACK
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droidproger.byedpilight.ui.MainScreen
import com.droidproger.byedpilight.ui.SettingsScreen
import com.droidproger.byedpilight.ui.TvScreen
import com.droidproger.byedpilight.ui.theme.ComposeAppTheme
import com.droidproger.byedpilight.ui.theme.TvComposeTheme
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
            if (packageManager.hasSystemFeature(FEATURE_LEANBACK)){
                TvComposeTheme {
                    CreateTvUi(
                        prefStore
                    )
                }
            }else{
                ComposeAppTheme {
                    dataModel.textMinLines = 5
                    CreateUi(
                        prefStore
                    )
                }
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

@Composable
fun CreateTvUi(prefStore: PrefStore){
    TvScreen(prefStore)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAppTheme {
        CreateUi(prefStore = PrefStore(LocalContext.current))
    }
}