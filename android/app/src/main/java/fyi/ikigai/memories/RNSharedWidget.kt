package fyi.ikigai.memories

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class RNSharedWidget(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "RNSharedWidget"
    }

    @ReactMethod
    fun setData(key: String?, data: String?, callback: Callback?) {
        val editor = context.getSharedPreferences("DATA", Context.MODE_PRIVATE).edit()
        editor.putString(key, data)
        editor.commit()
        val intent = Intent(currentActivity!!.applicationContext, GlanceWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids =
            AppWidgetManager.getInstance(currentActivity!!.applicationContext).getAppWidgetIds(
                ComponentName(
                    currentActivity!!.applicationContext, GlanceWidget::class.java
                )
            )
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ids)
        currentActivity!!.applicationContext.sendBroadcast(intent)
    }
}