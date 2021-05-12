package com.eec_cn.client4k.beans

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyBean(
    val publicKey: String
)

@Serializable
data class LoginBean(
    val cryptogram: String,
    val key: String,
    val mode: String
)

@Serializable
data class LoginInfoBean(val accessToken: String, val refreshToken: String)

@Serializable
data class TokenInfoBean(
    @SerialName("man")
    val userInfo: ManBean,
    @SerialName("org")
    val schoolInfo: OrgBean,
)

@Serializable
data class ManBean(
    val account: String,
    val accountId: String,
    val memberId: String,
    val mobile: String,
    val mobileVerified: Boolean,
    val name: String,
    val passwordCorrected: Boolean,
    val portrait: String,
    val sex: String,
    val type: String
)

@Serializable

data class OrgBean(
    val classradeCode: String,
    val classradeId: String,
    val classradeName: String,
    val collegeCode: String,
    val collegeId: String,
    val collegeName: String,
    val majorCode: String,
    val majorId: String,
    val majorName: String,
    val schoolCode: String,
    val schoolId: String,
    val schoolName: String
)

@Serializable
data class Pms(
    val attachment: Long,
    val base: Int,
    val course: Long,
    val exam: Int,
    val grad: Int
)

@Serializable
data class SchoolInfoBean(
    val accessUrl: String,
    val badge: String,
    val code: String,
    val collegeModels: List<String>,
    val id: String,
    val name: String
)
