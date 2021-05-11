package com.eec_cn.client4k.http

import com.eec_cn.client4k.EecClient

class AuthEecHttpConnect(
    private val eecClient: EecClient,
    httpConnect: IHttpConnect,
    url: String
) : EecHttpConnect(httpConnect, url) {
    override fun includeHeader(head: MutableMap<String, String>) {
        head["Authentication"] = eecClient.token.accessToken
    }
}
