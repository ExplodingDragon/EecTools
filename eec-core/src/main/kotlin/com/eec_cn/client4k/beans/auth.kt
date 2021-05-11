package com.eec_cn.client4k.beans

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
    val aex: Int,
    val aud: List<String>,
    val exp: Int,
    val iat: Int,
    val iss: String,
    val jti: String,
    val man: Man,
    val nbf: Int,
    val org: Org,
    val pms: Pms,
    val rft: Boolean,
    val sub: String
)

@Serializable
data class Man(
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

data class Org(
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
