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
 * Label class represents overlay text labels on channel thumbnails
 */
data class Label(
    var position: String? = null,
    var text: String? = null,
    var color: String? = null,
    var textColor: String? = null
) : Serializable {
    
    companion object {
        const val serialVersionUID = 727566175075960654L
        
        // Position constants
        const val POSITION_TOP_LEFT = "top-left"
        const val POSITION_TOP_RIGHT = "top-right"
        const val POSITION_BOTTOM_LEFT = "bottom-left"
        const val POSITION_BOTTOM_RIGHT = "bottom-right"
        const val POSITION_CENTER = "center"
    }
}
