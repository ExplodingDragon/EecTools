package com.eec_cn.client4k

import com.eec_cn.client4k.beans.LoginInfoBean
import com.eec_cn.client4k.http.*
import com.eec_cn.client4k.modules.group.GroupModule
import com.eec_cn.client4k.modules.operation.OperationModule
import com.eec_cn.client4k.modules.user.UserModule


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

    val modules = Modules(this)

    inner class Modules(eecClient: EecClient) {
        /**
         * 用户信息管理
         */
        val user = UserModule(eecClient)

        /**
         * 加入班级信息管理
         */
        val group = GroupModule(eecClient)

        /**
         * 作业管理
         */
        val operation = OperationModule(eecClient)
    }

    fun tryLogin() {
        token = login.login(eecConnect)
    }


    /**
     * 校验
     */


}
