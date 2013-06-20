package com.game.releases.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.transacao.TransacaoTaskNoProgress;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.game.releases.FeedsManager;
import com.game.releases.R;
import com.game.releases.SettingsActivity;
import com.game.releases.adapters.ReleaseAdapter;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class ReleasesFragment extends SherlockFragment implements Transacao {

	private ListView releases_lv;
	private List<SyndEntry> releases = null;
	private ListAdapter adapter;
	private ProgressBar progresso;
	private int icon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.layout_releases, null);
		return view;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		progresso = (ProgressBar) getView().findViewById(R.id.progressBarRel);
		releases_lv = (ListView) getView().findViewById(R.id.lv_releases);
		if (savedInstanceState != null) {
			releases = (List<SyndEntry>) savedInstanceState
					.getSerializable("releases");
			atualizaView();
		} else {
			TransacaoTaskNoProgress task = new TransacaoTaskNoProgress(
					getActivity().getApplicationContext(), this, progresso);
			// TransacaoTask task = new
			// TransacaoTask(getActivity().getApplicationContext(), this,
			// R.string.buscando);
			task.execute();
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void atualizaView() {
		if (releases != null) {
			Bundle args = getArguments();
			if (args != null) {
				icon = getArguments().getInt("icon");
				if (getActivity() != null) {
					getActivity().setTitle(args.getString("title"));
				}
			}
			adapter = new ReleaseAdapter(releases, getSherlockActivity(), icon);
			releases_lv.setAdapter(adapter);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("releases", (ArrayList<SyndEntry>) releases);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void executar() throws Exception {
		int cat = 0;
		if (getArguments() != null) {
			cat = getArguments().getInt("category", 0);
			Log.i(FeedsManager.LOG_TAG, "CAT " + cat);
		}
		releases = FeedsManager.getReleases(cat, 10);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search_menu, menu);
		MenuItem item = menu.findItem(R.id.search);
		SearchView sv = new SearchView(getSherlockActivity());
		sv.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				if (releases != null) {
					List<SyndEntry> auxList = new ArrayList<SyndEntry>();
					for (SyndEntry release : releases) {
						if (release.getTitle().toUpperCase(Locale.ENGLISH).contains(query.toUpperCase(Locale.ENGLISH))) {
							auxList.add(release);
						}
					}
					releases_lv.setAdapter(new ReleaseAdapter(auxList,
							getSherlockActivity(), icon));
					InputMethodManager input = (InputMethodManager) getSherlockActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					if(input != null){
						input.hideSoftInputFromWindow(releases_lv.getWindowToken(), 0);
					}
				}
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				if (newText.equals("")) {
					atualizaView();
				}
				return false;
			}
		});
		item.setActionView(sv);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.settings:
			intent = new Intent(getSherlockActivity().getApplicationContext(), SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
