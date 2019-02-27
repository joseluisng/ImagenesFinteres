package com.joseluisng.imagenesfinteres.fragments


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.joseluisng.imagenesfinteres.R
import java.nio.file.Files.find


class ProfileFragment : Fragment() {

    var toolbarColps : CollapsingToolbarLayout? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val miVista = inflater.inflate(R.layout.fragment_profile, container, false)

        toolbarColps = miVista.findViewById(R.id.collapsing_toolbar)
        toolbarColps!!.title =  "Profile"


        return miVista
    }


}
