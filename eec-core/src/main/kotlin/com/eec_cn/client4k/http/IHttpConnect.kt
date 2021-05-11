package com.eec_cn.client4k.http

import java.io.ByteArrayInputStream
import java.io.InputStream

interface IHttpConnect {
    enum class Method {
        GET, POST, PUT, DELETE
    }

    data class HttpResult(val code: Int, val header: Map<String, String>, val data: InputStream)

    fun connect(
        method: Method,
        url: String,
        header: Map<String, String>,
        body: InputStream = ByteArrayInputStream(
            byteArrayOf()
        )
    ): HttpResult


}
