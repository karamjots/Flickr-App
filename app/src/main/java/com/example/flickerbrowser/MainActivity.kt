package com.example.flickerbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickerbrowser.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

private val TAG="MainActivity"

private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())


class MainActivity : BaseActivity(),GetRawData.OnDownloadComplete,
    GetFlickrJsonData.OnDataAvailable,RecyclerItemClickListener.OnRecycleClickListener{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate method called")
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activateToolbar(false)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this,recycler_view,this))
        recycler_view.adapter = flickrRecyclerViewAdapter

//raw data is executed in the code below

//        val url=createUri("https://www.flickr.com/services/feeds/photos_public.gne","android,oreo","en-us",true)
//        val getRawData=GetRawData(this)
//        getRawData.execute(url)

    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG,".onItemClick: starts")
        Toast.makeText(this,"normal tap at position $position",Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG,".onItemLongClick :starts")
//        Toast.makeText(this,"long tap at position $position",Toast.LENGTH_SHORT).show()

        val photo = flickrRecyclerViewAdapter.getPhoto(position)
        if (photo !=null){
            val intent =Intent(this,PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER,photo)
            startActivity(intent)
        }
    }

    private fun createUri(baseURL: String, searchCriteria:String, lang:String, matchall:Boolean):String{
        Log.d(TAG,".createUri starts")
        return Uri.parse(baseURL)
        .buildUpon()
        .appendQueryParameter("tags",searchCriteria)
        .appendQueryParameter("tagmode",if (matchall)"All" else "ANY")
        .appendQueryParameter("lang",lang)
        .appendQueryParameter("format","json")
        .appendQueryParameter("nojsoncallback","1")
        .build().toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG,"onCreateOptionsMenu called")
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG,"onOptionsItemSelected called")
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this,SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


//    companion object{
//        private const val TAG="MainActivity"
//    }

   // bottom code is written for the callback to check when the downloading is done
    override fun onDownloadComplete(data:String,status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete called")

// parsed data is executed in the code below
            val getFlickrJsondata = GetFlickrJsonData(this)
            getFlickrJsondata.execute(data)
        } else {
            Log.d(TAG, "onDownloadComplete failed with status  $status. Error message is:$data")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG,".onDataAvailable called ")

        flickrRecyclerViewAdapter.loadNewData(data)

        Log.d(TAG,".onDataAvailable ends")
    }

    override fun onError(exception: Exception) {
        Log.e(TAG,"onError called with ${exception.message}")
    }

   // the code below is written to make search feature working
    override fun onResume() {
        Log.d(TAG,".onResume starts")
        super.onResume()

        val sharedPref= PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult =sharedPref.getString(FLICKR_QUERY,"")

        if (!queryResult.isNullOrBlank()){

            val url=createUri("https://www.flickr.com/services/feeds/photos_public.gne",queryResult,"en-us",true)
            val getRawData=GetRawData(this)
            getRawData.execute(url)

        }
        Log.d(TAG,".onResume ends")
    }
}