package com.game.releases;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.game.releases.models.ReleaseParcel;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class RssService extends Service {

	private Timer timer;
	private NotificationManager nManager;
	private int notificationsNumber = 0;
	public static final String NEW_RELEASE_ACTION = "New Release";
	public static final int NOTIFICATION_ID = 1;
	private List<SyndEntry> lastReleases = new ArrayList<SyndEntry>();
	private Boolean mustNotify = false;
	private SharedPreferences myPrefs;
	private String lastReleaseTitle;

	@Override
	public void onCreate() {
		myPrefs = getSharedPreferences(SettingsActivity.PREFERENCES,
				MODE_PRIVATE);
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(ServiceUtils.LOG_TAG, "iniciando Service");
		criaTask(intent);
		return super.onStartCommand(intent, flags, startId);
	}

	private void criaTask(Intent intent) {
		int period = myPrefs.getInt(SettingsActivity.PERIOD_PREF, 1);
		Boolean notifyMe = myPrefs.getBoolean(SettingsActivity.NOTIFY_PREF,
				true);
		nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		if (notifyMe) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					checkAndCreateXmlFile();
					verifyAndNotifyLastReleases();
				}
			}, 0, 60000 * period);
		}
	}

	private void checkAndCreateXmlFile() {
		for (int console = 0; console < 3; console++) {
			lastReleases.addAll(FeedsManager
					.getLastAllResourcesReleases(console));
		}
		if (!new File(FeedsManager.DEVICE_DIR, FeedsManager.XML_FILE_LAST_REL)
				.exists()) {
			FeedsManager.geraLastReleasesXML(lastReleases);
		}
	}

	private void verifyAndNotifyLastReleases() {
		if (lastReleases != null && lastReleases.size() > 0) {
			checkIfLastRelease();
			if (mustNotify) {
				FeedsManager.geraLastReleasesXML(lastReleases);
				Notification notification = buildNotification("Today Releases",
						R.drawable.icon_scene_rel, lastReleaseTitle, lastReleaseTitle,
						""+ ++notificationsNumber, ++notificationsNumber);
				nManager.notify(NOTIFICATION_ID, notification);
			} 
		}
	}

	private void checkIfLastRelease() {
		for (SyndEntry release : lastReleases) {
			FeedsManager.loadXML();
			if (FeedsManager.checkLastReleasesXML(release.getTitle())) {
				Log.i(ServiceUtils.LOG_TAG,
						"New Release = " + release.getTitle());
				nManager.cancel(NOTIFICATION_ID);
				Log.i("SERVICE", "Link = " + release.getLink());
				Log.i("SERVICE",
						"Host = " + FeedsManager.getUri(release.getUri()));
				lastReleaseTitle = release.getTitle();
				mustNotify = true;
				return;
			} else {
				Log.i("SERVICE", "Existe em cache, nao notifica!!!");
				mustNotify = false;
			}
		}

	}

	private Notification buildNotification(String title, int iconId,
			String ticket, String contentText, String contentInfo, int count) {
		Intent i = new Intent(this, NotificationActivity.class);
		ArrayList<ReleaseParcel> relParcels = new ArrayList<ReleaseParcel>();
		for (SyndEntry rel : lastReleases) {
			relParcels.add(builParcelables(rel));
		}
		i.putExtra("relParcels", relParcels);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 02, i,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// RemoteViews rv = new
		// RemoteViews(getApplicationContext().getPackageName(),
		// R.layout.activity_notification);
		// rv.setTextViewText(R.id.tv_console, title);
		// rv.setTextViewText(R.id.tv_release_name, contentInfo);
		// rv.setImageViewResource(R.id.iv_consoleIcon, R.drawable.x360_icon);
		Notification notification = new NotificationCompat.Builder(
				RssService.this).setSmallIcon(iconId).setContentTitle(title)
				.setContentInfo(contentInfo).setVibrate(new long[] { 0, 1000 })
				.setContentText(contentText).setContentIntent(pendingIntent)
				.setTicker(ticket).setNumber(count).build();
		// .setContent(rv)
		return notification;
	}

	private ReleaseParcel builParcelables(SyndEntry release) {
		ReleaseParcel relParcel = new ReleaseParcel();
		relParcel.setTitle(release.getTitle());
		relParcel.setUri(FeedsManager.getUri(release.getUri()));
		return relParcel;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		Log.i(ServiceUtils.LOG_TAG, "Destruindo Service");
		timer.cancel();
		super.onDestroy();
	}

}
