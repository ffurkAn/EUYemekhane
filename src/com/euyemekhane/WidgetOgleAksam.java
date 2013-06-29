package com.euyemekhane;

import java.util.Calendar;
import java.util.regex.Pattern;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;

public class WidgetOgleAksam extends AppWidgetProvider {
	
	private static final String ACTION_CLICK = "ACTION_CLICK";
	private Menu ogle, aksam;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		final Calendar c = Calendar.getInstance();
		MenuDAL dalMenu = new MenuDAL(context);
		ogle = dalMenu.GunlukOgleYemekGetir(c.get(Calendar.DAY_OF_MONTH), (c.get(Calendar.MONTH) + 1));
		aksam = dalMenu.GunlukAksamYemekGetir(c.get(Calendar.DAY_OF_MONTH), (c.get(Calendar.MONTH) + 1));
		ComponentName thisWidget = new ComponentName(context, WidgetOgleAksam.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		for (int widgetId : allWidgetIds) {

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			Pattern splitter = Pattern.compile("[\\/=]");
			String menu;
			String[] gunlukMenu;
			int size;

			if (ogle != null) {
				gunlukMenu = splitter.split(ogle.getMenu());
				size = gunlukMenu.length;
				menu = "";
				for (String s : gunlukMenu) {
					if (--size == 0) {
						menu += "\nToplam cal = ";
					}
					menu += "" + s.trim() + "\n";
				}
				
				if (ogle.getSevilmeyen() == 1) {
					remoteViews.setTextColor(R.id.widgetTextViewOgle, Color.RED);
				}
				remoteViews.setTextViewText(R.id.widgetTextViewOgleBold, ogle.getTarih() + " Öðle");
				remoteViews.setTextViewText(R.id.widgetTextViewOgle, menu);
			}
			
			if (aksam != null) {
				gunlukMenu = splitter.split(aksam.getMenu());
				size = gunlukMenu.length;
				menu = "";
				for (String s : gunlukMenu) {
					if (--size == 0) {
						menu += "\nToplam cal = ";
					}
					menu += "" + s.trim() + "\n";
				}
				
				if (aksam.getSevilmeyen() == 1) {
					remoteViews.setTextColor(R.id.widgetTextViewAksam, Color.RED);
				}
				remoteViews.setTextViewText(R.id.widgetTextViewAksamBold, aksam.getTarih() + " Akþam");
				remoteViews.setTextViewText(R.id.widgetTextViewAksam, menu);
			}

			Intent intent = new Intent(context, WidgetOgleAksam.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.widgetTextViewOgle, pendingIntent);
			remoteViews.setOnClickPendingIntent(R.id.widgetTextViewAksam, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
}
