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
 * Source class represents a content source within a channel
 */
data class Source(
    var id: String? = null,
    var name: String? = null,
    var contents: MutableList<Content> = mutableListOf()
) : Serializable {
    
    /**
     * Get the first available content
     */
    fun getFirstContent(): Content? {
        return contents.firstOrNull()
    }
    
    /**
     * Get content by name
     */
    fun getContentByName(name: String): Content? {
        return contents.find { it.name == name }
    }
    
    companion object {
        const val serialVersionUID = 727566175075960660L
    }
}
