package com.example.downloadmanager

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf

class DownloadCompletedReceiver(private val onDataReceived:(String)->Unit) : BroadcastReceiver( ) {
    private lateinit var downloadManager : DownloadManager
    var data= mutableStateOf("")

    @SuppressLint("Range")
    override fun onReceive(context: Context?, intent: Intent?) {
        downloadManager = context?.getSystemService(DownloadManager::class.java)!!
        //todo check the action
        if(intent?.action == "android.intent.action.DOWNLOAD_COMPLETE"){
            val id  = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1L)
           val query =  DownloadManager.Query().setFilterById(id)
            val cursor = downloadManager.query(query)
            if(cursor!=null && cursor.moveToFirst()){
                val statusIndex  = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                val status = cursor.getInt(statusIndex)
                if(status == DownloadManager.STATUS_SUCCESSFUL){
                    val localURIString =cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                    val fileUri = Uri.parse(localURIString)
                    onDataReceived.invoke(fileUri.toString())


                    Log.d("suraj","$fileUri")

                }
            }

            if(id != -1L){
                Toast.makeText(context, "Download with ID $id finished!", Toast.LENGTH_SHORT).show()
                data.value=id.toString()

            }
        }
    }
}