package pt.ipt.dam2023.cartrade.model

import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("id") val id: Int?
)