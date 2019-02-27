package com.joseluisng.imagenesfinteres.listeners

import com.joseluisng.imagenesfinteres.models.ImageModel

interface RecyclerImageListener {
    fun onClickCard(imagen: ImageModel, position: Int)
}