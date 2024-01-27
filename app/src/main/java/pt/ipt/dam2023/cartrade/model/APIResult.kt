package pt.ipt.dam2023.cartrade.model

import com.google.gson.annotations.SerializedName

data class APIResult (
    @SerializedName("code") val code: Int?,
    @SerializedName("description") val description: String?
)