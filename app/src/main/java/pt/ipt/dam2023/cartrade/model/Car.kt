package pt.ipt.dam2023.cartrade.model

import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("id") val id: Int,
    @SerializedName("marca") val marca: String?,
    @SerializedName("modelo") val modelo: String?,
    @SerializedName("versao") val versao: String?,
    @SerializedName("combustivel") val combustivel: String?,
    @SerializedName("ano") val ano: Int?,
    @SerializedName("kms") val kms: Int?,
    @SerializedName("potencia") val potencia: Int?,
    @SerializedName("cilindrada") val cilindrada: Int?,
    @SerializedName("cor") val cor: String?,
    @SerializedName("tipoCaixa") val tipoCaixa: String?,
    @SerializedName("numMudancas") val numMudancas: Int?,
    @SerializedName("numPortas") val numPortas: Int?,
    @SerializedName("lotacao") val lotacao: Int?,
    @SerializedName("condicao") val condicao: String?,
    @SerializedName("preco") val preco: Int?,
    @SerializedName("user") val user: String?
)

class CarPhotosResponse (
    @SerializedName("photos") val photos: List<String>?
)

class CarResponse(
    @SerializedName("id") val id: Int
)