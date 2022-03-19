package com.example.flickerbrowser

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

//this activity is written to extend it to main class

internal const val FLICKR_QUERY="FLICKR_QUERY"
internal const val PHOTO_TRANSFER="PHOTO_TRANSFER"

open class BaseActivity:AppCompatActivity() {
    private val TAG = "BaseActivity"


    //this function is written to add button to go back to the main page/home page
    internal fun activateToolbar(enableHome:Boolean){
        Log.d(TAG,".activateToolbar")

        var toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}