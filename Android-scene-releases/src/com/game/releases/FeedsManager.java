package com.game.releases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.util.Log;
import br.livroandroid.utils.MediaFileUtils;

import com.game.releases.models.LastReleasesXML;
import com.game.releases.models.RssAbgx;
import com.game.releases.models.RssBj;
import com.game.releases.models.RssXspeeds;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class FeedsManager {
	public static final String LOG_TAG = "FeedsManager";
	public static final String XML_FILE_LAST_REL = "lastReleases.xml";
	public static final String APP_NAME = "XceneReleases";
	public static final String LOCAL_DIR = "c://JAVA/testFiles";
	public static final String DEVICE_DIR = MediaFileUtils.createAppDownDir(APP_NAME).getPath();
	private static LastReleasesXML lastRelXML;
	
	public static void loadXML(){
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		xstream.alias("lastReleases", LastReleasesXML.class);
		xstream.alias("title", String.class);
//		File xml = new File(LOCAL_DIR,XML_FILE_LAST_REL);
		File xml = new File(DEVICE_DIR,XML_FILE_LAST_REL);
		lastRelXML =(LastReleasesXML) xstream.fromXML(xml);
	}
	
	@SuppressWarnings("unchecked")
	public static List<SyndEntry> getRss(String url) {
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = null;
		try {
			feed = input.build(new XmlReader(new URL(url)));
		} catch (IllegalArgumentException e) {
			Log.e("RSS", "ERRO", e);
		} catch (MalformedURLException e) {
			Log.e("RSS", "ERRO", e);
		} catch (FeedException e) {
			Log.e("RSS", "ERRO", e);
		} catch (IOException e) {
			Log.e("RSS", "ERRO", e);
		}
		return feed == null ? null : feed.getEntries();
	}

	public static List<SyndEntry> getLastAllResourcesReleases(int console) {
		String[] urls = { new RssAbgx().getRsslink(console),
				new RssBj().getRsslink(console),
				new RssXspeeds().getRsslink(console) };
		List<SyndEntry> todayReleases = new ArrayList<SyndEntry>();
		for (String links : urls) {
			SyndEntry rel = getTodayRelease(links);
			if (rel != null) {
				todayReleases.add(rel);
			}
		}

		return todayReleases;
	}
	
	public static Set<String> buildLastReleasesSet(List<SyndEntry> releases){
		Set<String> releaseNames = new HashSet<String>();
		for(SyndEntry rel : releases){
			releaseNames.add(rel.getTitle());
		}
		return releaseNames;
	}

	// No Bj buscar no element description por categoria : e Adicionado em
	
	public static String getUri(String uri){
		try {
			return new URI(uri).getHost();
		} catch (URISyntaxException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
			return null;
		}
	}
	
	private static SyndEntry getTodayRelease(String rss) {
		SyndEntry entry = FeedsManager.getRss(rss).get(0);
		return entry;
	}
	
	public static String getPlatform(String description){
		String retorno = "";
		return retorno;
	}
	
	public static String getReleaseDate(String description){
		String retorno = "";
		return retorno;
	}
	
	public static String getImageUrl(String description){
		String retorno = "";
		return retorno;
	}
	
	public static void geraLastReleasesXML(List<SyndEntry> lastReleases){
		List<String> xmlList = new ArrayList<String>();
		for(SyndEntry entry : lastReleases){
			xmlList.add(entry.getTitle());
		}
		LastReleasesXML xmlOBJ = new LastReleasesXML(xmlList);
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		xstream.alias("lastReleases", LastReleasesXML.class);
		xstream.alias("title", String.class);
		File xml = new File(DEVICE_DIR,XML_FILE_LAST_REL);
//		File xml = new File(LOCAL_DIR,XML_FILE_LAST_REL);
		if(xml.exists()){
			xml.delete();
		}
		try {
			xstream.toXML(xmlOBJ, new FileOutputStream(xml));
		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG, "Erro ao Gerar XML", e);
		}
	}
	public static Boolean checkLastReleasesXML(String title){
		if(lastRelXML != null){
			if(lastRelXML.getReleases().contains(title)){
				return false;
			}
		}
		return true;
	}
	
}
