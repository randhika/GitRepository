package com.game.releases.models;

import java.io.Serializable;

import android.util.Log;

public class Rss implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2354399454699333861L;
	private String rsslink;
	private String[] categories;

	/**
	 * 
	 * @param rsslink
	 * @param categories = pos = 0 (xbox360), 1(ps3), 2(wii), 3(pc)
	 */
	public Rss(String rsslink, String[] categories) {
		this.rsslink = rsslink;
		this.categories = categories;
	}

	public String[] getCategories() {
		return categories;
	}

	public String getRsslink(int position) {
		if(categories != null){
			return rsslink.replace("{cat}", categories[position]);
		}
		Log.i("RSS LINK", rsslink);
		return rsslink;
	}
}
