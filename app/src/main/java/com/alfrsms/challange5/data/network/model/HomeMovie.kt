package com.alfrsms.challange5.data.network.model

import com.google.gson.annotations.SerializedName

class HomeMovie (
    val title: String? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<MovieItem>? = null
        )