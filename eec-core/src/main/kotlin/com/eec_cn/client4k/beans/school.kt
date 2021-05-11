package com.eec_cn.client4k.beans

import kotlinx.serialization.Serializable

@Serializable
data class SchoolInfoBean(
    val accessUrl: String,
    val badge: String,
    val code: String,
    val collegeModels: List<String>,
    val id: String,
    val name: String
)
