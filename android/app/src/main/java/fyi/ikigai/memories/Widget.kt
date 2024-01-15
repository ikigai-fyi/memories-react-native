import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.SuccessResult
import fyi.ikigai.memories.ReactBridge
import fyi.ikigai.memories.StravaActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class Widget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        withContext(Dispatchers.Default) {
            val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTcwNDI5NzY2NywianRpIjoiMDhlN2FmYTAtNGM1Mi00NzUxLTlhN2MtNjJiYzU4ZDFjMjBmIiwidHlwZSI6ImFjY2VzcyIsInN1YiI6ImF0aF9BemNGS1JDVnJhOVM2OVo0IiwibmJmIjoxNzA0Mjk3NjY3fQ.NWYcxFjXlO4YYDB0J0Uc7fHOw8W85ffUx1wC80prGVk"

            val url = "https://api-dev.ikigai.fyi/rest/memories/current"
            val client = OkHttpClient()
            val request = Request.Builder()
                .addHeader("Authorization", "Bearer $token")
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val activity = Json.decodeFromString<StravaActivity>(response.body!!.string())

            println(activity)
        }

        provideContent {
            //MyContent(context)
            MyContent()
        }
    }

    @Composable
    private fun MyContent() {
        // val context = LocalContext.current
        val prefs = currentState<Preferences>()
        val token = prefs[ReactBridge.currentToken]

        println("MyContent")
        println("Authorization Bearer $token")

//        var activity by remember(token) { mutableStateOf<StravActivity?>(null) }
//        LaunchedEffect(token) {
//            activity = token?.let { context.getActivity(it) }
//        }


//        val currentName = prefs[ReactBridge.currentName]
//        val currentTime = prefs[ReactBridge.currentTime]
//        val currentDistance = prefs[ReactBridge.currentDistance]
//        val currentElevation = prefs[ReactBridge.currentElevation]
//        val pictureUrl = prefs[ReactBridge.currentPicture]
//        var image by remember(pictureUrl) { mutableStateOf<Bitmap?>(null) }
//
//        LaunchedEffect(pictureUrl) {
//            image = pictureUrl?.let { context.getImage(it) }
//        }
//
//        if (image != null) {
//            Column(
//                modifier = GlanceModifier.fillMaxSize().padding(12.dp)
//                    .background(Color(android.graphics.Color.WHITE))
//                    .background(ImageProvider(image!!), ContentScale.Crop)
//                    .clickable(actionStartActivity<MainActivity>()),
//                verticalAlignment = Alignment.Bottom,
//                horizontalAlignment = Alignment.Start
//            ) {
//                Text(
//                    text = currentName.toString(), style = TextStyle(
//                        color = ColorProvider(Color.White),
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold
//                    ), maxLines = 1,
//                    modifier = GlanceModifier.padding(bottom = 2.dp)
//                )
//                Text(
//                    text = currentTime.toString(), style = TextStyle(
//                        color = ColorProvider(Color.White),
//                        fontSize = 14.sp
//                        ), maxLines = 1
//                )
//                Row() {
//                    Text(
//                        text = currentDistance.toString(), style = TextStyle(
//                            color = ColorProvider(Color.White), fontSize = 14.sp
//                        ), maxLines = 1
//                    )
//                    Text(
//                        text = currentElevation.toString(), style = TextStyle(
//                            color = ColorProvider(Color.White),
//                            fontSize = 14.sp
//                        ), maxLines = 1,
//                        modifier = GlanceModifier.padding(start = 6.dp)
//                    )
//                }
//            }
//        } else {
//            CircularProgressIndicator()
//        }
    }

    private suspend fun Context.getActivity(token: String): StravaActivity {
        val url = "https://api-dev.ikigai.fyi/rest/memories/current"
        val client = OkHttpClient()
        val request = Request.Builder()
            .addHeader("Authorization", "Bearer $token")
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        return Json.decodeFromString<StravaActivity>(response.body!!.string())
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