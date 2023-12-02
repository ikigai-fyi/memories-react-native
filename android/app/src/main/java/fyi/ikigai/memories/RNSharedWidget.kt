package fyi.ikigai.memories

import NewMemoriesWidget
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
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

class RNSharedWidget(var context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    override fun getName(): String {
        return "RNSharedWidget"
    }

    private val coroutineScope = MainScope()

    private val glanceAppWidget: GlanceAppWidget = NewMemoriesWidget()

    @ReactMethod
    fun setData(key: String?, data: String?, callback: Callback) {
        val json = data?.let { JSONObject(it) }

        coroutineScope.launch {
            val glanceId = GlanceAppWidgetManager(context).getGlanceIds(NewMemoriesWidget::class.java)
                .firstOrNull()

            glanceId?.let {
                updateAppWidgetState(context, PreferencesGlanceStateDefinition, it) { pref ->
                    pref.toMutablePreferences().apply {
                        if (json != null) {
                            this[currentName] = json.getJSONObject("value").getString("name")
                            this[currentDate] = json.getJSONObject("value").getString("start_datetime")
                            this[currentDistance] = json.getJSONObject("value").getString("distance_in_meters")
                            this[currentHeight] = json.getJSONObject("value").getString("total_elevation_gain_in_meters")
                            this[currentImageUrl] = json.getJSONObject("value").getString("picture_url")
                        }
                    }
                }
                glanceAppWidget.update(context, it)
            }
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
