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

class ReadViewModel(application: Application):AndroidViewModel(application) {
    val readLD = MutableLiveData<Hobby>()

    val TAG = "volleyTag"
    private var queue: RequestQueue?= null

    fun fetch(hobby_id:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobby.hobby.json?=id$hobby_id"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object: TypeToken<Hobby>() { }.type
                val result = Gson().fromJson<Hobby>(it, sType)
                readLD.value = result
                Log.d("showvolley", result.toString())
            },
            {
                Log.d("showvolley", it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}