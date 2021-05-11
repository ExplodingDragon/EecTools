package com.eec_cn.client4k.http

import org.junit.jupiter.api.Test
import java.nio.charset.Charset

internal class SunHttpConnectTest{
    @Test
    fun connect() {
        val sunHttpConnect = SunHttpConnect()
        val connect = sunHttpConnect.connect(IHttpConnect.Method.POST, "https://www.baidu.com", mapOf())
        println(connect.code)
        println(connect.data.readAllBytes().toString(Charset.defaultCharset()))
        println(connect.header)

    }

}
