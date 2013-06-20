package com.game.releases.adapters;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.game.releases.FeedsManager;
import com.game.releases.R;
import com.game.releases.models.ReleaseParcel;

public class LastReleaseAdapter extends BaseAdapter{
	private List<ReleaseParcel> releaseList;
	private Activity context;

	public LastReleaseAdapter(List<ReleaseParcel> releaseList, Activity context) {
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
			linha = inflater.inflate(R.layout.last_releases_item, null);
			ViewHolder holder= new ViewHolder();
			holder.title = (TextView) linha.getRootView().findViewById(R.id.tv_title);
			holder.uri = (TextView) linha.getRootView().findViewById(R.id.tv_uri);
			holder.category = (TextView) linha.getRootView().findViewById(R.id.tv_category);
			holder.description = (WebView) linha.getRootView().findViewById(R.id.wv_description);
			linha.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) linha.getTag();
		ReleaseParcel release = releaseList.get(arg0);
		holder.title.setText(release.getTitle());
		holder.uri.setText(release.getUri());
		holder.category.setText(FeedsManager.getCategory(release.getDescription()));
		holder.description.loadData(release.getDescription(), "text/html", "UTF-8");
		Log.i(FeedsManager.LOG_TAG, release.getUri());
		return linha;
	}

	static class ViewHolder{
		TextView uri;
		TextView title;
		TextView category;
		WebView description;
	}

}
