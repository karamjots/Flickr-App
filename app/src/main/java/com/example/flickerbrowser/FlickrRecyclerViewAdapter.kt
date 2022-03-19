package com.example.flickerbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

// this whole code is written as adapter which provides information such as views,widgets,position and loading new data

class FlickrImageViewHolder(view:View):RecyclerView.ViewHolder(view){
    var thumbnail:ImageView=view.findViewById(R.id.thumbnail)
    var title:TextView=view.findViewById(R.id.title)

}

class FlickrRecyclerViewAdapter(private var photoList :List<Photo>):RecyclerView.Adapter<FlickrImageViewHolder>() {
    private val TAG="FlickAdapter"

    override fun getItemCount(): Int {
//        Log.d(TAG,".getItemCount called")
        return if (photoList.isNotEmpty())photoList.size else 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
//        called by the layout manager when it needs a new view
        Log.d(TAG,".onCreateViewHolder new view requested")
        val view=LayoutInflater.from(parent.context).inflate(R.layout.browse,parent,false)
        return FlickrImageViewHolder(view)
    }



    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
     // called bu the layout manager when it wants new data in the existing view


//        Log.d(TAG,".onBindViewHolder : ${photoItem.title}--> $position")

        if (photoList.isEmpty()){
            holder.thumbnail.setImageResource(R.drawable.placeholder)
            holder.title.setText(R.string.empty_photo)
        }else{
            val photoItem=photoList[position]
            Picasso.get()
                .load(photoItem.image)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail)
            holder.title.text=photoItem.title

        }
    }



  // to let the recyclerview know that their is new data/photo
    fun loadNewData(newPhotos:List<Photo>){
        photoList=newPhotos
        notifyDataSetChanged()
    }

    // to see the detail of the photo, or to use to open up in new layout
    fun getPhoto(position: Int):Photo?{
        return if (photoList.isNotEmpty()) photoList[position] else null
    }

}