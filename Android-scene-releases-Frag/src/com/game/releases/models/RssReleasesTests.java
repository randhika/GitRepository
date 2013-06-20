package com.game.releases.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.game.releases.FeedsManager;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class RssReleasesTests extends TestCase{

	List<SyndEntry> lr;
	
	public RssReleasesTests(){
		lr = new ArrayList<SyndEntry>();
		for(int i = 0 ; i < 3 ; i++){
			lr.addAll(FeedsManager.getLastAllResourcesReleases(i));
		}
	}
	
	public void testIfLastRelListNotEmpty() throws FileNotFoundException{
		assertTrue(lr.size() > 0);
	}
	
	
	public void testIfLastRelXMLCreate() throws FileNotFoundException{
		FeedsManager.geraLastReleasesXML(lr);
		assertTrue(new File(FeedsManager.LOCAL_DIR, FeedsManager.XML_FILE_LAST_REL).exists());
	}
	
	public void testIfLastRelAlreadyExists() throws FileNotFoundException{
		Boolean contains = false;
		for(SyndEntry entry : lr){
			contains = FeedsManager.checkLastReleasesXML(entry.getTitle());
		}
		assertTrue(contains);
	}
}
