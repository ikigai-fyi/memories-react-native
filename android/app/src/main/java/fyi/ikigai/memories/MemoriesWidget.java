package fyi.ikigai.memories;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Implementation of App Widget functionality.
 */
public class MemoriesWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences("DATA", Context.MODE_PRIVATE);
            String stringJsonData = sharedPref.getString("json", "{}");
            JSONObject widgetData = new JSONObject(stringJsonData);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.memories_widget);



            Log.e("MEMORIES", widgetData.getJSONObject("value").getString("name"));

            views.setTextViewText(R.id.name, widgetData.getJSONObject("value").getString("name"));
            views.setTextViewText(R.id.date, widgetData.getJSONObject("value").getString("start_datetime"));
            views.setTextViewText(R.id.distance, widgetData.getJSONObject("value").getString("distance_in_meters"));
            views.setTextViewText(R.id.height, widgetData.getJSONObject("value").getString("total_elevation_gain_in_meters"));
            URL url = new URL(widgetData.getJSONObject("value").getString("picture_url"));



            Thread thread = new Thread(() -> {
                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                views.setImageViewBitmap(R.id.image, bmp);

                appWidgetManager.updateAppWidget(appWidgetId, views);
            });

            thread.start();

            appWidgetManager.updateAppWidget(appWidgetId, views);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}