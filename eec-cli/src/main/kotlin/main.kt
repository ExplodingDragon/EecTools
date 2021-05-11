import activities.LoginActivity
import com.github.openEdgn.logger4k.getLogger
import com.github.open_edgn.fx.manager.FXBoot
import com.github.open_edgn.fx.manager.UIApplication

class Main

private val logger = Main::class.getLogger()

fun main(args: Array<String>) {
    logger.info("System Boot!")
    UIApplication.boot(
        FXBoot.Builder(LoginActivity::class)
            .build()
    )
}
