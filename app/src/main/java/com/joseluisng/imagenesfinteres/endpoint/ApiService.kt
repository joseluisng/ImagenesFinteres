package com.joseluisng.imagenesfinteres.endpoint

import com.joseluisng.imagenesfinteres.models.ImageModel
import com.joseluisng.imagenesfinteres.models.ImageModelPost
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/api/images")
    fun getAllImages(): Call<List<ImageModel>>

    @POST("/api/image")
    fun crateImage(@Body image: ImageModel?): Call<ImageModel>

    @DELETE("/api/image/{id}")
    fun deleteImage(@Path("id") id: String): Call<ImageModel>


    @POST("/api/image")
    fun uploadImageFile(@Body image: ImageModelPost): Call<ImageModelPost>

}