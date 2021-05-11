package com.eec_cn.client4k

data class Login(val accessToken: String, val refreshToken: String)

/**
 *  登录接口
 */
object LoginUtils {
    fun withPhoneLogin(phone: String, password: String): Login {
        TODO()
    }

    fun withTokenLogin(accessToken: String, refreshToken: String): Login {
        return Login(accessToken, refreshToken)
    }
}
