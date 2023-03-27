package com.caneryildirim.ulkeler.service


import com.caneryildirim.ulkeler.model.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface CountryAPI {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    suspend fun getCountries():Response<List<Country>>


}