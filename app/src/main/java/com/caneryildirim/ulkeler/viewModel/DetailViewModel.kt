package com.caneryildirim.ulkeler.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caneryildirim.ulkeler.model.Country
import com.caneryildirim.ulkeler.service.CountryDatabase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel:ViewModel() {
    val countryLive=MutableLiveData<Country>()
    private val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)
    }

    fun getDataFromRoom(uuid:Int,context: Context){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val dao=CountryDatabase(context.applicationContext).countryDao()
            val country=dao.getCountry(uuid)
            withContext(Dispatchers.Main){
                countryLive.value=country
            }
        }

    }



    override fun onCleared() {
        super.onCleared()
    }
}