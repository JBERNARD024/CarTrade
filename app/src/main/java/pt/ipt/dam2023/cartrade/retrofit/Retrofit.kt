package pt.ipt.dam2023.cartrade.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pt.ipt.dam2023.cartrade.retrofit.service.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {

    private val gson: Gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://jbernard024.pythonanywhere.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    fun getService() = retrofit.create(Service::class.java)
}