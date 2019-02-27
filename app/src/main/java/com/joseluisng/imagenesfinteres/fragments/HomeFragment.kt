package com.joseluisng.imagenesfinteres.fragments


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.joseluisng.imagenesfinteres.Activities.AddImageActivity
import com.joseluisng.imagenesfinteres.Activities.DetalleImagenActivity

import com.joseluisng.imagenesfinteres.R
import com.joseluisng.imagenesfinteres.adapters.AdapterImages
import com.joseluisng.imagenesfinteres.goToActivity
import com.joseluisng.imagenesfinteres.listeners.RecyclerImageListener
import com.joseluisng.imagenesfinteres.models.ImageModel
import com.joseluisng.imagenesfinteres.service
import com.joseluisng.imagenesfinteres.toast
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class HomeFragment : Fragment() {

    var listImage = ArrayList<ImageModel>()

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AdapterImages

    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val miVista = inflater.inflate(R.layout.fragment_home, container, false)
        // le pasamos la vista del recyclerview en el xml a la variable recycler
        recycler = miVista.recyclerView as RecyclerView

        // Hacemos la patición a la api rest y pintamos el recycler view
        getImages()

        // Parte del codigo para cuando deslizan la pantalla para hacer un refres y hacer peticion al api para cargar actualizaciones

        mHandler = Handler()
        miVista.swipe_refresh_layout.setOnRefreshListener {
            mRunnable = Runnable {

                getImages()

                swipe_refresh_layout.isRefreshing = false
            }
            mHandler.postDelayed(mRunnable, 2000)
        }


        return miVista
    }


    fun getImages(){

        // Traemos de el archivo Constantes la variable service
        service.getAllImages().enqueue(object : Callback<List<ImageModel>> {

            override fun onFailure(call: Call<List<ImageModel>>, t: Throwable) { t.printStackTrace() }

            override fun onResponse(call: Call<List<ImageModel>>, response: Response<List<ImageModel>>) {
               if(response.isSuccessful){
                   listImage = response.body() as ArrayList<ImageModel>
                   Log.e("Imagen: ", listImage.toString() )
                   setRecyclerView()
               }else{
                   activity?.toast("Algo fallo, revise su conexión a internet")
               }
            }

        })

    }

    fun setRecyclerView(){

        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        recycler.layoutManager = GridLayoutManager(context, 2)
        adapter = AdapterImages(listImage, object : RecyclerImageListener{
            override fun onClickCard(imagen: ImageModel, position: Int) {
                val intent = Intent(context, DetalleImagenActivity::class.java)
                intent.putExtra("id", imagen._id)
                intent.putExtra("imagen", imagen.path)
                intent.putExtra("title", imagen.title)
                intent.putExtra("description", imagen.description)
                intent.putExtra("fecha", imagen.created_at)
                startActivity(intent)
            }
        })

        recycler.adapter = adapter

    }




}
