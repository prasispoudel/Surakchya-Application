package com.example.surakchya;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RemoteViews;

public class SurakchyaAppWidgetProvider extends android.appwidget.AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
       // method for updating the widget and has  initial initialization information
        for (int appWidgetId: appWidgetIds){
           // intent for opening main activity
         //  Intent intent = new Intent(context,MainActivity.class);
            Intent intent = new Intent(context, TwoWheelerMode.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

            // Object of remote views
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.home_layout);
            views.setOnClickPendingIntent(R.id.textView,pendingIntent);
            views.setOnClickPendingIntent(R.id.widget_em_button,pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }
}
