package pt.ipt.dam2023.cartrade.retrofit.service

import pt.ipt.dam2023.cartrade.model.APIResult
import pt.ipt.dam2023.cartrade.model.AuthenticationResponse
import pt.ipt.dam2023.cartrade.model.Car
import pt.ipt.dam2023.cartrade.model.CarPhotosResponse
import pt.ipt.dam2023.cartrade.model.User
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface Service {
    /******************************************************U S E R S*****************************************************************/
    @GET("getUsers/")
    fun list_Users():Call<List<User>>

    @FormUrlEncoded
    @POST("addUser/")
    fun addUser(@Field("id") id: Int?, @Field("nome") nome: String?, @Field("apelido") apelido: String?
                ,@Field("email") email: String?, @Field("password") password: String?): Call<APIResult>

    @FormUrlEncoded
    @POST("authentication/")
    fun authentication(@Field("email") email: String?, @Field("password") password: String?): Call<AuthenticationResponse>

    @GET("getCars/")
    fun list_Cars(): Call<List<Car>>

    @GET("getCars/{id}/")
    fun getCar(@Path("id") id: Int): Call<Car>

    @GET("getCars/{id}/photos/")
    fun getCarPhotos(@Path("id") id: Int): Call<CarPhotosResponse?>?

    @FormUrlEncoded
    @POST("addCar/")
    fun addCar (@Field("id") id: Int,
               @Field("marca") marca: String?,
               @Field("modelo") modelo: String?,
               @Field("versao")  versao: String?,
               @Field("combustivel")  combustivel: String?,
               @Field("ano") ano: Int?,
               @Field("kms")  kms: Int?,
               @Field("potencia")  potencia: Int?,
               @Field("cilindrada")  cilindrada: Int?,
               @Field("cor")  cor: String?,
               @Field("tipoCaixa")  tipoCaixa: String?,
               @Field("numMudancas")  numMudancas: Int?,
               @Field("numPortas")  numPortas: Int?,
               @Field("lotacao")  lotacao: Int?,
               @Field("condicao")  condicao: String?,
               @Field("preco")  preco: Int?,
               @Field("user") user:String): Call<APIResult>


    @PUT("updateCar/{id}/")
    fun updateCar(@Path("id") id: Int,
                  @Path("marca") marca: String?,
                  @Path("modelo") modelo: String?,
                  @Path("versao")  versao: String?,
                  @Path("combustivel")  combustivel: String?,
                  @Path("ano") ano: Int?,
                  @Path("kms")  kms: Int?,
                  @Path("potencia")  potencia: Int?,
                  @Path("cilindrada")  cilindrada: Int?,
                  @Path("cor")  cor: String?,
                  @Path("tipoCaixa")  tipoCaixa: String?,
                  @Path("numMudancas")  numMudancas: Int?,
                  @Path("numPortas")  numPortas: Int?,
                  @Path("lotacao")  lotacao: Int?,
                  @Path("condicao")  condicao: String?,
                  @Path("preco")  preco: Int?,):Call<APIResult>
    @DELETE("deleteCar/{id}/")
    fun deletedCar(@Path("id") id:Int):Call<APIResult>
}