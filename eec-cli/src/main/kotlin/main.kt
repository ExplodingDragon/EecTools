import activities.LoginActivity
import com.github.openEdgn.logger4k.getLogger
import com.github.open_edgn.fx.manager.FXBoot
import com.github.open_edgn.fx.manager.UIApplication

class Main

private val logger = Main::class.getLogger()

fun main(args: Array<String>) {
    logger.info("新工科智慧云 第三方组件!")
    UIApplication.boot(
        FXBoot.Builder(LoginActivity::class)
            .build()
    )
}
