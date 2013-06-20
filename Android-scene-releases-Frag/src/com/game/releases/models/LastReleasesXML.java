package com.game.releases.models;

import java.util.List;

public class LastReleasesXML {
	
	private List<String> releases;

	public LastReleasesXML() {}
		
	
	public LastReleasesXML(List<String> releases) {
		super();
		this.releases = releases;
	}


	public List<String> getReleases() {
		return releases;
	}

	public void setReleases(List<String> releases) {
		this.releases = releases;
	}
	
	

	
}
