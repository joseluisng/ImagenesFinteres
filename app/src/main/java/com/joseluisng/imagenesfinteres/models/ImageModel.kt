package com.joseluisng.imagenesfinteres.models

import java.util.*

data class ImageModel(val created_at: Date?, val _id: String?, val title: String, val description: String, val filename: String,
                      val path: String, val originalnale: String, val mimeType: String, val size: Int)
