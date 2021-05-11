package activities

import com.github.open_edgn.fx.manager.activity.FXMLActivity
import javafx.scene.layout.VBox

class MainActivity : FXMLActivity<VBox>() {
    override val fxmlPath = "/fxml/activity_main.fxml"
    override val title = "新工科智慧云"

    override fun onStart() {
        val crossToken = intent.getExtra("crossToken", "")

    }


}
