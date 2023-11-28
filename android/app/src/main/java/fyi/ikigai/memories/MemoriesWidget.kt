package fyi.ikigai.memories

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

class MemoriesWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // Load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            // create your AppWidget here
            Content()
        }
    }

    @Composable
    private fun Content() {
        Text(text = "⏳")
        Column(
            modifier = GlanceModifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = "⏳")
            }
            Row {
                Text(text = "Feb 19, 2023", style = TextStyle(color = ColorProvider(Color(0xFFFF0000))))
            }
            Row {
                Text(text= "26,03km")
                Text(text= "450 m")
            }
        }
    }
}

