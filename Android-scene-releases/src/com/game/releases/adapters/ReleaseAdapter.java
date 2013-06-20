package com.game.releases.adapters;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.releases.FeedsManager;
import com.game.releases.R;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class ReleaseAdapter extends BaseAdapter{
	private List<SyndEntry> releaseList;
	private Activity context;

	public ReleaseAdapter(List<SyndEntry> releaseList, Activity context) {
		this.releaseList = releaseList;
		this.context = context;
	}

	@Override
	public int getCount() {
		if(releaseList != null){
			return releaseList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if(releaseList != null){
			return releaseList.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		if(releaseList != null){
			return releaseList.indexOf(releaseList.get(arg0));
		}
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View linha = arg1;
		if(linha == null){
			LayoutInflater inflater = context.getLayoutInflater();
			linha = inflater.inflate(R.layout.releases_item, null);
			ViewHolder holder= new ViewHolder();
			holder.title = (TextView) linha.getRootView().findViewById(R.id.tv_title);
			holder.releaseDate = (TextView) linha.getRootView().findViewById(R.id.tv_releaseDate);
			holder.uri = (TextView) linha.getRootView().findViewById(R.id.tv_uri);
			linha.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) linha.getTag();
		SyndEntry release = releaseList.get(arg0);
		holder.title.setText(release.getTitle());
		holder.releaseDate.setText(new Date().toString());
		holder.uri.setText(FeedsManager.getUri(release.getUri()));
		return linha;
	}

	static class ViewHolder{
		TextView uri;
		TextView title;
		TextView releaseDate;
		ImageButton link;
	}

}
