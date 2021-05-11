package com.eec_cn.client4k.http

import com.eec_cn.client4k.beans.DataWrapperBean
import com.github.openEdgn.logger4k.getLogger
import org.json.JSONObject

abstract class EecHttpConnect(
    private val httpConnect: IHttpConnect,
    private val url: String
) : IEECJsonConnect {
    private val logger = getLogger()
    private val userAgent: String =
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.56"

    override fun get(path: String, header: Map<String, String>): String {
        val head = header.toMutableMap()
        head["User-Agent"] = userAgent
        head["Accept"] = "application/json"
        head["Accept-Language"] = "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6"
        val data =
            httpConnect.connect(IHttpConnect.Method.GET, "$url$path", head).data.readBytes().toString(Charsets.UTF_8)
        logger.traceOnly {
            trace("GET '$path' \r\n {}", data)
        }
        return formatData(data)
    }



    override fun post(path: String, postData: String, header: Map<String, String>): String {
        val head = header.toMutableMap()
        includeHeader(head)
        head["User-Agent"] = userAgent
        head["Accept"] = "application/json"
        head["Content-Type"] = "application/json;charset=utf-8"
        head["Accept-Language"] = "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6"
        val data =
            httpConnect.connect(
                IHttpConnect.Method.POST,
                "$url$path",
                head,
                postData.toByteArray(Charsets.UTF_8).inputStream()
            ).data.readBytes().toString(Charsets.UTF_8)
        logger.traceOnly {
            trace("POST '$path' \r\n {}", data)
        }
        return formatData(data)
    }

    abstract fun includeHeader(head: MutableMap<String, String>)

    private fun formatData(data: String): String {
        val jsonObject = JSONObject(data)
        val output = DataWrapperBean(
            code = jsonObject.getInt("code"),
            success = jsonObject.getBoolean("success"),
            data = "",
            message = jsonObject.getString("message"),
            status = jsonObject.getInt("status"),
            tracer = jsonObject.getString("tracer")
        )
        if (output.success) {
            output.data = jsonObject.getJSONObject("data").toString()
            return output.data
        } else {
            throw RuntimeException(output.message)
        }
    }


}

