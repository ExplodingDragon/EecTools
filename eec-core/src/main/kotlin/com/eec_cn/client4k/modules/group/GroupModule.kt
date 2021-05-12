package com.eec_cn.client4k.modules.group

import com.eec_cn.client4k.EecClient
import com.eec_cn.client4k.modules.group.beans.ClassBean
import com.eec_cn.client4k.modules.group.beans.GroupBean
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GroupModule(private val eecClient: EecClient) {

    val classes: List<ClassBean>
        get() {
            val get = eecClient.authEecConnect.get("/course/courses/joinedCourses?type=3")
            return Json {
                ignoreUnknownKeys = true
            }.decodeFromString<GroupBean<ClassBean>>(get).list
        }


}
