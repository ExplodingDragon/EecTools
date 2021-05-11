package com.eec_cn.client4k

import com.eec_cn.client4k.beans.LoginInfoBean
import com.eec_cn.client4k.http.*
import com.eec_cn.client4k.modules.UserModule


/**
 * 通过 LoginUtils 建立登陆方案
 *
 * 非异步操作，不要在主线程执行
 *
 * @property login Login
 * @constructor
 */
class EecClient(private val login: ILogin, private val httpConnect: IHttpConnect = SunHttpConnect()) {

    lateinit var token: LoginInfoBean

    private val eecUrl: String = "https://www.eec-cn.com/api"

    /**
     *  不带 TOKEN 的 HTTP 请求
     */
    val eecConnect: IEECJsonConnect = NoAuthEecHttpConnect(httpConnect, eecUrl)

    /**
     *  带 TOKEN 的 HTTP 请求
     */
    val authEecConnect: IEECJsonConnect = AuthEecHttpConnect(this, httpConnect, eecUrl)



    init {
        tryLogin()
    }

    val user = UserModule(this)

    fun tryLogin() {
        token = login.login(eecConnect)
    }


    /**
     * 校验
     */


}
