package com.hjiee.core.manager

import android.content.Context
import com.hjiee.core.manager.FirebaseConfigManager.Companion.FORCE_UPDATE_VERSION
import com.hjiee.core.manager.FirebaseConfigManager.Companion.RECOMMEND_UPDATE_VERSION
import com.hjiee.core.manager.FirebaseConfigManager.Companion.UPDATE_VERSION_INFO
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.core.util.log.L
import com.hjiee.core.util.version.VersionCompareUtil.resultVersionCompare
import org.json.JSONObject
import javax.inject.Inject

class VersionManager @Inject constructor(
    private val context: Context,
    private val preference: SharedPreferenceProvider
) {

    val version: String
        get() = try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName.orEmpty()
        } catch (e: Exception) {
            L.e(e)
            ""
        }

    val forceUpdateVersion: String
        get() = try {
            updateTargetJsonObject.getString(FORCE_UPDATE_VERSION)
        } catch (e: Exception) {
            L.e(e)
            ""
        }


    val recommendUpdateVersion: String
        get() = try {
            updateTargetJsonObject.getString(RECOMMEND_UPDATE_VERSION)
        } catch (e: Exception) {
            L.e(e)
            ""
        }

    private val updateTargetInfo: String
        get() = preference.getPreferenceString(UPDATE_VERSION_INFO).orEmpty()

    private val updateTargetJsonObject: JSONObject
        get() = try {
            JSONObject(updateTargetInfo)
        } catch (e: Exception) {
            L.e(e)
            JSONObject()
        }


    var lastVisitTime
        get() = preference.getPreferenceString(KEY_LAST_VISIT)
        set(value) = preference.setValue(KEY_LAST_VISIT, value)

    fun updateInfo(): UpdateRequiredStatus {
        return resultVersionCompare(
            currentVersion = version,
            forceVersion = forceUpdateVersion,
            recommendVersion = recommendUpdateVersion
        )
    }

    companion object {
        const val KEY_LAST_VISIT = "last_visit"
    }
}

sealed class UpdateRequiredStatus {
    object Force : UpdateRequiredStatus()
    object Recommend : UpdateRequiredStatus()
    object None : UpdateRequiredStatus()
}