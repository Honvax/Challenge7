package com.alfrsms.challenge6.data.network.model.search


import com.alfrsms.challenge6.data.network.model.search.SearchItem
import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<SearchItem?>? = null
)