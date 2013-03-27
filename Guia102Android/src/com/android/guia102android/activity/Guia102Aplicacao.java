package com.android.guia102android.activity;

import br.livroandroid.utils.DownloadImagemUtil;
import android.app.Application;

public class Guia102Aplicacao extends Application{
    public static final String TAG = "GUIA102";
    private Guia102Aplicacao instance = null;
    private DownloadImagemUtil downloader;
    
    public Guia102Aplicacao getInstance() {
        return instance;
    }
    public void setInstance(Guia102Aplicacao instance) {
        this.instance = instance;
    }
    public DownloadImagemUtil getDownloader() {
        return downloader;
    }
    public void setDownloader(DownloadImagemUtil downloader) {
        this.downloader = downloader;
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
