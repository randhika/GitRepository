package com.android.guia102android.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.SAXException;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import br.com.guia102.modelos.Atividade;
import br.com.guia102.modelos.Bairro;
import br.com.guia102.modelos.Empresa;
import br.livroandroid.utils.AndroidUtils;
import br.livroandroid.utils.FtpService;
import br.livroandroid.utils.HttpHelper;
import br.livroandroid.utils.IOUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Guia102Service extends Guia102Activity {
	public static final String URL = "http://guia102nocelular.com.br/guia102Service/{tipo}";
	private static final String ENCODING = "ISO-8859-1";
	private static final boolean LOG_ON = false;

	public static List<Atividade> getAtividades(Context context, String service)
			throws IOException, SAXException {
		XStream x = new XStream(new DomDriver(ENCODING));
		List<Atividade> ativis = new ArrayList<Atividade>();
		if (!AndroidUtils.isNetworkAvailable(context)) {
			System.out.println("VAI LER DO XML");
			File xml = new File(Environment.getExternalStorageDirectory()
					+ "/atividades.xml");
			ativis = (List<Atividade>) x.fromXML(xml);
		} else {
			String url = URL.replace("{tipo}", service);
			InputStream in = HttpHelper.doPost(url, ENCODING);
			if (in != null) {
				String xml = IOUtils.toString(in, ENCODING);
				ativis = (List<Atividade>) x.fromXML(xml);
			}
		}
		return ativis;
	}

	public static List<Bairro> getBairros(Context context, String service)
			throws IOException, SAXException {
		XStream x = new XStream(new DomDriver(ENCODING));
		List<Bairro> bairros = new ArrayList<Bairro>();
		if (!AndroidUtils.isNetworkAvailable(context)) {
			System.out.println("VAI LER DO XML");
			File xml = new File(Environment.getExternalStorageDirectory()
					+ "/bairros.xml");
			bairros = (List<Bairro>) x.fromXML(xml);
		} else {
			String url = URL.replace("{tipo}", service);
			InputStream in = HttpHelper.doPost(url, ENCODING);
			if (in != null) {
				String xml = IOUtils.toString(in, ENCODING);
				bairros = (List<Bairro>) x.fromXML(xml);
			}
		}
		return bairros;
	}

	public static List<Empresa> getEmpresas(Context context, String service,
			String atividadeId, String cidadeId) throws IOException {
		XStream x = new XStream(new DomDriver(ENCODING));
		String url = URL.replace("{tipo}", service);
		List<Empresa> empresas = new ArrayList<Empresa>();
		if (!AndroidUtils.isNetworkAvailable(context)) {
			System.out.println("VAI LER DO XML");
			File xml = new File(Environment.getExternalStorageDirectory()
					+ "/empresas.xml");
			empresas = (List<Empresa>) x.fromXML(xml);
			List<Empresa> novaliSta = new ArrayList<Empresa>();
			for (Empresa e : empresas) {
				if (String.valueOf(e.getId_atividade()).equals(atividadeId)) {
					novaliSta.add(e);
				}
			}
			empresas = null;
			return novaliSta;
		} else {
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("atividadeId", atividadeId));
			params.add(new BasicNameValuePair("cidadeId", atividadeId));
			InputStream in = HttpHelper.doPostComParam(url, params);
			String xml = IOUtils.toString(in, ENCODING);
			x.alias("Empresas", List.class);
			x.alias("Empresa", Empresa.class);
			empresas = (List<Empresa>) x.fromXML(xml);
		}
		return empresas;
	}

	public Address pegaEndereco() {
		LocationManager man = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location mylocation = man
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		return null;
	}

	public static boolean baixaArquivosXml(Context context) {
		try {
			String caminho = Environment.getExternalStorageDirectory()
					.getPath();
			new FtpService().baixaArquivos("../xmlAndroid", caminho,
					FTPClient.ASCII_FILE_TYPE, FTPClient.ASCII_FILE_TYPE);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
