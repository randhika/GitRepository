package com.android.findme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.transacao.TransacaoTask;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.findme.model.Location;
import com.findme.model.Usuario;

public class LoginActivity extends FindMeAppActivity implements Transacao{
	
	private SharedPreferences myprefs;
	private UiLifecycleHelper uiHelper;
	private Session session;
	private Usuario app_user;
	private Handler handler = new Handler();
	
	private StatusCallback statusCallBackListener = new StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			Log.i(LOG_TAG, "chamando call!");
			if(session != null && session.isOpened()){
				Log.i(LOG_TAG, "sessao Aberta()!");
				LoginActivity.this.session = session;
				if(state.isOpened()){
					Log.i(LOG_TAG, "LOGADO!");
					LoginActivity.this.startTransacao(new TransacaoTask(LoginActivity.this, LoginActivity.this, R.string.logando));
				}
			}
			if(exception != null){
				final AlertDialog alert = new AlertDialog.Builder(LoginActivity.this).create();
				alert.setButton(AlertDialog.BUTTON_NEUTRAL,  "Fechar",
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						alert.dismiss();
					}
				});
				alert.setMessage( "Não foi Possível logar no Facebook >> " + exception.getMessage());
				alert.setTitle("ERRO");
				alert.show();
				Log.e(LOG_TAG, "ERRO", exception);
			}
		}
	};

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this,statusCallBackListener);
		uiHelper.onCreate(savedInstanceState);
		myprefs = getSharedPreferences("user", MODE_PRIVATE);
		verificaLogin();
		setContentView(R.layout.layout_user_profile);
		setTitleColor(getResources().getColor(R.color.white));
	}

	private void verificaLogin(){
		Log.i(LOG_TAG, "verificaLogin()!");
		if(getSharedPreferences("user", MODE_PRIVATE).getString("id",null) != null){
			Log.i(LOG_TAG, "Login Confirmado!");
			handler.post(new Thread(new Runnable() {
				@Override
				public void run() {
					Thread t = new Thread(new Runnable() {
						
						@Override
						public void run() {
							connectXMPP();
						};
					});
					t.start();
				}
			}));
			startActivity(new Intent(getApplicationContext(), Dashboard.class));
			finish();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
		Log.i(LOG_TAG, "chamando Resume()!");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		uiHelper.onPause();
		Log.i(LOG_TAG, "chamando OnPause()!");
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
		Log.i(LOG_TAG, "chamando OnDestroy()!");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void atualizaView() {
		Intent intent = new Intent(getApplicationContext(), Dashboard.class);
		intent.putExtra("app_user", app_user);
		startActivity(intent);
		finish();
	}

	@Override
	public void executar() throws Exception {
		Request.executeAndWait(Request.newMeRequest(session, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if(user != null){
					if(user != null){
						app_user = new Usuario(null, user.getUsername(), user.getId(), user.getInnerJSONObject().optString("gender"),
								null);
						Log.i(LOG_TAG, "User found");
						if(user.getLocation()!=null){
							Log.i(LOG_TAG, user.getLocation().getStreet());
							Location location = new Location(null, user.getLocation().getLatitude(), user.getLocation().getLongitude(), user.getLocation().getStreet());
							app_user.setLocation(location);
						}
						Editor editor = myprefs.edit();
						editor.putString("id", user.getId());
						editor.putString("username", user.getUsername());
						editor.putString("name", user.getName());
						editor.putString("sexo", user.getInnerJSONObject().optString("gender"));
						editor.commit();
					}
				}
			}
		}));
	}
	
}
