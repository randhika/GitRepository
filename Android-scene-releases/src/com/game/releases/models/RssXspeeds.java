package com.game.releases.models;

public class RssXspeeds extends Rss {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -561703507445104988L;

	public RssXspeeds(){
//		super("http://feeds.ign.com/ign/xbox-360-all",new String[]{"x360","abcp","abgw"});
		super("http://xspeeds.eu/rss.php?secret_key=a95956b52995050036e611be2e454f2b&feedtype=details&timezone=-3&showrows=20&categories={cat}",
				new String[]{"8","31","7"});
	}

}
