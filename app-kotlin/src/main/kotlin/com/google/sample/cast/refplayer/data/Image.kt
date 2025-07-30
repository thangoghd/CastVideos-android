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
 * Image class represents image metadata for channel thumbnails
 */
data class Image(
    var url: String? = null,
    var height: Int = 0,
    var width: Int = 0,
    var display: String? = null,
    var shape: String? = null
) : Serializable {
    
    companion object {
        const val serialVersionUID = 727566175075960655L
        
        // Display constants
        const val DISPLAY_COVER = "cover"
        const val DISPLAY_CONTAIN = "contain"
        const val DISPLAY_FILL = "fill"
        
        // Shape constants
        const val SHAPE_SQUARE = "square"
        const val SHAPE_RECTANGLE = "rectangle"
        const val SHAPE_CIRCLE = "circle"
    }
}
