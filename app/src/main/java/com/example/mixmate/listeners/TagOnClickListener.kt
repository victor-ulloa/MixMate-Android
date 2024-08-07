package com.example.mixmate.listeners

import android.view.View
import com.example.mixmate.data.Constants

interface TagOnClickListener {
    fun onTagClick(view : View, tag: Constants.Tags)
}