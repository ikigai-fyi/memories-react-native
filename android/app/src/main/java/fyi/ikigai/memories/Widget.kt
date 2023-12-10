import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.SuccessResult
import fyi.ikigai.memories.MainActivity
import fyi.ikigai.memories.ReactBridge


class Widget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MyContent()
        }
    }

    @Composable
    private fun MyContent() {
        val context = LocalContext.current
        val prefs = currentState<Preferences>()
        val currentName = prefs[ReactBridge.currentName]
        val currentTime = prefs[ReactBridge.currentTime]
        val currentDistance = prefs[ReactBridge.currentDistance]
        val currentElevation = prefs[ReactBridge.currentElevation]
        val url = prefs[ReactBridge.currentPicture]
        var image by remember(url) { mutableStateOf<Bitmap?>(null) }

        LaunchedEffect(url) {
            image = url?.let { context.getImage(it) }
        }

        if (image != null) {
            Column(
                modifier = GlanceModifier.fillMaxSize().padding(12.dp)
                    .background(Color(android.graphics.Color.WHITE))
                    .background(ImageProvider(image!!), ContentScale.Crop)
                    .clickable(actionStartActivity<MainActivity>()),
                verticalAlignment = Alignment.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = currentName.toString(), style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ), maxLines = 1,
                    modifier = GlanceModifier.padding(bottom = 2.dp)
                )
                Text(
                    text = currentTime.toString(), style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontSize = 14.sp
                        ), maxLines = 1
                )
                Row() {
                    Text(
                        text = currentDistance.toString(), style = TextStyle(
                            color = ColorProvider(Color.White), fontSize = 14.sp
                        ), maxLines = 1
                    )
                    Text(
                        text = currentElevation.toString(), style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 14.sp
                        ), maxLines = 1,
                        modifier = GlanceModifier.padding(start = 6.dp)
                    )
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }

    private suspend fun Context.getImage(url: String): Bitmap? {
        val request = ImageRequest.Builder(this).data(url).apply {
            memoryCachePolicy(CachePolicy.DISABLED)
            diskCachePolicy(CachePolicy.DISABLED)
        }.build()

        val imageLoader = ImageLoader(this)
        return when (val result = imageLoader.execute(request)) {
            is ErrorResult -> throw result.throwable
            is SuccessResult -> result.drawable.toBitmapOrNull()
        }
    }
}