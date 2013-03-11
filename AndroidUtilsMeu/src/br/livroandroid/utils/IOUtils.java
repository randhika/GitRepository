package br.livroandroid.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONStringer;

import android.util.Log;

public class IOUtils {

	public static final String TAG = "IOUtils";

	public static String toString(InputStream in, String charset)
			throws IOException {
		byte[] bytes = toBytes(in);
		String texto = new String(bytes, charset);
		return texto;
	}

	public static String toJson(Object object) throws JSONException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Method[] metodos = object.getClass().getDeclaredMethods();
		JSONStringer json = new JSONStringer();
		json.object();
		for (Method m : metodos) {
			if (m.getName().startsWith("get")) {
				json.key(m.getName().substring(3)).value(
						m.invoke(object, new Object[0]));
			}
		}
		json.endObject();
		return json.toString();
	}

	private static byte[] toBytes(InputStream in) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int len;
		try {
			while ((len = in.read(bytes)) > 0) {
				bos.write(bytes, 0, len);
			}
			byte[] bytes2 = bos.toByteArray();
			return bytes2;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} finally {
			try {
				bos.close();
				in.close();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}
		}
	}
	public static Date stringToDate(String format, String data){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sd = new SimpleDateFormat(format);
		try {
			c.setTime(sd.parse(data));
		} catch (ParseException e) {
			Log.e(TAG, "erro de parsing string to date", e);
		}
		return c.getTime();
	}
	public static String dateToString(String format, Date data){
		SimpleDateFormat sd = new SimpleDateFormat(format);
		return sd.format(data);
	}
}
