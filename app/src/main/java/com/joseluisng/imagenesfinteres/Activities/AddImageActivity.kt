package com.joseluisng.imagenesfinteres.Activities

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.CursorLoader
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.joseluisng.imagenesfinteres.*
import com.joseluisng.imagenesfinteres.models.ImageModelPost
import kotlinx.android.synthetic.main.activity_add_image.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class AddImageActivity : AppCompatActivity() {

    var bitmap: Bitmap? = null
    val PICK_IMAGE_REQUEST: Int = 1

    private val GALLERY_INTENT = 1

    var uri: Uri? = null
    lateinit var imagePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_image)


        btnBuscarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_INTENT)
        }

        btnGuardarImage.setOnClickListener {

            val tituloImage = etTitleImage.text.toString()
            val descriptionImage = etDescriptionImage.text.toString()

            if( bitmap != null && !tituloImage.isEmpty() && !descriptionImage.isEmpty()){

                publicarImagen(bitmap!!, tituloImage, descriptionImage)

            }else{
                toast("Es necesario Agregar una imagen y llenar todos los campos")
            }

        }

    }

    fun publicarImagen(bitmap: Bitmap, title: String, description: String){

        // convirtiendo la imagen a un string base64
        val baos: ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        val imageString: String = Base64.encodeToString(imageBytes, Base64.DEFAULT)

        //Log.e("Imagen: ",imageString)

        /*
        var imagen: ImageModelPost = ImageModelPost(title, description, imageString)
        service.uploadImageFile(imagen).enqueue(object : Callback<ImageModelPost>{
            override fun onFailure(call: Call<ImageModelPost>, t: Throwable) {
                toast(t.toString())
                Log.e("", t.toString())
            }

            override fun onResponse(call: Call<ImageModelPost>, response: Response<ImageModelPost>) {
               toast("Se agrego")
                toast(response.body().toString())
                Log.e("", response.body().toString())
            }

        })*/

        val queue = Volley.newRequestQueue(this)
        val url = "${baseUrl}/api/image"
        val jsonObject = JSONObject()

        jsonObject.put("image", imageString)
        jsonObject.put("title", title)
        jsonObject.put("description", description)

        val req = JsonObjectRequest(url, jsonObject,
                com.android.volley.Response.Listener { response ->

                    intent = Intent(this, MainActivity::class.java )
                    Toast.makeText(this, "Se agrego correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                },
                com.android.volley.Response.ErrorListener { error ->
                    error.printStackTrace()
                    toast(error.toString())
                    Toast.makeText(this, "No se pudo agregar", Toast.LENGTH_SHORT).show()
                }
        )

        queue.add(req)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK){

            uri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            imageCargada.setImageURI(uri)
            Log.e("uriresult:", uri.toString())

            //imagePath = getRealPathFromUri(this.uri!!)

        }

    }

    /*
    private fun getRealPathFromUri(uri: Uri): String{

        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val loader: CursorLoader = CursorLoader(this, uri, projection, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val column_indx: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result: String = cursor.getString(column_indx)
        cursor.close()
        return result
    }*/


}
