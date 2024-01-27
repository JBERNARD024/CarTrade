package pt.ipt.dam2023.cartrade.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int?,
    @SerializedName("nome") val nome: String?,
    @SerializedName("apelido") val apelido: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String?
)