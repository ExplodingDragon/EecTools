package com.eec_cn.client4k.http

class NoAuthEecHttpConnect(
    httpConnect: IHttpConnect,
    url: String
) : EecHttpConnect(httpConnect, url) {
    override fun includeHeader(head: MutableMap<String, String>) {
    }
}
