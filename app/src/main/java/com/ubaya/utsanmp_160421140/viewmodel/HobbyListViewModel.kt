package com.ubaya.utsanmp_160421140.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.utsanmp_160421140.model.Hobby

class HobbyListViewModel(application: Application): AndroidViewModel(application) {
    val hobbyLD = MutableLiveData<ArrayList<Hobby>>()
    val loadingErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private val TAG = "volleyTag"
    private var queue:RequestQueue ?= null

    fun fetch(){
        loadingErrorLD.value = false
        loadingLD.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobby.hobby.json"

        val stringRequest = StringRequest(Request.Method.GET,url,
            { response ->
                val sType = object:TypeToken<ArrayList<Hobby>>() { }.type
                val result = Gson().fromJson<ArrayList<Hobby>>(response, sType)
                hobbyLD.value = result

                loadingLD.value = false
                Log.d("Showvolley", response.toString())
            },
            {
                loadingErrorLD.value = true
                loadingLD.value = false
                Log.d("Showvolley", it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}