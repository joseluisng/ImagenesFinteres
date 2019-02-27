package com.joseluisng.imagenesfinteres.fragments


import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.joseluisng.imagenesfinteres.R
import com.joseluisng.imagenesfinteres.toast
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*


class NotificationsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val miVista = inflater.inflate(R.layout.fragment_notifications, container, false)


        //Boton que lanza un Alerdialog para notificar si quieres o no dejar de recibir notificaciones

        miVista.btnNoRecibirN.setOnClickListener {

            val dialog = AlertDialog.Builder(context)
            val dialoView = layoutInflater.inflate(R.layout.custom_dialog, null)
            dialog.setView(dialoView)
            dialog.setPositiveButton("Si", { dialogIterface: DialogInterface, i: Int ->
                activity?.toast("Ok, dejara de recibir notificaciones, puede activarlas nuevamente cuando guste")
            })
            dialog.setNegativeButton("no", {dialogIterface: DialogInterface, i: Int ->
                activity?.toast("Operacion cancelada")
            })

            //Otra forma de customizar un dialog
           /* val customDialog = dialog.create()
            customDialog.show()
            customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                activity?.toast("Operacion cancelada")

            }*/

            dialog.show()

        }



        return miVista
    }


}
