package com.asykurkhamid.gbook.http

import com.asykurkhamid.gbook.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory {

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInitInstance() : Services {
        return retrofit().create(Services::class.java)
    }

}