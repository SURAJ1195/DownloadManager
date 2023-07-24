package com.example.downloadmanager

import android.annotation.SuppressLint
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
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.downloadmanager.ui.theme.DownloadManagerTheme
import kotlinx.coroutines.launch
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            DownloadManagerTheme {
                val showProgress =  remember{ mutableStateOf(false) }
                val uriString = remember{ mutableStateOf("") }
                val downloadProgress = remember { mutableStateOf(0F)}
                val url = "https://drive.google.com/u/0/uc?id=12mEUaI-PkJchA941ZZ6tLAk6b-gydklt&export=download&confirm=t&uuid=2a7dc215-3511-4e76-8cee-90b94f54175c&at=ALt4Tm0HUwuJZa7w6s9sU5IfPanX:1689749301811"

                LaunchedEffect(key1 = true){
                    registerBroadcastReceiver(this@MainActivity, onCancelled = {
                        Toast.makeText(applicationContext, "this is called", Toast.LENGTH_SHORT).show()
                        downloadProgress.value = 0F
                        showProgress.value = false
                    }){
                        uriString.value = it
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//                    if(showProgress.value){
//                        CustomProgressbar(progressCount = downloadProgress.value.toInt(), onValueChanged = {})
//                    }
                    if(showProgress.value){
                        Text("Downloading please wait...")
                        Text("${downloadProgress.value.toInt()} %")
                    }
                    Text(text = uriString.value)
                    Spacer(modifier = Modifier.height(10.dp) )
                    Button(onClick = {
                        showProgress.value = true
                        CoroutineScope(Dispatchers.IO).launch {

                          getDownloadProgressValue(
                              context = this@MainActivity,
                             url =  url,
                              setTitle = "service.apk",
                              fileName = "Service.apk",
                              notificationType = NotificationType.VISIBLE,
                              networkType = NetworkType.WIFI_AND_DATA,
                              setMimeType = MimeType.APK

                          ).collect{
                              downloadProgress.value = it.toFloat()
                          }

                        }
                    }

                    ) {
                        Text(text = "Download APK")
                    }
                }
            }
        }
    }

    @SuppressLint("Range")
    fun getDownloadProgressValue(
        context: Context,
        url:String,
        setTitle:String,
        fileName:String,
        networkType: NetworkType,
        notificationType: NotificationType,
        setMimeType: MimeType

    ):Flow<Int> = flow {
        val downloader = AndroidDownloader(context)
        var isDownloading = true

        val id = downloader.downloadFile(
            url = url,
            setTitle = setTitle,
            fileName= fileName,
            networksType = networkType,
            notificationType = notificationType,
            setMimeType = setMimeType
        )

        Log.d("suraj", "id : $id")
        val downloadManager =
            applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager


        while(isDownloading) {
            val query = DownloadManager.Query().setFilterById(id)
            val cursor: Cursor = downloadManager.query(query)
            if (cursor != null && cursor.moveToFirst()) {

                val statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                val status = cursor.getInt(statusIndex)


                val bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                val bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                Log.d("suraj", "bytesTotal : $bytesTotal")
                Log.d("suraj", "bytesDownloaded :$bytesDownloaded")

                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    isDownloading = false
                }
                    when (bytesTotal) {
                        -1 -> {
                            emit(0)
                        }

                        else -> {
                             emit (((bytesDownloaded.toFloat() / bytesTotal.toFloat()) * 100).toInt())
                        }
                    }


            }


        }
    }


}

fun registerBroadcastReceiver(context: Context,onCancelled:(Int)->Unit,onReceived:(String)->Unit){
    val receiver = DownloadCompletedReceiver(onCanceledOrFinished = {
                             onCancelled(it)

    }, onDataReceived = {
        onReceived(it)
    }
    )
    val filter = IntentFilter("android.intent.action.DOWNLOAD_COMPLETE")
    context.registerReceiver(receiver,filter)
}

