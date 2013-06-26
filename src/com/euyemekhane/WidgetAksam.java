package com.euyemekhane;

import java.util.Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetAksam extends AppWidgetProvider {
	
	private static final String ACTION_CLICK = "ACTION_CLICK";
	private Menu aksam;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		final Calendar c = Calendar.getInstance();
		MenuDAL dalMenu = new MenuDAL(context);
		aksam = dalMenu.GunlukAksamYemekGetir(c.get(Calendar.DAY_OF_MONTH), (c.get(Calendar.MONTH) + 1));
		ComponentName thisWidget = new ComponentName(context, WidgetAksam.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		for (int widgetId : allWidgetIds) {

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout_small);

			remoteViews.setTextViewText(R.id.smallWidgetTextViewBold, aksam.getTarih() + "Akþam");
			remoteViews.setTextViewText(R.id.smallWidgetTextView, aksam.getMenu());

			Intent intent = new Intent(context, WidgetAksam.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.smallWidgetTextView, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
}