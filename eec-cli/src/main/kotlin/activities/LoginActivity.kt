package activities

import EecService
import com.eec_cn.client4k.EecClient
import com.eec_cn.client4k.LoginUtils
import com.eec_cn.client4k.beans.LoginInfoBean
import com.github.open_edgn.fx.manager.activity.FXMLActivity
import com.github.open_edgn.fx.manager.intent.Intent
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LoginActivity : FXMLActivity<VBox>() {
    override val fxmlPath = "/fxml/activity_login.fxml"
    override val title = "登陆 Eec-cn"

    @FXML
    private lateinit var phone: TextField

    @FXML
    private lateinit var password: PasswordField


    @FXML
    private lateinit var saveToken: CheckBox

    @FXML
    private lateinit var message: Label


    override fun onStart() {
        if (EecService.cfgPath.isFile) {
            message.text = "已开启自动登录！"
            root.isDisable = true
            try {

                val data = EecService.cfgPath.readText(Charsets.UTF_8)
                val token = Json.decodeFromString<LoginInfoBean>(data)
                EecService.eecClient = EecClient(LoginUtils.withTokenLogin(token))
                startMain()
                return
            } catch (e: Exception) {

            }finally {
                root.isDisable = false
            }
        }
    }

    fun login(actionEvent: ActionEvent) {
        root.isDisable = true
        try {
            EecService.eecClient = EecClient(LoginUtils.withPhoneLogin(phone.text, password.text))
            if (saveToken.isSelected) {
                EecService.cfgPath.writeText(Json.encodeToString(EecService.eecClient.token))
            }
            startMain()
            return
        }catch (e:Exception){
            message.text = e.message

        }finally {
            root.isDisable = false

        }
    }

    private fun startMain() {
        startActivity(Intent(this, MainActivity::class))
        finish()
    }

}
