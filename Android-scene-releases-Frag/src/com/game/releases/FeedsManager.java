package com.game.releases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import com.game.releases.models.Rss;
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
	public static final String DEVICE_DIR = MediaFileUtils.createAppDownDir(
			APP_NAME).getPath();
	private static LastReleasesXML lastRelXML;

	public static void loadXML() {
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		xstream.alias("lastReleases", LastReleasesXML.class);
		xstream.alias("title", String.class);
		// File xml = new File(LOCAL_DIR,XML_FILE_LAST_REL);
		File xml = new File(DEVICE_DIR, XML_FILE_LAST_REL);
		lastRelXML = (LastReleasesXML) xstream.fromXML(xml);
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

	public static List<SyndEntry> getReleases(int category, int qtdade_results) {
		List<SyndEntry> releases = new ArrayList<SyndEntry>();
		releases.addAll(getRssResults(new RssBj(), category, qtdade_results));
		releases.addAll(getRssResults(new RssXspeeds(), category,
				qtdade_results));
		if (category < 3) {
			releases.addAll(getRssResults(new RssAbgx(), category,
					qtdade_results));
		}
		return releases;
	}

	private static List<SyndEntry> getRssResults(Rss rss, int category,
			int qt_results) {
		List<SyndEntry> listaux = getRss(rss.getRsslink(category));
		if (listaux != null && listaux.size() > 0) {
			return listaux.subList(0, qt_results);
		}
		return null;
	}

	public static List<SyndEntry> getLastAllResourcesReleases(int console) {
		String[] urls = new String[3];
		urls[0] = new RssBj().getRsslink(console);
		urls[1] = new RssXspeeds().getRsslink(console);
		if(console < 3){
			urls [2]= new RssAbgx().getRsslink(console);
		}
		List<SyndEntry> todayReleases = new ArrayList<SyndEntry>();
		for (String links : urls) {
			if(links != null && !links.equals("")){
				todayReleases = getTodayRelease(links);
			}
		}
		return todayReleases;
	}

	// No Bj buscar no element description por categoria : e Adicionado em

	public static String getUri(String uri) {
		try {
			return new URI(uri).getHost();
		} catch (URISyntaxException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
			return null;
		}
	}

	private static List<SyndEntry> getTodayRelease(String rss) {
		Log.i(FeedsManager.LOG_TAG, rss);
		List<SyndEntry> list = FeedsManager.getRss(rss);
		if (list != null && list.size() > 3)
			return list.subList(0, 3);
		return null;
	}

	public static String getCategory(String description) {
		String retorno = "recupera category";
		Log.i(LOG_TAG, "Description>>" + description);
		return retorno;
	}

	public static String getReleaseDate(String description) {
		Log.i(LOG_TAG, "ORIGINAL>>" + description);
		String aux = description
				.substring(0, description
						.indexOf("<br /><a href=\"http://www.amazon.co.uk/"));
		aux = aux.substring(description.lastIndexOf("Release Date:"),
				aux.indexOf("</em>"));
		Log.i(LOG_TAG, "TREATED>>" + aux);
		String retorno = aux;
		return retorno;
	}

	public static String getImageUrl(String description) {
		String retorno = "";
		return retorno;
	}

	// DESCRIPTION CONTENT
	// <div style="float:left;">
	// <a class="url"
	// href="http://www.amazon.co.uk/The-Last-Of-Us-PS3/dp/B00844PC9S/ref=pd_zg_rss_nr_vg_h__videogames_1"><img
	// src="http://ecx.images-amazon.com/images/I/61JEp-wF3zL._SL160_.jpg"
	// alt="The Last" border="0" hspace="0" vspace="0" /></a>
	// </div>
	// <span class="riRssTitle"><a
	// href="http://www.amazon.co.uk/The-Last-Of-Us-PS3/dp/B00844PC9S/ref=pd_zg_rss_nr_vg_h__videogames_1">The
	// Last Of Us (PS3)</a></span>
	// <br />
	// <span class="riRssContributor">by Sony</span>
	// <br />
	// <b>Platform:</b>  <img
	// src="http://g-ecx.images-amazon.com/images/G/02/videogames/icons/browse-icon-ps3._V192561334_.gif"
	// width="20" align="absmiddle" style="padding-bottom:4px" height="20"
	// border="0" /> PlayStation 3<br />
	// <span class="riRssReleaseDate">
	// <em class="notPublishedYet">Release Date: 14 Jun 2013</em>
	// <br />
	// </span>
	// <br />
	// <a
	// href="http://www.amazon.co.uk/The-Last-Of-Us-PS3/dp/B00844PC9S/ref=pd_zg_rss_nr_vg_h__videogames_1">Buy
	// new:</a>
	// <font color="#990000"><b>£38.00</b></font> <br /><br />(Visit the <a
	// href="http://www.amazon.co.uk/gp/new-releases/videogames/ref=pd_zg_rss_nr_vg_h__videogames_1">Hot
	// Future Releases in PC &amp; Video Games</a> list for authoritative
	// information on this product's current rank.)
	//

	public static String editDescription(String description) {
		Log.i(LOG_TAG, "ORIGINAL>>" + description);
		String aux = description
				.substring(0, description
						.indexOf("<br /><a href=\"http://www.amazon.co.uk/"));
		Log.i(LOG_TAG, "TREATED>>" + aux);
		String retorno = aux;
		return retorno;
	}

	public static void geraLastReleasesXML(List<SyndEntry> lastReleases) {
		List<String> xmlList = new ArrayList<String>();
		for (SyndEntry entry : lastReleases) {
			xmlList.add(entry.getTitle());
		}
		LastReleasesXML xmlOBJ = new LastReleasesXML(xmlList);
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		xstream.alias("lastReleases", LastReleasesXML.class);
		xstream.alias("title", String.class);
		File xml = new File(DEVICE_DIR, XML_FILE_LAST_REL);
		// File xml = new File(LOCAL_DIR,XML_FILE_LAST_REL);
		if (xml.exists()) {
			xml.delete();
		}
		try {
			xstream.toXML(xmlOBJ, new FileOutputStream(xml));
		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG, "Erro ao Gerar XML", e);
		}
	}

	public static Boolean checkLastReleasesXML(String title) {
		if (lastRelXML != null) {
			if (lastRelXML.getReleases().contains(title)) {
				return false;
			}
		}
		return true;
	}

}
