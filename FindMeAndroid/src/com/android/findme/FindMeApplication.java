package com.android.findme;

import br.livroandroid.utils.DownloadImagemUtil;
import android.app.Application;

public class FindMeApplication extends Application {

	private FindMeApplication instance;
	private DownloadImagemUtil downloader;

	public DownloadImagemUtil getDownloader() {
		return downloader;
	}
	
	public void setDownloader(DownloadImagemUtil downloader) {
		this.downloader = downloader;
	}
	
	public FindMeApplication getInstance() {
		return instance;
	}
	public void setInstance(FindMeApplication instance) {
		this.instance = instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		downloader = new DownloadImagemUtil(this);
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		downloader = null;
	}
	
}
