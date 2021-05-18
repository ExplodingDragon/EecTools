package activities

import EecService
import com.eec_cn.client4k.modules.group.beans.ClassBean
import com.eec_cn.client4k.modules.operation.beans.OperationBean
import com.eec_cn.client4k.modules.operation.beans.OperationItemBean
import com.eec_cn.client4k.modules.operation.beans.OptionBean
import com.eec_cn.client4k.modules.operation.beans.QuestionType
import com.github.open_edgn.fx.manager.activity.FXMLActivity
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.FileChooser
import org.apache.commons.text.StringEscapeUtils
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : FXMLActivity<VBox>() {
    override val fxmlPath = "/fxml/activity_main.fxml"
    override val title = "新工科智慧云"

    private val classData = FXCollections.observableArrayList<ClassBean>()

    @FXML
    private lateinit var user: Label

    @FXML
    private lateinit var schoolLogo: ImageView

    @FXML
    private lateinit var classListView: ListView<ClassBean>

    @FXML
    private lateinit var operation: ListView<OperationBean>
    private val operationData = FXCollections.observableArrayList<OperationBean>()

    @FXML
    private lateinit var operationItem: ListView<OperationItemBean>
    private val operationItemData = FXCollections.observableArrayList<OperationItemBean>()

    @FXML
    private lateinit var detailsTitle: Label

    @FXML
    private lateinit var select: Label

    @FXML
    private lateinit var detailsMessage: TextArea

    @FXML
    private lateinit var userAnswer: TextArea

    @FXML
    private lateinit var detailsAnswer: TextArea

    @FXML
    private lateinit var answerView: VBox

    @FXML
    private lateinit var showAnswer: CheckBox


    private val modules = EecService.eecClient.modules

    @Volatile
    private var className: String = ""

    @Volatile
    private var operationName: String = ""

    override fun onStart() {
        bindView()
        schoolLogo.image = Image(modules.user.schoolInfo.badge)
        user.text = modules.user.tokenInfo.userInfo.name
        classData.clear()
        classData.addAll(modules.group.classes)
    }

    private fun showExport(item: OperationBean): ContextMenu {
        val contextMenu = ContextMenu()
        contextMenu.items.add(MenuItem("导出 ${item.operationInfo.name}"))
        contextMenu.setOnAction {
            export(item)
        }
        return contextMenu
    }

    private fun export(item: OperationBean) {
        val fileChooser = FileChooser()
        fileChooser.title = "保存 $className 作业"
        fileChooser.initialFileName = "作业-${item.operationInfo.name}.txt"
        fileChooser.extensionFilters
            .add(FileChooser.ExtensionFilter("TXT 文件", "*.txt"))
        val file = fileChooser.showSaveDialog(window.fxWindow) ?: return
        val toList = EecService.eecClient.modules.operation.getOperationItems(item).map {
            EecService.eecClient.modules.operation.getOperationItemDetails(it)
        }.toList()
        val writer = file.printWriter()
        if (file.absolutePath.endsWith(".txt")) {
            toList.forEach { it ->
                writer.println("""
                    第 ${it.orderNumber} 题
                    问 ：${formatHtml(it.titleText)}
                    ${
                    if (it.questionType == QuestionType.SINGLE ||
                        it.questionType == QuestionType.MULTIPLE
                    ) {
                        val optionBean = it.options as List<OptionBean>
                        optionBean.joinToString("\r\n")
                        { "选项 ${it.id} :  ${formatHtml(it.optionContent)}" }
                    } else {
                        ""
                    }
                }
                ${
                    if (it.userAnswer.isNotBlank()) {
                        "用户答案： ${formatHtml(it.userAnswer).replace("@@@", "、")}"
                    } else {
                        ""
                    }
                }
                 参考答案： ${formatHtml(it.answer).replace("@@@", "、")}
                 
                """.lines().joinToString("\r\n") { it.trim() })
            }
        } else {
//MARKDOWN
        }
        writer.flush()
        writer.close()
    }


    private fun bindView() {
        answerView.visibleProperty().bind(showAnswer.selectedProperty())
        classListView.items = classData
        classListView.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let {
                className = newValue.name
                operationData.clear()
                operationData.addAll(EecService.eecClient.modules.operation.getOperations(it))
            }
        }
        classListView.setCellFactory {
            object : ListCell<ClassBean>() {
                override fun updateItem(item: ClassBean?, empty: Boolean) {
                    super.updateItem(item, empty)
                    graphic = if (item != null) {
                        Label(item.name)
                    } else {
                        null
                    }
                }
            }
        }

        operation.items = operationData
        operationItem.items = operationItemData
        operation.setCellFactory {
            val value: ListCell<OperationBean> = object : ListCell<OperationBean>() {
                override fun updateItem(item: OperationBean?, empty: Boolean) {
                    super.updateItem(item, empty)
                    graphic = if (item != null) {
                        val vBox = VBox()


                        vBox.children.run {
                            val title = Label("${item.operationInfo.name} ")
                            title.font = Font.font(14.0)
                            add(title)
                            add(Label("成绩：${(100 * (item.operationInfo.userScore / item.operationInfo.totalScore)).toInt()} "))
                            val endTime = item.endTime.toLong() * 1000
                            val addIt = if (endTime < System.currentTimeMillis()) {
                                val status = Label("已截止")
                                status.textFill = Color.RED
                                status
                            } else {
                                val hours = (endTime - System.currentTimeMillis()) / 1000 / 60 / 60
                                val status = Label("剩余 $hours 小时.")
                                status.textFill = Color.GREEN
                                status
                            }
                            add(addIt)
                        }
                        vBox
                    } else {
                        null
                    }
                }
            }
            value.emptyProperty().addListener { _, _, c ->
                if (c) {
                    value.contextMenu = null
                } else {
                    value.contextMenu = showExport(value.item)
                }
            }
            value

        }
        operationItem.setCellFactory {
            object : ListCell<OperationItemBean>() {
                override fun updateItem(item: OperationItemBean?, empty: Boolean) {
                    super.updateItem(item, empty)
                    graphic = if (item != null) {
                        Label("第${item.orderNumber}题，${item.questionType.typeName}")
                    } else {
                        null
                    }
                }
            }
        }
        operation.selectionModel.selectedItemProperty()
            .addListener { _, _, newValue ->
                operationName = newValue.operationInfo.name
                operationItemData.clear()
                newValue?.run {
                    operationItemData.addAll(EecService.eecClient.modules.operation.getOperationItems(newValue))
                }
            }
        operationItem.selectionModel.selectedItemProperty()
            .addListener { _, _, newValue ->
                if (newValue == null) {
                    return@addListener
                }
                select.text = " $className > $operationName > 第 ${newValue.orderNumber} 题"
                val message = EecService.eecClient.modules.operation.getOperationItemDetails(newValue)
                detailsTitle.text = "第 ${message.orderNumber} 题 [${message.questionType.typeName}]"
                detailsMessage.text = """
                    问 ：${formatHtml(message.titleText)}
               
                    ${
                    if (message.questionType == QuestionType.SINGLE ||
                        message.questionType == QuestionType.MULTIPLE
                    ) {
                        val optionBean = message.options as List<OptionBean>
                        optionBean.joinToString("\r\n")
                        { "选项 ${it.id} :  ${formatHtml(it.optionContent)}" }
                    } else {
                        ""
                    }
                }
                """.lines().joinToString("\r\n") { it.trim() }
                userAnswer.text = "用户答案：\n ${formatHtml(message.userAnswer)}"
                detailsAnswer.text = "参考答案 ：\n ${formatHtml(message.answer).replace("@@@", "、")}"
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        window.isFullScreen = false
    }

    @FXML
    fun logout() {
        EecService.cfgPath.delete()
        finish()
    }

    private fun formatHtml(data: String): String {
        return delHTMLTag(StringEscapeUtils.unescapeHtml4(StringEscapeUtils.unescapeXml(data)))
    }

    private fun delHTMLTag(html: String): String {
        var htmlStr = html
        val regexScript = "<script[^>]*?>[\\s\\S]*?</script>" //定义script的正则表达式
        val regexStyle = "<style[^>]*?>[\\s\\S]*?</style>" //定义style的正则表达式
        val regexHtml = "<[^>]+>" //定义HTML标签的正则表达式
        val scriptPattern: Pattern = Pattern.compile(regexScript, Pattern.CASE_INSENSITIVE)
        val scriptMatcher: Matcher = scriptPattern.matcher(htmlStr)
        htmlStr = scriptMatcher.replaceAll("") //过滤script标签
        val stylePattern: Pattern = Pattern.compile(regexStyle, Pattern.CASE_INSENSITIVE)
        val styleMatcher: Matcher = stylePattern.matcher(htmlStr)
        htmlStr = styleMatcher.replaceAll("") //过滤style标签
        val htmlPattern: Pattern = Pattern.compile(regexHtml, Pattern.CASE_INSENSITIVE)
        val htmlMatcher: Matcher = htmlPattern.matcher(htmlStr)
        htmlStr = htmlMatcher.replaceAll("") //过滤html标签
        return htmlStr.trim { it <= ' ' } //返回文本字符串
    }
}


