package com.eec_cn.client4k.modules.group.beans

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupBean<T:Any>(
    @SerialName("fieldList")
    val list: List<T>,
    val totalPage: Int,
    val totalRow: Int
)

@Serializable
data class ClassBean(
    val classHour: Int,
    val courseSignInOpen: Int,
    val courseTeachClasses: List<CourseTeachClassBean>,
    val createTime: Double,
    val creator: String,
    val creatorId: String,
    val disable: Int,
    val endTime: Double,
    val icon: String,
    val id: String,
    val isArchive: Int,
    val isDelete: Int,
    val isTemplate: Int,
    val name: String,
    val obeEnabled: Boolean,
    val orgId: String,
    val publishStatus: Int,
    val specialtyName: String,
    val startTime: Double,
    val studentCount: Int,
    val term: String,
    val type: Int,
    val videoDrag: Int
):java.io.Serializable

@Serializable
data class CourseTeachClassBean(
    val code: String,
    val courseId: String,
    val createAt: Long,
    val createBy: String,
    val id: String,
    val name: String,
    val updateAt: Long,
    val updateBy: String
)
