package com.eec_cn.client4k.beans

import kotlinx.serialization.Serializable

@Serializable
data class DataWrapperBean(
    val code: Int,
    var `data`: String,
    val message: String,
    val status: Int,
    val success: Boolean,
    val tracer: String
)
