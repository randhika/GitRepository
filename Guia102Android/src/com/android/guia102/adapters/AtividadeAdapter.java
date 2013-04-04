package com.android.guia102.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.suaempresa.modelos.Atividade;

import com.android.guia102android.activity.Guia102Aplicacao;
import com.android.guia102android.activity.R;

public class AtividadeAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Atividade> atividades;

	public AtividadeAdapter(Activity context, List<Atividade> atividades) {
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.atividades = atividades;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return atividades != null ? atividades.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return atividades != null ? atividades.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		if (view == null) {
			// nao existe a view no cache para esta linha, entao cria um novo
			holder = new ViewHolder();
			// busca o layout para cada carro com foto
			int layout = R.layout.atividad_item;
			view = inflater.inflate(layout, null);
			view.setTag(holder);
			holder.tNome = (TextView) view.findViewById(R.id.tnome);
			holder.imgFoto = (ImageView) view.findViewById(R.id.img);
		} else {
			// ja existe no cache
			holder = (ViewHolder) view.getTag();
		}
		Atividade a = atividades.get(position);
		// Agora que temos a view podemos atualizar os valores
		holder.tNome.setText(a.getNome());
		return view;
	}

	// Design pattern ViewHolder para Android
	static class ViewHolder {
		TextView tNome;
		ImageView imgFoto;
	}
}
