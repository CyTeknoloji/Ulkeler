package com.caneryildirim.ulkeler.viewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caneryildirim.ulkeler.model.Country
import com.caneryildirim.ulkeler.service.CountryAPI
import com.caneryildirim.ulkeler.service.CountryDatabase
import com.caneryildirim.ulkeler.util.CustomSharedPreferences
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class FeedViewModel:ViewModel() {
    val countryListLive=MutableLiveData<ArrayList<Country>>()
    val uploadDataLive=MutableLiveData<Boolean>(false)
    val errorDataLive=MutableLiveData<Boolean>(false)
    private lateinit var contextFromFragment:Context
    private lateinit var customSharedPreferences:CustomSharedPreferences
    private val refreshTime=10*60*1000*1000*1000L
    private var job:Job?=null
    private val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.localizedMessage)
        viewModelScope.launch(Dispatchers.Main) {
            errorDataLive.value=true
            uploadDataLive.value=false
        }
    }

    fun getContext(context: Context){
        contextFromFragment=context
        customSharedPreferences= CustomSharedPreferences(context)
    }

    fun refreshDataForSwipe(context: Context){
        getDataFromRetrofit(context)
    }


    fun refreshData(context: Context){
        val savedTime=customSharedPreferences.getTime()
        if (savedTime!=null && savedTime!=0L && System.nanoTime()-savedTime<refreshTime){
            getDataFromRoom(context)
        }else{
            getDataFromRetrofit(context)
        }

    }

    private fun getDataFromRoom(context: Context) {
        uploadDataLive.value=true
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val dao=CountryDatabase(context).countryDao()
            val countryList=dao.gelAllCountries()
            withContext(Dispatchers.Main){
                showCountries(countryList)
                Toast.makeText(context,"Roomdan geldi",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDataFromRetrofit(context: Context){
        uploadDataLive.value=true

        val retrofit=Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryAPI::class.java)

        job=viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response=retrofit.getCountries()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        Toast.makeText(context,"Api den geldi",Toast.LENGTH_SHORT).show()
                        storeInRoom(context,it)
                    }
                }else{
                    uploadDataLive.value=false
                    errorDataLive.value=true
                }
            }
        }
    }

    private fun storeInRoom(context:Context,countryList:List<Country>){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val dao=CountryDatabase(context.applicationContext).countryDao()
            dao.deleteAllCountries()
            val listLong=dao.insertAll(*countryList.toTypedArray())
            var i=0
            while (i<countryList.size){
                countryList[i].uuid=listLong[i].toInt()
                i += 1
            }
            withContext(Dispatchers.Main){
                showCountries(countryList)
                customSharedPreferences.saveTime(System.nanoTime())
            }

        }
    }

    private fun showCountries(countryList:List<Country>){
        uploadDataLive.value=false
        errorDataLive.value=false
        countryListLive.value= ArrayList(countryList)
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}