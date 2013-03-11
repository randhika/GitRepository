package br.livroandroid.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpHelper {
	private static final String TAG = "Http";
	public static boolean LOG_ON = true;

	public static String doGet(String url, String charset) throws IOException {
		if (LOG_ON) {
			Log.d(TAG, ">> Http doGet " + url);
		}
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.connect();
		InputStream in = conn.getInputStream();
		if (in != null) {
			String s = IOUtils.toString(in, charset);
			in.close();
			if (LOG_ON) {
				Log.d(TAG, "<< HTTPdoGet: " + s);
			}
			conn.disconnect();
			return s;
		} else
			return null;
	}

	public static InputStream doPostComParam(String url,
			List<? extends NameValuePair> parametrosPost) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(parametrosPost));
			HttpResponse response = client.execute(post);
			return response.getEntity().getContent();
		} catch (Exception e) {
			Log.e("ERRO", "Erro doPost", e);
		}
		return null;
	}

	/**
	 * Metodo que utiliza um Singleton HttpCLient - indicado chamadas frequentes
	 * e/ou multithreading
	 * 
	 * @param client
	 *            - HttpClient instance
	 * @param url
	 * @param parametrosPost
	 *            - parametros
	 * @return o conteudo do response
	 */
	public static InputStream doPostComParam(HttpClient client, String url,
			List<? extends NameValuePair> parametrosPost) {
		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(parametrosPost));
			HttpResponse response = client.execute(post);
			return response.getEntity().getContent();
		} catch (Exception e) {
			Log.e("ERRO", "Erro doPost", e);
		}
		return null;
	}

	/**
	 * Metodo POST MultiPart para envio de parametros e arquivos
	 * 
	 * @param client
	 * @param url
	 * @param parametrosPost
	 * @param arquivo
	 * @return
	 */
	public static InputStream doPostComParamMultiPart(HttpClient client,
			String url, MultipartEntity multipart, FileInputStream is,
			String arquivo) {
		try {
			HttpPost post = new HttpPost(url);
			byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
			InputStreamBody isb = new InputStreamBody(new ByteArrayInputStream(
					bytes), arquivo);
			multipart.addPart("foto", isb);
			post.setEntity(multipart);
			HttpResponse response = client.execute(post);
			return response.getEntity().getContent();
		} catch (Exception e) {
			Log.e("ERRO", "Erro doPost", e);
		}
		return null;
	}

	public static InputStream doPost(String url, String charset) {
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			InputStream in = conn.getInputStream();
			return in != null ? in : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String streamToString(InputStream in, String charset) {
		try {
			return IOUtils.toString(in, charset);
		} catch (Exception e) {
			Log.e("ERRO", "erro na conversao do inputStream", e);
		}
		return null;
	}
}
