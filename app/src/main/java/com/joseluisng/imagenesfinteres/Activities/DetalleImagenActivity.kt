package com.joseluisng.imagenesfinteres.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.joseluisng.imagenesfinteres.*
import com.joseluisng.imagenesfinteres.models.ImageModel
import kotlinx.android.synthetic.main.activity_detalle_imagen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleImagenActivity : AppCompatActivity() {

    var id = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_imagen)

        try {

            val bundle: Bundle = intent.extras!!
            id = bundle.getString("id")
            val image = bundle.getString("imagen")
            val title = bundle.getString("title")
            val description = bundle.getString("description")
            val fecha = bundle.getString("fecha")

            imageDetail.loadByResource(baseUrl + image)
            tvTitleDetail.setText(title)
            tvDescriptionDetail.setText(description)
            tvFechaDetail.setText(fecha)

        }catch (e: Exception){

        }

        btnDelete.setOnClickListener {
            deleteImage(id)
        }

    }

    fun deleteImage(id: String){

        service.deleteImage(id).enqueue(object : Callback<ImageModel> {
            override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                val g = response.body()
                Log.e("Game: ", Gson().toJson(g))

                goToActivity<MainActivity> {
                    toast("Imagen Borrada")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

            }

        })

    }


}
