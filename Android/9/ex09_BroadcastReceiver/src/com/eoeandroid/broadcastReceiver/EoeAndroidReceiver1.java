package com.eoeandroid.broadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EoeAndroidReceiver1 extends BroadcastReceiver {
	Context context;
	public static int NOTIFICATION_ID = 21321;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		showNotification();
	}

	private void showNotification() {

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon,
				"��EoeAndroidReceiver1��", System.currentTimeMillis());

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(context, ActivityMain.class), 0);
		notification.setLatestEventInfo(context, "��EoeAndroidReceiver1��", null,
				contentIntent);
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
}
