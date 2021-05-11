package com.eec_cn.client4k


/**
 * 通过 LoginUtils 建立登陆方案
 *
 * 非异步操作，不要在主线程执行
 *
 * @property login Login
 * @constructor
 */
class EecClient(private val login: Login){
    init {
        verify()
    }

    /**
     * 校验
     */
    fun verify() {

    }

}
