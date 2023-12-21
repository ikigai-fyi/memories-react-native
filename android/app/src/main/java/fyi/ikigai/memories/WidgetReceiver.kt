package fyi.ikigai.memories

import Widget
import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import okhttp3.internal.notify

class WidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = Widget()

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        ReactBridge.notify()
    }
}
