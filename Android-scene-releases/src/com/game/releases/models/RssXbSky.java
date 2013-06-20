package com.game.releases.models;

public class RssXbSky extends Rss {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8042598933563904200L;

	public RssXbSky(){
		super("http://bt.xbox-sky.cc:88/rss.php",null);
	}
	
	@Override
	public String getRsslink(int position) {
		if(position == 0){
			return super.getRsslink(position);
		}else return null;
	}

}
