package com.example.themetest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max


class MainActivity : AppCompatActivity() {

    val prefs by lazy {
        this.getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE)
    }
    lateinit var rootLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = if (read("key", true)) {
            R.style.AppTheme_NoActionBar
        } else {
            R.style.AppTheme2
        }
        setTheme(theme)
        setContentView(R.layout.activity_main)

//        rootLayout = findViewById(R.id.main)
//        rootLayout.visibility = View.INVISIBLE
//        val viewTreeObserver: ViewTreeObserver = rootLayout.getViewTreeObserver()
//        if (viewTreeObserver.isAlive) {
//            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
//                override fun onGlobalLayout() {
//                    revealActivity(
//                        resources.displayMetrics.widthPixels / 2,
//                        resources.displayMetrics.heightPixels / 2
//                    )
//                    rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                }
//            })
//        }

        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            createIntend(R.anim.fragment_open_enter, R.anim.fragment_open_exit)
        }
        arrow.setOnClickListener {
                createIntend(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
        }
    }

    private fun createIntend(fragmentOpenEnter: Int, fragmentOpenExit: Int) {
        val reverse = !read("key", true)
        write("key", reverse)

        val intend = Intent(this@MainActivity, this@MainActivity.javaClass)
        startActivity(intend)
        overridePendingTransition(fragmentOpenEnter, fragmentOpenExit)
        finish()
    }


    private fun revealActivity(x: Int, y: Int) {
            val finalRadius = (max(
                rootLayout.width,
                rootLayout.height
            ) * 1.1).toFloat()

            // create the animator for this view (the start radius is zero)
            val circularReveal =
                ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0f, finalRadius)
            circularReveal.duration = 400
            circularReveal.interpolator = AccelerateInterpolator()

            // make the view visible and start the animation
        rootLayout.visibility = View.VISIBLE
            circularReveal.start()
    }

    
    fun read(key: String, value: Boolean): Boolean {
        return prefs.getBoolean(key, value)
    }

    fun write(key: String, value: Boolean) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
