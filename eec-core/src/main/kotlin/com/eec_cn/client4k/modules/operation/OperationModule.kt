package com.eec_cn.client4k.modules.operation

import com.eec_cn.client4k.EecClient
import com.eec_cn.client4k.beans.NothingBean
import com.eec_cn.client4k.modules.group.beans.ClassBean
import com.eec_cn.client4k.modules.group.beans.GroupBean
import com.eec_cn.client4k.modules.operation.beans.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * 作业模块
 *
 */
class OperationModule(private val eecClient: EecClient) {
    /**
     * 得到发布的作业列表
     */
    fun getOperations(`class`: ClassBean): List<OperationBean> {
        val get = eecClient.authEecConnect
            .get("/course/homeworks/published/student?courseId=${`class`.id}")
        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString<GroupBean<OperationBean>>(get).list
    }

    fun getOperationItems(operation: OperationBean): List<OperationItemBean> {
        val get = eecClient.authEecConnect
            .get("/course/homeworkQuestions/student?homeworkId=${operation.operationInfo.id}")
        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString(get)
    }

    fun getOperationItemDetails(item: OperationItemBean): OperationItemDetailsBean<out Any> {
        val get = eecClient.authEecConnect
            .get("/course/homeworkQuestions/${item.id}/student/detail")
        return Json {
            ignoreUnknownKeys = true
        }.run {
            val any = when (item.questionType) {
                QuestionType.SINGLE,
                QuestionType.MULTIPLE ->
                    decodeFromString<OperationItemDetailsBean<OptionBean>>(get)
                QuestionType.JUDGMENT,
                QuestionType.BLANK,
                QuestionType.SIMPLE_ASK,
                QuestionType.LARGE_BLANK -> decodeFromString<OperationItemDetailsBean<NothingBean>>(get)
            }
            any
        }


    }
}
