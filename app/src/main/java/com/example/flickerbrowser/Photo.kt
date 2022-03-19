package com.example.flickerbrowser

import android.util.Log
import java.io.IOException
import java.io.ObjectStreamException
import java.io.Serializable

class Photo(var title:String, var author :String, var authorID:String, var link:String, var tags :String, var image:String):Serializable {

    companion object{
        private const val serialVersionUID=1L
    }

    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorID='$authorID', link='$link', tags='$tags', image='$image')"
    }

   // the whole codes below is to implement complex Serializable
    @Throws(IOException::class)
    private fun writeObject(out:java.io.ObjectOutputStream){
        Log.d("photo","writeObject called")
        out.writeUTF(title)
        out.writeUTF(author)
        out.writeUTF(authorID)
        out.writeUTF(link)
        out.writeUTF(tags)
        out.writeUTF(image)

    }

    @Throws(IOException::class,ClassNotFoundException::class)
    private fun readObject(inStream:java.io.ObjectInputStream){
        Log.d("photo","readObject called")
        title= inStream.readUTF()
        author=inStream.readUTF()
        authorID=inStream.readUTF()
        link=inStream.readUTF()
        tags=inStream.readUTF()
        image=inStream.readUTF()
    }

    @Throws(ObjectStreamException::class)
    private fun readObjectNoData(){
        Log.d("photo","readObjectNoData called")
    }


    }

