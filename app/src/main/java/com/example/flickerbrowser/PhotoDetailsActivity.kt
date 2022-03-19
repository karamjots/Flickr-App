package com.example.flickerbrowser

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import com.example.flickerbrowser.databinding.ActivityPhotoDetailsBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo_details.*

class PhotoDetailsActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPhotoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d(TAG,".onCreate:starts")
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activateToolbar(true)


        // this code is written to display the photo and other information into the new activity

        val photo = intent.getSerializableExtra(PHOTO_TRANSFER) as Photo

//        photo_title.text = photo.title
        photo_title.text=resources.getString(R.string.photo_tittle_text,photo.title)
//        photo_tags.text=photo.tags
        photo_tags.text=resources.getString(R.string.photo_tag_text,photo.tags)

        photo_author.text = photo.author
//        photo_author.text=resources.getString(R.string.photo_author_text,"my" ,"own","code")

        Picasso.get()
            .load(photo.link)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(photo_image)






    }


}