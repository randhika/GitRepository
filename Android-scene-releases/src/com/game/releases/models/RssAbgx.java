package com.game.releases.models;

public class RssAbgx extends Rss {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6775283419802701949L;

	public RssAbgx(){
		super("http://www.abgx.net/rss/{cat}/newrls.rss",new String[]{"x360","abcp","abgw"});
	}

}
