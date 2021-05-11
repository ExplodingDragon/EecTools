import com.eec_cn.client4k.EecClient
import java.io.File

object EecService {
    val cfgPath: File = File(System.getProperty("user.home"),".eecToken.json")


    lateinit var eecClient: EecClient
}
