package com.eec_cn.client4k.http

import com.eec_cn.client4k.http.IHttpConnect.Method
import com.github.openEdgn.logger4k.getLogger
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class SunHttpConnect() : IHttpConnect {
    private val logger = getLogger()
    override fun connect(
        method: Method,
        url: String,
        header: Map<String, String>,
        body: InputStream
    ): IHttpConnect.HttpResult {
        val urlConnect: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
        urlConnect.requestMethod = method.name
        header.forEach { (t, u) ->
            urlConnect.setRequestProperty(t, u)
        }
        urlConnect.doInput = true
        urlConnect.readTimeout = 3 * 1000
        urlConnect.connectTimeout = 3 * 1000
        if (arrayOf(Method.POST,Method.PUT).contains(method)){
            urlConnect.doOutput = true
            body.transferTo(urlConnect.outputStream)
        }
        urlConnect.connect()
        logger.trace("{} {}.",method,url)

        val responseHeader =
            urlConnect.headerFields.map { Pair(it.key, it.value.joinToString(separator = ";")) }.toMap()
        val inputStream = when {
            urlConnect.responseCode >= HttpURLConnection.HTTP_OK &&
                    urlConnect.responseCode < HttpURLConnection.HTTP_BAD_REQUEST-> {
                urlConnect.inputStream
            }
            urlConnect.responseCode >= HttpURLConnection.HTTP_BAD_REQUEST -> {
                urlConnect.errorStream
            }
            else -> {
                urlConnect.errorStream
            }
        }
        return IHttpConnect.HttpResult(urlConnect.responseCode, responseHeader, inputStream)
    }

}
