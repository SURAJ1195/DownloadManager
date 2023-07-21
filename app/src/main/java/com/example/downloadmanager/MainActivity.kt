package com.example.downloadmanager

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.downloadmanager.ui.theme.DownloadManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val downloader = AndroidDownloader(this)

        setContent {
            val uriString = remember{
                mutableStateOf("")
            }
            val receiver =DownloadCompletedReceiver{
                uriString.value = it
            }
            val filter = IntentFilter("android.intent.action.DOWNLOAD_COMPLETE")
            registerReceiver(receiver,filter)

            DownloadManagerTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = uriString.value)
                    Spacer(modifier = Modifier.height(10.dp) )
                    Button(onClick = {

                        downloader.downloadFile("https://drive.google.com/u/0/uc?id=12mEUaI-PkJchA941ZZ6tLAk6b-gydklt&export=download&confirm=t&uuid=2a7dc215-3511-4e76-8cee-90b94f54175c&at=ALt4Tm0HUwuJZa7w6s9sU5IfPanX:1689749301811")
                    }) {
                        Text(text = "Download APK")
                    }
                }
            }
        }
    }
}


