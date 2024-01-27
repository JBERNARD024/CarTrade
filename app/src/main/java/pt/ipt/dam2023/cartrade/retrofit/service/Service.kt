package pt.ipt.dam2023.cartrade.retrofit.service

import pt.ipt.dam2023.cartrade.model.APIResult
import pt.ipt.dam2023.cartrade.model.AuthenticationResponse
import pt.ipt.dam2023.cartrade.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @GET("getUsers/")
    fun list():Call<List<User>>

    @FormUrlEncoded
    @POST("addUser/")
    fun addUser(@Field("id") id: Int?, @Field("nome") nome: String?, @Field("apelido") apelido: String?
                ,@Field("email") email: String?, @Field("password") password: String?): Call<APIResult>

    @FormUrlEncoded
    @POST("authentication/")
    fun authentication(@Field("email") email: String?, @Field("password") password: String?): Call<AuthenticationResponse>
}