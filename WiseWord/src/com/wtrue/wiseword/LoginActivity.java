package com.wtrue.wiseword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.wtrue.bean.WWUser;
import com.wtrue.netmonitor.NetUtils.NetType;
import com.wtrue.utils.BmobUtils;

public class LoginActivity extends BaseActivity implements OnClickListener{
	/**
	 * 账号、密码输入框
	 */
	private EditText username_et,password_et;
	/**
	 * 登陆按钮
	 */
	private Button login_bt;
	private String lUserName,lPassWord;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		initView();
	}
	private void initView(){
		username_et = (EditText) this.findViewById(R.id.username_et);
		password_et = (EditText) this.findViewById(R.id.password_et);
		login_bt = (Button) this.findViewById(R.id.login_bt);
		login_bt.setOnClickListener(this);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onNetworkConnected(NetType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onNetworkDisConnected() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.login_bt://登陆按钮
			lUserName = username_et.getText().toString().trim();
			lPassWord = password_et.getText().toString().trim();
			if(lUserName.equals("")||lUserName == null){
				Toast.makeText(LoginActivity.this, "请正确输入账号...", Toast.LENGTH_SHORT).show();
				return;
			}
			if(lPassWord.equals("")||lPassWord == null){
				Toast.makeText(LoginActivity.this, "请正确输入密码...", Toast.LENGTH_SHORT).show();
				return;
			}
			BmobUtils.userLogin(lUserName, lPassWord, new SaveListener<WWUser>() {
				
				@Override
				public void done(WWUser arg0, BmobException arg1) {
					// TODO Auto-generated method stub
					if(arg1 == null){//登陆成功
						WWUser user = BmobUser.getCurrentUser(WWUser.class);//获取登录成功后的本地用户信息
						Log.d("登陆成功", user.toString());
						startActivity(new Intent(LoginActivity.this,MainActivity.class));
						Toast.makeText(LoginActivity.this, "登陆成功...", Toast.LENGTH_SHORT).show();
					}else{//登陆失败
						Log.e("登陆失败", arg1.toString()+"");
					}
				}
			});
			break;

		default:
			break;
		}
	}

}
