package com.wtrue.wiseword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.wtrue.bean.WWUser;
import com.wtrue.netmonitor.NetUtils.NetType;
import com.wture.utlis.BmobUtlis;

/**
 * 注册界面
 * @author Ben
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{
	/**
	 * 账号和密码输入框
	 */
	private EditText username_et,password_et;
	private Button register_bt;
	private String rUserName;
	private String rPassWord;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		initView();
	}
	private void initView(){
		username_et = (EditText) this.findViewById(R.id.username_et);
		password_et = (EditText) this.findViewById(R.id.password_et);
		register_bt = (Button) this.findViewById(R.id.register_bt);
		register_bt.setOnClickListener(this);
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
		case R.id.register_bt://注册按钮
			rUserName = username_et.getText().toString().trim();
			rPassWord = password_et.getText().toString().trim();
			if(rUserName.equals("")||rUserName==null){
				Toast.makeText(RegisterActivity.this, "请正确输入账号...", Toast.LENGTH_SHORT).show();
				return;
			}
			if(rPassWord.equals("")||rPassWord.trim()==null){
				Toast.makeText(RegisterActivity.this, "请正确输入密码...", Toast.LENGTH_SHORT).show();
				return;
			}
			BmobUtlis.userRegister(rUserName, rPassWord, new SaveListener<WWUser>() {
				
				@Override
				public void done(WWUser arg0, BmobException arg1) {
					// TODO Auto-generated method stub
					if(arg1 == null){
						Toast.makeText(RegisterActivity.this, "注册成功...:"+arg0.toString(), Toast.LENGTH_SHORT).show();
						Log.e("注册成功", arg0.toString()+"");
					}else{
						if(arg1.getErrorCode() == 202){//错误code如果是202的话，那么就是用户输入的账号已经注册过了。
							Toast.makeText(RegisterActivity.this, "此账号已经注册，请重新输入账号...", Toast.LENGTH_SHORT).show();
							return;
						}
					}
				}
			});
			break;
		}
		
	}

}
