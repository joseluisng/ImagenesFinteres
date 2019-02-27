package com.joseluisng.imagenesfinteres.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.joseluisng.imagenesfinteres.R
import com.joseluisng.imagenesfinteres.baseUrl
import com.joseluisng.imagenesfinteres.inflate
import com.joseluisng.imagenesfinteres.listeners.RecyclerImageListener
import com.joseluisng.imagenesfinteres.loadByResource
import com.joseluisng.imagenesfinteres.models.ImageModel
import kotlinx.android.synthetic.main.imagemodel_recycler.view.*
import java.text.SimpleDateFormat

class AdapterImages(private val images: List<ImageModel>, private val listener: RecyclerImageListener): RecyclerView.Adapter<AdapterImages.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.imagemodel_recycler))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(images[position], listener)

    override fun getItemCount(): Int = images.size


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(image: ImageModel, listener: RecyclerImageListener) = with(itemView){

            //Picasso.get().load(game.image).resize(600,300).into(imageViewGame)
            imageViewFinteres.loadByResource(baseUrl + image.path)
            tvtitleImage.text = image.title
            tvDescriptionImage.text = image.description
            tvFechaImage.text = image.created_at.toString()
            // Clicks Events
            //Estamos utilizando los listener que es las funciones del interface para no encapsular la logica dentro del adaptador
            // Ya que el adaptador lo que tiene que hacer es resmplazar solo los valores
            // y cuando hagas un click es decir que el listener va a llamar a ese metodo
            setOnClickListener { listener.onClickCard(image, adapterPosition) }

        }

    }


}