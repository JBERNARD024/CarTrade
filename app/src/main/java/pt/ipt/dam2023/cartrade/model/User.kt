package pt.ipt.dam2023.cartrade.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id") val int: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String?)