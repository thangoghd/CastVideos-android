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
package com.google.sample.cast.refplayer.data

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import com.google.android.gms.cast.MediaInfo

/**
 * An [AsyncTaskLoader] that loads the list of channels and converts to MediaInfo in the background.
 */
class ChannelItemLoader(context: Context?, private val mUrl: String) :
    AsyncTaskLoader<List<MediaInfo>?>(context!!) {
    
    override fun loadInBackground(): List<MediaInfo>? {
        return try {
            Log.d(TAG, "Loading channels from URL: $mUrl and converting to MediaInfo")
            
            val mediaList = if (mUrl.startsWith("file:///android_asset/")) {
                val assetPath = mUrl.substring("file:///android_asset/".length)
                ChannelProvider.buildMediaFromAssets(context, assetPath)
            } else {
                ChannelProvider.buildMediaFromChannels(mUrl)
            }
            
            Log.d(TAG, "Loaded and converted ${mediaList?.size ?: 0} MediaInfo from channels")
            Log.d(TAG, "Channel list size: ${ChannelProvider.getChannelList()?.size ?: 0}")
            mediaList
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch and convert channel data to MediaInfo", e)
            null
        }
    }

    override fun onStartLoading() {
        super.onStartLoading()
        Log.d(TAG, "Starting to load channels and convert to MediaInfo")
        forceLoad()
    }

    /**
     * Handles a request to stop the Loader.
     */
    override fun onStopLoading() {
        Log.d(TAG, "Stopping channel to MediaInfo load")
        // Attempt to cancel the current load task if possible.
        cancelLoad()
    }

    companion object {
        private const val TAG = "ChannelItemLoader"
    }
}
