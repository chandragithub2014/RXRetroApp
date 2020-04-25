package com.rx.rxretroapp.di

import com.rx.rxretroapp.retrorx.repository.APIService
import com.rx.rxretroapp.retrorx.repository.RetroURL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetroRxModule {


    @Provides
    fun  provideRetroInfo() : APIService{
       return  Retrofit
           .Builder()
           .baseUrl(RetroURL.BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
           .build()
           .create(APIService::class.java)

    }
}