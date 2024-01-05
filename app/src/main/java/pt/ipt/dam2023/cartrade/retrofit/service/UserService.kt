package pt.ipt.dam2023.cartrade.retrofit.service

import pt.ipt.dam2023.cartrade.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("users/")
    fun list_users(): Call<List<User>>


}