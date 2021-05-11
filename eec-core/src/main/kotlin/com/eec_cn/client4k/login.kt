package com.eec_cn.client4k

import com.eec_cn.client4k.beans.KeyBean
import com.eec_cn.client4k.beans.LoginBean
import com.eec_cn.client4k.beans.LoginInfoBean
import com.eec_cn.client4k.http.IEECJsonConnect
import com.github.openEdgn.logger4k.getLogger
import com.github.open_edgn.security4k.asymmetric.rsa.RsaPublic
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject


interface ILogin {
    fun login(httpConnect: IEECJsonConnect): LoginInfoBean
}

/**
 *  登录接口
 */
object LoginUtils {
    private val logger = getLogger()
    fun withPhoneLogin(phone: String, password: String): ILogin {
        return object : ILogin {
            override fun login(httpConnect: IEECJsonConnect): LoginInfoBean {
                val key = Json.decodeFromString<KeyBean>(httpConnect.get("/auth/key"))
                val rsaPublic = RsaPublic(key.publicKey)
                val loginBean = LoginBean(
                    cryptogram = rsaPublic.encodeText("$phone|$password").replace("\r\n", ""),
                    key = key.publicKey,
                    mode = "Password"
                )
                logger.trace("login Post \r\n{}", loginBean)
                val postData = JSONObject(loginBean)
                val string = httpConnect.post("/auth/login", postData.toString())
                logger.trace("login Result:{}", string)
                return Json.decodeFromString(
                    string
                )
            }
        }
    }

    fun withTokenLogin(token:LoginInfoBean): ILogin {
        return object : ILogin {
            override fun login(httpConnect: IEECJsonConnect): LoginInfoBean {
                return token
            }
        }

    }

}
