package com.example.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

// url = https://drive.google.com/u/0/uc?id=12mEUaI-PkJchA941ZZ6tLAk6b-gydklt&export=download&confirm=t&uuid=2a7dc215-3511-4e76-8cee-90b94f54175c&at=ALt4Tm0HUwuJZa7w6s9sU5IfPanX:1689749301811

class AndroidDownloader(
    private val context:Context
):Downloader{
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadFile(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
                //we should check here the mime type correctly
            .setMimeType("application/vnd.android.package-archive")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("service.apk")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"service.apk")
        return downloadManager.enqueue(request)
    }
}