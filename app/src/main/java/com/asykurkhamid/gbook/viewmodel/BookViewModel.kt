package com.asykurkhamid.gbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asykurkhamid.gbook.http.ServiceFactory
import com.asykurkhamid.gbook.model.BookModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookViewModel: ViewModel() {

    private var bookData: MutableLiveData<BookModel> = MutableLiveData()
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun callBookData(key: String){

            isLoading.value = true
            val service = ServiceFactory().getInitInstance()
            val api = service.getBooks(key)

            api.enqueue(object : Callback<BookModel> {
                override fun onFailure(call: Call<BookModel>, t: Throwable) {
                    isLoading.postValue(false)
                    bookData.postValue(null)
                }

                override fun onResponse(call: Call<BookModel>, response: Response<BookModel>) {
                    isLoading.postValue(false)
                    if (response.isSuccessful && response.body()?.items != null) {
                        bookData.postValue(response.body())
                    }else{
                        bookData.postValue(null)
                    }
                }
            })
    }

    fun getBook(): LiveData<BookModel>? {
        return bookData
    }

    fun isLoading():LiveData<Boolean>{
        return isLoading
    }
}