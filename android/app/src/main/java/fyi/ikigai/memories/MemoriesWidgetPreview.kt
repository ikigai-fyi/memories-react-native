package fyi.ikigai.memories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.preferencesOf
import androidx.glance.appwidget.ExperimentalGlanceRemoteViewsApi
import com.google.android.glance.appwidget.host.glance.GlanceAppWidgetHostPreview

@Preview
@OptIn(ExperimentalGlanceRemoteViewsApi::class)
@Composable
fun MemoriesWidgetPreview() {
    GlanceAppWidgetHostPreview(
        modifier = Modifier.fillMaxSize(),
        glanceAppWidget = MemoriesWidget(),
        state = preferencesOf(),
        displaySize = DpSize(200.dp, 200.dp),
    )
}