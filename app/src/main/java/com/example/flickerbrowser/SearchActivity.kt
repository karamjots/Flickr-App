package com.example.flickerbrowser

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.navigation.ui.AppBarConfiguration
import androidx.preference.PreferenceManager
import com.example.flickerbrowser.databinding.ActivitySearchBinding

class SearchActivity :BaseActivity() {
    private val TAG ="SearchActivity"

    private var searchView:SearchView?=null

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,".onCreate: starts")
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activateToolbar(true)
        Log.d(TAG,".onCreate: ends")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG,".onCreateOptionsMenu : starts")

        // bottom code is written to inflate menu_search for searchbar and to perform search function in the search tool bar

        menuInflater.inflate(R.menu.menu_search,menu)

        val searchManager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView=menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
//        Log.d(TAG,".onCreateOptionsMenu : $componentName ")
//        Log.d(TAG,".onCreateOptionsMenu : hint is ${searchView?.queryHint}")
//        Log.d(TAG,".onCreateOptionsMenu : $searchableInfo")

        searchView?. isIconified =false

// the code below is written for search function to add listener and to make search bar work by giving the KEY (FLICKR_QUERY)
// and using the same kay in the main activity onResume activity to perform the  search function

        searchView?.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG,".onQueryTextSubmit :called")

                val sharedPref=PreferenceManager.getDefaultSharedPreferences(applicationContext)
                sharedPref.edit().putString(FLICKR_QUERY,query).apply()
                searchView?.clearFocus()
                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // this code will take us to the home screen main activity when user decided to cancel the search

        searchView?.setOnCloseListener {
            finish()
            false
        }

        Log.d(TAG,".onCreateOptionsMenu : returning")
        return true
    }
}