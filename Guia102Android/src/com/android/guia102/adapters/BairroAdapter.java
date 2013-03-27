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
import br.com.suaempresa.modelos.Bairro;

import com.android.guia102android.activity.Guia102Aplicacao;
import com.android.guia102android.activity.R;

public class BairroAdapter extends BaseAdapter {
   
    private LayoutInflater inflater;
    private List<Bairro> bairros;
    
    public BairroAdapter(Activity context, List<Bairro> bairros) {
	this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	this.bairros = bairros;
    }

    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return bairros != null ? bairros.size() : 0;
    }

    @Override
    public Object getItem(int position) {
	return bairros  != null ? bairros.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
	ViewHolder holder = null;
	if(view == null){
//	    nao existe a view no cache para esta linha, entao cria um novo
	    holder = new ViewHolder();
//	    busca o layout para cada carro com foto
	    int layout  = R.layout.atividad_item;
	    view = inflater.inflate(layout, null);	 
	    view.setTag(holder);
	    holder.tNome = (TextView) view.findViewById(R.id.tnome);
	    holder.imgFoto = (ImageView) view.findViewById(R.id.img);
	}else {
//	    ja existe no cache
	    holder = (ViewHolder)view.getTag();
	}
	
	Log.i(Guia102Aplicacao.TAG, "progress");
	Bairro b = bairros.get(position);
//	Agora que temos a view podemos atualizar os valores
	holder.tNome.setText(b.getNome());
	return view;
    }

//Design pattern ViewHolder para Android 
   static class ViewHolder {
       TextView tNome;
       ImageView imgFoto;
    }
}


