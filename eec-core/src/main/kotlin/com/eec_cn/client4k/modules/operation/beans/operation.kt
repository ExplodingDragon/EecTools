package com.eec_cn.client4k.modules.operation.beans

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


@Serializable
data class OperationBean(
    @SerialName("courseId")
    val courseId: String,
    @SerialName("createTime")
    val createTime: Double,
    @SerialName("creator")
    val creator: String,
    @SerialName("creatorId")
    val creatorId: String,
    @SerialName("endTime")
    val endTime: Double,
    @SerialName("homeworkDTO")
    val operationInfo: HomeworkBean,
    @SerialName("id")
    val id: String,
    @SerialName("readCount")
    val readCount: Int,
    @SerialName("readEndTime")
    val readEndTime: Double = 0.0,
    @SerialName("readType")
    val readType: Int,
    @SerialName("reviewTask")
    val reviewTask: Int,
    @SerialName("sequence")
    val sequence: Double = 0.0,
    @SerialName("showAnswer")
    val showAnswer: Int,
    @SerialName("showScore")
    val showScore: Int,
    @SerialName("submitTask")
    val submitTask: Int,
    @SerialName("teachClass")
    val teachClass: TeachClassBean
)

@Serializable
data class HomeworkBean(
    @SerialName("id")
    val id: String,
    @SerialName("isDelete")
    val isDelete: Int,
    @SerialName("name")
    val name: String,
    @SerialName("questionCount")
    val questionCount: Int,
    @SerialName("readCount")
    val readCount: Int,
    @SerialName("showAnswer")
    val showAnswer: Int,
    @SerialName("showScore")
    val showScore: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("subStatus")
    val subStatus: Int,
    @SerialName("totalScore")
    val totalScore: Double = 0.0,
    @SerialName("userScore")
    val userScore: Double = 0.0
)

@Serializable
data class TeachClassBean(
    @SerialName("code")
    val code: String,
    @SerialName("courseId")
    val courseId: String,
    @SerialName("createAt")
    val createAt: Long,
    @SerialName("createBy")
    val createBy: String,
    @SerialName("experimentWeightChange")
    val experimentWeightChange: String,
    @SerialName("experimentWeightChangeTitle")
    val experimentWeightChangeTitle: String,
    @SerialName("homeworkWeightChange")
    val homeworkWeightChange: String,
    @SerialName("homeworkWeightChangeTitle")
    val homeworkWeightChangeTitle: String,
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("updateAt")
    val updateAt: Long,
    @SerialName("updateBy")
    val updateBy: String
)


@Serializable
data class OperationItemBean(
    @SerialName("id")
    val id: String,
    @SerialName("isAnswer")
    val isAnswer: Int,
    @SerialName("orderNumber")
    val orderNumber: Int,
    @SerialName("qsnType")
    @Serializable(with = QuestionTypeSerializer::class)
    val questionType: QuestionType,
    @SerialName("userScore")
    val userScore: Double = 0.0
)

enum class QuestionType(val type: Int, val typeName: String) {

    SINGLE(1, "单选题"),
    MULTIPLE(2, "多选题"),
    JUDGMENT(3, "判断题"),
    SIMPLE_ASK(5, "简答题"),
    BLANK(6, "填空题"),
    LARGE_BLANK(7, "应用题"),

}

class QuestionTypeSerializer : KSerializer<QuestionType> {
    override fun deserialize(decoder: Decoder): QuestionType {
        val decodeInt = decoder.decodeInt()
        for (value in QuestionType.values()) {
            if (value.type == decodeInt) {
                return value
            }
        }
        throw RuntimeException("未找到TYPE为 $decodeInt 的类型.")
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("QuestionType", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: QuestionType) {
        encoder.encodeInt(value.type)
    }

}

@Serializable
data class OperationItemDetailsBean<T:Any>(
    @SerialName("answer")
    val answer: String = "",
    @SerialName("creator")
    val creator: String,
    @SerialName("creatorId")
    val creatorId: String,
    @SerialName("difficulty")
    val difficulty: Int,
    @SerialName("everyScore")
    val everyScore: Double,
    @SerialName("homeworkId")
    val homeworkId: String,
    @SerialName("hwAnswerId")
    val hwAnswerId: String = "",
    @SerialName("id")
    val id: String,
    @SerialName("isAutojudge")
    val isAutojudge: Int,
    @SerialName("isCorrect")
    val isCorrect: Int = 0,
    @SerialName("libQuestionId")
    val libQuestionId: String,
    @SerialName("options")
    val options: List<T> = arrayListOf(),
    @SerialName("orderNumber")
    val orderNumber: Int,
    @SerialName("parentId")
    val parentId: String,
    @SerialName("qsnType")
    @Serializable(with = QuestionTypeSerializer::class)
    val questionType: QuestionType,
    @SerialName("titleText")
    val titleText: String,
    @SerialName("userAnswer")
    val userAnswer: String = "",
    @SerialName("userScore")
    val userScore: Double = 0.0
)



@Serializable
data class OptionBean(
    @SerialName("id")
    val id: String,
    @SerialName("optionContent")
    val optionContent: String
)
