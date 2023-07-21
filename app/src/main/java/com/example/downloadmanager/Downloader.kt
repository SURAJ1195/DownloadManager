package com.example.downloadmanager

interface Downloader {
    fun downloadFile(url:String):Long
}