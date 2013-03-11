package com.android.findme;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import br.livroandroid.transacao.Transacao;
import br.livroandroid.transacao.TransacaoTask;
import br.livroandroid.utils.MediaFileUtils;

import com.android.findme.interfaces.FindMeFbUser;
import com.android.findme.util.SystemUiHider;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class LoginActivity extends FindMeAppActivity implements Transacao{
	
	private Session fbsession;
	private FindMeFbUser user;
	private List<GraphUser> friends;
	private TextView tv_nome;
	private Button confirm_btn;
	private LoginButton fbButton;
	private ProfilePictureView profileView;
	private ImageView iv_newphoto;
	private SharedPreferences myprefs;
	private UiLifecycleHelper uiHelper;
	
	private StatusCallback statusCallBackListener = new StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			Log.i(LOG_TAG, "chamando call!");
			if(session != null && session.isOpened()){
				fbsession = session;
				Log.i(LOG_TAG, "sessao Aberta()!");
				if(state.isOpened()){
					Log.i(LOG_TAG, "LOGADO!");
					startTransacao(new TransacaoTask(LoginActivity.this, LoginActivity.this, R.string.com_facebook_loading));
				}
			}
			if(exception != null){
				Toast.makeText(getApplicationContext(), "Não foi Possível logar no Facebook >> " + exception.getMessage(), Toast.LENGTH_LONG).show();
				confirm_btn.setVisibility(Button.VISIBLE);
				Log.e(LOG_TAG, "ERRO", exception);
			}
		}
	};
	public FindMeFbUser getUser() {
		return user;
	}

	public void setUser(FindMeFbUser user) {
		this.user = user;
	}

	public List<GraphUser> getFriends() {
		return friends;
	}

	public void setFriends(List<GraphUser> friends) {
		this.friends = friends;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this,statusCallBackListener);
		uiHelper.onCreate(savedInstanceState);
		verificaConfirm();
		setContentView(R.layout.layout_user_profile);
		myprefs = getSharedPreferences("user", MODE_PRIVATE);
		tv_nome = (TextView) findViewById(R.id.tv_profile_name);
		profileView = (ProfilePictureView) findViewById(R.id.iv_profile_photo);
		iv_newphoto = (ImageView) findViewById(R.id.iv_findme_photo);
		confirm_btn = (Button) findViewById(R.id.bt_confirm_profile);
		fbButton = (LoginButton) findViewById(R.id.fb_button);
		confirm_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), Dashboard.class);
				myprefs.edit().putBoolean("confirmado", true).commit();
				startActivity(i);
				finish();
			}
		});
	}

	public void startTransacao(TransacaoTask task){
		task.execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login_menu, menu);
		menu.findItem(R.id.logout).setVisible(false);
		return true;
	}
	
	@Override
	public void executar() throws Exception {
		Log.i(LOG_TAG, "Executando a asyncTask");
		Request.executeAndWait(Request.newMeRequest(fbsession, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if(user != null){
					Log.i(LOG_TAG, "User found");
					setUser(user.cast(FindMeFbUser.class));
				}
			}
		}));
	}
	
	@Override
	public void atualizaView() {
		Log.i(LOG_TAG, "Atualizando a view!");
		if(user != null){
			tv_nome.setText("Olá " + user.getName() + ", comece a procurar gente interessante e perto de você!");
			tv_nome.invalidate();
			tv_nome.setShadowLayer(1.5F, 1.5F, 1.5F, R.color.com_facebook_usersettingsfragment_connected_text_color);
			profileView.setCropped(true);
			profileView.setProfileId(user.getId());
			Editor editor = myprefs.edit();
			editor.putString("username", user.getUsername());
			editor.putString("name", user.getName());
			editor.putString("id", user.getId());
			editor.commit();
			fbButton.setVisibility(LoginButton.INVISIBLE);
			fbButton.invalidate();
			confirm_btn.setVisibility(Button.VISIBLE);
			confirm_btn.invalidate();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		if(requestCode == MediaFileUtils.TAKE_PICTURE && resultCode == RESULT_OK){
			String arquivo =myprefs.getString("foto", null);
			trocaImagens(profileView, iv_newphoto, arquivo);
		}else if(requestCode == MediaFileUtils.TAKE_GALLERY && resultCode == RESULT_OK){
			trocaImagens(profileView, iv_newphoto, getPathFileSelected(data));
		}
	}
	
	private void verificaConfirm(){
		Log.i(LOG_TAG, "verificaConfirm()!");
		if(getSharedPreferences("user", MODE_PRIVATE).getBoolean("confirmado",false)){
			Log.i(LOG_TAG, "Login Confirmado!");
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
	
}
