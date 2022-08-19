package constants

object URLManager {
    const val EDU_LOGIN_SSO_URL =
        "https://sso.dlut.edu.cn/cas/login?service=http%3A%2F%2Fjxgl.dlut.edu.cn%2Fstudent%2Fucas-sso%2Flogin"
    val EDU_CHECK = "http://jxgl.dlut.edu.cn/student/for-std/change-major-apply/get-my-applies?batchId=${System.getenv("BATCH")}&studentId=${System.getenv("STU")}"
}