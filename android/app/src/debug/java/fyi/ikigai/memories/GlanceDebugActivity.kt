package fyi.ikigai.memories

import androidx.datastore.preferences.core.mutablePreferencesOf
import androidx.glance.appwidget.ExperimentalGlanceRemoteViewsApi
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.google.android.glance.tools.viewer.GlanceSnapshot
import com.google.android.glance.tools.viewer.GlanceViewerActivity

@OptIn(ExperimentalGlanceRemoteViewsApi::class)
class GlanceDebugActivity : GlanceViewerActivity() {

    override suspend fun getGlanceSnapshot(
        receiver: Class<out GlanceAppWidgetReceiver>,
    ): GlanceSnapshot {
        return when (receiver) {
            MemoriesWidgetReceiver::class.java -> GlanceSnapshot(
                instance = MemoriesWidget(),
                state = mutablePreferencesOf(),
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun getProviders() = listOf(MemoriesWidgetReceiver::class.java)
}