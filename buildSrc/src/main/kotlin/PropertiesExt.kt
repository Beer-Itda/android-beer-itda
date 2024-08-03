import java.io.File
import java.util.*

object PropertiesExt {
    const val ENABLE_AGGREGATING_TASK = false
    private const val FILE_SECRURE = "secure.properties"
    const val KAKAO = "kakao"
    const val BASE_URL = "base_url"

    private val secureProperties = secure()

    private fun secure(): Properties {
        val secureProperties = Properties()
        File(FILE_SECRURE).run {
            println("FILE_SECRURE : ${exists()}")
            if (exists()) {
                secureProperties.load(inputStream())
            }
        }
        return secureProperties
    }

    fun getKakaoKey(): String = secureProperties.getProperty(KAKAO) ?: ""
    fun getBaseUrl(): String = secureProperties.getProperty(BASE_URL) ?: ""
}
