package com.joseluisng.imagenesfinteres

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.joseluisng.imagenesfinteres.Activities.AddImageActivity
import com.joseluisng.imagenesfinteres.Activities.AddImagenActivityjava
import com.joseluisng.imagenesfinteres.adapters.PagerAdapter
import com.joseluisng.imagenesfinteres.fragments.HomeFragment
import com.joseluisng.imagenesfinteres.fragments.NotificationsFragment
import com.joseluisng.imagenesfinteres.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var prevBottomSelected: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Agregando el toolbar que diseñamos
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        setUpViewPager(getPagerAdapter())
        setUpBottomNavigationBar()

    }

    // Función que hace la instancia del adaptador y agregamos los fragment al adaptador
    private fun getPagerAdapter(): PagerAdapter {
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(ProfileFragment())
        adapter.addFragment(NotificationsFragment())
        return adapter
    }

    // Cargamos las vista y obtenemos cuantos fragment tiene el adaptador
    private fun setUpViewPager(adapter: PagerAdapter){
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.count
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            // función para obtener el icono que se selecciono y deseleccionar el anterior icono seleccionado
            override fun onPageSelected(position: Int) {
                if(prevBottomSelected == null){
                    bottomNavigation.menu.getItem(0).isChecked = false
                }else{
                    prevBottomSelected!!.isChecked = false
                }
                bottomNavigation.menu.getItem(position).isChecked = true
                prevBottomSelected = bottomNavigation.menu.getItem(position)
            }

        })

    }

    // Cargar al viewpager cual item del menu se selecciono y darle un valor
    private fun setUpBottomNavigationBar(){
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                R.id.inicio_bottom -> {
                    viewPager.currentItem = 0;true
                }
                R.id.profile_bottom -> {
                    viewPager.currentItem = 1;true
                }
                R.id.notification_bottom -> {
                    viewPager.currentItem = 2;true
                }
                else -> false
            }
        }
    }

    // Poner en la barra superior la opción de un menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_general, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Cuando seleccionan la opción de el menu de la barra superior, se ejecuta esta función
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_add_image -> {
                goToActivity<AddImageActivity> {
                    //flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


}


