package activities

import com.github.open_edgn.fx.manager.activity.FXMLActivity
import com.github.open_edgn.fx.manager.intent.Intent
import javafx.fxml.FXML
import javafx.scene.layout.VBox
import javafx.scene.web.WebView

class LoginActivity : FXMLActivity<VBox>() {
    override val fxmlPath = "/fxml/activity_login.fxml"
    override val title = "登陆 Eec-cn"

    @FXML
    lateinit var webView: WebView
    override fun onStart() {
        window.isMaximized = true
        webView.engine.load("https://www.eec-cn.com/")
        webView.engine.titleProperty().addListener { _ ->
            window.title = webView.engine.title
        }
        webView.engine.locationProperty().addListener { _ ->
            if (webView.engine.location == "https://www.eec-cn.com/student/group") {
                if (webView.engine.executeScript("localStorage.getItem('crossToken') != null").toString().toBoolean()) {
                    val token = webView.engine.executeScript("localStorage.getItem('crossToken')").toString()
                    logger.info(
                        "User Token : {}", token
                    )
                    startActivity(Intent(this, MainActivity::class).putExtra("crossToken", token))
                }
            }
        }
    }

}
