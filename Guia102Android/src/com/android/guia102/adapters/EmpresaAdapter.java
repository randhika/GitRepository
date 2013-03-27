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
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.suaempresa.modelos.Empresa;
import br.livroandroid.utils.DownloadImagemUtil;

import com.android.guia102android.activity.Guia102Aplicacao;
import com.android.guia102android.activity.Guia102Service;
import com.android.guia102android.activity.R;

public class EmpresaAdapter extends BaseAdapter {
   
    private LayoutInflater inflater;
    private List<Empresa> empresas;
    private Activity context;
    private DownloadImagemUtil downloader;
    
    public EmpresaAdapter(Activity context, List<Empresa> empresas) {
	this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	this.empresas = empresas;
	this.context = context;
	Guia102Aplicacao application = (Guia102Aplicacao)context.getApplication();
	this.downloader = application.getDownloader();
    }

    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return empresas != null ? empresas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
	return empresas  != null ? empresas.get(position) : null;
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
	    int layout  = R.layout.empresa_item;
	    view = inflater.inflate(layout, null);	 
	    view.setTag(holder);
	    holder.tNome = (TextView) view.findViewById(R.id.tnome);
	    holder.tTel = (TextView) view.findViewById(R.id.tTel);
	    holder.tEndereco = (TextView) view.findViewById(R.id.tEndereco);
	    holder.imgFoto = (ImageView) view.findViewById(R.id.img);
	    holder.progress = (ProgressBar) view.findViewById(R.id.progress);
	}else {
//	    ja existe no cache
	    holder = (ViewHolder)view.getTag();
	}
	
	//VERIFICAR PQ O IMAGEFOTO ESTA NULL
	holder.imgFoto.setImageBitmap(null);
	Log.i(Guia102Aplicacao.TAG, "progress");
	Empresa e = empresas.get(position);
//	Agora que temos a view podemos atualizar os valores
	holder.tNome.setText(e.getNome_fantasia());
	if(holder.tTel != null){
	    holder.tTel.setText(e.getTelefone1());
	    holder.tEndereco.setText(e.getRua() + "," + e.getNumero());
	}
	String urlLogo = Guia102Service.URL.replace("{tipo}","recursos/logo/" + e.getLogomarca());
	downloader.download(context, urlLogo, holder.imgFoto, holder.progress);
	return view;
    }

//Design pattern ViewHolder para Android 
   static class ViewHolder {
       TextView tNome;
       TextView tTel;
       TextView tEndereco;
       ImageView imgFoto;
       ProgressBar progress;
    }
}


