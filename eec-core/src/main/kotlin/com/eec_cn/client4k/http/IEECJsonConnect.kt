package com.eec_cn.client4k.http

interface IEECJsonConnect {

    fun get(path: String, header: Map<String, String> = mapOf()): String
    fun post(path: String, postData: String, header: Map<String, String> = mapOf()): String

}
