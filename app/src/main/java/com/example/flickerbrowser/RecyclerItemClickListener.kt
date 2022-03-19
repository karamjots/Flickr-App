package com.example.flickerbrowser

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(context:Context,recyclerview:RecyclerView,private val listener:OnRecycleClickListener)
    :RecyclerView.SimpleOnItemTouchListener() {

    private val TAG ="RecyclerItemClickListen"

    interface OnRecycleClickListener{
        fun onItemClick(view: View, position:Int)
        fun onItemLongClick(view: View,position: Int)
    }

    //this code is written to assign gesture and to distinguish between clicks
    private val gestureDetector = GestureDetectorCompat(context,object :GestureDetector.SimpleOnGestureListener(){

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d(TAG,".onSingleTapUp called: starts")

            //e.x and e.y indicates the position where the user clicked
            val childView = recyclerview.findChildViewUnder(e.x,e.y)
            Log.d(TAG,".onSingleTapUp calling listener: OnItemClick")
            if (childView != null) {
                listener.onItemClick(childView,recyclerview.getChildAdapterPosition(childView))
            }
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG,".onLongPress called: starts")
            val childView=recyclerview.findChildViewUnder(e.x,e.y)
            Log.d(TAG,".onLongPress calling listener: OnItemLongClick")
            if (childView != null) {
                listener.onItemLongClick(childView,recyclerview.getChildAdapterPosition(childView))
            }
        }
    })

    // this function is written to know about clicks/touch
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG,"onInterceptTouchEvent:starts $e")

        val result= gestureDetector.onTouchEvent(e)
        Log.d(TAG,"onInterceptTouchEvent() returning:$result")
//        return super.onInterceptTouchEvent(rv, e)
        return result
    }
}