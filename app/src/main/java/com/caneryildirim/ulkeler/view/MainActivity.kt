package com.caneryildirim.ulkeler.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.caneryildirim.ulkeler.R
import com.caneryildirim.ulkeler.databinding.ActivityMainBinding
import com.caneryildirim.ulkeler.model.User
import com.caneryildirim.ulkeler.service.CountryAPI
import com.caneryildirim.ulkeler.util.CustomSharedPreferences
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    @Inject
    lateinit var testPreferences: CustomSharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)



     }
}