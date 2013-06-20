package com.game.releases;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.transacao.TransacaoTask;

import com.game.releases.adapters.ReleaseAdapter;
import com.game.releases.models.Rss;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class ReleasesActivity extends Activity implements Transacao{

	private ListView releases_x360_lv;
	private ListView releases_ps3_lv;
	private ListView releases_wii_lv;
	private List<SyndEntry> releases_x360 = null;
	private List<SyndEntry> releases_ps3 = null;
	private List<SyndEntry> releases_wii = null;
	private ListAdapter adapterX360; 
	private ListAdapter adapterPs3; 
	private ListAdapter adapterWii; 
	private TabHost tabhost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_releases);
		tabhost = (TabHost) findViewById(R.id.releasesTH);
		tabhost.setup();
		
		setTitle(getIntent().getStringExtra("title"));
		
		TabSpec tabX360 = tabhost.newTabSpec("Xbox 360");
		tabX360.setIndicator("XBOX360", getResources().getDrawable(R.drawable.ic_launcher));
		tabX360.setContent(R.id.tabx360);
		
		TabSpec tabPs3 = tabhost.newTabSpec("Ps3");
		tabPs3.setIndicator("PS3");
		tabPs3.setContent(R.id.tabps3);
		
		TabSpec tabWii = tabhost.newTabSpec("Wii");
		tabWii.setIndicator("Wii");
		tabWii.setContent(R.id.tabwii);
		
		tabhost.addTab(tabX360);
		tabhost.addTab(tabPs3);
		tabhost.addTab(tabWii);
		
//		adapterX360 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//		adapterPs3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//		adapterWii = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		TransacaoTask task = new TransacaoTask(this, this, R.string.buscando);
		task.execute();
		setupActionBar();
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public void atualizaView() {
//		if(releases_x360 != null){
//			for (SyndEntry e : releases_x360) {
//				adapterX360.add(e.getTitle());
//			}
//		}
//		if(releases_ps3 != null){
//			for (SyndEntry e : releases_ps3) {
//				adapterPs3.add(e.getTitle());
//			}
//		}
//		if(releases_ps3 != null){
//			for (SyndEntry e : releases_wii) {
//				adapterWii.add(e.getTitle());
//			}
//		}
//		adapterX360.notifyDataSetChanged();
//		adapterPs3.notifyDataSetChanged();
//		adapterWii.notifyDataSetChanged();
		adapterX360 = new ReleaseAdapter(releases_x360, this);
		adapterPs3 = new ReleaseAdapter(releases_ps3, this);
		adapterWii = new ReleaseAdapter(releases_wii, this);
		releases_x360_lv = (ListView) findViewById(R.id.feeds_x360_list);
		releases_ps3_lv = (ListView) findViewById(R.id.feeds_ps3_list);
		releases_wii_lv = (ListView) findViewById(R.id.feeds_wii_list);
		releases_x360_lv.setAdapter(adapterX360);
		releases_ps3_lv.setAdapter(adapterPs3);
		releases_wii_lv.setAdapter(adapterWii);
	}

	@Override
	public void executar() throws Exception {
		Rss link = (Rss) getIntent().getSerializableExtra("rssurl");
		releases_x360 = FeedsManager.getRss(link.getRsslink(0));
		releases_ps3 = FeedsManager.getRss(link.getRsslink(1));
		releases_wii = FeedsManager.getRss(link.getRsslink(2));
		Log.i("ENTRIES",""+ releases_x360.size());		
	}

}
