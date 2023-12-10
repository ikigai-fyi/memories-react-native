package fyi.ikigai.memories

import Widget
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class ReactBridge(private var context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    override fun getName(): String {
        return "ReactBridge"
    }

    private val coroutineScope = MainScope()

    private val glanceAppWidget: GlanceAppWidget = Widget()

    @ReactMethod
    fun setData(data: String?, callback: Callback) {
        val json = data?.let { JSONObject(it) }

        coroutineScope.launch {
            val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(Widget::class.java)

            glanceIds.forEach { glanceId ->
                glanceId.let {
                    updateAppWidgetState(context, PreferencesGlanceStateDefinition, it) { pref ->
                        pref.toMutablePreferences().apply {
                            if (json != null) {
                                this[currentName] = json.getString("name")
                                this[currentTime] = json.getString("time")
                                this[currentDistance] = json.getString("distance")
                                this[currentElevation] =
                                    json.getString("elevation")
                                this[currentPicture] = json.getString("picture")
                            }
                        }
                    }
                    glanceAppWidget.update(context, it)
                }
            }

            callback(null, glanceIds.size)
        }
    }

    companion object {
        val currentName = stringPreferencesKey("name")
        val currentTime = stringPreferencesKey("time")
        val currentDistance = stringPreferencesKey("distance")
        val currentElevation = stringPreferencesKey("elevation")
        val currentPicture = stringPreferencesKey("picture")
    }
}
