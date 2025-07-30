/*
 * Copyright 2022 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.sample.cast.refplayer.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.VideoView
import com.google.android.gms.cast.MediaInfo
import org.json.JSONObject
import java.io.IOException

/**
 * Helper class for MediaPlayer operations with custom headers support
 */
object MediaPlayerHelper {
    private const val TAG = "MediaPlayerHelper"
    
    /**
     * Set video URI with custom headers support
     * Falls back to regular setVideoURI if headers are not available
     */
    fun setVideoURIWithHeaders(videoView: VideoView, mediaInfo: MediaInfo) {
        val uri = Uri.parse(mediaInfo.contentId)
        val headers = extractHeadersFromMediaInfo(mediaInfo)
        
        if (headers.isNotEmpty()) {
            Log.d(TAG, "Setting video URI with headers: $headers")
            try {
                // Use reflection to access setVideoURI with headers
                val method = VideoView::class.java.getMethod(
                    "setVideoURI", 
                    Uri::class.java, 
                    Map::class.java
                )
                method.invoke(videoView, uri, headers)
                Log.d(TAG, "Successfully set video URI with headers")
            } catch (e: Exception) {
                Log.w(TAG, "Failed to set headers via reflection, using regular setVideoURI: ${e.message}")
                videoView.setVideoURI(uri)
            }
        } else {
            Log.d(TAG, "No headers found, using regular setVideoURI")
            videoView.setVideoURI(uri)
        }
    }
    
    /**
     * Extract headers from MediaInfo custom data
     */
    fun extractHeadersFromMediaInfo(mediaInfo: MediaInfo): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        
        try {
            val customData = mediaInfo.customData
            if (customData != null) {
                Log.d(TAG, "Custom data found: $customData")
                
                val headersJson = customData.optJSONObject("headers")
                if (headersJson != null) {
                    Log.d(TAG, "Headers JSON found: $headersJson")
                    
                    val keys = headersJson.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        val value = headersJson.getString(key)
                        headers[key] = value
                        Log.d(TAG, "Header: $key = $value")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error extracting headers from MediaInfo: ${e.message}")
        }
        
        return headers
    }
    
    /**
     * Create MediaPlayer with custom headers
     */
    fun createMediaPlayerWithHeaders(context: Context, uri: Uri, headers: Map<String, String>): MediaPlayer? {
        return try {
            val mediaPlayer = MediaPlayer()
            if (headers.isNotEmpty()) {
                Log.d(TAG, "Creating MediaPlayer with headers: $headers")
                mediaPlayer.setDataSource(context, uri, headers)
            } else {
                Log.d(TAG, "Creating MediaPlayer without headers")
                mediaPlayer.setDataSource(context, uri)
            }
            mediaPlayer
        } catch (e: IOException) {
            Log.e(TAG, "Failed to create MediaPlayer: ${e.message}")
            null
        }
    }
}
