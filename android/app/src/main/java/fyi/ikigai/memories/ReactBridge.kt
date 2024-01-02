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
    fun setToken(token: String?, callback: Callback) {
        coroutineScope.launch {
            val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(Widget::class.java)

            glanceIds.forEach { glanceId ->
                glanceId.let {
                    updateAppWidgetState(context, PreferencesGlanceStateDefinition, it) { pref ->
                        pref.toMutablePreferences().apply {
                            if (token != null) {
                                this[currentToken] = token
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
        val currentToken = stringPreferencesKey("token")
    }
}
