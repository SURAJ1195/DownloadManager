package com.example.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.example.downloadmanager.NotificationType.*

// url = https://drive.google.com/u/0/uc?id=12mEUaI-PkJchA941ZZ6tLAk6b-gydklt&export=download&confirm=t&uuid=2a7dc215-3511-4e76-8cee-90b94f54175c&at=ALt4Tm0HUwuJZa7w6s9sU5IfPanX:1689749301811

class AndroidDownloader(
    private val context:Context
):Downloader{
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadFile(
        url: String,
        setTitle:String,
        fileName: String,
        notificationType:NotificationType,
        networksType: NetworkType,
        setMimeType: MimeType,

        ): Long {


        val setNotifications= when(notificationType){
            VISIBLE -> DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            HIDDEN -> DownloadManager.Request.VISIBILITY_HIDDEN
            NOTIFY_ON_COMPLETION -> DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION
        }
        val networkType = when(networksType){
            NetworkType.WIFI_AND_DATA ->DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
            NetworkType.ONLY_WIFI -> DownloadManager.Request.NETWORK_WIFI
            NetworkType.ONLY_DATA -> DownloadManager.Request.NETWORK_MOBILE
        }
        val setMimeTypes = when(setMimeType){
            com.example.downloadmanager.MimeType.APK -> "application/vnd.android.package-archive"
            com.example.downloadmanager.MimeType.IMAGE -> "image/*"
            com.example.downloadmanager.MimeType.AUDIO -> "audio/MP3"
            com.example.downloadmanager.MimeType.PDF -> "application/pdf"
        }

        val request = DownloadManager.Request(url.toUri())
                //we should check here the mime type correctly
            .setMimeType(setMimeTypes)
            .setAllowedNetworkTypes(networkType)
            .setNotificationVisibility(setNotifications)
            .setTitle(setTitle)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
        return downloadManager.enqueue(request)
    }
}
