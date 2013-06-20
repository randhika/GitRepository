package com.game.releases.models;

public class RssBj extends Rss {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6744052165982695600L;

	public RssBj(){
		super("http://www.bj2.me/rss.php?cat={cat}", new String[]{"55","66","56"});
	}

}
