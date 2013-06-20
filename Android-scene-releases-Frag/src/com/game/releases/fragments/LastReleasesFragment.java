package com.game.releases.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.transacao.TransacaoTaskNoProgress;

import com.actionbarsherlock.app.SherlockFragment;
import com.game.releases.FeedsManager;
import com.game.releases.R;
import com.game.releases.adapters.ReleaseAdapter;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class LastReleasesFragment extends SherlockFragment implements Transacao {

	private ListView releases_lv;
	private List<SyndEntry> releases = null;
	private ListAdapter adapter;
	private ProgressBar progresso;

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
		getActivity().setTitle(
				getActivity().getIntent().getStringExtra("title"));
		progresso = (ProgressBar) getView().findViewById(R.id.progressBarRel);
		releases_lv = (ListView) getView().findViewById(R.id.lv_releases);
		if(savedInstanceState != null){
			releases = (List<SyndEntry>) savedInstanceState.getSerializable("releases");
			atualizaView();
		}else{
			TransacaoTaskNoProgress task = new TransacaoTaskNoProgress(getActivity().getApplicationContext(), this, progresso);
//			TransacaoTask task = new TransacaoTask(getActivity().getApplicationContext(), this, R.string.buscando);
			task.execute();
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void atualizaView() {
			adapter = new ReleaseAdapter(releases, getActivity(), getArguments().getInt("icon"));
			releases_lv.setAdapter(adapter);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("releases", (ArrayList<SyndEntry>)releases);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void executar() throws Exception {
		releases = new ArrayList<SyndEntry>();
		for(int cat = 0 ; cat < 4 ; cat++){
			releases.addAll(FeedsManager.getLastAllResourcesReleases(cat));
		}
	}

}
