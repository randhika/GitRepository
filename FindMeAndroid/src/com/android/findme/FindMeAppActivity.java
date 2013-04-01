package com.android.findme;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import br.livroandroid.transacao.TransacaoTask;
import br.livroandroid.utils.MediaFileUtils;

import com.android.findme.listenners.ChatNoLocalMessageListenner;
import com.facebook.Session;
import com.facebook.widget.ProfilePictureView;
import com.findme.model.Usuario;

public class FindMeAppActivity extends Activity {
	public static String LOG_TAG = "FINDME";
	private SharedPreferences myprefs;
	public static final int TIRA_FOTO = 1002;
	public static final int FOTO_GALERIA = 1003;
	public static final int NOTIFICATION_ID = 1004;
	protected static XMPPConnection connection;
	private static final String HOST = "192.168.1.2";
	private static final int PORT = 5222;
	public static final String APP_DIR = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
			+ "/FindMe";
	public static final String SUFIX_SMACK = "@" + HOST + "/findme";

	public void startTransacao(TransacaoTask task) {
		task.execute();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myprefs = getSharedPreferences("user", MODE_PRIVATE);
	}

	public void trocaImagens(View invisibleView, ImageView visibleView,
			String filePath) {
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		visibleView.setImageBitmap(bitmap);
		if (bitmap != null) {
			Log.i(LOG_TAG, "Image Found!");
			invisibleView.setVisibility(View.INVISIBLE);
			invisibleView.invalidate();
			visibleView.setVisibility(ImageView.VISIBLE);
			visibleView.invalidate();
		}
	}

	/**
	 * MENU ONCLICK METHODS
	 */

	public void newPhoto(MenuItem menu) {
		Editor editor = myprefs.edit();
		editor.putString("foto",
				MediaFileUtils.takePicture(this, myprefs.getString("id", null)));
		editor.commit();
	}

	public void logOut(MenuItem menu) {
		Log.i(LOG_TAG, "Logging Out!");
		final AlertDialog logout = new AlertDialog.Builder(this)
				.setTitle("Log Out")
				.setMessage(
						myprefs.getString("name", null)
								+ ", deseja realmente sair?").create();
		logout.setIcon(R.drawable.alert_48);
		logout.setButton(AlertDialog.BUTTON_POSITIVE, "Sim",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							if (connection != null && connection.isConnected()) {
								connection.disconnect();
							}
							Session.getActiveSession()
									.closeAndClearTokenInformation();
							if (Session.getActiveSession() == null
									|| Session.getActiveSession().isClosed()) {
								Log.i(LOG_TAG, "Session Closed!");
								myprefs.edit().clear().commit();
								if (myprefs.getString("id", null) == null) {
									startActivity(new Intent(
											getApplicationContext(),
											LoginActivity.class));
									finish();
								}
							}
						} catch (Exception e) {
							Log.e(LOG_TAG, "Erro Logout", e);
						}

					}
				});
		logout.setButton(AlertDialog.BUTTON_NEGATIVE, "Não",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						logout.cancel();
					}
				});
		logout.show();
	}

	public void selecionaGaleria(MenuItem item) {
		MediaFileUtils.gallerySearch(this, "image/*");
	}

	public String getPathFileSelected(Intent data) {
		Uri imagemSelecionada = data.getData();
		Log.i(LOG_TAG, "URI selecionado >>>" + imagemSelecionada);
		String[] colunaFilePath = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(imagemSelecionada,
				colunaFilePath, null, null, null);
		cursor.moveToFirst();
		int indexColuna = cursor.getColumnIndex(colunaFilePath[0]);
		String arquivo = cursor.getString(indexColuna);
		Log.i(LOG_TAG, "Arquivo selecionado >>>" + arquivo);
		myprefs.edit().putString("foto", arquivo).commit();
		return arquivo;
	}

	// public static String takePicture(Activity activity, String id){
	// Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	// File caminho = MediaFileUtils.getFile(MediaFileUtils.IMAGE_TYPE,id);
	// Uri uri = Uri.fromFile(caminho);
	// camera.putExtra(MediaStore.EXTRA_OUTPUT,uri);
	// activity.startActivityForResult(camera, TIRA_FOTO);
	// return caminho.getPath();
	// }

	public void connectXMPP() {
		ConnectionConfiguration configuration = new ConnectionConfiguration(
				HOST, PORT);
		connection = new XMPPConnection(configuration);
		try {
			connection.connect();
			if (connection != null) {
				Log.i(LOG_TAG, "Connected ? " + connection.isConnected());
				SharedPreferences mysprefs = getSharedPreferences("user",
						MODE_PRIVATE);
				// seta a aceitacao automatica para o usuario inicialmente
				connection.getRoster().setSubscriptionMode(
						SubscriptionMode.accept_all);
				// recupera os dados do usuario e tenta logar
				String username = mysprefs.getString("username", null);
				String senha = "1234";
				try {
					Log.i(LOG_TAG, "Logging XMPP");
					connection.login(username, senha);
				} catch (XMPPException e) {
					Log.e(LOG_TAG,
							"UsuarioXMPP não encontrado, criando conta...");
					Map<String, String> att = new HashMap<String, String>();
					att.put("name", mysprefs.getString("name", null));
					try {
						connection.getAccountManager().createAccount(username,
								senha, att);
						Log.i(LOG_TAG, "Conta XMPP criada");
					} catch (XMPPException acc_exc) {
						Log.e(LOG_TAG, "UsuarioXMPP não criado!", acc_exc);
					}
				}
				setUserPresence();
				Log.i(LOG_TAG, "XMPP log success!");
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "XMPP Connection ERROR", e);
			connection = null;
		}
	}

	public void setUserProfilePicture(ProfilePictureView fb_prof_pic,
			ImageView iv_prof, Usuario user) {
		if (user.getPicturePath() == null || user.getPicturePath().equals("")) {
			fb_prof_pic.setCropped(true);
			fb_prof_pic.setProfileId(user.getFacebookId());
		} else {
			trocaImagens(fb_prof_pic, iv_prof, user.getPicturePath());
		}
	}

	public void setUserPresence() throws XMPPException {
		if (connection != null) {
			Presence presence = new Presence(Type.available);
			presence.setStatus("Tô na pista!");
			connection.sendPacket(presence);
		} else
			throw new XMPPException("Não Conectado!");
	}

	public void setChatListenner(Usuario app_user){
		if(connection != null){
			final Usuario user = app_user;
			connection.getChatManager().addChatListener(new ChatManagerListener() {
				@Override
				public void chatCreated(Chat chat, boolean createdLocally) {
					if(!createdLocally){
						Log.i(LOG_TAG, "Chat created! " + chat.getParticipant());
						chat.addMessageListener(new ChatNoLocalMessageListenner(user, FindMeAppActivity.this));
					}
				};
			});
		}else{
			Log.i(LOG_TAG, "XMPPConnection NULL");
		}
	}

	public void printHash() {
		// Add code to print out the key hash
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.danilo.findme", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
