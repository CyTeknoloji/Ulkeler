package com.caneryildirim.ulkeler.model

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class User
    @Inject
    constructor() {

        fun testFunc(view:View){
            Snackbar.make(view,"Test Yapıldı",Snackbar.LENGTH_LONG).show()
        }


}