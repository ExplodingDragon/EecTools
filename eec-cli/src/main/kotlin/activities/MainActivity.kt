package activities

import EecService
import com.github.open_edgn.fx.manager.activity.FXMLActivity
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import kotlin.system.exitProcess

class MainActivity : FXMLActivity<VBox>() {
    override val fxmlPath = "/fxml/activity_main.fxml"
    override val title = "新工科智慧云"

    @FXML
    private lateinit var user:Label

    @FXML
    private lateinit var schoolLogo:ImageView

    override fun onStart() {
        schoolLogo.image = Image(EecService.eecClient.user.schoolInfo.badge)
        user.text = EecService.eecClient.user.tokenInfo.man.name
    }

    fun logout(mouseEvent: MouseEvent) {
        EecService.cfgPath.deleteOnExit()
        exitProcess(0)
    }


}
