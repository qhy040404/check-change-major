import constants.Constants
import constants.URLManager
import kotlinx.coroutines.delay
import utils.Des
import utils.Requests
import javax.swing.JOptionPane
import kotlin.system.exitProcess

fun login() {
    val des = Des()

    val id = System.getenv("ID")
    val passwd = System.getenv("PASS")

    val ltResponse = Requests.get(URLManager.EDU_LOGIN_SSO_URL)
    val ltData = try {
        "LT" + ltResponse.split("LT")[1].split("cas")[0] + "cas"
    } catch (e: Exception) {
        return
    }
    val ltExecution = ltResponse.split("name=\"execution\" value=\"")[1].split("\"")[0]

    val rawData = "$id$passwd$ltData"
    val rsa = des.strEnc(rawData, "1", "2", "3")

    Requests.post(
        URLManager.EDU_LOGIN_SSO_URL,
        Requests.loginPostData(id, passwd, ltData, rsa, ltExecution),
        Constants.CONTENT_TYPE_SSO
    )
}

suspend fun main() {
    var timer = 0
    login()

    while (true) {
        timer++
        if (timer == 10) {
            timer = 0
            login()
        }

        delay(5000L)
        val applyData = Requests.get(URLManager.EDU_CHECK)

        if (applyData.contains("\"enrollResult\": true")) {
            //JOptionPane.showMessageDialog(null,"润啦","好",JOptionPane.INFORMATION_MESSAGE)
            println("TRUE! NICE")
            exitProcess(114514)
        } else if (applyData.contains("\"enrollResult\": false")) {
            //JOptionPane.showMessageDialog(null,"润不了啦","完蛋",JOptionPane.ERROR_MESSAGE)
            println("FALSE! Just cheer up.")
            exitProcess(114514)
        }
    }
}