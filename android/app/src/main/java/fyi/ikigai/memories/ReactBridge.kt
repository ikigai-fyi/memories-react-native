package fyi.ikigai.memories

import Widget
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

class ReactBridge(var context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
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
                                this[currentName] = json.getJSONObject("value").getString("name")
                                this[currentDate] =
                                    json.getJSONObject("value").getString("start_datetime")
                                this[currentDistance] =
                                    json.getJSONObject("value").getString("distance_in_meters")
                                this[currentHeight] = json.getJSONObject("value")
                                    .getString("total_elevation_gain_in_meters")
                                this[currentImageUrl] =
                                    json.getJSONObject("value").getString("picture_url")
                            }
                        }
                    }
                    glanceAppWidget.update(context, it)
                }
            }

            callback(glanceIds.size)
        }
    }

    companion object {
        val currentName = stringPreferencesKey("name")
        val currentDate = stringPreferencesKey("date")
        val currentDistance = stringPreferencesKey("distance")
        val currentHeight = stringPreferencesKey("height")
        val currentImageUrl = stringPreferencesKey("url")
    }
}
