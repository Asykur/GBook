package com.asykurkhamid.gbook.http

import com.asykurkhamid.gbook.model.BookModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    @GET("volumes")
    fun getBooks(@Query("q") kewWord: String): Call<BookModel>

}