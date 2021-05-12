package com.eec_cn.client4k.modules.user

import com.eec_cn.client4k.EecClient
import com.eec_cn.client4k.beans.SchoolInfoBean
import com.eec_cn.client4k.beans.TokenInfoBean
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*

class UserModule(private val eecClient: EecClient) {

    val tokenInfo: TokenInfoBean
        get() = kotlin.run {
            val decodeText =
                Base64.getDecoder().decode(
                    eecClient.token.accessToken
                        .split(".")[1]
                        .replace("_", "/")
                        .replace("-", "+")
                )
                    .toString(Charsets.UTF_8)
            Json {
                ignoreUnknownKeys = true
            }.decodeFromString(decodeText)

        }

    val userId: String
        get() = tokenInfo.userInfo.accountId


    val schoolInfo: SchoolInfoBean
        get() = Json.decodeFromString(
            eecClient.eecConnect
                .get("/base/schools/${tokenInfo.schoolInfo.schoolCode}?details=page")
        )
}
