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

import android.util.Log
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import org.json.JSONObject

/**
 * Helper class for Cast operations with custom headers support
 */
object CastHelper {
    private const val TAG = "CastHelper"
    
    /**
     * Load media with custom headers support
     */
    fun loadMediaWithHeaders(
        castSession: CastSession?,
        mediaInfo: MediaInfo,
        autoPlay: Boolean = true,
        position: Long = 0
    ) {
        if (castSession == null) {
            Log.w(TAG, "CastSession is null, cannot load media")
            return
        }
        
        val remoteMediaClient = castSession.remoteMediaClient
        if (remoteMediaClient == null) {
            Log.w(TAG, "RemoteMediaClient is null, cannot load media")
            return
        }
        
        // Extract headers from custom data if available
        val customData = mediaInfo.customData
        var loadRequestData = MediaLoadRequestData.Builder()
            .setMediaInfo(mediaInfo)
            .setAutoplay(autoPlay)
            .setCurrentTime(position)
        
        // Add custom data including headers
        if (customData != null) {
            loadRequestData = loadRequestData.setCustomData(customData)
            Log.d(TAG, "Loading media with custom data: $customData")
        }
        
        val request = loadRequestData.build()
        
        remoteMediaClient.load(request).setResultCallback(
            ResultCallback { result ->
                if (result.status.isSuccess) {
                    Log.d(TAG, "Media loaded successfully with headers")
                } else {
                    Log.e(TAG, "Failed to load media: ${result.status}")
                }
            }
        )
    }
    
    /**
     * Get the current CastSession
     */
    fun getCurrentCastSession(): CastSession? {
        return CastContext.getSharedInstance()?.sessionManager?.currentCastSession
    }
    
    /**
     * Check if we're currently casting
     */
    fun isCasting(): Boolean {
        val castSession = getCurrentCastSession()
        return castSession != null && castSession.isConnected
    }
}
