package com.caneryildirim.ulkeler.service


import com.caneryildirim.ulkeler.model.Country
import com.caneryildirim.ulkeler.viewModel.FeedViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface CountryAPI {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    suspend fun getCountries():Response<List<Country>>


}


