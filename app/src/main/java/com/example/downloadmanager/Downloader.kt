package com.example.downloadmanager

interface Downloader {
    fun downloadFile(
        url:String,
        setTitle:String,
        fileName:String,
        notificationType: NotificationType,
        networksType: NetworkType,
        setMimeType: MimeType
        ):Long
}