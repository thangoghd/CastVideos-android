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

import java.io.Serializable

/**
 * Channel class represents a streaming channel with metadata and sources
 */
data class Channel(
    var id: String? = null,
    var name: String? = null,
    var subtitle: String? = null,
    var labels: MutableList<Label> = mutableListOf(),
    var image: Image? = null,
    var type: String? = null,
    var display: String? = null,
    var sources: MutableList<Source> = mutableListOf()
) : Serializable {
    
    /**
     * Get the primary video URL from the first available stream
     */
    fun getPrimaryVideoUrl(): String? {
        return sources.firstOrNull()
            ?.getFirstContent()
            ?.getFirstStream()
            ?.getDefaultStreamLink()
            ?.url
    }
    
    /**
     * Get the primary stream link for playback
     */
    fun getPrimaryStreamLink(): StreamLink? {
        return sources.firstOrNull()
            ?.getFirstContent()
            ?.getFirstStream()
            ?.getDefaultStreamLink()
    }
    
    /**
     * Get image URL for thumbnails
     */
    fun getImageUrl(): String? {
        return image?.url
    }
    
    /**
     * Get formatted title for display
     */
    fun getDisplayTitle(): String? {
        return name
    }
    
    /**
     * Get formatted description for display
     */
    fun getDisplayDescription(): String? {
        return subtitle
    }
    
    /**
     * Check if channel has valid streaming sources
     */
    fun hasValidSources(): Boolean {
        return sources.isNotEmpty() && 
               sources.any { source -> 
                   source.contents.isNotEmpty() && 
                   source.contents.any { content -> 
                       content.streams.isNotEmpty() && 
                       content.streams.any { stream -> 
                           stream.streamLinks.isNotEmpty() 
                       } 
                   } 
               }
    }
    
    override fun toString(): String {
        return "Channel{" +
                "id='$id', " +
                "name='$name', " +
                "subtitle='$subtitle', " +
                "type='$type', " +
                "display='$display', " +
                "imageUrl='${getImageUrl()}', " +
                "primaryVideoUrl='${getPrimaryVideoUrl()}'" +
                "}"
    }
    
    companion object {
        const val serialVersionUID = 727566175075960661L
        
        // Channel type constants
        const val TYPE_SINGLE = "single"
        const val TYPE_PLAYLIST = "playlist"
        const val TYPE_LIVE = "live"
        
        // Display type constants
        const val DISPLAY_THUMBNAIL_ONLY = "thumbnail-only"
        const val DISPLAY_FULL = "full"
        const val DISPLAY_COMPACT = "compact"
    }
}
